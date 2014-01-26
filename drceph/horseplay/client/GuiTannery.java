package drceph.horseplay.client;

import org.lwjgl.opengl.GL11;

import drceph.horseplay.ContainerTannery;
import drceph.horseplay.TanneryLiquidReagent;
import drceph.horseplay.TileEntityTannery;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;

public class GuiTannery extends GuiContainer {

	private TileEntityTannery tileEntity;
    
    public GuiTannery(IInventory player, TileEntityTannery chest) {
            super(new ContainerTannery(player, chest));
            this.tileEntity = chest;
            this.allowUserInput=false;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int p1, int p2) {
    	this.drawCenteredString(fontRenderer, "Leather Tannery", this.xSize/2, 4, 0x404040);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(new ResourceLocation("horseplay","textures/gui/tannery.png"));
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        //liquid
        if (tileEntity.reagent != null) {
        	double ratio = tileEntity.MAX_VOLUME / 40.0;
            int scaled_volume = (int) Math.round(tileEntity.volume / ratio);

            drawTexturedModalRect(x+13, y+18+40-scaled_volume, 192+(16*tileEntity.reagent.getOffset()), 0, 16, scaled_volume);
            drawTexturedModalRect(x+13, y+18, 176, 0, 16, 39);
        }
        
	}
	
	 
	
	@Override
    public void drawCenteredString(FontRenderer par1FontRenderer,
                    String par2Str, int par3, int par4, int par5) {
            par1FontRenderer.drawString(par2Str, par3 - par1FontRenderer.getStringWidth(par2Str) / 2, par4, par5);
    }

}
