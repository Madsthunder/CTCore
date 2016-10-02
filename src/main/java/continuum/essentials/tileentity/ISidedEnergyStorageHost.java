package continuum.essentials.tileentity;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

public interface ISidedEnergyStorageHost extends IEnergyStorage
{
	void setSide(SidedEnergyStorage storage);
	SidedEnergyStorage getCurrentSide();
	SidedEnergyStorage getSidedEnergyStorage(EnumFacing side);
	
	public static class Impl extends EnergyStorage implements ISidedEnergyStorageHost
	{
		private final Map<EnumFacing, SidedEnergyStorage> sides = Maps.newHashMap();
		private SidedEnergyStorage current;
		
		public Impl(int capacity, int maxReceive, int maxExtract)
		{
			super(capacity, maxReceive, maxExtract);
			for(EnumFacing side : EnumFacing.values())
				this.sides.put(side, new SidedEnergyStorage(this, side));
		}

		@Override
		public void setSide(SidedEnergyStorage storage)
		{
			this.current = storage;
		}

		@Override
		public SidedEnergyStorage getCurrentSide()
		{
			return this.current;
		}

		@Override
		public SidedEnergyStorage getSidedEnergyStorage(EnumFacing side)
		{
			return this.sides.get(side);
		}
	}
}
