package drceph.horseplay.items;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemHorseProfiler extends Item {
	
	private static String[] horseTypes = {"Horse","Donkey","Mule","Zombie Horse","Skeleton Horse"};
	
	private static String getStringVariant (int variant) {
		String[] colours = {"White","Creamy","Chestnut","Brown","Black","Gray","Dark Brown"};
		String[] markings = {"","with a white blaze","with pinto markings","with white spots","with black dapple"};
		return colours[variant%256] + " horse " +markings[variant/256];
	}
	
	private static String getHealthBin (double health) {
		if (health < 17.14) return "Lowest";
		if (health >= 17.14 && health < 19.28) return "Lower";
		if (health >= 19.28 && health < 21.42) return "Low";
		if (health >= 21.42 && health < 23.56) return "Normal";
		if (health >= 23.56 && health < 25.7) return "High";
		if (health >= 25.7 && health < 27.84) return "Higher";
		if (health >= 27.84) return "Highest";
		return "Error";
	}
	
	private static String getJumpBin (double jump) {
		if (jump < 0.4857) return "Lowest";
		if (jump >= 0.4857 && jump < 0.5714) return "Lower";
		if (jump >= 0.5714 && jump < 0.6571) return "Low";
		if (jump >= 0.6571 && jump < 0.7428) return "Normal";
		if (jump >= 0.7428 && jump < 0.8285) return "High";
		if (jump >= 0.8285 && jump < 0.9142) return "Higher";
		if (jump >= 0.9142) return "Highest";
		return "Error";
	}
	
	private static String getSpeedBin (double speed) {
		if (speed < 0.14464) return "Slowest";
		if (speed >= 0.14464 && speed < 0.17678) return "Slower";
		if (speed >= 0.17678 && speed < 0.20892) return "Slow";
		if (speed >= 0.20892 && speed < 0.24106) return "Normal";
		if (speed >= 0.24106 && speed < 0.2732) return "Fast";
		if (speed >= 0.2732 && speed < 0.30534) return "Faster";
		if (speed >= 0.30534) return "Fastest";
		return "Error";
	}

	public ItemHorseProfiler(int id, String icoName) {
			super(id);
			setMaxStackSize(1);
			setUnlocalizedName(icoName);
			setTextureName("Horseplay:"+icoName);
			setCreativeTab(CreativeTabs.tabTools);
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player,
			Entity entity) {
		// TODO Auto-generated method stub
		if (!(entity instanceof EntityHorse)) {
			return false;
		}
		
		EntityHorse horse = (EntityHorse) entity;
		double jump = horse.getHorseJumpStrength();
		double speed = horse.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
		double health = horse.getMaxHealth();
		
		boolean sugarConsumed = player.inventory.consumeInventoryItem(Item.sugar.itemID);
		
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.CLIENT) {
			EntityClientPlayerMP playerMP = (EntityClientPlayerMP) player;

			if (sugarConsumed) {
				if (horse.getHorseType() == 0) {
					playerMP.addChatMessage(getStringVariant(horse.getHorseVariant()));
				} else {
					playerMP.addChatMessage(horseTypes[horse.getHorseType()]);
				}
	
				playerMP.addChatMessage("Max health: "+getHealthBin(health));
				playerMP.addChatMessage("Run speed: "+getSpeedBin(speed));
				playerMP.addChatMessage("Jump height: "+getJumpBin(jump));
				
			} else {
				playerMP.addChatMessage("Sugar required to power Horse Profiler");
			}
		}
		
		return true;
		
	}

}
