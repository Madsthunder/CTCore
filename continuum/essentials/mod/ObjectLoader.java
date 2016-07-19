package continuum.essentials.mod;

import net.minecraftforge.fml.relauncher.Side;

public interface ObjectLoader<OH extends ObjectHolder, EH> 
{
	public default void construction(CTMod<OH, EH> mod)
	{
		
	}
	
	public default void pre(CTMod<OH, EH> mod)
	{
		
	}
	
	public default void init(CTMod<OH, EH> mod)
	{
		
	}
	
	public default void post(CTMod<OH, EH> mod)
	{
		
	}
	
	public default Side getSide()
	{
		return null;
	}
	
	public String getName();
}
