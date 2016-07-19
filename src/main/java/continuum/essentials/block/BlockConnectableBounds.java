package continuum.essentials.block;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockConnectableBounds extends BlockConnectable implements IBlockBoundable
{
	private final ConnectableCuboids cuboids;
	private AxisAlignedBB bounds = Block.FULL_BLOCK_AABB;
	
	public BlockConnectableBounds(Material material, Predicate<Pair<IBlockAccess, BlockPos>> predicate, ConnectableCuboids cuboids)
	{
		super(material, predicate);
		this.cuboids = cuboids;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB box, List list, Entity entity)
	{
		AxisAlignedBB aabb;
		for (ICuboid cuboid : this.cuboids.getCuboidsFromState(state.getActualState(world, pos)))
			if(box.intersectsWith((aabb = cuboid.getSelectableCuboid().offset(pos))))
				list.add(aabb);
	}
	
	@Override
	public RayTraceResult collisionRayTrace(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d finish)
	{
		return CuboidSelector.getSelectionBox(this, world, pos, start, finish, this.cuboids.getCuboidsFromState(state.getActualState(world, pos)));
	}
	
	@Override
	public RayTraceResult rayTrace(BlockPos pos, Vec3d start, Vec3d finish, AxisAlignedBB boundingBox)
	{
		return super.rayTrace(pos, start, finish, boundingBox);
	}
	
	@Override
	public void setBlockBounds(AxisAlignedBB aabb)
	{
		this.bounds = aabb;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess access, BlockPos pos)
	{
		return this.bounds;
	}
	
	public ConnectableCuboids getCuboids()
	{
		return this.cuboids;
	}
}
