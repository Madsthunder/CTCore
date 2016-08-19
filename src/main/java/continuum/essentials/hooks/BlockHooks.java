package continuum.essentials.hooks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
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
	
	public static Block[] allItemsToBlocks(Item... items)
	{
		Block[] blocks = new Block[items.length];
		for(Integer i : ObjectHooks.increment(items.length))
			blocks[i] = Block.getBlockFromItem(items[i]);
		return blocks;
	}
	
	public static EnumFacing[] getFacingsFromAxis(Axis axis)
	{
		if(axis == null)
			return new EnumFacing[0];
		EnumFacing[] facings = new EnumFacing[2];
		int i = 0;
		for(EnumFacing facing : EnumFacing.values())
			if(facing.getAxis() == axis)
				facings[i++] = facing;
		return facings;
	}
}
