package continuum.essentials.hooks;

import java.util.Iterator;

public class ObjectHooks
{
	public static Iterable<Integer> increment(Integer end)
	{
		return increment(0, end);
	}
	
	public static Iterable<Integer> increment(Integer start, Integer end)
	{
		return new IntegerIncrement(start, end);
	}
	
	public static Boolean anyNull(Object... objects)
	{
		for(Object obj : objects)
			if(obj == null)
				return true;
		return false;
	}
	
	public static Boolean anyNull(Iterable<Object> objects)
	{
		for(Object obj : objects)
			if(obj == null)
				return true;
		return false;
	}
	
	private static class IntegerIncrement implements Iterable<Integer>
	{
		private final Iter iterator;
		
		public IntegerIncrement(Integer start, Integer end)
		{
			this.iterator = new Iter(start, end);
		}
		
		@Override
		public Iterator<Integer> iterator()
		{
			return this.iterator;
		}
		
		private static class Iter implements Iterator<Integer>
		{
			private Integer increment;
			private final Integer end;
			
			public Iter(Integer start, Integer end)
			{
				this.increment = start;
				this.end = end;
			}
			
			@Override
			public boolean hasNext()
			{
				return this.increment < this.end;
			}
			
			@Override
			public Integer next()
			{
				return this.increment++;
			}
		}
	}
}
