package continuum.core.mod;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import continuum.core.loaders.ClientLoader;
import continuum.essentials.mod.CTMod;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CTCore_Mod extends DummyModContainer
{
	private CTMod<CTCore_OH, CTCore_EH> mod;
	
	public CTCore_Mod()
	{
		super(new ModMetadata());
		ModMetadata metadata = this.getMetadata();
		metadata.modId = "ctcore";
		metadata.name = "Continuum: Core";
		metadata.version = "2.4.0";
	}
    
    @Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
    	bus.register(this);
    	return true;
    }
    
    @Subscribe
    public void construction(FMLConstructionEvent event)
    {
    	(this.mod = new CTMod(CTCore_OH.getHolder(this), new CTCore_EH(), ClientLoader.I)).construction(event);
    }
    
    @Subscribe
    public void pre(FMLPreInitializationEvent event)
    {
    	this.mod.pre(event);
    }
    
    @Subscribe
    public void init(FMLInitializationEvent event)
    {
    	this.mod.init(event);
    }
    
    @Subscribe
    public void post(FMLPostInitializationEvent event)
    {
    	this.mod.post(event);
    }
}
