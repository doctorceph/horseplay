package drceph.horseplay;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

public class TileEntityTannery extends TileEntity implements ISidedInventory, IFluidTank {
	
	public static final int MAX_VOLUME = FluidContainerRegistry.BUCKET_VOLUME*2;
	public static final int SLOT_COUNT = 2;
	
	private ItemStack[] inventory;
	private int volume;
	private TanneryLiquidReagent reagent;
	
	public TileEntityTannery() {
		this.inventory = new ItemStack[SLOT_COUNT];
		volume = 0;
		reagent = null;
	}
	


	//sided inventory methods
	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inventory[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		 if (inventory[i] != null) {
             if (inventory[i].stackSize <= j) {
                     ItemStack itemstack = inventory[i];
                     inventory[i] = null;
                     onInventoryChanged();
                     return itemstack;
             }
             ItemStack itemstack1 = inventory[i].splitStack(j);
             if (inventory[i].stackSize == 0) {
                     inventory[i] = null;
             }
             onInventoryChanged();
             return itemstack1;
     } else {
             return null;
     }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		if (this.inventory[var1] != null) {
            ItemStack var2 = this.inventory[var1];
            this.inventory[var1] = null;
            return var2;
    } else {
            return null;
    }
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
                itemstack.stackSize = getInventoryStackLimit();
        }
        onInventoryChanged();
		
	}

	@Override
	public String getInvName() {
		// TODO Auto-generated method stub
		return "TileEntityTannery";
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

	@Override
	public void openChest() {
		
	}

	@Override
	public void closeChest() {
		
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO LIMIT TO LEATHER
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		int[] accessibleSlots = {0,1};
		return accessibleSlots;
	}

	//Insert any side, only into slot[0]
	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return i==0 ? true : false;
	}

	//extract any side, only from slot[1]
	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i==1 ? true : false;
	}
	
	
	//Fluid tank methods
	@Override
	public FluidStack getFluid() {
		if (reagent != null) {
			FluidStack reagentCopy = reagent.getReagent().copy();
			reagentCopy.amount = volume;
			return reagentCopy;
		}
		return null;
	}

	@Override
	public int getFluidAmount() {
		return volume;
	}

	@Override
	public int getCapacity() {
		return MAX_VOLUME;
	}

	@Override
	public FluidTankInfo getInfo() {
		FluidTankInfo fti = new FluidTankInfo(getFluid(), getCapacity());
		return null;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		// TODO Auto-generated method stub
		if (reagent == null && TanneryLiquidReagent.isValidReagent(resource.fluidID)) {
			setCurrentReagent(resource.fluidID);
		}
		
		if (reagent.getReagentId() != resource.fluidID) {
			return 0;
		}
		
		int payload = resource.amount;
		
		if (volume+payload <= MAX_VOLUME) {
			if (doFill) {
				volume += payload;
			}
			return payload;
		} else {
			int difference = MAX_VOLUME - volume;
			if (doFill) {
				volume = MAX_VOLUME;
			}
			return difference;
		}
	}
	
	

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		
		NBTTagList tagList = tagCompound.getTagList("Inventory");

        for (int i = 0; i < tagList.tagCount(); i++) {
                NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);

                byte slot = tag.getByte("Slot");

                if (slot >= 0 && slot < inventory.length) {
                        inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
                }
        }
        setCurrentReagent(tagCompound.getInteger("fluidId"));
        volume = tagCompound.getInteger("fluidAmount");
	}
	
	private void setCurrentReagent(int fluidId) {
		this.reagent = TanneryLiquidReagent.getReagentById(fluidId);
	}



	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		NBTTagList itemList = new NBTTagList();

        for (int i = 0; i < inventory.length; i++) {
                ItemStack stack = inventory[i];

                if (stack != null) {
                        NBTTagCompound tag = new NBTTagCompound();

                        tag.setByte("Slot", (byte) i);
                        stack.writeToNBT(tag);
                        itemList.appendTag(tag);
                }
        }

        tagCompound.setTag("Inventory", itemList);
        int fluidId = reagent==null?-1:reagent.getReagentId();
        tagCompound.setInteger("fluidId", fluidId);
        tagCompound.setInteger("amount", volume);
        
	}


}
