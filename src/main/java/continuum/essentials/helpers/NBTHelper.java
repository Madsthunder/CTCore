package continuum.essentials.helpers;

import java.util.Iterator;

import continuum.essentials.nbt.IWritableObject;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.INBTSerializable;

public class NBTHelper
{
	public static <T extends NBTBase> Iterable<T> increment(Class<T> clasz, NBTTagList list)
	{
		return new NBTListIncrement(list);
	}
	
	public static <T extends NBTBase> NBTTagCompound writeList(Class<T> clasz, String listName, Iterable<? extends INBTSerializable<T>> nbt)
	{
		NBTTagCompound compound = new NBTTagCompound();
		compound.setTag(listName, compileList(clasz, nbt));
		return compound;
	}
	
	public static <T extends NBTBase> NBTTagList compileList(Class<T> clasz, Iterable<? extends INBTSerializable<T>> nbt)
	{
		NBTTagList list = new NBTTagList();
		for (INBTSerializable<T> object : nbt)
			list.appendTag(object.serializeNBT());
		return list;
	}
	
	private static class NBTListIncrement<T extends NBTBase> implements Iterable<T>
	{
		private final Iterator<T> iterator;
		
		public NBTListIncrement(NBTTagList list)
		{
			this.iterator = new Iter(list);
		}
		
		@Override
		public Iterator<T> iterator()
		{
			return this.iterator;
		}
		
		private static class Iter<T extends NBTBase> implements Iterator<T>
		{
			private final NBTTagList list;
			private final Iterator<Integer> iterator;
			
			private Iter(NBTTagList list)
			{
				this.list = list;
				this.iterator = ObjectHelper.increment(list.tagCount()).iterator();
			}
			
			@Override
			public boolean hasNext()
			{
				return this.iterator.hasNext();
			}
			
			@Override
			public T next()
			{
				return (T)this.list.get(this.iterator.next());
			}
		}
	}
}
