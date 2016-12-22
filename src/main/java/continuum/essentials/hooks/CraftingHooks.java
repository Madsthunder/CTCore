package continuum.essentials.hooks;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class CraftingHooks
{
	public static InventoryCrafting createDummyCraftingTable()
	{
		return createDummyCraftingTable(3, 3);
	}
	
	public static InventoryCrafting createDummyCraftingTable(Integer width, Integer height)
	{
		return new InventoryCrafting(InventoryHooks.createDummyContainer(), width, height);
	}
	
	public static CraftingManager getCraftingManager()
	{
		return CraftingManager.getInstance();
	}
	
	public static void removeAllRecipesForItem(ItemStack stack)
	{
		List<IRecipe> recipes = getCraftingManager().getRecipeList();
		List<IRecipe> markedForRemoval = Lists.newArrayList();
		ItemStack stack1;
		for (IRecipe recipe : recipes)
			if((stack1 = recipe.getRecipeOutput()) != null && stack.isItemEqual(stack) && stack1.getCount() == stack.getCount())
				markedForRemoval.add(recipe);
		for (IRecipe recipe : markedForRemoval)
			recipes.remove(recipe);
	}
}
