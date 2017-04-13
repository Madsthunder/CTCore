package continuum.core.plugins;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLCallHook;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.Name("Continuum: Core")
@IFMLLoadingPlugin.MCVersion("1.11.2")
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
	public String getAccessTransformerClass()
	{
		return null;
	}
	
	@Override
	public void injectData(Map<String, Object> data)
	{
		
	}
}
