package com.HadronCraft.tile;

import com.HadronCraft.helpers.EnergyHelper;

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

public class TileCableCopper extends TileCable{

	public TileCableCopper() {
		super(20000, 120, 0.5);
	}

	/*protected EnergyStorage storage = new EnergyStorage(20000);
	public int energy;
	protected double Voltage = 120; //Volts
	protected double Resistance = 0.5; // Ohms
	protected double Intensity = energy / Voltage; // Amperes
	public final int lossConstant = 1200; //Constante de perda usada nas equações de perda para dar o resultado pretendido
	double powerLoss = storage.getMaxEnergyStored() * (((lossConstant / Voltage) + (Resistance / lossConstant)) / 100); // 10% da capacidade total 
	*/
	public void updateEntity(){
		super.updateEntity();
		//energyReceive();
		//energySend();
	}
	
		
	/*public void energySend(){
		for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS){
			TileEntity tile = this.worldObj.getTileEntity(xCoord + side.offsetX, yCoord + side.offsetY, zCoord + side.offsetZ);
			
			if (tile instanceof IEnergyHandler){
				storage.extractEnergy(((IEnergyHandler) tile).receiveEnergy(side.getOpposite(), storage.extractEnergy(storage.getMaxExtract(), true), false), false);
			}
			if (tile instanceof IEnergyReceiver){
				storage.extractEnergy(((IEnergyReceiver) tile).receiveEnergy(side.getOpposite(), storage.extractEnergy(storage.getMaxExtract(), true), false), false);
			}
			if (tile instanceof TileCableCopper){
				//storage.extractEnergy((int)powerLoss / 10, false);
				storage.extractEnergy(((IEnergyReceiver) tile).receiveEnergy(side.getOpposite(), storage.extractEnergy(storage.getMaxExtract(), true), false), false);
			}
		}
	}*/
	
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
	
	/*@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, false);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return storage.extractEnergy(maxExtract, false);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return storage.getMaxEnergyStored();
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
	*/
}
