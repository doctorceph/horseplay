package drceph.horseplay;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
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
	
	public static CraftingHandler craftHandler = new CraftingHandler();

	//VARIABLES
	private boolean useSteel = true;
	private boolean useDust = true;
	private int lightTannedLeatherId = 8944;
	private int wellTannedLeatherId = 8945;
	private int reinforcedTannedLeatherId = 8946;
	private int horseProfilerId = 8947;
	private int leatherTanneryId = 1600;
	private int sulfuricAcidBucketId = 8949;
	//private int sulfuricAcidId = 1601;
	
	//Item for access
	public static Item lightTannedLeather;
	public static Item wellTannedLeather;
	public static Item reinforcedTannedLeather;
	public static Item horseProfiler;
	public static Block leatherTannery;
	public static Fluid sulfuricAcid;
	public static Item sulfuricAcidBucket;
	//public static Block sulfuricAcidBlock;
	
	@EventHandler // used in 1.6.2
	//@PreInit    // used in 1.5.2
	public void preInit(FMLPreInitializationEvent event) {
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		useSteel = config.get(Configuration.CATEGORY_GENERAL,"use_ingotSteel",true).getBoolean(true);
		useDust = config.get(Configuration.CATEGORY_GENERAL,"use_dustForSulfuric",true).getBoolean(true);
		
		lightTannedLeatherId = config.getItem(Configuration.CATEGORY_ITEM, "lightTannedLeather", 8944).getInt(8944);
		wellTannedLeatherId = config.getItem(Configuration.CATEGORY_ITEM, "wellTannedLeather", 8945).getInt(8945);
		reinforcedTannedLeatherId = config.getItem(Configuration.CATEGORY_ITEM, "reinforcedTannedLeather", 8946).getInt(8946);
		horseProfilerId = config.getItem(Configuration.CATEGORY_ITEM, "horseProfiler", 8947).getInt(8947);
		leatherTanneryId = config.getBlock(Configuration.CATEGORY_BLOCK, "leatherTannery", 1600).getInt(1600);
		sulfuricAcidBucketId = config.getItem(Configuration.CATEGORY_ITEM, "sulfuricAcidBucket", 8949).getInt(8949);
		
		//ITEM CREATION
		lightTannedLeather = new ItemProcessedLeather(lightTannedLeatherId, "lightTannedLeather");
		wellTannedLeather = new ItemProcessedLeather(wellTannedLeatherId,"wellTannedLeather");
		reinforcedTannedLeather = new ItemProcessedLeather(reinforcedTannedLeatherId,"reinforcedTannedLeather");//currently no use
		horseProfiler = new ItemHorseProfiler(horseProfilerId,"horseProfiler");

		GameRegistry.registerItem(lightTannedLeather, "lightTannedLeather");
		GameRegistry.registerItem(wellTannedLeather, "wellTannedLeather");
		GameRegistry.registerItem(reinforcedTannedLeather, "reinforcedTannedLeather");//currently no use
		GameRegistry.registerItem(horseProfiler,"horseProfiler");

		HorseplayBlockRenderer hbr = new HorseplayBlockRenderer(RenderingRegistry.getNextAvailableRenderId());
		RenderingRegistry.registerBlockHandler(hbr);
		
		leatherTannery = new BlockTannery(leatherTanneryId,hbr.getRenderId());
		
		GameRegistry.registerBlock(leatherTannery, ItemBlockTannery.class, "leatherTannery");
		
		GameRegistry.registerTileEntity(drceph.horseplay.TileEntityTannery.class, "tileEntityTannery");
		
		//LIQUID CREATION
		sulfuricAcid = new Fluid("sulfuric");
		FluidRegistry.registerFluid(sulfuricAcid);
		sulfuricAcid.setUnlocalizedName("sulfuric");
		
		sulfuricAcidBucket = new ItemBucketSulfuricAcid(sulfuricAcidBucketId, FluidRegistry.getFluidID("sulfuric"));
		GameRegistry.registerItem(sulfuricAcidBucket, "sulfuricAcidBucket");
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("sulfuric", FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(sulfuricAcidBucket), new ItemStack(Item.bucketEmpty));
		
		
	}
	
	@ForgeSubscribe
	public void postStitch(TextureStitchEvent.Post event)
	{
	    sulfuricAcid.setIcons(BlockTannery.getTanneryIcon("sulfuricTop"), BlockTannery.getTanneryIcon("sulfuricSide"));
		
	}

	@EventHandler // used in 1.6.2
	//@Init       // used in 1.5.2
	public void load(FMLInitializationEvent event) {
		
		//addnames
		LanguageRegistry.addName(lightTannedLeather, "Light Tanned Leather");
		LanguageRegistry.addName(wellTannedLeather, "Well Tanned Leather");
		LanguageRegistry.addName(reinforcedTannedLeather, "Reinforced Leather");
		LanguageRegistry.addName(horseProfiler, "Horse Profiler");
		LanguageRegistry.addName(leatherTannery, "Leather Tannery");
		LanguageRegistry.addName(sulfuricAcidBucket,"Sulfuric Acid Bucket");
		
		//Liquid Handling
		if (FluidRegistry.isFluidRegistered("sulfuric")) {
			new TanneryLiquidReagent(FluidRegistry.getFluidStack("sulfuric", FluidContainerRegistry.BUCKET_VOLUME), 1, 0);
		}
		
		if (FluidRegistry.isFluidRegistered("juice")) {
			new TanneryLiquidReagent(FluidRegistry.getFluidStack("juice", FluidContainerRegistry.BUCKET_VOLUME), 2, 1);
		}
		
		//Processing recipes
		
		TanneryRecipe.registerRecipe(
				new FluidStack(FluidRegistry.getFluid("sulfuric"),FluidContainerRegistry.BUCKET_VOLUME), 
				new ItemStack(Item.leather), new ItemStack(Horseplay.wellTannedLeather));
		TanneryRecipe.registerRecipe(
				new FluidStack(FluidRegistry.getFluid("sulfuric"),FluidContainerRegistry.BUCKET_VOLUME), 
				new ItemStack(Horseplay.lightTannedLeather), new ItemStack(Horseplay.wellTannedLeather));
		
		if (FluidRegistry.isFluidRegistered("juice")) {
			TanneryRecipe.registerRecipe(
					new FluidStack(FluidRegistry.getFluid("juice"),FluidContainerRegistry.BUCKET_VOLUME), 
					new ItemStack(Item.leather), new ItemStack(Horseplay.lightTannedLeather));
			TanneryRecipe.registerRecipe(
					new FluidStack(FluidRegistry.getFluid("juice"),FluidContainerRegistry.BUCKET_VOLUME), 
					new ItemStack(Horseplay.lightTannedLeather), new ItemStack(Horseplay.wellTannedLeather));
		}
		
		/* CURRENTLY NOT USED
		GameRegistry.addRecipe(new ItemStack(reinforcedTannedLeather),
				"yxy","xxx","yxy",
				'x',new ItemStack(wellTannedLeather),
				'y', new ItemStack(Item.silk));*/
		
		//Block recipe
		GameRegistry.addRecipe(new ItemStack(leatherTannery),
				"zxz","zyz","zzz",
				'x',new ItemStack(Item.stick),
				'y',new ItemStack(Item.silk),
				'z',new ItemStack(Block.stone));
		
		//Liquid recipe
		boolean hasSulfur = false;
		boolean hasCoal = false;
		for (String ore : OreDictionary.getOreNames()) {
			if (ore.equals("dustSulfur")) hasSulfur = true;
			if (ore.equals("dustCoal")) hasCoal = true;
		}
		
		boolean dustExists = hasSulfur || hasCoal;
		
		if (useDust && dustExists) {
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(sulfuricAcidBucket), new Object[]{
				"dustSulfur","dustCoal"}));
		} else {
			ItemStack gunpowder = new ItemStack(Item.gunpowder);
			GameRegistry.addShapelessRecipe(new ItemStack(sulfuricAcidBucket),
					gunpowder, gunpowder, 
					Item.coal, Item.bucketWater);
		}
		
		
		
		//Saddle recipe, use Steel if it exists and is in config, else use iron	
		boolean steelExists = false;
		for (String ore : OreDictionary.getOreNames()) {
			if (ore.equals("ingotSteel")) steelExists = true;
		}
		
		
		
		
		if (useSteel && steelExists) {
			for (int i = 0; i < 16; i++) {
				ItemStack currentCarpet = new ItemStack(Block.carpet, 1, i);
				GameRegistry.addRecipe(new ShapedOreRecipe(Item.saddle, true, new Object[]{
							"xxx","xyx","z z",
							Character.valueOf('x'),new ItemStack(wellTannedLeather),
							Character.valueOf('y'),currentCarpet,
							Character.valueOf('z'),"ingotSteel"}));
			}
			
			GameRegistry.addRecipe(new ShapedOreRecipe(horseProfiler, true, new Object[]{
					"aba","aba","cdc",
					Character.valueOf('a'),"ingotSteel",
					Character.valueOf('b'),new ItemStack(Block.thinGlass),
					Character.valueOf('c'),new ItemStack(Item.redstone),
					Character.valueOf('d'),new ItemStack(Item.diamond)}));
			
		} else {
			for (int i = 0; i < 16; i++) {
				ItemStack currentCarpet = new ItemStack(Block.carpet, 1, i);
				GameRegistry.addRecipe(new ItemStack(Item.saddle),
						"xxx","xyx","z z",
						'x',new ItemStack(wellTannedLeather),
						'y',currentCarpet,
						'z',new ItemStack(Item.ingotIron));
				}
			GameRegistry.addRecipe(new ItemStack(horseProfiler),
					"aba","aba","cdc",
					'a',new ItemStack(Item.ingotIron),
					'b',new ItemStack(Block.thinGlass),
					'c',new ItemStack(Item.redstone),
					'd',new ItemStack(Item.diamond));
		}
		
		
		GameRegistry.registerCraftingHandler(craftHandler);
		
		proxy.registerRenderers();
		NetworkRegistry.instance().registerGuiHandler(this, proxy);
	}
	
	@EventHandler // used in 1.6.2
	//@PostInit   // used in 1.5.2
	public void postInit(FMLPostInitializationEvent event) {
		// Stub Method
		MinecraftForge.EVENT_BUS.register(this);
	}
}