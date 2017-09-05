package com.HadronCraft.worldgen;

import java.util.Random;

import com.HadronCraft.block.HcBlocks;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class OreGen implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.dimensionId){
		case -1:
			generateNether(random, chunkX * 16, chunkZ * 16, world);
			break;
		case 0:
			generateOverworld(random, chunkX * 16, chunkZ * 16, world);
			break;
		case 1:
			generateEnd(random, chunkX * 16, chunkZ * 16, world);
			break;
		}

	}
	
	//spawnChance is how many times the process happens, basically, how many veins there are per chunk.(I think)
	private void addOre(Block block, Block blockspawn, Random random, World world, int posX, int posZ, int minY, int maxY, int minVein, int maxVein, int spawnChance){
		for(int i = 0; i < spawnChance; i++){
			int defaultChunkSize = 16;
			
			int xPos = posX + random.nextInt(defaultChunkSize);
			int yPos = minY + random.nextInt(maxY - minY);
			int zPos = posZ + random.nextInt(defaultChunkSize);
			
			new WorldGenMinable(block, (minVein + random.nextInt(maxVein - minVein)), blockspawn).generate(world, random, xPos, yPos, zPos);
		}
	}

	private void generateEnd(Random random, int chunkX, int chunkZ, World world) {
		// TODO Auto-generated method stub
		
	}

	private void generateOverworld(Random random, int chunkX, int chunkZ, World world) {
		addOre(HcBlocks.oreCopper, Blocks.stone, random, world, chunkX, chunkZ, 3, 90, 4, 8, 120);
		addOre(HcBlocks.oreTin, Blocks.stone, random, world, chunkX, chunkZ, 3, 80, 4, 6, 90);
		addOre(HcBlocks.oreSilver, Blocks.stone, random, world, chunkX, chunkZ, 2, 50, 4, 8, 50);
		addOre(HcBlocks.oreUranium, Blocks.stone, random, world, chunkX, chunkZ, 2, 50, 4, 6, 60);
		addOre(HcBlocks.orePlutonium, Blocks.stone, random, world, chunkX, chunkZ, 2, 40, 3, 4, 30);
	}

	private void generateNether(Random random, int chunkX, int chunkZ, World world) {
		// TODO Auto-generated method stub
		
	}

}
