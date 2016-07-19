package continuum.essentials.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface ITileEntityProvider
{
	public default boolean hasTileEnity(IBlockState state)
	{
		return true;
	}
	
	public TileEntity createTileEntity(World world, IBlockState state);
}