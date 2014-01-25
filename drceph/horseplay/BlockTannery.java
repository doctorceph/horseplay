package drceph.horseplay;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockTannery extends Block {
	
    @SideOnly(Side.CLIENT)
    private Icon tanneryInnerIcon;
    @SideOnly(Side.CLIENT)
    private Icon tanneryTopIcon;
    @SideOnly(Side.CLIENT)
    private Icon tanneryBottomIcon;
    
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
		this.setCreativeTab(CreativeTabs.tabBlock);
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
        return par0Str.equals("inner") ? ((BlockTannery)(GameRegistry.findBlock("drceph.horseplay", "leatherTannery"))).tanneryInnerIcon : null;
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
    
    @Override
    public int getRenderType() {
    	return this.blockRenderId;
    }
	
}
