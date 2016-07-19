package continuum.essentials.block;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

public class ConnectableCuboids
{
	private final ICuboid core;
	private final List<ICuboid> directionalCuboids = Lists.newArrayListWithCapacity(6);
	private final List<ICuboid> extraCuboids;
	
	public ConnectableCuboids(ICuboid... cuboids)
	{
		Integer i = 0;
		this.core = cuboids[i++];
		for (Integer k = 0; k < 6; k++)
			this.directionalCuboids.add(cuboids[i++]);
		this.extraCuboids = Lists.newArrayListWithCapacity(cuboids.length - i);
		Integer j = i;
		for (Integer k = 0; k < cuboids.length - i; k++)
			this.extraCuboids.add(cuboids[j++]);
	}
	
	public ICuboid getCoreCuboid()
	{
		return this.core;
	}
	
	public ICuboid getDirectionalCuboid(EnumFacing direction)
	{
		return this.directionalCuboids.get(direction.ordinal());
	}
	
	public List<ICuboid> getDirectionalCuboids()
	{
		return this.directionalCuboids;
	}
	
	public List<ICuboid> getExtraCuboids()
	{
		return this.extraCuboids;
	}
	
	public List<ICuboid> getAllCuboids()
	{
		List<ICuboid> cuboids = Lists.newArrayList();
		cuboids.add(this.getCoreCuboid());
		cuboids.addAll(this.getDirectionalCuboids());
		cuboids.addAll(this.getExtraCuboids());
		return cuboids;
	}
	
	public List<ICuboid> getCuboidsFromState(IBlockState state)
	{
		List<ICuboid> cuboids = Lists.newArrayList();
		cuboids.add(this.getCoreCuboid());
		for (EnumFacing direction : EnumFacing.values())
		{
			ICuboid cuboid;
			if(state.getValue(BlockConnectable.isConnected[direction.ordinal()]) && (cuboid = this.getDirectionalCuboid(direction)) != null && cuboid.isUsable(state))
				cuboids.add(cuboid);
		}
		for (ICuboid cuboid : this.getExtraCuboids())
			if(cuboid.isUsable(state))
				cuboids.add(cuboid);
		return cuboids;
	}
}
