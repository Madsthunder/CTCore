package net.minecraftforge.fml.common.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.IForgeRegistry.AddCallback;
import net.minecraftforge.fml.common.registry.IForgeRegistry.ClearCallback;
import net.minecraftforge.fml.common.registry.IForgeRegistry.CreateCallback;

public class Registries
{
	public static <V extends IForgeRegistryEntry<V>> FMLControlledNamespacedRegistry<V> createRegistry(Class<? extends V> clasz, Integer min, Integer max)
	{
		return createRegistry(clasz, null, min, max, null, null, null);
	}
	
	public static <V extends IForgeRegistryEntry<V>> FMLControlledNamespacedRegistry<V> createRegistry(Class<? extends V> clasz, ResourceLocation defaultValue, Integer min, Integer max)
	{
		return createRegistry(clasz, defaultValue, min, max, null, null, null);
	}
	
	public static <V extends IForgeRegistryEntry<V>> FMLControlledNamespacedRegistry<V> createRegistry(Class<? extends V> clasz, Integer min, Integer max, CreateCallback<V> create, ClearCallback<V> clear, AddCallback<V> add)
	{
		return createRegistry(clasz, null, min, max, create, clear, add);
	}
	
	public static <V extends IForgeRegistryEntry<V>> FMLControlledNamespacedRegistry<V> createRegistry(Class<? extends V> clasz, ResourceLocation defaultValue, Integer min, Integer max, CreateCallback<V> create, ClearCallback<V> clear, AddCallback<V> add)
	{
		return new FMLControlledNamespacedRegistry(defaultValue, min, max, clasz, add, clear, create);
	}
}
