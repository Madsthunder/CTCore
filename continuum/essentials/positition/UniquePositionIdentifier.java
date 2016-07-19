package continuum.essentials.positition;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class UniquePositionIdentifier
{
	private final IBlockAccess access;
	private final BlockPos pos;
	
	public UniquePositionIdentifier(IBlockAccess access, BlockPos pos)
	{
		this.access = access;
		this.pos = pos;
	}
	
	@Override
	public final int hashCode()
	{
		return this.getBlockPos().hashCode();
	}
	
	@Override
	public final boolean equals(Object obj)
	{
		if(!(obj instanceof UniquePositionIdentifier))
			return false;
		UniquePositionIdentifier upid = (UniquePositionIdentifier)obj;
		return upid.getBlockPos().equals(this.getBlockPos()) && upid.getBlockAccess() == this.getBlockAccess();
	}
	
	public final IBlockAccess getBlockAccess()
	{
		return this.access;
	}
	
	public final BlockPos getBlockPos()
	{
		return this.pos;
	}
	
	public IBlockState getBlockState()
	{
		return this.getBlockAccess().getBlockState(this.getBlockPos());
	}
	
	public IBlockState getActualState()
	{
		return this.getBlockAccess().getBlockState(this.getBlockPos()).getActualState(this.getBlockAccess(), this.getBlockPos());
	}
	
	public final Boolean hasTileEntity()
	{
		return this.getTileEntity() != null;
	}
	
	public TileEntity getTileEntity()
	{
		return this.getBlockAccess().getTileEntity(pos);
	}
}
