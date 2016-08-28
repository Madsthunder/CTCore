package continuum.essentials.hooks;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

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
	
	public static class Vec3dIntersects implements Predicate<Vec3d>
	{
		private final AxisAlignedBB aabb;
		
		public Vec3dIntersects(AxisAlignedBB aabb)
		{
			this.aabb = aabb;
		}
		
		@Override
		public boolean apply(Vec3d vec)
		{
			return aabb.isVecInside(vec);
		}
	}
	
	public static class AABBValid implements Predicate<AxisAlignedBB>
	{
		private final AxisAlignedBB aabb;
		private final AxisAlignedBB exclude;
		
		public AABBValid(AxisAlignedBB aabb, AxisAlignedBB exclude)
		{
			this.aabb = aabb;
			this.exclude = exclude;
		}
		
		@Override
		public boolean apply(AxisAlignedBB aabb)
		{ 
			return !exclude.intersectsWith(aabb) && getVolume(aabb) > 0;
		}
	}
}
