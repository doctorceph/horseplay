package drceph.horseplay;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.entity.EntityClientPlayerMP;
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
		if (health < 18.0) return "Low";
		if (health >= 18.0 && health < 21.0) return "Medium-Low";
		if (health >= 21.0 && health < 24.0) return "Medium";
		if (health >= 24.0 && health < 27.0) return "Medium-High";
		if (health >= 27.0) return "High";
		return "Error";
	}
	
	private static String getJumpBin (double jump) {
		if (jump < 0.52) return "Low";
		if (jump >= 0.52 && jump < 0.64) return "Medium-Low";
		if (jump >= 0.64 && jump < 0.76) return "Medium";
		if (jump >= 0.76 && jump < 0.88) return "Medium-High";
		if (jump >= 0.88) return "High";
		return "Error";
	}
	
	private static String getSpeedBin (double speed) {
		if (speed < 0.1575) return "Slow";
		if (speed >= 0.1575 && speed < 0.2025) return "Medium-Slow";
		if (speed >= 0.2025 && speed < 0.2475) return "Medium";
		if (speed >= 0.2475 && speed < 0.2925) return "Medium-Fast";
		if (speed >= 0.2925) return "Fast";
		return "Error";
	}

	public ItemHorseProfiler(int id, String icoName) {
			super(id);
			setMaxStackSize(1);
			setUnlocalizedName(icoName);
			setTextureName("Horseplay:"+icoName);
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
