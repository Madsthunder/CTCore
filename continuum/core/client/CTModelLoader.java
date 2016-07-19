package continuum.core.client;

import continuum.core.mod.Core_EH;
import continuum.core.mod.Core_OH;
import continuum.essentials.mod.CTMod;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class CTModelLoader implements ICustomModelLoader
{
	private static ResourceLocation currentLocation;
	private IResourceManager manager;
	
	@Override
	public void onResourceManagerReload(IResourceManager manager)
	{
		this.manager = manager;
	}

	@Override
	public boolean accepts(ResourceLocation location)
	{
		if(Core_OH.models.containsKey(location))
			return true;
		return false;
	}

	@Override
	public IModel loadModel(ResourceLocation location)
	{
		this.currentLocation = location;
		return Core_OH.models.get(location);
	}

}
