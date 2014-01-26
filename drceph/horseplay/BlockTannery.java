package drceph.horseplay;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BlockTannery extends BlockContainer {
	
    @SideOnly(Side.CLIENT)
    private Icon tanneryInnerIcon;
    @SideOnly(Side.CLIENT)
    private Icon tanneryTopIcon;
    @SideOnly(Side.CLIENT)
    private Icon tanneryBottomIcon;
    @SideOnly(Side.CLIENT)
    private Icon sulfuricAcidTopIcon;
    @SideOnly(Side.CLIENT)
    private Icon sulfuricAcidSideIcon;
    
    private int blockRenderId;

	public BlockTannery(int id, int renderId) {
		this(id, Material.iron);
		this.blockRenderId = renderId;
	}
	
	public BlockTannery(int id, Material material) {
		super(id, material);
		this.setHardness(5F);
		this.setUnlocalizedName("leatherTannery");
		this.setStepSound(Block.soundMetalFootstep);
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return createNewTileEntity(world);
	}
	
	
	
	
    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.tanneryInnerIcon = par1IconRegister.registerIcon("Horseplay:tannery_inner");
        this.tanneryTopIcon = par1IconRegister.registerIcon("Horseplay:tannery_top");
        this.tanneryBottomIcon = par1IconRegister.registerIcon("Horseplay:tannery_bottom");
        this.blockIcon = par1IconRegister.registerIcon("Horseplay:tannery_side");
        this.sulfuricAcidTopIcon = par1IconRegister.registerIcon("Horseplay:sulfuric_still");
        this.sulfuricAcidSideIcon = par1IconRegister.registerIcon("Horseplay:sulfuric_flow");
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
        return par1 == 1 ? this.tanneryTopIcon : (par1 == 0 ? this.tanneryBottomIcon : this.blockIcon);
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    
    @SideOnly(Side.CLIENT)
    public static Icon getTanneryIcon(String par0Str)
    {
    	BlockTannery blockTannery = (BlockTannery)(GameRegistry.findBlock("drceph.horseplay", "leatherTannery"));
        if (par0Str.equals("inner")) return blockTannery.tanneryInnerIcon; 
        if (par0Str.equals("bottom")) return blockTannery.tanneryBottomIcon;
        if (par0Str.equals("sulfuricTop")) return blockTannery.sulfuricAcidTopIcon;
        if (par0Str.equals("sulfuricSide")) return blockTannery.sulfuricAcidSideIcon;
        return null;
    }
    
    /**
     * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
     * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
     */
    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        float f = 0.125F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBoundsForItemRender();
    }
    
    private ItemStack consumeContainer(ItemStack stack) {
    	 if (stack.stackSize == 1) {
             if (stack.getItem().hasContainerItem()) {
                     return stack.getItem().getContainerItemStack(stack);
             } else {
                     return null;
             }
     } else {
             stack.splitStack(1);

             return stack;
     }
    	
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y,
    		int z, EntityPlayer player, int i, float f,
    		float g, float t) {
    	
    	TileEntity tileEntity = world.getBlockTileEntity(x,y,z);
    	
    	if (tileEntity == null || player.isSneaking()) {
    		return false;
    	}
    	
    	if (world.isRemote) {
    		return true;
    	}
    	
    	if (tileEntity instanceof TileEntityTannery) {
    		TileEntityTannery tileEntityTannery = (TileEntityTannery) tileEntity;
    		
    		ItemStack current = player.inventory.getCurrentItem();
    		if (current != null) {
    			FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(current);
    			
    			if (liquid != null) {
    				System.out.println(tileEntityTannery.volume);
    				int qty = tileEntityTannery.fill(liquid, true);
    				if (qty != 0) {
    					player.inventory.setInventorySlotContents(player.inventory.currentItem, consumeContainer(current));
    				}
    				System.out.println(tileEntityTannery.volume);
    				return true;
    			} else {
    				player.openGui(Horseplay.instance, 0, world, x, y, z);
    				return true;
    			}
    		} else {
    			player.openGui(Horseplay.instance, 0, world, x, y, z);
    			return true; //empty hands
    		}
    	}
    	return false;
    	
    }
    
    @Override
    public int getRenderType() {
    	return this.blockRenderId;
    }
    
    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return GameRegistry.findItem("drceph.horseplay", "leatherTanneryItem").itemID;
    }
    
    public static void updateBlockState(int progress, TanneryLiquidReagent tlr, World world, int x, int y, int z) {
        int state = 0;
    	if (progress <= 0) {
    		if (tlr != null) {
    			if ((FluidRegistry.getFluidName(tlr.getReagent())).equals("juice")) state = 2;
    			else state = 1;
    		}
    	} else {
    		if (tlr != null) {
    			if ((FluidRegistry.getFluidName(tlr.getReagent())).equals("juice")) state = 4;
    			else state = 3;
    		}
    	}
    	
        world.setBlockMetadataWithNotify(x, y, z, state, 1);
}

@Override
public void breakBlock(World par1World, int par2, int par3, int par4,
		int par5, int par6) {
	dropItems(par1World, par2, par3, par4);
	super.breakBlock(par1World, par2, par3, par4, par5, par6);
}
    
	@Override
	public TileEntity createNewTileEntity(World world) {
		// TODO Auto-generated method stub
		return new TileEntityTannery();
	}
	
	
	private void dropItems(World world, int x, int y, int z){
        Random rand = new Random();

        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (!(tileEntity instanceof IInventory)) {
                return;
        }
        IInventory inventory = (IInventory) tileEntity;

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
                ItemStack item = inventory.getStackInSlot(i);

                if (item != null && item.stackSize > 0) {
                        float rx = rand.nextFloat() * 0.8F + 0.1F;
                        float ry = rand.nextFloat() * 0.8F + 0.1F;
                        float rz = rand.nextFloat() * 0.8F + 0.1F;

                        ItemStack newItem = new ItemStack(item.itemID, item.stackSize, item.getItemDamage());
                        if (item.hasTagCompound()) {
                            newItem.setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                        }
                        EntityItem entityItem = new EntityItem(world,
                                        x + rx, y + ry, z + rz,
                                        newItem);

                        float factor = 0.05F;
                        entityItem.motionX = rand.nextGaussian() * factor;
                        entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                        entityItem.motionZ = rand.nextGaussian() * factor;
                        world.spawnEntityInWorld(entityItem);
                        item.stackSize = 0;
                }
        }
}
	
    
}
