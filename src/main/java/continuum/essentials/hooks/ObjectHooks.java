package continuum.essentials.hooks;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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
	
	public static <T, O> List<O> applyAll(Function<T, O> function, Iterable<T> ts)
	{
		List list = Lists.newArrayList();
		for(T t : ts)
			list.add(function.apply(t));
		return list;
	}
	
	public static <K, V> HashMap<K, V> fromEntries(Entry<K, V>... entries)
	{
		HashMap<K, V> map = Maps.newHashMap();
		for(Entry<K, V> entry : entries)
			map.put(entry.getKey(), entry.getValue());
		return map;
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
	
	public static int greatestNumber(Iterable<Integer> ints)
	{
		Integer i = null;
		for(Integer integer : ints)
			if(i == null || integer > i)
				i = integer;
		return i;
	}
	
	public static String toSection(String string, char sectionStart, char sectionEnd)
	{
		if(string != null)
		{
			int start = string.indexOf(sectionStart);
			int end = string.indexOf(sectionEnd);
			if(start > -1 && start < end)
				return string.substring(start + 1, end);
		}
		return null;
	}
}
