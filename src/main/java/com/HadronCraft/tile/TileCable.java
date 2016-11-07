package com.HadronCraft.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/*
 * Used just to group every cable type under one type of class
 */

public class TileCable extends TileEntity implements IEnergyHandler, IEnergyProvider, IEnergyReceiver{
	
	/*WIP*/protected EnergyStorage storage = new EnergyStorage(20000);
	/*WIP*/public int energy;
	/*WIP*/protected double Voltage = 120; //Volts
	/*WIP*/protected double Resistance = 0.5; // Ohms
	/*WIP*/protected double Intensity = energy / Voltage; // Amperes
	/*WIP*/public final int lossConstant = 1200; //Constante de perda usada nas equações de perda para dar o resultado pretendido
	/*WIP*/double powerLoss = storage.getMaxEnergyStored() * (((lossConstant / Voltage) + (Resistance / lossConstant)) / 100);
	protected TileCable Master;
	private boolean isMaster;
	private boolean firstRun = true;
	
	/*WIP*/public TileCable(double MaxPower ,double Voltage, double Resistance){
		this.storage.setCapacity((int) MaxPower);
		this.storage.setMaxTransfer((int) MaxPower);
		this.Voltage = Voltage;
		this.Resistance = Resistance;
	}
	
	/*
	 * Things that were added before.
	 * (Thing from the legacy generic cable that was just a placeholder)
	 */
	public boolean hasMaster(){
		return this.Master != null;
	}
	
	public boolean isMaster(){
		return isMaster;
	}
	
	public TileCable getMaster(){
		return this.Master;
	}
	
	public void setMaster(TileCable Master){
		this.Master = Master;
	}
	
	public void updateEntity(){
		super.updateEntity();
		if(firstRun){
			this.createMultiblock();
			this.firstRun = false;
		}
		//energyReceive();
		energySend();
	}
	
	private void createMultiblock(){
        if(Master == null || Master.isInvalid()) {
            List<TileCable> connectedNetwork = new ArrayList<TileCable>();
            Stack<TileCable> directNear = new Stack<TileCable>();
            TileCable master = this;
            directNear.add(this);
            while(!directNear.isEmpty()) {
            	TileCable storage = directNear.pop();
                if(storage.isMaster()) {
                    master = storage;
                }
                connectedNetwork.add(storage);
                for(ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                    TileEntity tile = worldObj.getTileEntity(storage.xCoord + side.offsetX, storage.yCoord + side.offsetY, storage.zCoord + side.offsetZ);
                    if(tile instanceof TileCable && !connectedNetwork.contains(tile)) {
                    	directNear.add((TileCable)tile);
                    }
                }
            }
            System.out.println("Setting master to " + master.xCoord + ", " + master.yCoord + ", " + master.zCoord + " for " + connectedNetwork.size() + " blocks");
            for(TileCable cable : connectedNetwork) {
            	cable.setMaster(master);
            }
        }
    }

	public void invalidate(){
		super.invalidate();
		for(ForgeDirection side : ForgeDirection.VALID_DIRECTIONS){
			TileEntity tile = worldObj.getTileEntity(xCoord + side.offsetX, yCoord + side.offsetY, zCoord + side.offsetZ);
			if(tile instanceof TileCable){
				((TileCable) tile).Master = null;
				((TileCable) tile).createMultiblock();
			}
		}
	}
	
	/*
	 * 
	 * Stuff added after
	 * (Stuff that used to be implemented in the CableCopper, now to be used in every cable type)
	 * 
	 */
			
	public void energySend(){
		for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS){
			TileEntity tile = this.worldObj.getTileEntity(xCoord + side.offsetX, yCoord + side.offsetY, zCoord + side.offsetZ);
			
			if (tile instanceof IEnergyHandler){
				Master.storage.extractEnergy(((IEnergyHandler) tile).receiveEnergy(side.getOpposite(), storage.extractEnergy(storage.getMaxExtract(), true), false), false);
			}
			if (tile instanceof IEnergyReceiver){
				Master.storage.extractEnergy(((IEnergyReceiver) tile).receiveEnergy(side.getOpposite(), storage.extractEnergy(storage.getMaxExtract(), true), false), false);
			}
			if (tile instanceof TileCableCopper){
				Master.storage.extractEnergy(((IEnergyReceiver) tile).receiveEnergy(side.getOpposite(), storage.extractEnergy(storage.getMaxExtract(), true), false), false);
			}
		}
	}
	
	/*public void energyReceive(){
		for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS){
			TileEntity tile = this.worldObj.getTileEntity(xCoord + side.offsetX, yCoord + side.offsetY, zCoord + side.offsetZ);
			
			if (tile instanceof IEnergyHandler){
				storage.receiveEnergy(((IEnergyHandler) tile).extractEnergy(side.getOpposite(), storage.receiveEnergy(storage.getMaxExtract(), true), false), false);

			}
			if (tile instanceof IEnergyProvider){
				storage.receiveEnergy(((IEnergyProvider) tile).extractEnergy(side.getOpposite(), storage.receiveEnergy(storage.getMaxExtract(), true), false), false);

			}
		}
	}*/
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return Master.storage.receiveEnergy(maxReceive, false);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return Master.storage.extractEnergy(maxExtract, false);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return Master.storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return Master.storage.getMaxEnergyStored();
	}
	
	public Packet getDescriptionPacket(){
		NBTTagCompound nbtTag = new NBTTagCompound();
		nbtTag.setInteger("Energy", this.storage.getEnergyStored());
		this.energy = nbtTag.getInteger("Energy");
		this.writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbtTag);
	}
	
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet){
		super.onDataPacket(net, packet);
		this.readFromNBT(packet.func_148857_g());
	}
	
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		
		if (nbt.hasKey("storage")){
			this.storage.readFromNBT(nbt.getCompoundTag("storage"));
		}
	}
	
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		
		NBTTagCompound energyTag = new NBTTagCompound();
		this.storage.writeToNBT(energyTag);
		nbt.setTag("storage", energyTag);
	}
	
}
