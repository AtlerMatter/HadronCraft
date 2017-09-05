package com.HadronCraft.tile;

import com.HadronCraft.lib.RefStrings;

import cpw.mods.fml.common.registry.GameRegistry;

public class HcTileEntity {
	
	public static void mainRegistry(){
		registerTileEntity();
	}
	
	public static void registerTileEntity(){
		GameRegistry.registerTileEntity(TileCable.class, RefStrings.MODID + TileCable.unlocalizedName);
		GameRegistry.registerTileEntity(TileCableWIP.class, RefStrings.MODID + "tileCableWIP");
		GameRegistry.registerTileEntity(TileGeneratorCombustion.class, RefStrings.MODID + "generatorCombustion");
		GameRegistry.registerTileEntity(TileElectricSmelter.class, RefStrings.MODID + "electricSmelter");
		GameRegistry.registerTileEntity(TileFissionReactor.class, RefStrings.MODID + "fissionReactor");
	}
}
