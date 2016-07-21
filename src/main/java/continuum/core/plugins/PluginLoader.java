package continuum.core.plugins;

import java.io.File;
import java.util.List;
import java.util.Map;

import continuum.essentials.events.DebugInfoEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.Name("Continuum: Core")
@IFMLLoadingPlugin.MCVersion("1.10.2")
@IFMLLoadingPlugin.TransformerExclusions({ "org", "com", "joptsimple", "oshi" })
public class PluginLoader implements IFMLLoadingPlugin
{
	@Override
	public String[] getASMTransformerClass()
	{
		return new String[] { "continuum.core.plugins.transformers.DebugEventTransformer" };
	}

	@Override
	public String getModContainerClass()
	{
		return "continuum.core.mod.CTCore_Mod";
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
	}

	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}
}
