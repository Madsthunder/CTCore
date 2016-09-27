package continuum.essentials.mod;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

import continuum.core.mod.CTCore_OH;
import net.minecraft.crash.CrashReport;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.FMLInjectionData;
import net.minecraftforge.fml.relauncher.Side;

public class CTMod<OH extends ObjectHolder, EH>
{
	private final OH objectHolder;
	private final EH eventHandler;
	private boolean processed = false;
	private boolean constructed = false;
	private boolean preInited = false;
	private boolean inited = false;
	private boolean postInited = false;
	private List<ObjectLoader<OH, EH>> loaders;
	private Logger logger;
	private Side side;
	private int remaining = 0;
	
	public CTMod(OH holder, ObjectLoader<OH, EH>... loaders)
	{
		this(holder, null, loaders);
	}
	
	public CTMod(OH holder, EH eventHandler, ObjectLoader<OH, EH>... loaders)
	{
		this.objectHolder = holder;
		CTCore_OH.mods.put(this.getObjectHolder().getModid().toLowerCase(), this);
		this.loaders = Lists.newArrayList(loaders);
		this.logger = LogManager.getLogger(this.getName());
		if((this.eventHandler = eventHandler) != null)
			MinecraftForge.EVENT_BUS.register(this.getEventHandler());
	}
	
	@Mod.EventHandler
	public void construction(FMLConstructionEvent event)
	{
		if(!this.constructed)
		{
			this.side = event.getSide();
			this.setupLoaders();
			ProgressBar bar = ProgressManager.push("Constructing " + this.getName(), this.remaining = this.getLoaderAmount());
			try
			{
				for (ObjectLoader<OH, EH> loader : this.loaders)
				{
					bar.step("Loading Objects - " + loader.getName());
					this.remaining--;
					loader.construction(this);
				}
				this.printComplete("Construction");
			}
			catch(Exception e)
			{
				this.printFailure("Construction", e);
			}
			finally
			{
				this.constructed = true;
				for (; this.remaining > 0; remaining--)
				{
					bar.step("Don't Read This");
					System.out.println("step");
				}
				ProgressManager.pop(bar);
			}
		}
		else
		{
			this.getLogger().error(this.getName() + " Has Already Been Constructed");
			this.getLogger().error(new Throwable().getMessage());
		}
	}
	
	@Mod.EventHandler
	public void pre(FMLPreInitializationEvent event)
	{
		if(!this.preInited)
		{
			this.side = event.getSide();
			ProgressBar bar = ProgressManager.push("Pre Initializing " + this.getName(), this.remaining = this.getLoaderAmount());
			try
			{
				for (ObjectLoader<OH, EH> loader : this.loaders)
				{
					bar.step("Loading Objects - " + loader.getName());
					remaining--;
					loader.pre(this);
				}
				this.printComplete("PreInitialization");
			}
			catch(Exception e)
			{
				this.printFailure("PreInitialization", e);
			}
			finally
			{
				this.preInited = true;
				for (; this.remaining > 0; --remaining)
					bar.step("Don't Read This");
				ProgressManager.pop(bar);
			}
		}
		else
		{
			this.getLogger().error(this.getName() + " Has Already Been PreInitialized");
			this.getLogger().error(new Throwable().getMessage());
		}
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		if(!this.inited)
		{
			ProgressBar bar = ProgressManager.push("Initializing " + this.getName(), this.remaining = this.getLoaderAmount());
			try
			{
				for (ObjectLoader<OH, EH> loader : this.loaders)
				{
					bar.step("Loading Objects - " + loader.getName());
					remaining--;
					loader.init(this);
				}
				this.printComplete("Initialization");
			}
			catch(Exception e)
			{
				this.printFailure("Initialization", e);
			}
			finally
			{
				this.inited = true;
				for (; this.remaining > 0; remaining--)
				{
					bar.step("Don't Read This");
				}
				ProgressManager.pop(bar);
			}
		}
		else
		{
			this.getLogger().error(this.getName() + " Has Already Been Initialized");
			this.getLogger().error(new Throwable().getMessage());
		}
	}
	
	@Mod.EventHandler
	public void post(FMLPostInitializationEvent event)
	{
		if(!this.postInited)
		{
			ProgressBar bar = ProgressManager.push("Post Initializing " + this.getName(), this.remaining = this.getLoaderAmount());
			try
			{
				for (ObjectLoader<OH, EH> loader : this.loaders)
				{
					bar.step("Loading Objects - " + loader.getName());
					remaining--;
					loader.post(this);
				}
				this.printComplete("Post Initialization");
			}
			catch(Exception e)
			{
				this.printFailure("Post Initialization", e);
			}
			finally
			{
				this.postInited = true;
				for (; this.remaining > 0; remaining--)
				{
					bar.step("Don't Read This");
				}
				ProgressManager.pop(bar);
			}
		}
		else
		{
			this.getLogger().error(this.getName() + " Has Already Been Post Initialized");
			this.getLogger().error(new Throwable().getMessage());
		}
	}
	
	private void setupLoaders()
	{
		if(!this.processed)
		{
			List<ObjectLoader<OH, EH>> loaders = Lists.newArrayList();
			Side side;
			for (ObjectLoader<OH, EH> loader : this.loaders)
				if((side = loader.getSide()) == null ? true : this.side == side)
					loaders.add(loader);
			this.loaders = loaders;
			this.processed = true;
		}
		else
		{
			this.getLogger().error(this.getName() + "'s Loaders Have Already Been Processed");
			this.getLogger().error(new Throwable().getMessage());
		}
	}
	
	private String getPrintStart()
	{
		return this.getName() + " " + this.getVersion() + " For MC " + MinecraftForge.MC_VERSION + " " + this.getSide() + " Has ";
	}
	
	public void printComplete(String stage)
	{
		this.getLogger().trace(this.getPrintStart() + "Completed " + stage + ".");
	}
	
	public void printFailure(String stage, Exception e)
	{
		this.getLogger().error(CrashReport.makeCrashReport(e, this.getPrintStart() + "Failed " + stage + ". " + this.getName() + " May Not Work Correctly.").getCompleteReport());
	}
	
	public OH getObjectHolder()
	{
		return this.objectHolder;
	}
	
	public EH getEventHandler()
	{
		return this.eventHandler;
	}
	
	public final String getModid()
	{
		return this.getObjectHolder().getModid();
	}
	
	public final String getName()
	{
		return this.getObjectHolder().getName();
	}
	
	public final String getVersion()
	{
		return this.getObjectHolder().getVersion();
	}
	
	public final Side getSide()
	{
		return this.side;
	}
	
	public Logger getLogger()
	{
		return this.logger;
	}
	
	public List<ObjectLoader<OH, EH>> getLoaders()
	{
		return this.loaders;
	}
	
	public final int getLoaderAmount()
	{
		return this.getLoaders().size();
	}
}
