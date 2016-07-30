package continuum.essentials.hooks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class InventoryHooks
{
	public static Container createDummyContainer()
	{
		return new Container()
		{
			public boolean canInteractWith(EntityPlayer player)
			{
				return true;
			}
		};
	}
}
