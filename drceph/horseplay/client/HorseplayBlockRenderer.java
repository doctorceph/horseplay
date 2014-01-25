package drceph.horseplay.client;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockFluid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import drceph.horseplay.BlockTannery;

public class HorseplayBlockRenderer implements ISimpleBlockRenderingHandler {
	
	private int renderId;
	
	public HorseplayBlockRenderer(int renderId) {
		this.renderId = renderId;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		if (!(block instanceof BlockTannery)) return false;
		BlockTannery blockTannery = (BlockTannery) block;
		renderer.renderStandardBlock(block, x, y, z);
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(blockTannery.getMixedBrightnessForBlock(world, x, y, z));
		
		float f = 1.0F;
        int l = blockTannery.colorMultiplier(world, x, y, z);
        float f1 = (float)(l >> 16 & 255) / 255.0F;
        float f2 = (float)(l >> 8 & 255) / 255.0F;
        float f3 = (float)(l & 255) / 255.0F;
        float f4;

        if (EntityRenderer.anaglyphEnable)
        {
            float f5 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
            f4 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
            float f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
            f1 = f5;
            f2 = f4;
            f3 = f6;
        }
        
        tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
        Icon icon = blockTannery.getBlockTextureFromSide(2);
        f4 = 0.125F;
        
        
        renderer.renderFaceXPos(blockTannery, (double)((float)x - 1.0F + f4), (double)y, (double)z, icon);
        renderer.renderFaceXNeg(blockTannery, (double)((float)x + 1.0F - f4), (double)y, (double)z, icon);
        renderer.renderFaceZPos(blockTannery, (double)x, (double)y, (double)((float)z - 1.0F + f4), icon);
        renderer.renderFaceZNeg(blockTannery, (double)x, (double)y, (double)((float)z + 1.0F - f4), icon);
        Icon icon1 = BlockTannery.getTanneryIcon("inner");
        renderer.renderFaceYPos(blockTannery, (double)x, (double)((float)y - 1.0F + 0.25F), (double)z, icon1);
        renderer.renderFaceYNeg(blockTannery, (double)x, (double)((float)y + 1.0F - 0.75F), (double)z, icon1);
        
        Icon icon2 = BlockFluid.getFluidIcon("water_still");
        renderer.renderFaceYPos(blockTannery, (double)x, (double)((float)y - 1.0F + (6.0F + 2.0F * 3.0F) / 16.0F), (double)z, icon2);

        float ll = 0.40F;
        float ww = 0.05F;
        renderer.setRenderBounds((double)(0.5F - ll), 0.94D, (double)(0.5F - ww), (double)(0.5F + ll), 0.99D, (double)(0.5F + ww));
        renderer.renderStandardBlock(Block.planks,x,y,z);
        renderer.setRenderBounds(0.25D,0.5D,0.48D,0.29D,0.96D,0.52D);
        renderer.renderStandardBlock(Block.cloth,x,y,z);
        renderer.setRenderBounds(0.71D,0.5D,0.48D,0.75D,0.96D,0.52D);
        renderer.renderStandardBlock(Block.cloth,x,y,z);
        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        
		return true;
	}
	


	@Override
	public boolean shouldRender3DInInventory() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return renderId;
	}
	
	

}
