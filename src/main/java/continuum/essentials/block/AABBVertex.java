package continuum.essentials.block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map.Entry;

import com.google.common.base.Joiner;
import com.google.common.base.Predicates;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import continuum.essentials.hooks.BlockHooks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class AABBVertex extends Vec3d
{
	private final VertexCorner corner;
	private final HashBiMap<EnumFacing, AABBVertex> adjacentVerticies = HashBiMap.create();
	
	private AABBVertex(double x, double y, double z, VertexCorner corner)
	{
		super(x, y, z);
		this.corner = corner;
	}
	
	private void putAdjacentVerticies(Iterable<AABBVertex> verticies)
	{
		for(AABBVertex vertex : verticies)
		{
			EnumFacing direction = getDirection(this, vertex);
			if(direction != null)
				this.adjacentVerticies.put(direction, vertex);
		}
	}
	
	public static EnumFacing getDirection(AABBVertex from, AABBVertex to)
	{
		if(from.xCoord != to.xCoord && from.yCoord == to.yCoord && from.zCoord == to.zCoord)
			return from.xCoord > to.xCoord ? EnumFacing.WEST : EnumFacing.EAST;
		if(from.yCoord != to.yCoord && from.xCoord == to.xCoord && from.zCoord == to.zCoord)
			return from.yCoord > to.yCoord ? EnumFacing.DOWN : EnumFacing.UP;
		if(from.zCoord != to.zCoord && from.xCoord == to.xCoord && from.yCoord == to.yCoord)
			return from.zCoord > to.zCoord ? EnumFacing.NORTH : EnumFacing.SOUTH;
		return null;
	}
	
	public EnumFacing getVertexDirection(Vec3d vec)
	{
		return this.adjacentVerticies.inverse().get(vec);
	}
	
	public AABBVertex getVertex(EnumFacing direction)
	{
		return this.adjacentVerticies.get(direction);
	}
	
	public HashSet<Entry<EnumFacing, AABBVertex>> getEntries()
	{
		return Sets.newHashSet(this.adjacentVerticies.entrySet());
	}
	
	public HashSet<AABBVertex> getVerticies()
	{
		return Sets.newHashSet(this.adjacentVerticies.values());
	}
	
	public AxisAlignedBB getAABB()
	{
		HashSet<AABBVertex> verticies = Sets.newHashSetWithExpectedSize(8);
		verticies.add(this);
		verticies.addAll(this.adjacentVerticies.values());
		int prevSize;
		do
		{
			prevSize = verticies.size();
			for(AABBVertex vertex : Sets.newHashSet(verticies))
				verticies.addAll(vertex.getVerticies());
		}
		while(prevSize != verticies.size());
		for(AABBVertex vertex : verticies)
			if(vertex.xCoord != this.xCoord && vertex.yCoord != this.yCoord && vertex.zCoord != this.zCoord)
				return new AxisAlignedBB(this.xCoord, this.yCoord, this.zCoord, vertex.xCoord, vertex.yCoord, vertex.zCoord);
		return null;
	}
	
	public AABBVertexSet linkVerticiesToAABB(AxisAlignedBB aabb, AxisAlignedBB exclusion)
	{
		HashSet<AABBVertex> verticies = Sets.newHashSet();
		if(aabb.isVecInside(this))
			for(AxisAlignedBB box : BlockHooks.splitAABB(aabb, this))
				if(!exclusion.intersectsWith(box))
					verticies.addAll(fromAABB(box));
		return new AABBVertexSet(verticies);
	}
	
	public String toString()
	{
		ArrayList<String> strings = Lists.newArrayList();
		for(Entry<EnumFacing, AABBVertex> entry : this.adjacentVerticies.entrySet())
			strings.add("{corner=" + entry.getValue().corner + ", x=" + entry.getValue().xCoord + ", y=" + entry.getValue().yCoord + ", z=" + entry.getValue().zCoord + "}=" + entry.getKey());
		StringBuilder s = new StringBuilder().append("AABBVertex{corner=" + this.corner + ", verticies=[");
		Joiner.on(", ").appendTo(s, strings).append("]}");
		return s.toString();
	}
	
	public static HashSet<AABBVertex> fromAABB(AxisAlignedBB aabb)
	{
		HashSet<AABBVertex> verticies = Sets.newHashSet();
		for(VertexCorner corner : VertexCorner.values())
			verticies.add(corner.from(aabb));
		for(AABBVertex vertex : verticies)
			vertex.putAdjacentVerticies(Iterables.filter(verticies, Predicates.not(Predicates.equalTo(vertex))));
		return verticies;
	}
	
	public static enum VertexCorner
	{
		WEST_DOWN_NORTH(1, 1, 1),
		EAST_DOWN_NORTH(0, 1, 1),
		WEST_UP_NORTH(1, 0, 1),
		EAST_UP_NORTH(0, 0, 1),
		WEST_DOWN_SOUTH(1, 1, 0),
		EAST_DOWN_SOUTH(0, 1, 0),
		WEST_UP_SOUTH(1, 0, 0),
		EAST_UP_SOUTH(0, 0, 0);
		
		private final AxisDirection[] directions;
		
		private VertexCorner(int x, int y, int z)
		{
			AxisDirection[] ds = AxisDirection.values();
			this.directions = new AxisDirection[] { ds[x], ds[y], ds[z] };
		}
		
		public AABBVertex from(AxisAlignedBB aabb)
		{
			double x;
			double y;
			double z;
			switch(this.directions[0])
			{
				case POSITIVE : x = aabb.maxX;
				break;
				default : x = aabb.minX;
			}
			switch(this.directions[1])
			{
				case POSITIVE : y = aabb.maxY;
				break;
				default : y = aabb.minY;
			}
			switch(this.directions[2])
			{
				case POSITIVE : z = aabb.maxZ;
				break;
				default : z = aabb.minZ;
			}
			return new AABBVertex(x, y, z, this);
		}
	}
}
