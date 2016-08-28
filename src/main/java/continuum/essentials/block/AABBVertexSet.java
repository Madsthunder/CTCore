package continuum.essentials.block;

import java.util.HashSet;
import java.util.Iterator;

import com.google.common.collect.Sets;

public class AABBVertexSet implements Iterable<AABBVertex>
{
	private final HashSet<AABBVertex> verticies;
	
	public AABBVertexSet(Iterable<AABBVertex> verticies)
	{
		this.verticies = Sets.newHashSet(verticies);
		this.setup();
	}
	
	public AABBVertexSet(AABBVertexSet list, AABBVertex verticies)
	{
		this.verticies = Sets.newHashSet(verticies);
		this.verticies.addAll(list.verticies);
		this.setup();
	}
	
	private void setup()
	{
		int prevSize;
		do
		{
			prevSize = this.verticies.size();
			for(AABBVertex vertex : this.verticies)
				this.verticies.addAll(vertex.getVerticies());
		}
		while(prevSize != this.verticies.size());
	}
	
	public HashSet<AABBVertex> getVerticies()
	{
		return Sets.newHashSet(this.verticies);
	}
	public Iterator<AABBVertex> iterator()
	{
		return this.verticies.iterator();
	}
}
