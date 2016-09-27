package continuum.essentials.hooks;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import continuum.essentials.block.AABBVertex;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHooks
{
	public static final Predicate<AxisAlignedBB> notZeroVolume = new Predicate<AxisAlignedBB>()
			{
				@Override
				public boolean apply(AxisAlignedBB aabb)
				{
					return getVolume(aabb) != 0;
				}
			};
			
	public static List<BlockSnapshot> setBlockStateWithSnapshots(World world, BlockPos pos, IBlockState state)
	{
		world.captureBlockSnapshots = true;
		world.setBlockState(pos, state);
		world.captureBlockSnapshots = false;
		List<BlockSnapshot> snapshots = (List<BlockSnapshot>)world.capturedBlockSnapshots.clone();
		world.capturedBlockSnapshots.clear();
		return snapshots;
	}
	
	public static void restoreBlockSnapshots(World world, List<BlockSnapshot> snapshots)
	{
		for (BlockSnapshot snapshot : snapshots)
		{
			world.restoringBlockSnapshots = true;
			snapshot.restore(true, false);
			world.restoringBlockSnapshots = false;
		}
	}
	
	public static Block[] allItemsToBlocks(Item... items)
	{
		Block[] blocks = new Block[items.length];
		for(Integer i : ObjectHooks.increment(items.length))
			blocks[i] = Block.getBlockFromItem(items[i]);
		return blocks;
	}
	
	public static EnumFacing[] getDirectonsFromAxis(Axis axis)
	{
		if(axis == null)
			return new EnumFacing[0];
		List<EnumFacing> directions = Lists.newArrayList();
		int i = 0;
		for(EnumFacing facing : EnumFacing.values())
			if(facing.getAxis() == axis)
				directions.add(facing);
		return directions.toArray(new EnumFacing[0]);
	}
	
	public static void createLandingEffects(WorldServer world, Vec3d vec, IBlockState state, int particles)
	{
		world.spawnParticle(EnumParticleTypes.BLOCK_DUST, vec.xCoord, vec.yCoord, vec.zCoord, particles, 0, 0, 0, 0.15000000596046448D, Block.getStateId(state));
	}
	
	@SideOnly(Side.CLIENT)
	public static void createHitEffects(ParticleManager manager, World world, RayTraceResult result, AxisAlignedBB aabb, IBlockState state)
	{
		BlockPos pos = result.getBlockPos();
		EnumFacing direction = result.sideHit;
		Random random = new Random();
		double d0 = .20000000298023224;
		double d1 = .10000000149011612;
		double x = pos.getX() + random.nextDouble() * (aabb.maxX - aabb.minX - d0) + d1 + aabb.minX;
		double y = pos.getY() + random.nextDouble() * (aabb.maxY - aabb.minY - d0) + d1 + aabb.minY;
		double z = pos.getZ() + random.nextDouble() * (aabb.maxZ - aabb.minZ - d0) + d1 + aabb.minZ;
		switch(direction)
		{
			case DOWN :
				y = pos.getY() + aabb.minY - d1;
				break;
			case UP :
				y = pos.getY() + aabb.maxY + d1;
				break;
			case NORTH :
				z = pos.getZ() + aabb.minZ - d1;
				break;
			case SOUTH :
				z = pos.getZ() + aabb.maxZ + d1;
				break;
			case WEST :
				x = pos.getX() + aabb.minX - d1;
				break;
			case EAST :
				x = pos.getX() + aabb.maxX + d1;
				break;
		}
		manager.addEffect(((ParticleDigging)new ParticleDigging.Factory().getEntityFX(0, world, x, y, z, 0, 0, 0, Block.getStateId(state))).setBlockPos(pos).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
	}
	
	@SideOnly(Side.CLIENT)
	public static void createDestroyEffects(ParticleManager manager, World world, BlockPos pos, IBlockState state)
	{
		for(int j : ObjectHooks.increment(4))
			for(int k : ObjectHooks.increment(4))
				for(int l : ObjectHooks.increment(4))
				{
					double x = pos.getX() + (j + .5) / 4;
					double y = pos.getY() + (k + .5) / 4;
					double z = pos.getZ() + (l + .5) / 4;
					manager.addEffect(((ParticleDigging)new ParticleDigging.Factory().getEntityFX(0, world, x, y, z, x - pos.getX() - .5, y - pos.getY() - .5, z - pos.getZ() - .5, Block.getStateId(state))).setBlockPos(pos));
				}
	}
	
	public static HashSet<AxisAlignedBB> splitAABB(AxisAlignedBB subject, Vec3d vec)
	{
		AxisAlignedBB wdn = new AxisAlignedBB(subject.minX, subject.minY, subject.minZ, vec.xCoord, vec.yCoord, vec.zCoord);
		AxisAlignedBB edn = new AxisAlignedBB(vec.xCoord, subject.minY, subject.minZ, subject.maxX, vec.yCoord, vec.zCoord);
		AxisAlignedBB wun = new AxisAlignedBB(subject.minX, vec.yCoord, subject.minZ, vec.xCoord, subject.maxY, vec.zCoord);
		AxisAlignedBB eun = new AxisAlignedBB(vec.xCoord, vec.yCoord, subject.minZ, subject.maxX, subject.maxY, vec.zCoord);
		AxisAlignedBB wds = new AxisAlignedBB(subject.minX, subject.minY, vec.zCoord, vec.xCoord, vec.yCoord, subject.maxZ);
		AxisAlignedBB eds = new AxisAlignedBB(vec.xCoord, subject.minY, vec.zCoord, subject.maxX, vec.yCoord, subject.maxZ);
		AxisAlignedBB wus = new AxisAlignedBB(subject.minX, vec.yCoord, vec.zCoord, vec.xCoord, subject.maxY, subject.maxZ);
		AxisAlignedBB eus = new AxisAlignedBB(vec.xCoord, vec.yCoord, vec.zCoord, subject.maxX, subject.maxY, subject.maxZ);
		return Sets.newHashSet(wdn, edn, wun, eun, wds, eds, wus, eus);
	}
	
	public static double getVolume(AxisAlignedBB aabb)
	{
		return (aabb.maxX - aabb.minX) * (aabb.maxY - aabb.minY) * (aabb.maxZ - aabb.minZ);
	}
	
	public static <T> Iterable<AxisAlignedBB> splitAABBs(Iterable<AxisAlignedBB> boxes, Iterable<? extends T> list, Predicate<? super T> predicate, Function<? super T, Iterable<AxisAlignedBB>> function)
	{
		for(T t : list)
			if(predicate.apply(t))
			{
				AxisAlignedBB aabb2;
				Set<AxisAlignedBB> aabbs = Sets.newHashSet();
				for(AxisAlignedBB aabb0 : function.apply(t))
					for(AABBVertex vertex0 : AABBVertex.fromAABB(aabb0))
						for(AxisAlignedBB aabb1 : boxes)
							for(AABBVertex vertex1 : vertex0.linkVerticiesToAABB(aabb1, aabb0))
								if((aabb2 = vertex1.getAABB()) != null)
									aabbs.add(aabb2);
				if(!aabbs.isEmpty())
						boxes = aabbs;
			}
		return boxes;
	}
	
	public static boolean isInsideAABB(AxisAlignedBB aabb, Number x, Number y, Number z)
	{
		return isInsideAABB(aabb, new Vec3d(x.doubleValue(), y.doubleValue(), z.doubleValue()));
	}
	
	public static boolean isInsideAABB(AxisAlignedBB aabb, Vec3d vec)
	{
		return aabb.intersectsWithXY(vec) && aabb.intersectsWithXZ(vec) && aabb.intersectsWithYZ(vec);
	}
	
	public static boolean isFlushWithSide(EnumFacing direction, AxisAlignedBB aabb, AxisAlignedBB subject)
	{
		switch(direction)
		{
			case DOWN : return aabb.minY == subject.minY;
			case UP : return aabb.maxY == subject.maxY;
			case NORTH : return aabb.minZ == subject.minZ;
			case SOUTH : return aabb.maxZ == subject.maxZ;
			case WEST : return aabb.minX == subject.minX;
			case EAST : return aabb.maxX == subject.maxX;
			default : return false;
		}
	}
	
	public static AxisAlignedBB createAABBFromSide(EnumFacing direction, AxisAlignedBB aabb)
	{
		switch(direction)
		{
			case DOWN : return new AxisAlignedBB(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.minY, aabb.maxZ);
			case UP : return new AxisAlignedBB(aabb.minX, aabb.maxY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ);
			case NORTH : return new AxisAlignedBB(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.minZ);
			case SOUTH : return new AxisAlignedBB(aabb.minX, aabb.minY, aabb.maxZ, aabb.maxX, aabb.maxY, aabb.maxZ);
			case WEST : return new AxisAlignedBB(aabb.minX, aabb.minY, aabb.minZ, aabb.minX, aabb.maxY, aabb.maxZ);
			case EAST : return new AxisAlignedBB(aabb.maxX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ);
			default : return null;
		}
	}
	
	public static AxisAlignedBB dialate(AxisAlignedBB aabb, double by)
	{
		return new AxisAlignedBB(aabb.minX * by, aabb.minY * by, aabb.minZ * by, aabb.maxX * by, aabb.maxY * by, aabb.maxZ * by);
	}
}
