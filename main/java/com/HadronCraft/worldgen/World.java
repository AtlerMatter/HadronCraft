package com.HadronCraft.worldgen;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;

public class World {
	public static void mainRegistry(){
		initializeWorldGen();
	}
	
	public static void initializeWorldGen(){
		registerWorldGen(new OreGen(), 1);
	}
	
	public static void registerWorldGen(IWorldGenerator worldGenClass, int weightedProbability){
		GameRegistry.registerWorldGenerator(worldGenClass, weightedProbability);
	}
}
