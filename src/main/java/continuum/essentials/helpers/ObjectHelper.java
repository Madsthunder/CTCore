package continuum.essentials.helpers;

import java.util.Iterator;

public class ObjectHelper
{
	public static Iterable<Integer> increment(Integer end)
	{
		return increment(0, end);
	}
	
	public static Iterable<Integer> increment(Integer start, Integer end)
	{
		return new IntegerIncrement(start, end);
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
