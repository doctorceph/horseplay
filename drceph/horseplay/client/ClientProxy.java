package drceph.horseplay.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import drceph.horseplay.CommonProxy;
import drceph.horseplay.client.GuiTannery;
import drceph.horseplay.TileEntityTannery;

public class ClientProxy extends CommonProxy {

	@Override
    public void registerRenderers() {
		//ClientRegistry.bindTileEntitySpecialRenderer(drceph.horseplay.TileEntityTannery.class, new HorseplayBlockRenderer());
    }
	
	@Override
	public void messagePlayer(EntityPlayer player) {
		player.addChatMessage("You clicked it, srsly!!!");
	}
	
	 @Override
     public Object getClientGuiElement(int ID, EntityPlayer player, World world, int X, int Y, int Z) {
             TileEntity te=world.getBlockTileEntity(X, Y, Z);
             if (te!=null && te instanceof TileEntityTannery) {
                     TileEntityTannery ten=(TileEntityTannery) te;
                     return new GuiTannery(player.inventory, ten);
             } else {
                     return null;
             }
     }
	
}
