package com.HadronCraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class OrePlutonium extends Block{

	protected OrePlutonium(Material p_i45394_1_) {
		super(Material.rock);
		this.setHarvestLevel("pickaxe" , 2);
		this.setHardness(5.0F);
		this.setResistance(15.0F);
	}

}
