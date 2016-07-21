package continuum.core.mod;

import java.util.HashMap;

import com.google.common.collect.Maps;

import continuum.essentials.mod.CTMod;
import continuum.essentials.mod.ObjectHolder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;

public class Core_OH implements ObjectHolder
{
	private static Core_OH holder;
	
	static Core_OH getHolder(ModContainer mod)
	{
		return holder == null ? new Core_OH(mod) : holder;
	}
	
	private final ModContainer mod;
	
	private Core_OH(ModContainer mod)
	{
		this.mod = mod;
	}
	
	@Override
	public String getModid()
	{
		return this.mod.getModId();
	}
	
	@Override
	public String getName()
	{
		return this.mod.getName();
	}
	
	@Override
	public String getVersion()
	{
		return this.mod.getVersion();
	}
	
	public static final HashMap<ResourceLocation, IModel> models = Maps.newHashMap();
	
	public static final HashMap<String, CTMod> mods = Maps.newHashMap();
}
