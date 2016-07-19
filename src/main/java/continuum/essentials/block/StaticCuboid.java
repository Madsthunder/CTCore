package continuum.essentials.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;

public class StaticCuboid implements ICuboid
{
	private ICuboid base;
	private final AxisAlignedBB selectable;
	private final AxisAlignedBB showable;
	private Object extdata;
	
	public StaticCuboid(ICuboid cuboid)
	{
		this(cuboid.getSelectableCuboid(), cuboid.getShowableCuboid(), cuboid.getExtraData());
		this.base = cuboid;
	}
	
	public StaticCuboid(AxisAlignedBB aabb)
	{
		this(aabb, aabb, null);
	}
	
	public StaticCuboid(AxisAlignedBB aabb, Object extdata)
	{
		this(aabb, aabb, extdata);
	}
	
	public StaticCuboid(AxisAlignedBB selectable, AxisAlignedBB showable)
	{
		this(selectable, showable, null);
	}
	
	public StaticCuboid(AxisAlignedBB selectable, AxisAlignedBB showable, Object extdata)
	{
		this.selectable = selectable;
		this.showable = showable;
		this.extdata = extdata;
	}
	
	@Override
	public AxisAlignedBB getSelectableCuboid()
	{
		return this.selectable;
	}
	
	@Override
	public AxisAlignedBB getShowableCuboid()
	{
		return this.showable;
	}
	
	@Override
	public ICuboid addExtraData(Object obj)
	{
		this.extdata = obj;
		return this;
	}
	
	@Override
	public Object getExtraData()
	{
		return this.extdata;
	}
	
	@Override
	public Boolean isUsable(Object obj)
	{
		if(this.base != null)
			return this.base.isUsable(obj);
		return true;
	}
	
	@Override
	public ICuboid copy()
	{
		return new StaticCuboid(this);
	}
}
