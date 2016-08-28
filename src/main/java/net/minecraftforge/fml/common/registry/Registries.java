package net.minecraftforge.fml.common.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.IForgeRegistry.AddCallback;
import net.minecraftforge.fml.common.registry.IForgeRegistry.ClearCallback;
import net.minecraftforge.fml.common.registry.IForgeRegistry.CreateCallback;
import net.minecraftforge.fml.common.registry.IForgeRegistry.SubstitutionCallback;

public class Registries
{
	public static <V extends IForgeRegistryEntry<V>> FMLControlledNamespacedRegistry<V> createRegistry(Class<? extends V> clasz, int min, int max)
	{
		return createRegistry(clasz, null, min, max, null, null, null, null);
	}
	
	public static <V extends IForgeRegistryEntry<V>> FMLControlledNamespacedRegistry<V> createRegistry(Class<? extends V> clasz, ResourceLocation defaultValue, int min, int max)
	{
		return createRegistry(clasz, defaultValue, min, max, null, null, null, null);
	}
	
	public static <V extends IForgeRegistryEntry<V>> FMLControlledNamespacedRegistry<V> createRegistry(Class<? extends V> clasz, int min, int max, CreateCallback<V> create, ClearCallback<V> clear, AddCallback<V> add, SubstitutionCallback<V> substitution)
	{
		return createRegistry(clasz, null, min, max, create, clear, add, substitution);
	}
	
	public static <V extends IForgeRegistryEntry<V>> FMLControlledNamespacedRegistry<V> createRegistry(Class<? extends V> clasz, ResourceLocation defaultValue, int min, int max, CreateCallback<V> create, ClearCallback<V> clear, AddCallback<V> add, SubstitutionCallback<V> substitution)
	{
		return new FMLControlledNamespacedRegistry(defaultValue, min, max, clasz, HashBiMap.create(), add, clear, create, substitution);
	}
}
