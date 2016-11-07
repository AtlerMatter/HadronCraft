package com.HadronCraft.block;

import com.HadronCraft.tile.TileCable;
import com.HadronCraft.tile.TileCableCopper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import scala.Array;

public class BlockCableCopper extends BlockCable{

	protected BlockCableCopper(Material p_i45386_1_) {
		super(Material.ground);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block){
		
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileCableCopper();
	}

}
