package continuum.essentials.mod;

import org.apache.commons.lang3.tuple.Pair;

import continuum.essentials.hooks.ItemHooks.ItemBlockMeta;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public interface ObjectHolder
{
	public String getModid();
	
	public String getName();
	
	public String getVersion();

	public static <B extends Block> B newBlock(B block, String name, CreativeTabs tab)
	{
		block.setRegistryName(name);
		block.setUnlocalizedName(block.getRegistryName().toString());
		if(tab != null)
			block.setCreativeTab(tab);
		return block;
	}
	
	public static <I extends Item> I newItem(I item, String name, CreativeTabs tab)
	{
		item.setRegistryName(name);
		item.setUnlocalizedName(item.getRegistryName().toString());
		if(tab != null)
			item.setCreativeTab(tab);
		return item;
	}

	public static ItemBlock newItemBlock(Block block)
	{
		ItemBlock item = new ItemBlock(block);
		item.setRegistryName(block.getRegistryName());
		return item;
	}
	
	public static ItemBlock newItemBlock(Block block, int maxMeta)
	{
		ItemBlock item = new ItemBlock(block)
				{
			public int getMetadata(int meta)
			{
				return Math.min(meta, maxMeta);
			}
				};
		item.setRegistryName(block.getRegistryName()).setHasSubtypes(true);
		return item;
	}
	
	public static <B extends Block> Pair<B, ItemBlock> setupItemBlock(B block, String name, CreativeTabs tab)
	{
		block.setRegistryName(name);
		block.setUnlocalizedName(block.getRegistryName().toString());
		if(tab != null)
			block.setCreativeTab(tab);
		return Pair.of(block, newItemBlock(block));
	}
	
	public static <B extends Block> Pair<B, ItemBlock> setupItemBlock(B block, String name, CreativeTabs tab, int meta)
	{
		block.setRegistryName(name);
		block.setUnlocalizedName(block.getRegistryName().toString());
		if(tab != null)
			block.setCreativeTab(tab);
		return Pair.of(block, newItemBlock(block, meta));
	}
	
}
