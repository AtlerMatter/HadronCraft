package com.HadronCraft.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCable extends BlockContainer{

	protected BlockCable(Material Material) {
		super(Material.ground);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return null;
	}

}
