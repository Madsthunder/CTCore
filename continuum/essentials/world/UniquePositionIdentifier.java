package continuum.essentials.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class UniquePositionIdentifier
{
	public final IBlockAccess access;
	public final BlockPos pos;
	
	public UniquePositionIdentifier(IBlockAccess access, BlockPos pos)
	{
		this.access = access;
		this.pos = pos;
	}
}
