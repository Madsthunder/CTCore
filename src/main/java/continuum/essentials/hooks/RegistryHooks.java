package continuum.essentials.hooks;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public class RegistryHooks
{
	private static class EmptyRegistry<I extends IForgeRegistryEntry<I>> implements IForgeRegistry<I>
	{
		private final Class<I> clasz;
		
		public EmptyRegistry(Class<I> clasz)
		{
			this.clasz = clasz;
		}
		
		@Override
		public Iterator<I> iterator()
		{
			return Iterators.emptyIterator();
		}
		
		@Override
		public Class<I> getRegistrySuperType()
		{
			return null;
		}
		
		@Override
		public void register(I value)
		{
			
		}
		
		@Override
		public void registerAll(I... values)
		{
			
		}
		
		@Override
		public boolean containsKey(ResourceLocation key)
		{
			return false;
		}
		
		@Override
		public boolean containsValue(I value)
		{
			return false;
		}
		
		@Override
		public I getValue(ResourceLocation key)
		{
			return null;
		}
		
		@Override
		public ResourceLocation getKey(I value)
		{
			return null;
		}
		
		@Override
		public Set<ResourceLocation> getKeys()
		{
			return Sets.newHashSet();
		}
		
		@Override
		public List<I> getValues()
		{
			return Lists.newArrayList();
		}
		
		@Override
		public Set<Entry<ResourceLocation, I>> getEntries()
		{
			return Sets.newHashSet();
		}
		
		@Override
		public <T> T getSlaveMap(ResourceLocation slaveMapName, Class<T> type)
		{
			return null;
		}
		
	}
}
