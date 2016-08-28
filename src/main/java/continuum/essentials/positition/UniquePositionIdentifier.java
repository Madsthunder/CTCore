package continuum.essentials.positition;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class UniquePositionIdentifier extends BlockPos
{
	private final IBlockAccess access;
	
	public UniquePositionIdentifier(UniquePositionIdentifier upid, EnumFacing facing)
	{
		this(upid, facing, 1);
	}
	
	public UniquePositionIdentifier(UniquePositionIdentifier upid, EnumFacing facing, int amount)
	{
		this(upid.getBlockAccess(), upid.offset(facing, amount));
	}
	
	public UniquePositionIdentifier(IBlockAccess access, int x, int y, int z)
	{
		super(x, y, z);
		this.access = access;
	}
	
	public UniquePositionIdentifier(IBlockAccess access, BlockPos pos)
	{
		this(access, pos.getX(), pos.getY(), pos.getZ());
	}
	
	@Override
	public final boolean equals(Object obj)
	{
		if(super.equals(obj))
			if(obj instanceof UniquePositionIdentifier)
				return ((UniquePositionIdentifier)obj).getBlockAccess() == this.getBlockAccess();
			else
				return true;
		else
			return false;
	}
	
	public final IBlockAccess getBlockAccess()
	{
		return this.access;
	}
	
	public final int getCoordFromAxis(Axis axis)
	{
		switch(axis)
		{
			case X : return this.getX();
			case Y : return this.getY();
			case Z : return this.getZ();
			default : return 0;
		}
	}
	public IBlockState getBlockState()
	{
		return this.getBlockAccess().getBlockState(this);
	}
	
	public IBlockState getActualState()
	{
		return this.getBlockAccess().getBlockState(this).getActualState(this.getBlockAccess(), this);
	}
	
	public final Boolean hasTileEntity()
	{
		return this.getTileEntity() != null;
	}
	
	public TileEntity getTileEntity()
	{
		return this.getBlockAccess().getTileEntity(this);
	}
	
	public boolean isBlockInstanceOf(Class clasz)
	{
		return clasz.isInstance(this.getBlockState().getBlock());
	}
	
	public boolean isTileEntityInstanceOf(Class clasz)
	{
		return clasz.isInstance(this.getTileEntity());
	}
	
	public Class<? extends Block> getBlockClass()
	{
		return this.getBlockState().getBlock().getClass();
	}
	
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return this.getTileEntity().getClass();
	}
	
	public boolean blockEquals(Block block)
	{
		return this.getBlockState().getBlock() == block;
	}
}
