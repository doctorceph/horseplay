package drceph.horseplay;

import net.minecraft.item.Item;

public class ItemProcessedLeather extends Item {

	public ItemProcessedLeather(int id, String icoName) {
		super(id);
		setMaxStackSize(64);
		setUnlocalizedName(icoName);
		setTextureName("Horseplay:"+icoName);
	}
}
