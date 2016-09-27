package continuum.essentials.block;

import com.google.common.base.Function;

import net.minecraft.util.math.AxisAlignedBB;

public interface ICuboid
{
	public static final Function<ICuboid, AxisAlignedBB> CUBOID_TO_SELECTABLE = new Function<ICuboid, AxisAlignedBB>()
	{
		@Override
		public AxisAlignedBB apply(ICuboid cuboid)
		{
			return cuboid.getSelectableCuboid();
		}
	};
	
	public static final Function<ICuboid, AxisAlignedBB> CUBOID_TO_SHOWABLE = new Function<ICuboid, AxisAlignedBB>()
	{
		@Override
		public AxisAlignedBB apply(ICuboid cuboid)
		{
			return cuboid.getShowableCuboid();
		}
	};
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
