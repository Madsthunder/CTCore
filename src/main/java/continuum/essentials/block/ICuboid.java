package continuum.essentials.block;

import net.minecraft.util.math.AxisAlignedBB;

public interface ICuboid
{
	public AxisAlignedBB getSelectableCuboid();
	
	public AxisAlignedBB getShowableCuboid();
	
	public ICuboid addExtraData(Object obj);
	
	public Object getExtraData();
	
	public ICuboid copy();
	
	default public Boolean isUsable(Object obj)
	{
		return true;
	}
}
