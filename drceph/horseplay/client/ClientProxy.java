package drceph.horseplay.client;

import net.minecraft.entity.player.EntityPlayer;
import drceph.horseplay.CommonProxy;

public class ClientProxy extends CommonProxy {

	@Override
    public void registerRenderers() {
            // This is for rendering entities and so forth later on
    }
	
	@Override
	public void messagePlayer(EntityPlayer player) {
		player.addChatMessage("You clicked it, srsly!!!");
	}
	
}
