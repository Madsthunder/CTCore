package continuum.essentials.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class CTItemBlock extends ItemBlock
{

	public CTItemBlock(Block block)
	{
		super(block);
		this.setRegistryName(block.getRegistryName());
	}

}
