package continuum.essentials.helpers;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

public class ModHooks
{
	private static final Loader loader = Loader.instance();
	
	public static ModContainer getCurrentModContainer()
	{
		return loader.activeModContainer();
	}
	
	public static String getCurrentModid()
	{
		return getCurrentModContainer().getModId();
	}
	
	public static String getCurrentName()
	{
		return getCurrentModContainer().getName();
	}
	
	public static String getCurrentVersion()
	{
		return getCurrentModContainer().getVersion();
	}
}
