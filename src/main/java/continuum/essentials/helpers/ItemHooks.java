package continuum.essentials.helpers;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ItemHooks
{
	public static ItemBlock registerItemBlock(Block block)
	{
		return registerItemBlock(new ItemBlock(block), block);
	}
	
	public static ItemBlock registerItemBlockMeta(Block block, Integer maxMeta)
	{
		return registerItemBlock(new ItemBlockMeta(block, maxMeta), block);
	}
	
	public static ItemBlock registerItemBlock(ItemBlock item, Block block)
	{
		ForgeRegistries.ITEMS.register(item.setRegistryName(block.getRegistryName()));
		return item;
	}
	
	private static class ItemBlockMeta extends ItemBlock
	{
		private final Integer maxMeta;
		
		public ItemBlockMeta(Block block, Integer maxMeta)
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
