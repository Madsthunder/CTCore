package continuum.essentials.block;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.google.common.collect.ObjectArrays;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class CTBlock extends Block
{

	public CTBlock(Material material, String registryName, Class<? extends ItemBlock> itemBlock)
	{
		this(material, material.getMaterialMapColor(), registryName, itemBlock);
	}
	
	public CTBlock(Material material, MapColor color, String registryName, Class<? extends ItemBlock> itemBlock)
	{
		super(material, color);
		this.setRegistryName(registryName);
		ForgeRegistries.BLOCKS.register(this);
        ItemBlock item = null;
        if (itemBlock != null)
			try
			{
				item = (itemBlock.getConstructor(new Class<?>[]{Block.class}).newInstance(new Object[]{this}));
				item.setRegistryName(registryName);
			} catch (Exception e)
			{
				System.out.println("Could not register ItemBlock for Block \'" + this.toString() + "\'.");
				e.printStackTrace();
			}
		ForgeRegistries.ITEMS.register(item);
	}
}
