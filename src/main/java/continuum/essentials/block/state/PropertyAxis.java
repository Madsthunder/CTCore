package continuum.essentials.block.state;

import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;

public class PropertyAxis extends PropertyEnum<Axis>
{
	
	protected PropertyAxis(String name, Collection<Axis> values)
	{
		super(name, Axis.class, values);
	}
	
	public static PropertyAxis create(String name)
	{
		return create(name, Predicates.<Axis>alwaysTrue());
	}
	
	public static PropertyAxis create(String name, Predicate<Axis> filter)
	{
		return create(name, Collections2.<Axis>filter(Lists.newArrayList(Axis.values()), filter));
	}
	
	public static PropertyAxis create(String name, Collection<Axis> values)
	{
		return new PropertyAxis(name, values);
	}
}
