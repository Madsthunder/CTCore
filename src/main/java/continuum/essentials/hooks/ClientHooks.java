package continuum.essentials.hooks;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientHooks
{
	@SideOnly(Side.CLIENT)
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	public static ResourceLocation fromMC(String location)
	{
		return new ResourceLocation("minecraft", location);
	}
	
	@SideOnly(Side.CLIENT)
	public static void assignAllItemsToVariant(String variant, Block... blocks)
	{
		assignAllItemsToVariant(variant, 0, blocks);
	}
	
	@SideOnly(Side.CLIENT)
	public static void assignAllItemsToVariant(String variant, int meta, Block... blocks)
	{
		Item item;
		ResourceLocation name;
		for(Block block : blocks)
			if((item = Item.getItemFromBlock(block)) != null && (name = item.getRegistryName()) != null)
				ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(name, variant));
	}

	@SideOnly(Side.CLIENT)
	public static void assignAllModelsToItem(IForgeRegistryEntry entry, String... locations)
	{
		assignAllModelsToItem(ModHooks.getCurrentModid(), entry, locations);
	}

	@SideOnly(Side.CLIENT)
	public static void assignAllModelsToItem(String modid, IForgeRegistryEntry entry, String... locations)
	{
		assignAllModelsToItem(modid, 0, entry, locations);
	}

	@SideOnly(Side.CLIENT)
	public static void assignAllModelsToItem(int metaOffset, IForgeRegistryEntry entry, String... locations)
	{
		assignAllModelsToItem(ModHooks.getCurrentModid(), metaOffset, entry, locations);
	}
	
	@SideOnly(Side.CLIENT)
	public static void assignAllModelsToItem(String modid, int metaOffset, IForgeRegistryEntry entry, String... locations)
	{
		Item item = ForgeRegistries.ITEMS.getValue(entry.getRegistryName());
		if(item != null)
			for (int i : ObjectHooks.increment(locations.length))
				ModelLoader.setCustomModelResourceLocation(item, i + metaOffset, new ModelResourceLocation(modid + ":" + locations[i], "inventory"));
	}
	
	@SideOnly(Side.CLIENT)
	public static void assignAllBlocksToStateMapper(IStateMapper mapper, Block... blocks)
	{
		for(Block block : blocks)
			ModelLoader.setCustomStateMapper(block, mapper);
	}
	public static boolean isFancyEnabled()
	{
		return FMLCommonHandler.instance().getSide() == Side.CLIENT ? mc.isFancyGraphicsEnabled() : false;
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerColorMultiplier(IBlockColor multiplier, Block... blocks)
	{
		mc.getBlockColors().registerBlockColorHandler(multiplier, blocks);
	}

	@SideOnly(Side.CLIENT)
	public static void registerColorMultiplier(IItemColor multiplier, Item... items)
	{
		mc.getItemColors().registerItemColorHandler(multiplier, items);
	}
	
	@SideOnly(Side.CLIENT)
	public static Pair<Integer, Integer> getRotations(ModelRotation rotation)
	{
		String r = rotation.name().toLowerCase();
		Integer x = r.startsWith("x270") ? 270 : r.startsWith("x180") ? 180 : r.startsWith("x90") ? 90 : 0;
		Integer y = r.endsWith("y270") ? 270 : r.endsWith("y180") ? 180 : r.endsWith("y90") ? 90 : 0;
		return Pair.of(x, y);
	}

	@SideOnly(Side.CLIENT)
	public static ModelRotation addRotations(ModelRotation rotation1, ModelRotation rotation2)
	{
		Pair<Integer, Integer> rotations = addRotations(getRotations(rotation1), getRotations(rotation2));
		return ModelRotation.getModelRotation(rotations.getLeft(), rotations.getRight());
	}

	@SideOnly(Side.CLIENT)
	public static ModelRotation addRotations(ModelRotation rotation1, Pair<Integer, Integer> rotations2)
	{
		Pair<Integer, Integer> rotations = addRotations(getRotations(rotation1), rotations2);
		return ModelRotation.getModelRotation(rotations.getLeft(), rotations.getRight());
	}

	@SideOnly(Side.CLIENT)
	public static Pair<Integer, Integer> addRotations(Pair<Integer, Integer> rotations1, ModelRotation rotation2)
	{
		return addRotations(rotations1, getRotations(rotation2));
	}

	@SideOnly(Side.CLIENT)
	public static Pair<Integer, Integer> addRotations(Pair<Integer, Integer> rotations1, Pair<Integer, Integer> rotations2)
	{
		return addRotations(rotations1.getLeft(), rotations1.getRight(), rotations2.getLeft(), rotations2.getRight());
	}

	@SideOnly(Side.CLIENT)
	public static Pair<Integer, Integer> addRotations(Integer x1, Integer y1, Integer x2, Integer y2)
	{
		Integer x = x1 + x2, y = y1 + y2;
		for (; x >= 360;)
			x -= 360;
		for (; y >= 360;)
			y -= 360;
		return Pair.of(x, y);
	}
}
