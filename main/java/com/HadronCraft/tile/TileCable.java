package com.HadronCraft.tile;

import java.util.ArrayList;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileCable extends TileEntity implements IEnergyHandler{
	
	/*WIP*/public EnergyStorage storage;
	public static String unlocalizedName;
	/*WIP*/public int energy;
	/*WIP*/protected double Voltage = 120; //Volts
	/*WIP*/protected double Resistance = 0.5; // Ohms
	/*WIP*/protected double Intensity = energy / Voltage; // Amperes
	/*WIP*/public final int lossConstant = 1200; //Constante de perda usada nas equações de perda para dar o resultado pretendido
	/*WIP*/double powerLoss;
	public TileCable Master;
	public ArrayList<TileCable> Slaves = new ArrayList<TileCable>();
	
	
	public void updateEntity(){
		super.updateEntity();	
		if (this.Master == null){
			adjacentNetworksCheck();
		}
		
		if (this.Master != null){
			energySend();
		}
	}
	
	public enum CableTypes{
		Copper,
		Tin,
		Silver,
		Gold,
		Superconductor;
	}
	
	public TileCable (CableTypes Type){
		switch(Type){
		case Copper:
			setProperties("CableCopper", 140000, 120, 0.5);
			break;
		case Tin:
			setProperties("CableTin", 140000, 120, 0.4);
			break;
		case Silver:
			setProperties("CableSilver", 140000, 120, 0.25);
			break;
		case Gold:
			setProperties("CableGold", 140000, 120, 0.1);
			break;
		case Superconductor:
			setProperties("CableSuperconductor", 140000, 120, 0.0);
			break;
		}
	}
	
	/*WIP*/private void setProperties(String Name, int MaxPower, int Voltage, double Resistance){
		this.unlocalizedName = Name;
		this.Voltage = Voltage;
		this.Resistance = Resistance;
		this.storage = new EnergyStorage(MaxPower);
		this.powerLoss = storage.getMaxEnergyStored() * (((lossConstant / Voltage) + (Resistance / lossConstant)) / 100);
	}
	
	//FIXME Fix this, ain't passing energy from energy cube to energy cube
	public void energySend(){
		for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS){
			TileEntity tile = this.worldObj.getTileEntity(this.xCoord + side.offsetX, this.zCoord + side.offsetY, this.zCoord + side.offsetZ);
			
			if ((tile instanceof IEnergyReceiver) && (!tile.getClass().equals(TileCable.class))){
				this.Master.storage.extractEnergy(((IEnergyReceiver) tile).receiveEnergy(side.getOpposite(), storage.extractEnergy(storage.getMaxExtract(), true), false), false);
			}else if ((tile instanceof IEnergyHandler) && (!tile.getClass().equals(TileCable.class))){
				this.Master.storage.extractEnergy(((IEnergyHandler) tile).receiveEnergy(side.getOpposite(), storage.extractEnergy(storage.getMaxExtract(), true), false), false);
			}
		}
	}
	
	//TODO [Should be fixed] Fix this. Infinite loop on joining two networks.
	public void adjacentNetworksCheck(){
		System.out.println("Performing adjacentNetworksCheck");
		
		//This is used to check if there are two different networks when placing this block
		ArrayList<TileCable> adjacentNetworks = new ArrayList<TileCable>();
		for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS){
			TileEntity te = this.worldObj.getTileEntity(this.xCoord + side.offsetX, this.yCoord + side.offsetY, this.zCoord + side.offsetZ);
			
			if ((te instanceof TileCable) && (((TileCable) te).Master != null)){
				TileCable adjMaster = ((TileCable)te).Master;
				if (!adjacentNetworks.contains(adjMaster)){
					adjacentNetworks.add(adjMaster);
				}
			}
		}
		if (adjacentNetworks.size() > 1){
			for (TileCable tile : adjacentNetworks){
				tile.Master = this;
				for (TileCable slave : tile.Slaves){
					slave.Master = this;
				}
				tile.Slaves.clear();
			}
			System.out.println("More than one network detected");
		}
		cableNetworkInitialization();
	}
	
	public void cableNetworkInitialization(){
		System.out.println("Performing cableNetworkInitialization");

		for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS){
			TileEntity tile = this.worldObj.getTileEntity(this.xCoord + side.offsetX, this.yCoord + side.offsetY, this.zCoord + side.offsetZ);
			
			if ((tile instanceof TileCable) && (((TileCable)tile).Master != null) && (this.Master == null)){
				TileCable newMaster = ((TileCable)tile).Master;
				this.Master = newMaster;
				newMaster.Slaves.add(this);
				System.out.println("Setting Master to:" + this.Master.xCoord + "," + this.Master.yCoord + "," + this.Master.zCoord);
				break;
			}
		}
		if (this.Master == null){
			this.Master = this;
			System.out.println("Setting THIS as a master");
		}
	}
	
	//FIXME not making new networks after splitting one
	@Override
	public void invalidate(){
		super.invalidate();
		System.out.println("Performing invalidate");

		if (this.Master == this){
			System.out.println("This is a Master, so, Clearing Slave's Master");
			for(TileCable slave : this.Slaves){
				slave.Master = null;
				//Every cable will have no master, so they will reinitialize and start looking for one
			}
		}else{
			//This will be removed from the master's slave list, and if he is connected to more than one cable, the whole network must be reinitialized
			int connectedCables = 0;
			for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS){
				TileEntity tile = this.worldObj.getTileEntity(this.xCoord + side.offsetX, this.yCoord + side.offsetY, this.zCoord + side.offsetZ);
				
				if (tile instanceof TileCable){
					connectedCables++;
				}
			}
			System.out.println("Invalidating: Number of adjacent cables: " + connectedCables);
			if (connectedCables > 1){
				System.out.println("Clearing my master's slaves's Master as part of invalidation");
				this.Master.Master = null;
				for (TileCable slave : this.Master.Slaves){
					slave.Master = null;
				}
				this.Master.Slaves.clear();
			}
		}
	}
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		if (this.Master != null){
			return this.Master.storage.receiveEnergy(maxReceive, simulate);
		}else return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if (this.Master != null){
			return this.Master.storage.extractEnergy(maxExtract, simulate);
		}else return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		if (this.Master != null){
			return this.Master.storage.getEnergyStored();
		}else return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		if (this.Master != null){
			return this.Master.storage.getMaxEnergyStored();
		}else return 0;
	}

}
