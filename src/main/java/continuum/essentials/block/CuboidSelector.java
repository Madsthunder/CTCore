package continuum.essentials.block;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;

public class CuboidSelector 
{
	public static RayTraceResult getSelectionBox(IBlockBoundable block, IBlockAccess access, BlockPos pos, Vec3d start, Vec3d finish, List<? extends ICuboid> cuboids)
	{
		return getSelectionBox(block, access, pos, start, finish, cuboids.toArray(new ICuboid[0]));
	}
	
	public static RayTraceResult getSelectionBox(IBlockBoundable block, IBlockAccess access, BlockPos pos, Vec3d start, Vec3d finish, ICuboid... cuboids)
	{
		ArrayList<RayTraceResult> candidates = Lists.newArrayList();
		for(ICuboid cuboid : cuboids)
			candidates.add(block.rayTrace(pos, start, finish, cuboid.getSelectableCuboid()));
		RayTraceResult result = null;
		ICuboid cuboid = null;
		for(Integer i = 0; i < cuboids.length; i++)
		{
			ICuboid cc = cuboids[i];
			RayTraceResult rc = candidates.get(i);
			if(result == null)
			{
				result = rc;
				cuboid = cc;
			}
			else if(rc != null)
				if(start.distanceTo(rc.hitVec) < start.distanceTo(rc.hitVec))
				{
					result = rc;
					cuboid = cc;
				}
		}
		if(result != null && cuboid != null)
		{
			result.hitInfo = cuboid.getExtraData();
			block.setBlockBounds(cuboid.getShowableCuboid());
		}
		
		return result;
	}

    private RayTraceResult rayTrace(BlockPos pos, Vec3d start, Vec3d end, AxisAlignedBB boundingBox)
    {
        Vec3d vec3d = start.subtract((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
        Vec3d vec3d1 = end.subtract((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
        RayTraceResult raytraceresult = boundingBox.calculateIntercept(vec3d, vec3d1);
        return raytraceresult == null ? null : new RayTraceResult(raytraceresult.hitVec.addVector((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()), raytraceresult.sideHit, pos);
    }
}
