package continuum.essentials.tileentity;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;

public class SidedEnergyStorage implements IEnergyStorage
{
	private final ISidedEnergyStorageHost host;
	private final EnumFacing side;
	
	public SidedEnergyStorage(ISidedEnergyStorageHost host, EnumFacing side)
	{
		this.host = host;
		this.side = side;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate)
	{
		SidedEnergyStorage storage = this.host.getCurrentSide();
		this.host.setSide(this);
		int receive = this.host.receiveEnergy(maxReceive, simulate);
		this.host.setSide(storage);
		return receive;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate)
	{
		SidedEnergyStorage storage = this.host.getCurrentSide();
		this.host.setSide(this);
		int extract = this.host.extractEnergy(maxExtract, simulate);
		this.host.setSide(storage);
		return extract;
	}

	@Override
	public int getEnergyStored()
	{
		SidedEnergyStorage storage = this.host.getCurrentSide();
		this.host.setSide(this);
		int stored = this.host.getEnergyStored();
		this.host.setSide(storage);
		return stored;
	}

	@Override
	public int getMaxEnergyStored()
	{
		SidedEnergyStorage storage = this.host.getCurrentSide();
		this.host.setSide(this);
		int max = this.host.getEnergyStored();
		this.host.setSide(storage);
		return max;
	}

	@Override
	public boolean canExtract()
	{
		SidedEnergyStorage storage = this.host.getCurrentSide();
		this.host.setSide(this);
		boolean canExtract = this.host.canExtract();
		this.host.setSide(storage);
		return canExtract;
	}

	@Override
	public boolean canReceive()
	{
		SidedEnergyStorage storage = this.host.getCurrentSide();
		this.host.setSide(this);
		boolean canReceive = this.host.canReceive();
		this.host.setSide(storage);
		return canReceive;
	}
	
	public EnumFacing getSide()
	{
		return this.side;
	}
}
