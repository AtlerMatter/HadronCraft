package com.HadronCraft.handler;

import com.HadronCraft.block.HcBlocks;
import com.HadronCraft.item.HcItems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class CraftingManager {
	
	public static void mainRegistry(){
		addCraftingRecipes();
		addSmeltingRecipes();
	}
	
	public static void addCraftingRecipes(){
		//Shaped Recipes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(HcItems.plateSteel, 1), "SS ", "SS ", 'S', "ingotSteel"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(HcItems.plateBronze, 1), "BB ", "BB ", 'B', "ingotBronze"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(HcItems.circuitBasic, 1), "CCC", "RGR", "CCC", 'C', "cableCopper", 'R', Items.redstone , 'G', Items.gold_ingot));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(HcItems.circuitAdvanced, 1), "CCC", "GBG", "CCC", 'C', "cableCopper", 'B', "circuitBasic" , 'G', Items.gold_ingot));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(HcItems.circuitElite, 1), "CCC", "DAD", "CCC", 'C', "cableCopper", 'D', Items.diamond , 'A', "circuitAdvanced"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(HcBlocks.cableCopper, 3), " C ", " C ", " C ", 'C', "ingotCopper"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(HcItems.batteryBasic, 1), " T ", "TCT", "TRT", 'T', "ingotTin", 'C', Items.coal, 'R', Items.redstone));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(HcBlocks.generatorCombustionIdle, 1), "CFC", "SBS", "CLC", 'L', Items.flint_and_steel, 'C', "cableCopper", 'F', Blocks.furnace, 'S', "ingotSteel", 'B', "circuitBasic"));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(HcItems.steelPick, 1), "SSS", " W ", " W ", 'S', "ingotSteel", 'W', Items.stick));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(HcItems.steelAxe, 1), "SS ", "SW ", " W ", 'S', "ingotSteel", 'W', Items.stick));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(HcItems.steelShovel, 1), " S ", " W ", " W ", 'S', "ingotSteel", 'W', Items.stick));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(HcItems.steelSword, 1), " S ", " S ", " W ", 'S', "ingotSteel", 'W', Items.stick));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(HcItems.steelHoe, 1), "SS ", " W ", " W ", 'S', "ingotSteel", 'W', Items.stick));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(HcItems.bronzePick, 1), "BBB", " W ", " W ", 'B', "ingotBronze", 'W', Items.stick));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(HcItems.bronzeAxe, 1), "BB ", "BW ", " W ", 'B', "ingotBronze", 'W', Items.stick));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(HcItems.bronzeShovel, 1), " B ", " W ", " W ", 'B', "ingotBronze", 'W', Items.stick));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(HcItems.bronzeSword, 1), " B ", " B ", " W ", 'B', "ingotBronze", 'W', Items.stick));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(HcItems.bronzeHoe, 1), "BB ", " W ", " W ", 'B', "ingotBronze", 'W', Items.stick));
		
		//Shapeless Recipes
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(HcItems.dustBronze), "dustTin", "dustCopper", "dustCopper"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(HcItems.dustSteel), Items.iron_ingot, Items.coal, Items.coal));
	}
	
	public static void addSmeltingRecipes(){
		GameRegistry.addSmelting(HcBlocks.oreCopper, new ItemStack(HcItems.ingotCopper), 0.7F);
		GameRegistry.addSmelting(HcBlocks.oreTin, new ItemStack(HcItems.ingotTin), 0.7F);
		GameRegistry.addSmelting(HcBlocks.oreSilver, new ItemStack(HcItems.ingotSilver), 0.7F);
		GameRegistry.addSmelting(HcItems.dustBronze, new ItemStack(HcItems.ingotBronze), 1.0F);
		GameRegistry.addSmelting(HcItems.dustSteel, new ItemStack(HcItems.ingotSteel), 1.0F);
		GameRegistry.addSmelting(HcItems.dustCopper, new ItemStack(HcItems.ingotCopper), 0.5F);
		GameRegistry.addSmelting(HcItems.dustTin, new ItemStack(HcItems.ingotTin), 0.6F);
		GameRegistry.addSmelting(HcItems.dustSilver, new ItemStack(HcItems.ingotSilver), 0.9F );
	}
}
