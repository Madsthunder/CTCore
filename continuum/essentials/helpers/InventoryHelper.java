package continuum.essentials.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class InventoryHelper
{
	public static Container createDummyContainer()
	{
		return new Container() { public boolean canInteractWith(EntityPlayer player) { return true; } };
	}
}
