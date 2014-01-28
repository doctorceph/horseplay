package drceph.horseplay;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

public class ItemBlockTannery extends ItemBlock {
	
	public ItemBlockTannery(int par1) {
		super(par1);
		setUnlocalizedName("leatherTannery");
	}

	@Override
	public CreativeTabs getCreativeTab() {
		return CreativeTabs.tabBlock;
	}
	
	
}
