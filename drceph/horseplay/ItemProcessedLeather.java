package drceph.horseplay;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemProcessedLeather extends Item {

	public ItemProcessedLeather(int id, String icoName) {
		super(id);
		setMaxStackSize(64);
		setCreativeTab(CreativeTabs.tabMaterials);
		setUnlocalizedName(icoName);
		setTextureName("Horseplay:"+icoName);
	}
}
