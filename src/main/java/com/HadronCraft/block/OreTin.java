package com.HadronCraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class OreTin extends Block{

	protected OreTin(Material p_i45394_1_) {
		super(Material.rock);
		this.setHarvestLevel("pickaxe" , 1);
		this.setHardness(3.0F);
		this.setResistance(15.0F);
	}

}
