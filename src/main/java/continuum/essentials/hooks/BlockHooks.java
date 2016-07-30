package continuum.essentials.hooks;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;

public class BlockHooks
{
	public static List<BlockSnapshot> setBlockStateWithSnapshots(World world, BlockPos pos, IBlockState state)
	{
		world.captureBlockSnapshots = true;
		world.setBlockState(pos, state);
		world.captureBlockSnapshots = false;
		List<BlockSnapshot> snapshots = (List<BlockSnapshot>)world.capturedBlockSnapshots.clone();
		world.capturedBlockSnapshots.clear();
		return snapshots;
	}
	
	public static void restoreBlockSnapshots(World world, List<BlockSnapshot> snapshots)
	{
		for (BlockSnapshot snapshot : snapshots)
		{
			world.restoringBlockSnapshots = true;
			snapshot.restore(true, false);
			world.restoringBlockSnapshots = false;
		}
	}
}
