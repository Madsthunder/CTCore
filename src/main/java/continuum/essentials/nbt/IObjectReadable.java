package continuum.essentials.nbt;

import net.minecraft.nbt.NBTTagCompound;

public interface IObjectReadable<T>
{
	public T getObject(NBTTagCompound compound);
}
