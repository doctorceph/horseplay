package drceph.horseplay;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTannery extends Container {

	public TileEntityTannery tileEntity;
	private EntityPlayer invokingPlayer;
	private int lastVolume = 0;
	private int lastFluidId = 0;
	private int lastProgress = 0;

	public ContainerTannery(IInventory playerInventory, TileEntityTannery tileEntity) {
		this.tileEntity = tileEntity;
		this.invokingPlayer = ((InventoryPlayer) playerInventory).player;
		layoutContainer(playerInventory,tileEntity);
	}
	
	

	private void layoutContainer(IInventory playerInventory, IInventory inventory) {
		addSlotToContainer(new Slot(inventory, 0, 39, 41));
		addSlotToContainer(new Slot(inventory, 1, 146, 41));
		for (int inventoryRow = 0; inventoryRow < 3; inventoryRow++)
		{
			for (int inventoryColumn = 0; inventoryColumn < 9; inventoryColumn++)
			{
				addSlotToContainer(new Slot(playerInventory, inventoryColumn + inventoryRow * 9 + 9, 8 + inventoryColumn * 18, 84 + inventoryRow * 18));
			}
		}

		for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++)
		{
			addSlotToContainer(new Slot(playerInventory, hotbarSlot, 8 + hotbarSlot * 18, 142));
		}

	}

	public EntityPlayer getPlayer() {
		return invokingPlayer;
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafter) {
		// TODO Auto-generated method stub
		super.addCraftingToCrafters(crafter);

		crafter.sendProgressBarUpdate(this, 0, this.tileEntity.volume);
		crafter.sendProgressBarUpdate(this, 1, this.tileEntity.getCurrentFluidId());
		crafter.sendProgressBarUpdate(this, 2, this.tileEntity.runProgress);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); i++) {
			ICrafting crafter = (ICrafting)this.crafters.get(i);
			if (this.lastVolume != this.tileEntity.volume) {
				crafter.sendProgressBarUpdate(this, 0, this.tileEntity.volume);
			}
			if (this.lastFluidId != this.tileEntity.getCurrentFluidId()) {
				crafter.sendProgressBarUpdate(this, 1, this.tileEntity.getCurrentFluidId());
			}
			if (this.lastProgress != this.tileEntity.runProgress) {
				crafter.sendProgressBarUpdate(this, 0, this.tileEntity.runProgress);
			}
		}

		this.lastVolume = this.tileEntity.volume;
		this.lastFluidId = this.tileEntity.getCurrentFluidId();
		this.lastProgress = this.tileEntity.runProgress;
	}
	
	@Override
	public void onCraftMatrixChanged(IInventory par1iInventory) {
		// TODO Auto-generated method stub
		super.onCraftMatrixChanged(par1iInventory);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
            // TODO Auto-generated method stub
            super.updateProgressBar(par1, par2);
            
            if (par1 == 0) {
                    this.tileEntity.volume = par2;
            }
            if (par1 == 1) {
                    this.tileEntity.setCurrentReagent(par2);
            }
            if (par1 == 2) {
            		this.tileEntity.runProgress = par2;
            }
    }


	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return tileEntity.isUseableByPlayer(entityplayer);
	}
	
	

	@Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot) {
            // TODO Auto-generated method stub
            ItemStack stack = null;
    Slot slotObject = (Slot) inventorySlots.get(slot);

    //null checks and checks if the item can be stacked (maxStackSize > 1)
    if (slotObject != null && slotObject.getHasStack()) {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            //merges the item into player inventory since its in the tileEntity
            //this assumes only 1 slot, for inventories with > 1 slots, check out the Chest Container.
            if (slot <= 1) {
                    if (!mergeItemStack(stackInSlot, 2,
                                    inventorySlots.size(), true)) {
                            return null;
                    }
            //places it into the tileEntity is possible since its in the player inventory
            } else if (!mergeItemStack(stackInSlot, 0, 1, false)) {
                    return null;
            }

            if (stackInSlot.stackSize == 0) {
                    slotObject.putStack(null);
            } else {
                    slotObject.onSlotChanged();
            }
    }

    return stack;
    }
	
	@Override
	public ItemStack slotClick(int par1, int par2, int par3,
			EntityPlayer par4EntityPlayer) {
		// TODO Auto-generated method stub
		return super.slotClick(par1, par2, par3, par4EntityPlayer);
	}
	
}
