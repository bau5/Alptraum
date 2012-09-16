package _bau5.alptraum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

public class AlpRecipeManager 
{
	private HashMap<String, Boolean> recipes = new HashMap<String, Boolean>();
	public Map researchRecipes = new HashMap();
	private WorldSensitiveInfo alpWorldInfo;
	private Alptraum core = Alptraum.instance();
	
	public AlpRecipeManager(WorldSensitiveInfo worldSensitiveInfo)
	{
		alpWorldInfo = worldSensitiveInfo;
		this.initRecipes();
	}
	private void initRecipes()
	{
		recipes.put("Debug", true);
		recipes.put("Nightmare Axe", false);
		recipes.put("Nightmare Pickaxe", false);
		recipes.put("Nightmare Sword", false);
		recipes.put("Nightmare Sphere", false);
		recipes.put("Nightmare Orb", false);
		researchRecipes.put(Alptraum.shiftingResidue.shiftedIndex, new ItemStack(Alptraum.bookOne, 1, 0));
		updateRecipes();
	}
	public void enableRecipe(String string, boolean state)
	{
		recipes.put(string, state);
		updateRecipes();

		Alptraum.logP("Recipe Set: " +string +" @ " +state);
	}
	public void enableResearch(int itemID, ItemStack stackResult)
	{
		 researchRecipes.put(Integer.valueOf(itemID), stackResult);
		 core.logP(itemID + " " +stackResult.toString());
	}
	private void updateRecipes() 
	{
		if(recipes.get("Nightmare Pickaxe"))
		{
			GameRegistry.addRecipe(new ItemStack(Alptraum.shiftingPickaxe, 1), new Object[]
					{
				 		"@@@", " # ", " # ", Character.valueOf('#'), Item.stick, Character.valueOf('@'), Alptraum.alpMultiItem
					});
		}
		if(recipes.get("Nightmare Sword"))
		{
			GameRegistry.addRecipe(new ItemStack(Alptraum.shiftingSword, 1), new Object[]
					{
						"#", "@", "@", Character.valueOf('#'), Item.stick, Character.valueOf('@'), Alptraum.alpMultiItem
					});
		}
		if(recipes.get("Nightmare Orb"))
		{
			GameRegistry.addRecipe(new ItemStack(Alptraum.shiftingOrb, 1, 50), new Object[]
					{
						" # ", "#@#", " # ",
						Character.valueOf('#'), Alptraum.shiftingIngot,
						Character.valueOf('@'), Alptraum.shiftingResidue
					});
		}
		if(recipes.get("Nightmare Sphere"))
		{
			GameRegistry.addRecipe(new ItemStack(Alptraum.shiftingSphere, 1, 50), new Object[]
					{
						" # ", "#@#", " # ", Character.valueOf('#'), Alptraum.alpMultiItem, Character.valueOf('@'), Alptraum.shiftingOrb
					});
		}
		if(recipes.get("Debug"))
		{
			GameRegistry.addRecipe(new ItemStack(Alptraum.shiftingAxe, 1), new Object[]
					{
						"#",Character.valueOf('#'), Item.stick
					});
			GameRegistry.addRecipe(new ItemStack(Alptraum.shiftingPickaxe, 1), new Object[]
					{
						"##",Character.valueOf('#'), Item.stick
					});
			GameRegistry.addRecipe(new ItemStack(Alptraum.shiftingSword, 1), new Object[]
					{
						"###",Character.valueOf('#'), Item.stick
					});
//			GameRegistry.addRecipe(new ItemStack(Alptraum.alpMultiItem, 64, 2), new Object[]
//					{
//						"d",Character.valueOf('d'), Block.dirt
//					});
			GameRegistry.addRecipe(new ItemStack(Alptraum.shiftingOrb, 1), new Object[]
					{
						"dd",Character.valueOf('d'), Block.dirt
					});
			GameRegistry.addRecipe(new ItemStack(Alptraum.shiftingSphere, 1), new Object[]
					{
						"ddd",Character.valueOf('d'), Block.dirt
					});
			GameRegistry.addRecipe(new ItemStack(Alptraum.blockUtility, 1, 0), new Object[]
					{
						"d", Character.valueOf('d'), Block.dirt
					});
		}
	}
	public ItemStack getResearchResult(ItemStack item) 
    {
        return (ItemStack)researchRecipes.get(Integer.valueOf(item.itemID));
    }
	public HashMap getRecipesHM()
	{
		return recipes;
	}
}
