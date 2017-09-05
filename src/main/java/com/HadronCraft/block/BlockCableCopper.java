package com.HadronCraft.block;

import com.HadronCraft.tile.TileCable;
import com.HadronCraft.tile.TileCable.CableTypes;
import com.HadronCraft.tile.TileCableWIP;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import scala.Array;

public class BlockCableCopper extends BlockCable{

	protected BlockCableCopper(Material material) {
		super(Material.ground);
		this.setHardness(1.0F);
		this.opaque = false;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block){
		super.onNeighborBlockChange(world, x, y, z, block);
		
		world.getTileEntity(x, y, z).updateEntity();
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		//return new TileCable(CableTypes.Copper);
		return new TileCableWIP(com.HadronCraft.tile.TileCableWIP.CableTypes.Copper);
	}
	
	/*public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_){
		super.onBlockActivated(w, x, y, z, p, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
		TileCable tile = (TileCable) (w.getTileEntity(x, y, z));
		if(tile.Master == tile){
			w.playSound((double)x, (double)y, (double)z, "anvil", 1f, 1f, true);
		}
		return true;
	}
*/
}
