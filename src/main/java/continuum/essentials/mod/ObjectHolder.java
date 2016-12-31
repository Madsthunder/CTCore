package continuum.essentials.mod;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public interface ObjectHolder
{
	public static final Constructor<Block> DEFAULT_BLOCK_CONSTRUCTOR = getBlockConstructor();
	public static final Constructor<Item> DEFAULT_ITEM_CONSTRUCTOR = getItemConstructor();
	
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
	
	public static List<Block> newBlocks(List<String> names, CreativeTabs tab)
	{
		return newBlocks(DEFAULT_BLOCK_CONSTRUCTOR, Lists.newArrayList(), names, tab);
	}
	
	public static <B extends Block> List<B> newBlocks(Constructor<B> constructor, List<Object> constructor_args, List<String> names, CreativeTabs tab)
	{
		return newBlocks(new Callable<B>()
		{
			
			@Override
			public B call() throws Exception
			{
				return constructor.newInstance(Iterables.toArray(constructor_args, Object.class));
			}
			
		}, constructor_args, names, tab);
	}
	
	public static <B extends Block> List<B> newBlocks(Callable<B> constructor, List<Object> constructor_args, List<String> names, CreativeTabs tab)
	{
		List<B> blocks = Lists.newArrayList();
		String current = "null";
		int remaining = names.size();
		try
		{
			for(String name : names)
			{
				current = name;
				blocks.add(newBlock(constructor.call(), name, tab));
				remaining--;
			}
		}
		catch(Exception exception)
		{
			throw new IllegalStateException("Failed To Construct Block \"" + current + "\" Due To A " + exception.getClass() + "; Had " + remaining + " Blocks Remaining.", exception);
		}
		return blocks;
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
		return newItems(DEFAULT_ITEM_CONSTRUCTOR, Lists.newArrayList(), names, tab);
	}
	
	public static <I extends Item> List<I> newItems(Constructor<I> constructor, List<Object> constructor_args, List<String> names, CreativeTabs tab)
	{
		return newItems(new Callable()
		{
			
			@Override
			public Object call() throws Exception
			{
				return constructor.newInstance(Iterables.toArray(constructor_args, Object.class));
			}
		}, constructor_args, names, tab);
	}
	
	public static <I extends Item> List<I> newItems(Callable<I> constructor, List<Object> constructor_args, List<String> names, CreativeTabs tab)
	{
		List<I> items = Lists.newArrayList();
		String current = "null";
		int remaining = names.size();
		try
		{
			for(String name : names)
			{
				current = name;
				items.add(newItem(constructor.call(), name, tab));
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
	
	public static Constructor<Block> getBlockConstructor()
	{
		try
		{
			return Block.class.getConstructor();
		}
		catch(Exception exception)
		{
			throw new IllegalStateException("Failed To Get Default Item Constructor.", exception);
		}
		
	}
	
	public static Constructor<Item> getItemConstructor()
	{
		try
		{
			return Item.class.getConstructor();
		}
		catch(Exception exception)
		{
			throw new IllegalStateException("Failed To Get Default Item Constructor.", exception);
		}
		
	}
}
