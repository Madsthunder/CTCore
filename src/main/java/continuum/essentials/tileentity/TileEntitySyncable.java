package continuum.essentials.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 
 * @author Madsthunder Convenience class primariy used to send and recieve
 *         packets. Uses the variable {@link TileEntitySyncable#shouldSyncTags} to
 *         detect whether packets should be sent and recieved. BY DEFAULT
 *         {@link TileEntitySyncable#shouldSyncTags} IS FALSE, DEFINE WHETHER THIS
 *         IS TRUE IN YOUR TILEENTITY CONSTRUCTOR.
 *
 */
public abstract class TileEntitySyncable extends TileEntity
{
	public final Boolean shouldSyncTags;
	
	public TileEntitySyncable()
	{
		this(false);
	}
	
	public TileEntitySyncable(Boolean packets)
	{
		this.shouldSyncTags = packets;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.merge(this.writeItemsToNBT());
		return compound;
	}
	
	/**
	 * 
	 * @return An {@link NBTTagCompound} that is later merged with another
	 *         {@link NBTTagCompound} in {@link #writeToNBT(NBTTagCompound)}
	 */
	public abstract NBTTagCompound writeItemsToNBT();
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.readItemsFromNBT(compound);
	}
	
	/**
	 * 
	 * @param compound
	 *            The {@link NBTTagCompound} from
	 *            {@link #readFromNBT(NBTTagCompound)}
	 */
	public abstract void readItemsFromNBT(NBTTagCompound compound);
	
	@Override
	public NBTTagCompound getUpdateTag()
	{
		NBTTagCompound compound = super.getUpdateTag();
		if(this.shouldSyncTags) compound.setTag("ItemsNBT", this.writeItemsToNBT());
		return compound;
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound compound)
	{
		NBTTagCompound items = compound.getCompoundTag("ItemsNBT");
		compound.removeTag("ItemsNBT");
		super.handleUpdateTag(compound);
		this.readItemsFromNBT(compound);
	}
}
