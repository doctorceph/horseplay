package drceph.horseplay;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;

public class BlockSulfuricAcid extends BlockFluidClassic {

	@SideOnly(Side.CLIENT)
	protected Icon stillIcon;
	@SideOnly(Side.CLIENT)
	protected Icon flowingIcon;

	public BlockSulfuricAcid(int id, Fluid fluid, Material material) {
		super(id, fluid, material);
		setUnlocalizedName("sulfuric");
		setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public Icon getIcon(int side, int meta) {
		return (side == 0 || side == 1)? stillIcon : flowingIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister register) {
		stillIcon = register.registerIcon("Horseplay:sulfuric_still");
		flowingIcon = register.registerIcon("Horseplay:sulfuric_flow");
	}

	@Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
		if (world.getBlockMaterial(x,  y,  z).isLiquid()) return false;
		return super.canDisplace(world, x, y, z);
	}

	@Override
	public boolean displaceIfPossible(World world, int x, int y, int z) {
		if (world.getBlockMaterial(x,  y,  z).isLiquid()) return false;
		return super.displaceIfPossible(world, x, y, z);
	}

	//Chemical burns!!
	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity ent) {
		if(!par1World.isRemote) {        

			if(ent instanceof EntityLivingBase) {
				ent.attackEntityFrom(DamageSource.drown, 1);
			}
		}

	}

}
