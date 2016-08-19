package continuum.core.loaders;

import java.util.HashMap;

import continuum.core.client.ModelDirectory;
import continuum.core.mod.Core_EH;
import continuum.core.mod.Core_OH;
import continuum.essentials.mod.CTMod;
import continuum.essentials.mod.ObjectLoader;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientLoader implements ObjectLoader<Core_OH, Core_EH>
{
	public static final ClientLoader I = new ClientLoader();
	
	private ClientLoader()
	{
		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void construction(CTMod<Core_OH, Core_EH> mod)
	{
		ModelLoaderRegistry.registerLoader(new ModelDirectory());
		try
		{
			Launch.classLoader.loadClass("continuum.essentials.events.DebugInfoEvent");
		}
		catch(ClassNotFoundException exception)
		{
			exception.printStackTrace();
		}
	}
	
	@Override
	public String getName()
	{
		return "Client";
	}
	
	@Override
	public Side getSide()
	{
		return Side.CLIENT;
	}
}
