package continuum.essentials.block;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public interface IBlockBoundable
{
	public void setBlockBounds(AxisAlignedBB aabb);
	
	public RayTraceResult rayTrace(BlockPos pos, Vec3d start, Vec3d finish, AxisAlignedBB boundingBox);
}
