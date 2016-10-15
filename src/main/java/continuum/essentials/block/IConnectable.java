package continuum.essentials.block;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface IConnectable
{
	public boolean canConnectTo(IBlockAccess access, BlockPos pos, EnumFacing direction);
	
	public static boolean isConnectedTo(IConnectable connectable, IBlockAccess access, BlockPos pos, EnumFacing direction)
	{
		return connectable.canConnectTo(access, pos, direction) && canOtherConnect(access, pos, direction);
	}
	
	public static boolean canOtherConnect(IBlockAccess access, BlockPos pos, EnumFacing direction)
	{
		Block block = access.getBlockState(pos.offset(direction)).getBlock();
		return block instanceof IConnectable ? ((IConnectable)block).canConnectTo(access, pos.offset(direction), direction.getOpposite()) : true;
	}
}
