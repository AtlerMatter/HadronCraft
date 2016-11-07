package com.HadronCraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class OreSilver extends Block{

	protected OreSilver(Material p_i45394_1_) {
		super(Material.rock);
		this.setHarvestLevel("pickaxe" , 2);
		this.setHardness(4.0F);
		this.setResistance(15.0F);
	}

}
