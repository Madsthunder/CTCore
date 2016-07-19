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
 *         packets. Uses the variable {@link CTTileEntity#shouldSyncPackets} to
 *         detect whether packets should be sent and recieved. BY DEFAULT
 *         {@link CTTileEntity#shouldSyncPackets} IS FALSE, DEFINE WHETHER THIS
 *         IS TRUE IN YOUR TILEENTITY CONSTRUCTOR.
 *
 */
public abstract class CTTileEntity extends TileEntity
{
	public final Boolean shouldSyncPackets;
	
	public CTTileEntity()
	{
		this(false);
	}
	
	public CTTileEntity(Boolean packets)
	{
		this.shouldSyncPackets = packets;
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
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		if(this.shouldSyncPackets)
			return new SPacketUpdateTileEntity(this.pos, 0, this.writeItemsToNBT());
		return null;
	}
	
	@Override
	public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet)
	{
		if(this.shouldSyncPackets)
			this.readItemsFromNBT(packet.getNbtCompound());
	}
}
