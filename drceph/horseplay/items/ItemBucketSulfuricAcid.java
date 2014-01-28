package drceph.horseplay.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.world.World;

public class ItemBucketSulfuricAcid extends ItemBucket {

	public ItemBucketSulfuricAcid(int par1, int par2) {
		super(par1, par2);
		setContainerItem(Item.bucketEmpty);
		setTextureName("Horseplay:bucket_sulfuric");
		setUnlocalizedName("sulfuricAcidBucket");
		// TODO Auto-generated constructor stub
	}
	
	//NO PLACEY
	@Override
	public boolean tryPlaceContainedLiquid(World par1World, int par2, int par3,
			int par4) {
		return false;
	}

}
