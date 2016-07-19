package continuum.essentials.block;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.common.registry.RegistryDelegate;

public class BlockStateList<V extends Comparable<V>>
{
	public final RegistryDelegate<Block> delegate;
	public final IProperty<V> property;
	private final ArrayList<V> values = new ArrayList<V>();
	
	public BlockStateList(IProperty<V> property, V... values)
	{
		this(property, null, values);
	}
	
	public BlockStateList(IProperty<V> property, Block block, V... values)
	{
		this.property = property;
		this.delegate = block == null ? null : block.delegate;
		Collection<V> aValues = this.property.getAllowedValues();
		for (V value : values)
			if(aValues.contains(value))
				this.values.add(value);
	}
	
	public Boolean containsProperty(IBlockState state)
	{
		if(this.delegate == null || state.getBlock() == this.delegate.get())
			if(state.getProperties().containsKey(this.property))
			{
				System.out.println(state + ", " + this.values.contains(state.getValue(this.property)));
				return this.values.contains(state.getValue(this.property));
			}
		return false;
	}
}
