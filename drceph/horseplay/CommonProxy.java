package drceph.horseplay;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler {

    // Client stuff
    public void registerRenderers() {
            // Nothing here as the server doesn't render graphics or entities!
    }
    
    public void messagePlayer(EntityPlayer player) {
    }

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
        if (te!=null && te instanceof TileEntityTannery) {
                TileEntityTannery ten = (TileEntityTannery) te;
                return new ContainerTannery(player.inventory, ten);
        } else { 
                return null;
        }
	}


	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}
}