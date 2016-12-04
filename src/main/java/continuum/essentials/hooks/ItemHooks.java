package continuum.essentials.hooks;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ItemHooks
{
	public static ItemBlock registerItemBlock(Block block)
	{
		return registerItemBlock(new ItemBlock(block), block);
	}
	
	public static ItemBlock registerItemBlockMeta(Block block, int maxMeta)
	{
		return registerItemBlock(new ItemBlockMeta(block, maxMeta), block);
	}
	
	public static ItemBlock registerItemBlock(ItemBlock item)
	{
		return registerItemBlock(item, item.block);
	}
	
	public static ItemBlock registerItemBlock(ItemBlock item, Block block)
	{
		ForgeRegistries.ITEMS.register(item.setRegistryName(block.getRegistryName()));
		return item;
	}
	
	public static Item[] allItemsToBlocks(Block... blocks)
	{
		Item[] items = new Item[blocks.length];
		for(Integer i : ObjectHooks.increment(blocks.length))
			items[i] = Item.getItemFromBlock(blocks[i]);
		return items;
	}
	
	public static class ItemBlockMeta extends ItemBlock
	{
		private final int maxMeta;
		
		public ItemBlockMeta(Block block, int maxMeta)
		{
			super(block);
			this.setHasSubtypes(true);
			this.maxMeta = maxMeta;
		}
		
		public int getMetadata(int meta)
		{
			return Math.min(meta, this.maxMeta);
		}
	}
}
