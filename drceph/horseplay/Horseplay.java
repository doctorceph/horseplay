package drceph.horseplay;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemReed;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler; // used in 1.6.2
//import cpw.mods.fml.common.Mod.PreInit;    // used in 1.5.2
//import cpw.mods.fml.common.Mod.Init;       // used in 1.5.2
//import cpw.mods.fml.common.Mod.PostInit;   // used in 1.5.2
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import drceph.horseplay.client.HorseplayBlockRenderer;

@Mod(modid="drceph.horseplay", name="Horseplay", version="0.001")
@NetworkMod(clientSideRequired=true)
public class Horseplay {

	// The instance of your mod that Forge uses.
	@Instance(value = "drceph.horseplay")
	public static Horseplay instance;

	// Says where the client and server 'proxy' code is loaded. 1600 for block
	@SidedProxy(clientSide="drceph.horseplay.client.ClientProxy", serverSide="drceph.horseplay.CommonProxy")
	public static CommonProxy proxy;

	//VARIABLES
	private boolean useSteel = true;
	private int lightTannedLeatherId = 8944;
	private int wellTannedLeatherId = 8945;
	private int reinforcedTannedLeatherId = 8946;
	private int horseProfilerId = 8947;
	private int leatherTanneryItemId = 8948;
	private int leatherTanneryId = 1600;
	
	//Item for access
	public static Item lightTannedLeather;
	public static Item wellTannedLeather;
	public static Item reinforcedTannedLeather;
	public static Item horseProfiler;
	public static Item leatherTanneryItem;
	public static Block leatherTannery;
	
	@EventHandler // used in 1.6.2
	//@PreInit    // used in 1.5.2
	public void preInit(FMLPreInitializationEvent event) {
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		useSteel = config.get(Configuration.CATEGORY_GENERAL,"use_ingotSteel",true).getBoolean(true);
		
		lightTannedLeatherId = config.getItem(Configuration.CATEGORY_ITEM, "lightTannedLeather", 8944).getInt(8944);
		wellTannedLeatherId = config.getItem(Configuration.CATEGORY_ITEM, "wellTannedLeather", 8945).getInt(8945);
		reinforcedTannedLeatherId = config.getItem(Configuration.CATEGORY_ITEM, "reinforcedTannedLeather", 8946).getInt(8946);
		horseProfilerId = config.getItem(Configuration.CATEGORY_ITEM, "horseProfiler", 8947).getInt(8947);
		leatherTanneryId = config.getBlock(Configuration.CATEGORY_BLOCK, "leatherTannery", 1600).getInt(1600);
		leatherTanneryItemId = config.getItem(Configuration.CATEGORY_ITEM, "leatherTanneryItem", 8948).getInt(8948);
		
		lightTannedLeather = new ItemProcessedLeather(lightTannedLeatherId, "lightTannedLeather");
		wellTannedLeather = new ItemProcessedLeather(wellTannedLeatherId,"wellTannedLeather");
		reinforcedTannedLeather = new ItemProcessedLeather(reinforcedTannedLeatherId,"reinforcedTannedLeather");
		
		horseProfiler = new ItemHorseProfiler(horseProfilerId,"horseProfiler");
	
		GameRegistry.registerItem(lightTannedLeather, "lightTannedLeather");
		GameRegistry.registerItem(wellTannedLeather, "wellTannedLeather");
		GameRegistry.registerItem(reinforcedTannedLeather, "reinforcedTannedLeather");
		GameRegistry.registerItem(horseProfiler,"horseProfiler");

		HorseplayBlockRenderer hbr = new HorseplayBlockRenderer(RenderingRegistry.getNextAvailableRenderId());
		RenderingRegistry.registerBlockHandler(hbr);
		
		leatherTannery = new BlockTannery(leatherTanneryId,hbr.getRenderId());		
		GameRegistry.registerBlock(leatherTannery, "leatherTannery");
		
		leatherTanneryItem = (new ItemReed(leatherTanneryItemId, leatherTannery)).setUnlocalizedName("leatherTannery").setCreativeTab(CreativeTabs.tabBlock).setTextureName("Horseplay:tannery");
		GameRegistry.registerItem(leatherTanneryItem, "leatherTanneryItem");
	}

	@EventHandler // used in 1.6.2
	//@Init       // used in 1.5.2
	public void load(FMLInitializationEvent event) {
		
		
		
		//add leather intermediaries 
		LanguageRegistry.addName(lightTannedLeather, "Light Tanned Leather");
		LanguageRegistry.addName(wellTannedLeather, "Well Tanned Leather");
		LanguageRegistry.addName(reinforcedTannedLeather, "Reinforced Leather");
		LanguageRegistry.addName(horseProfiler, "Horse Profiler");
		LanguageRegistry.addName(leatherTannery, "Leather Tannery");
		LanguageRegistry.addName(leatherTanneryItem, "Leather Tannery");
		
		//Processing recipes
	    // SMELTING RECIPES ARE TEMPORARY!!!!!!
		GameRegistry.addSmelting(Item.leather.itemID,new ItemStack(lightTannedLeather),0.0f);
		GameRegistry.addSmelting(lightTannedLeather.itemID,new ItemStack(wellTannedLeather),0.0f);
		//END TEMPORARY
		
		GameRegistry.addRecipe(new ItemStack(reinforcedTannedLeather),
				"yxy","xxx","yxy",
				'x',new ItemStack(wellTannedLeather),
				'y', new ItemStack(Item.silk));
		
		//Saddle recipe, use Steel if it exists and is in config, else use iron	
		boolean steelExists = false;
		for (String ore : OreDictionary.getOreNames()) {
			if (ore.equals("ingotSteel")) steelExists = true;
		}
		
		if (useSteel && steelExists) {
			GameRegistry.addRecipe(new ShapedOreRecipe(Item.saddle, true, new Object[]{
						"xxx","xyx","z z",
						Character.valueOf('x'),new ItemStack(reinforcedTannedLeather),
						Character.valueOf('y'),new ItemStack(Block.carpet),
						Character.valueOf('z'),"ingotSteel"}));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(horseProfiler, true, new Object[]{
					"aba","aba","cdc",
					Character.valueOf('a'),"ingotSteel",
					Character.valueOf('b'),new ItemStack(Block.thinGlass),
					Character.valueOf('c'),new ItemStack(Item.redstone),
					Character.valueOf('d'),new ItemStack(Item.diamond)}));
			
		} else {
			GameRegistry.addRecipe(new ItemStack(Item.saddle),
					"xxx","xyx","z z",
					'x',new ItemStack(reinforcedTannedLeather),
					'y',new ItemStack(Block.carpet),
					'z',new ItemStack(Item.ingotIron));
			
			GameRegistry.addRecipe(new ItemStack(horseProfiler),
					"aba","aba","cdc",
					'a',new ItemStack(Item.ingotIron),
					'b',new ItemStack(Block.thinGlass),
					'c',new ItemStack(Item.redstone),
					'd',new ItemStack(Item.diamond));
		}
		
		//Add tanned leather items
		proxy.registerRenderers();
	}

	@EventHandler // used in 1.6.2
	//@PostInit   // used in 1.5.2
	public void postInit(FMLPostInitializationEvent event) {
		// Stub Method
	}
}