package continuum.essentials.mod;

import org.apache.commons.lang3.tuple.Pair;

import continuum.essentials.hooks.ItemHooks;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public interface ObjectHolder
{
	public String getModid();
	
	public String getName();
	
	public String getVersion();
	
	public static <B extends Block> B setupBlock(B block, String name, CreativeTabs tab)
	{
		block.setRegistryName(name);
		block.setUnlocalizedName(block.getRegistryName().toString());
		if(tab != null)
			block.setCreativeTab(tab);
		return block;
	}
	
	public static <I extends Item> I setupItem(I item, String name, CreativeTabs tab)
	{
		item.setRegistryName(name);
		item.setUnlocalizedName(item.getRegistryName().toString());
		if(tab != null)
			item.setCreativeTab(tab);
		return item;
	}
	
	public static <B extends Block> Pair<B, ItemBlock> setupItemBlock(B block, String name, CreativeTabs tab)
	{
		block.setRegistryName(name);
		block.setUnlocalizedName(block.getRegistryName().toString());
		if(tab != null)
			block.setCreativeTab(tab);
		return Pair.of(block, ItemHooks.createItemBlock(block));
	}
	
	public static <B extends Block> Pair<B, ItemBlock> setupItemBlock(B block, String name, CreativeTabs tab, int meta)
	{
		block.setRegistryName(name);
		block.setUnlocalizedName(block.getRegistryName().toString());
		if(tab != null)
			block.setCreativeTab(tab);
		return Pair.of(block, ItemHooks.createItemBlockMeta(block, meta));
	}
	
}
