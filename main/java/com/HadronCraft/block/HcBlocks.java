package com.HadronCraft.block;

import com.HadronCraft.creativetabs.HcTabs;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.oredict.OreDictionary;

public class HcBlocks {
	
	public static void mainRegistry(){
		initializeBlock();
		registerBlock();
		registerOre();
	}
	
	//Ores
	public static Block oreCopper;
	public static Block oreTin;
	public static Block oreSilver;
	//Nuclear Stuff's Ores
	public static Block oreUranium;
	public static Block orePlutonium;
	//Machine Stuffs
		//Energy Transmitters
	public static Block cableCopper;
		//Generators
	public static Block generatorCombustionIdle;
	public static Block generatorCombustionActive;
	public static Block fissionReactorIdle;
	public static Block fissionReactorActive;
		//Energy Users
	public static Block electricSmelterIdle;
	public static Block electricSmelterActive;
	//Blocks
	
	public static void initializeBlock(){
		oreCopper = new OreCopper(Material.rock).setBlockName("oreCopper").setCreativeTab(HcTabs.tabHC).setBlockTextureName("hadroncraft:oreCopper");
		oreTin = new OreTin(Material.rock).setBlockName("oreTin").setCreativeTab(HcTabs.tabHC).setBlockTextureName("hadroncraft:oreTin");
		oreSilver = new OreSilver(Material.rock).setBlockName("oreSilver").setCreativeTab(HcTabs.tabHC).setBlockTextureName("hadroncraft:oreSilver");
		oreUranium = new OreUranium(Material.rock).setBlockName("oreUranium").setCreativeTab(HcTabs.tabHC).setBlockTextureName("hadroncraft:oreUranium");
		orePlutonium = new OrePlutonium(Material.rock).setBlockName("orePlutonium").setCreativeTab(HcTabs.tabHC).setBlockTextureName("hadroncraft:orePlutonium");
		cableCopper = new BlockCableCopper(Material.ground).setBlockName("cableCopper").setCreativeTab(HcTabs.tabHC);
		generatorCombustionActive = new BlockGeneratorCombustion(true).setBlockName("generatorCombustionActive").setLightLevel(0.4F);
		generatorCombustionIdle = new BlockGeneratorCombustion(false).setBlockName("generatorCombustionIdle").setCreativeTab(HcTabs.tabHC);
		electricSmelterActive = new BlockElectricSmelter(true).setBlockName("electricSmelterActive").setLightLevel(0.5F);
		electricSmelterIdle = new BlockElectricSmelter(false).setBlockName("electricSmelterIdle").setCreativeTab(HcTabs.tabHC);
		fissionReactorActive = new BlockFissionReactor(true).setBlockName("fissionReactorActive").setLightLevel(0.6F);
		fissionReactorIdle = new BlockFissionReactor(false).setBlockName("fissionReactorIdle").setCreativeTab(HcTabs.tabHC);
		
	}
	
	public static void registerBlock(){
		GameRegistry.registerBlock(oreCopper, oreCopper.getUnlocalizedName());
		GameRegistry.registerBlock(oreTin, oreTin.getUnlocalizedName());
		GameRegistry.registerBlock(oreSilver, oreSilver.getUnlocalizedName());
		GameRegistry.registerBlock(oreUranium, oreUranium.getUnlocalizedName());
		GameRegistry.registerBlock(orePlutonium, orePlutonium.getUnlocalizedName());
		GameRegistry.registerBlock(cableCopper, cableCopper.getUnlocalizedName());
		GameRegistry.registerBlock(generatorCombustionActive, generatorCombustionActive.getUnlocalizedName());
		GameRegistry.registerBlock(generatorCombustionIdle, generatorCombustionIdle.getUnlocalizedName());
		GameRegistry.registerBlock(electricSmelterActive, electricSmelterActive.getUnlocalizedName());
		GameRegistry.registerBlock(electricSmelterIdle, electricSmelterIdle.getUnlocalizedName());
		GameRegistry.registerBlock(fissionReactorIdle, fissionReactorIdle.getUnlocalizedName());
	}
	
	
	public static void registerOre(){
		OreDictionary.registerOre("oreCopper", oreCopper);
		OreDictionary.registerOre("oreTin", oreTin);
		OreDictionary.registerOre("oreSilver", oreSilver);
		OreDictionary.registerOre("oreUranium", oreUranium);
		OreDictionary.registerOre("orePlutonium", orePlutonium);
		OreDictionary.registerOre("cableCopper", cableCopper);
	}

}
