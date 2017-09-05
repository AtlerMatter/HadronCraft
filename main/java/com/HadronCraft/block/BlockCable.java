package com.HadronCraft.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * A generic cable which all cable types extend, just for the purpose of being able to reference all cable blocks using this
 * @author LukeDC
 */
public class BlockCable extends BlockContainer{

	protected BlockCable(Material Material) {
		super(Material.ground);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return null;
	}

}
