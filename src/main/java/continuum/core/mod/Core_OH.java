package continuum.core.mod;

import java.util.HashMap;

import com.google.common.collect.Maps;

import continuum.essentials.mod.CTMod;
import continuum.essentials.mod.ObjectHolder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.fml.common.Mod;

public class Core_OH implements ObjectHolder
{
	private static Core_OH holder;
	
	static Core_OH getHolder(Class<Core> clasz)
	{
		return holder == null ? new Core_OH(clasz.getAnnotation(Mod.class)) : holder;
	}
	
	private final Mod mod;
	
	private Core_OH(Mod mod)
	{
		this.mod = mod;
	}
	
	@Override
	public String getModid() 
	{
		return this.mod.modid();
	}

	@Override
	public String getName() 
	{
		return this.mod.name();
	}

	@Override
	public String getVersion() 
	{
		return this.mod.version();
	}
	
	public static final HashMap<ResourceLocation, IModel> models = Maps.newHashMap();
	
	public static final HashMap<String, CTMod> mods = Maps.newHashMap();
}
