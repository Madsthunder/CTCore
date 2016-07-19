package continuum.essentials.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public abstract class CTBlockContainer extends BlockContainer
{
	
	public CTBlockContainer(Material material, String registryName, Item itemBlock)
	{
		this(material, material.getMaterialMapColor(), registryName, itemBlock);
	}
	
	public CTBlockContainer(Material material, MapColor color, String registryName, Item itemBlock)
	{
		super(material, color);
		this.setRegistryName(registryName);
		ForgeRegistries.BLOCKS.register(this);
		itemBlock.setRegistryName(registryName);
		ForgeRegistries.ITEMS.register(itemBlock);
	}
	
}
