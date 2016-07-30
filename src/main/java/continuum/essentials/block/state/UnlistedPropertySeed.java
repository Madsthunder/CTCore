package continuum.essentials.block.state;

import net.minecraftforge.common.property.IUnlistedProperty;

public class UnlistedPropertySeed implements IUnlistedProperty<Integer>
{
	public static final UnlistedPropertySeed seed = new UnlistedPropertySeed();
	
	@Override
	public String getName()
	{
		return "seed";
	}

	@Override
	public boolean isValid(Integer value)
	{
		return true;
	}

	@Override
	public Class<Integer> getType()
	{
		return Integer.class;
	}

	@Override
	public String valueToString(Integer value)
	{
		return value.toString();
	}
}
