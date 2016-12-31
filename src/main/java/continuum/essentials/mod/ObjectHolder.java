package continuum.essentials.mod;

import java.lang.reflect.Constructor;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class ObjectHolder
{
	private static final Constructor<Item> DEFAULT_CONSTRUCTOR;
	
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
	
	public static List<Item> newItems(List<String> names, CreativeTabs tab)
	{
		return newItems(DEFAULT_CONSTRUCTOR, Lists.newArrayList(), names, tab);
	}
	
	public static <I extends Item> List<I> newItems(Constructor<I> constructor, List<Object> constructor_args, List<String> names, CreativeTabs tab)
	{
		List<I> items = Lists.newArrayList();
		String current = "null";
		int remaining = names.size();
		try
		{
			for(String name : names)
			{
				current = name;
				items.add(newItem(constructor.newInstance(Iterables.toArray(constructor_args, Object.class)), name, tab));
				remaining--;
			}
		}
		catch(Exception exception)
		{
			throw new IllegalStateException("Failed To Construct Item \"" + current + "\" Due To A " + exception.getClass() + "; Had " + remaining + " Items Remaining.", exception);
		}
		return items;
	}
	
	public static ItemBlock newItemBlock(Block block)
	{
		ItemBlock item = new ItemBlock(block);
		item.setRegistryName(block.getRegistryName());
		return item;
	}
	
	public static List<ItemBlock> newItemBlocks(Block... blocks)
	{
		return newItemBlocks(Lists.newArrayList(blocks));
	}
	
	public static List<ItemBlock> newItemBlocks(List<Block> blocks)
	{
		List<ItemBlock> item_blocks = Lists.newArrayList();
		for(Block block : blocks)
			item_blocks.add(newItemBlock(block));
		return item_blocks;
	}
	
	public static ItemBlock newItemBlock(Block block, int maxMeta)
	{
		ItemBlock item = new ItemBlock(block)
		{
			@Override
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
	
	static
	{
		try
		{
			DEFAULT_CONSTRUCTOR = Item.class.getConstructor();
		}
		catch(Exception exception)
		{
			throw new IllegalStateException("Failed To Get Default Item Constructor.", exception);
		}
	}
}
