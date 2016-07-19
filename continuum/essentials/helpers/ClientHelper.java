package continuum.essentials.helpers;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Loader;

public class ClientHelper
{
	public static ResourceLocation fromMC(String location)
	{
		return new ResourceLocation("minecraft", location);
	}
	
	public static void assignAllModelsToItem(Block block, String... locations)
	{
		assignAllModelsToItem(Loader.instance().activeModContainer().getModId().toLowerCase(), Item.getItemFromBlock(block), locations);
	}
	
	public static void assignAllModelsToItem(Item item, String... locations)
	{
		assignAllModelsToItem(Loader.instance().activeModContainer().getModId().toLowerCase(), item, locations);
	}
	
	public static void assignAllModelsToItem(String modid, Block block, String... locations)
	{
		assignAllModelsToItem(modid, 0, Item.getItemFromBlock(block), locations);
	}
	
	public static void assignAllModelsToItem(String modid, Item item, String... locations)
	{
		assignAllModelsToItem(modid, 0, item, locations);
	}
	
	public static void assignAllModelsToItem(Integer metaOffset, Block block, String... locations)
	{
		assignAllModelsToItem(Loader.instance().activeModContainer().getModId().toLowerCase(), metaOffset, Item.getItemFromBlock(block), locations);
	}
	
	public static void assignAllModelsToItem(Integer metaOffset, Item item, String... locations)
	{
		assignAllModelsToItem(Loader.instance().activeModContainer().getModId().toLowerCase(), metaOffset, item, locations);
	}
	
	public static void assignAllModelsToItem(String modid, Integer metaOffset, Block block, String... locations)
	{
		assignAllModelsToItem(modid, metaOffset, Item.getItemFromBlock(block), locations);
	}
	
	public static void assignAllModelsToItem(String modid, Integer metaOffset, Item item, String... locations)
	{
		for(Integer i = 0; i < locations.length; i++)
			ModelLoader.setCustomModelResourceLocation(item, i + metaOffset, new ModelResourceLocation(modid + ":" + locations[i], "inventory"));
	}
	
	public static Pair<Integer, Integer> getRotations(ModelRotation rotation)
	{
		String r = rotation.name().toLowerCase();
		Integer x = r.startsWith("x270") ? 270 : r.startsWith("x180") ? 180 : r.startsWith("x90") ? 90 : 0;
		Integer y = r.endsWith("y270") ? 270 : r.endsWith("y180") ? 180 : r.endsWith("y90") ? 90 : 0;
		return Pair.of(x, y);
	}
	
	public static ModelRotation addRotations(ModelRotation rotation1, ModelRotation rotation2)
	{
		Pair<Integer, Integer> rotations = addRotations(getRotations(rotation1), getRotations(rotation2));
		return ModelRotation.getModelRotation(rotations.getLeft(), rotations.getRight());
	}
	
	public static ModelRotation addRotations(ModelRotation rotation1, Pair<Integer, Integer> rotations2)
	{
		Pair<Integer, Integer> rotations = addRotations(getRotations(rotation1), rotations2);
		return ModelRotation.getModelRotation(rotations.getLeft(), rotations.getRight());
	}
	
	public static Pair<Integer, Integer> addRotations(Pair<Integer, Integer> rotations1, ModelRotation rotation2)
	{
		return addRotations(rotations1, getRotations(rotation2));
	}
	
	public static Pair<Integer, Integer> addRotations(Pair<Integer, Integer> rotations1, Pair<Integer, Integer> rotations2)
	{
		return addRotations(rotations1.getLeft(), rotations1.getRight(), rotations2.getLeft(), rotations2.getRight());
	}
	
	public static Pair<Integer, Integer> addRotations(Integer x1, Integer y1, Integer x2, Integer y2)
	{
		Integer x = x1 + x2, y = y1 + y2;
		for(; x >= 360;)
			x -= 360;
		for(; y >= 360;)
			y -= 360;
		return Pair.of(x, y);
	}
}
