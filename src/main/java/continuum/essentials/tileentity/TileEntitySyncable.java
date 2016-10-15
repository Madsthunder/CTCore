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
 *         packets. Uses the variable {@link TileEntitySyncable#syncTags} to
 *         detect whether packets should be sent and recieved. BY DEFAULT
 *         {@link TileEntitySyncable#syncTags} IS FALSE, DEFINE WHETHER THIS
 *         IS TRUE IN YOUR TILEENTITY CONSTRUCTOR.
 *
 */
public class TileEntitySyncable extends TileEntity
{
	public final boolean syncTags;
	public final boolean syncCaps;
	public final boolean syncPackets;
	
	public TileEntitySyncable()
	{
		this(true, true, true);
	}
	
	public TileEntitySyncable(boolean tags, boolean caps, boolean packets)
	{
		this.syncTags = tags;
		this.syncCaps = caps;
		this.syncPackets = packets;
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
	public NBTTagCompound writeItemsToNBT()
	{
		return new NBTTagCompound();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		BlockPos pos = !compound.hasKey("x") || !compound.hasKey("y") || !compound.hasKey("z") ? this.pos : null;
		super.readFromNBT(compound);
		this.readItemsFromNBT(compound);
		this.pos = pos == null ? this.pos : pos;
	}
	
	/**
	 * 
	 * @param compound
	 *            The {@link NBTTagCompound} from
	 *            {@link #readFromNBT(NBTTagCompound)}
	 */
	public void readItemsFromNBT(NBTTagCompound compound)
	{
		
	}
	
	@Override
	public NBTTagCompound getUpdateTag()
	{
		NBTTagCompound compound = new NBTTagCompound();
		BlockPos pos = this.getPos();
		compound.setInteger("x", pos.getX());
		compound.setInteger("y", pos.getY());
		compound.setInteger("z", pos.getZ());
		if(this.syncTags) compound.merge(this.writeItemsToNBT());
		NBTTagCompound compound1 = new NBTTagCompound();
		super.readFromNBT(compound1);
		if(this.syncCaps && compound1.hasKey("ForgeCaps"))
			compound.setTag("ForgeCaps", compound1.getCompoundTag("ForgeCaps"));
		return compound;
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		return new SPacketUpdateTileEntity(this.getPos(), 0, this.getUpdateTag());
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound compound)
	{
		this.readFromNBT(compound);
	}
	
	@Override
	public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet)
	{
		this.readFromNBT(packet.getNbtCompound());
	}
}
