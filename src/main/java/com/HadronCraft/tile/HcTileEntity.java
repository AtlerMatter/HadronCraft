package com.HadronCraft.tile;

import com.HadronCraft.lib.RefStrings;

import cpw.mods.fml.common.registry.GameRegistry;

public class HcTileEntity {
	
	public static void mainRegistry(){
		registerTileEntity();
	}
	
	public static void registerTileEntity(){
		GameRegistry.registerTileEntity(TileCableCopper.class, RefStrings.MODID + "cableCopper");
		GameRegistry.registerTileEntity(TileGeneratorCombustion.class, RefStrings.MODID + "generatorCombustion");
		GameRegistry.registerTileEntity(TileElectricSmelter.class, RefStrings.MODID + "electricSmelter");
	}
}
