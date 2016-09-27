package continuum.core.mod;

import java.util.ArrayList;

import continuum.essentials.sounds.IAdaptableSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class CTCore_EH
{
	@SideOnly(Side.CLIENT)
	private static ModelLoader modelLoader;
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void addItemTooltip(ItemTooltipEvent event)
	{
		if(event.isShowAdvancedItemTooltips())
		{
			ArrayList<String> oresList = new ArrayList<String>();
			for (Integer id : OreDictionary.getOreIDs(event.getItemStack()))
				oresList.add(OreDictionary.getOreName(id));
			if(!oresList.isEmpty())
				event.getToolTip().add("Ore Names:");
			event.getToolTip().addAll(oresList);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onSoundPlay(PlaySoundEvent event)
	{
		ISound sound = event.getResultSound();
		SoundEvent sevent;
		if((sevent = ForgeRegistries.SOUND_EVENTS.getValue(sound.getSoundLocation())) instanceof IAdaptableSound)
		{
			event.setResultSound(((IAdaptableSound)sevent).getSound(sound, Minecraft.getMinecraft().theWorld, new BlockPos(MathHelper.floor_float(sound.getXPosF()), MathHelper.floor_float(sound.getYPosF()), MathHelper.floor_float(sound.getZPosF()))));
			this.onSoundPlay(event);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onModelBake(ModelBakeEvent event)
	{
		modelLoader = event.getModelLoader();
	}
	
	@SideOnly(Side.CLIENT)
	public static ModelLoader getModelLoader()
	{
		return modelLoader;
	}
}
