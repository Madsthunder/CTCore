package continuum.essentials.client.colors;

import continuum.essentials.hooks.ObjectHooks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;

public class LeavesColorMultiplier implements IBlockColor, IItemColor
{
	private LeavesColorMultiplier()
	{
		
	}
	
	@Override
	public int colorMultiplier(IBlockState state, IBlockAccess access, BlockPos pos, int tintIndex)
	{
		return ObjectHooks.anyNull(access, pos) ? ColorizerFoliage.getFoliageColorBasic() : BiomeColorHelper.getFoliageColorAtPos(access, pos);
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex)
	{
		return ColorizerFoliage.getFoliageColorBasic();
	}
	
	public static final LeavesColorMultiplier I = new LeavesColorMultiplier();
}
