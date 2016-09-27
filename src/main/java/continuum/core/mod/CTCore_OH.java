package continuum.core.mod;

import java.util.HashMap;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

import continuum.essentials.mod.CTMod;
import continuum.essentials.mod.ObjectHolder;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.fml.common.ModContainer;

public class CTCore_OH implements ObjectHolder
{
	private static CTCore_OH holder;
	
	static CTCore_OH getHolder(ModContainer mod)
	{
		return holder == null ? new CTCore_OH(mod) : holder;
	}
	
	private final ModContainer mod;
	
	private CTCore_OH(ModContainer mod)
	{
		this.mod = mod;
	}
	
	public ModContainer getMod()
	{
		return this.mod;
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
	
	public static final HashMap<ResourceLocation, Function<Object, IModel>> models = Maps.newHashMap();
	
	public static final HashMap<String, CTMod> mods = Maps.newHashMap();
}
