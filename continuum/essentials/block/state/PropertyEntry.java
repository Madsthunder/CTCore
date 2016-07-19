package continuum.essentials.block.state;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.base.Optional;

import net.minecraft.block.properties.PropertyHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PropertyEntry<T extends Comparable<T> & IForgeRegistryEntry<T>> extends PropertyHelper<T>
{
	private IForgeRegistry<T> registry;
	private final HashMap<T, String> classToString = new HashMap<T, String>();
	private final HashMap<String, T> stringToClass = new HashMap<String, T>();
	
	public PropertyEntry(String name, Class<T> vclass, IForgeRegistry<T> registry)
	{
		super(name, vclass);
		this.refreshRegistry(registry);
	}
	
	@Override
	public Collection<T> getAllowedValues()
	{
		return registry.getValues();
	}
	
	@Override
    @SideOnly(Side.CLIENT)
	public Optional<T> parseValue(String value)
	{
        return Optional.<T>fromNullable(this.stringToClass.get(value));
    }

	@Override
	public String getName(T value)
	{
		return this.classToString.get(value);
	}
	
	public void refreshRegistry(IForgeRegistry<T> registry)
	{
		this.registry = registry;
		List<T> values = this.registry.getValues();
		this.classToString.clear();
		this.stringToClass.clear();
		if(values.size() != this.classToString.size() || values.size() != this.stringToClass.size())
			for(Entry<ResourceLocation, T> entry : registry.getEntries())
			{
				ResourceLocation l = entry.getKey();
				this.stringToClass.put(l.getResourceDomain().toLowerCase() + "_" + l.getResourcePath().toLowerCase(), entry.getValue());
				this.classToString.put(entry.getValue(), l.getResourceDomain().toLowerCase() + "_" +  l.getResourcePath().toLowerCase());
			}
	}

}
