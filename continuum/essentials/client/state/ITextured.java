package continuum.essentials.client.state;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ITextured
{
	@SideOnly(Side.CLIENT)
	public ResourceLocation getTexture(IBlockState state);
}
