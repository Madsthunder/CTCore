package continuum.essentials.events;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Cancelable
public class DebugInfoEvent extends Event
{
	private final List<String> original;
	private final List<String> list;
	private final EnumSide side;
	
	public DebugInfoEvent(List<String> list, EnumSide side)
	{
		this.original = Lists.newArrayList(list);
		this.list = list;
		this.side = side;
	}
	
	public List getDebugInfo()
	{
		return this.list;
	}
	
	public EnumSide getInfoSide()
	{
		return this.side;
	}
	
	public static enum EnumSide
	{
		LEFT,
		RIGHT;
	}
	
	public static void modifyLeft(List<String> list)
	{
		DebugInfoEvent event = new DebugInfoEvent(list, EnumSide.LEFT);
		if(MinecraftForge.EVENT_BUS.post(event))
		{
			list.clear();
			list.addAll(event.original);
		}
	}
	
	public static void modifyRight(List<String> list)
	{
		DebugInfoEvent event = new DebugInfoEvent(list, EnumSide.RIGHT);
		if(MinecraftForge.EVENT_BUS.post(event))
		{
			list.clear();
			list.addAll(event.original);
		}
	}
}
