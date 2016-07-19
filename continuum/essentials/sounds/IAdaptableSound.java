package continuum.essentials.sounds;

import net.minecraft.client.audio.ISound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**Before using this, a few quick notes
 * First off, if you want to use step sounds you have to use Block.onFallenUpon() to place step sounds, and a special SoundType class to replace it
 * Second, place sounds need to be placed when Block.onBlockPlacedBy() is called, and replaced in a special SoundType class
 * For Fall sounds, modify it when a LivingFallEvent is posted*/
public interface IAdaptableSound
{
	@SideOnly(Side.CLIENT)
	public ISound getSound(ISound fallback, World world, BlockPos pos);
	public AdaptableSoundType getType();
}
