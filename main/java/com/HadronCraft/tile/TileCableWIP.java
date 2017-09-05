package com.HadronCraft.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * This is the class that contains every cable type, which is changed based on the arguments of the tileCable constructor
 * NOTE: The Cables do not work, and i am forgetting them for now, while i work on other stuff, which i can "easily" get working.
 * @author LukeDC
 */

public class TileCableWIP extends TileEntity implements IEnergyHandler{
	
	/*WIP*/protected EnergyStorage storage = new EnergyStorage(20000);
	public static String unlocalizedName;
	/*WIP*/public int energy;
	/*WIP*/protected double Voltage = 120; //Volts
	/*WIP*/protected double Resistance = 0.5; // Ohms
	/*WIP*/protected double Intensity = energy / Voltage; // Amperes
	/*WIP*/public final int lossConstant = 1200; //Constante de perda usada nas equações de perda para dar o resultado pretendido
	/*WIP*/double powerLoss = storage.getMaxEnergyStored() * (((lossConstant / Voltage) + (Resistance / lossConstant)) / 100);
	
	private boolean firstRun = true;
	protected TileCableWIP Master;
	public boolean isMaster = this.isMaster();
	
	
	public void updateEntity(){
		super.updateEntity();
		if(firstRun){
			createMultiblock();
			firstRun = false;
		}
		energySend();
		
		markDirty();
	}
	
	
	public enum CableTypes{
		Copper,
		Tin,
		Silver,
		Gold,
		Superconductor;
	}
	
	public TileCableWIP(CableTypes Type){
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
		this.storage.setCapacity(MaxPower);
		this.storage.setMaxTransfer(MaxPower);
		this.Voltage = Voltage;
		this.Resistance = Resistance;
	}
	
	public boolean hasMaster(){
		return this.Master != null;
	}
	
	public boolean isMaster(){
		return this.Master == this;
	}
	
	public TileCableWIP getMaster(){
		return this.Master;
	}
	
	public void setMaster(TileCableWIP Master){
		this.Master = Master;
		markDirty();
	}
	
	public void setMaster(World world, int x, int y, int z){
		this.Master = ((TileCableWIP) world.getTileEntity(x, y, z));
		markDirty();
	}
	
	private void createMultiblock(){
        if(Master == null || Master.isInvalid()) {
            List<TileCableWIP> connectedNetwork = new ArrayList<TileCableWIP>();
            Stack<TileCableWIP> directNear = new Stack<TileCableWIP>();
            TileCableWIP master = this;
            directNear.add(this);
            while(!directNear.isEmpty()) {
            	TileCableWIP storage = directNear.pop();
                if(storage.isMaster()) {
                    master = storage;
                }
                connectedNetwork.add(storage);
                for(ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                    TileEntity tile = worldObj.getTileEntity(storage.xCoord + side.offsetX, storage.yCoord + side.offsetY, storage.zCoord + side.offsetZ);
                    if(tile instanceof TileCableWIP && !connectedNetwork.contains(tile)) {
                    	directNear.add((TileCableWIP)tile);
                    }
                }
            }
            System.out.println("Setting master to " + master.xCoord + ", " + master.yCoord + ", " + master.zCoord + " for " + connectedNetwork.size() + " blocks");
            for(TileCableWIP cable : connectedNetwork) {
            	cable.setMaster(master);
            }
        }
        markDirty();
    }
	
	public void invalidate(){
		super.invalidate();
		for(ForgeDirection side : ForgeDirection.VALID_DIRECTIONS){
			TileEntity tile = worldObj.getTileEntity(xCoord + side.offsetX, yCoord + side.offsetY, zCoord + side.offsetZ);
			if(tile instanceof TileCableWIP){
				((TileCableWIP) tile).Master = null;
				((TileCableWIP) tile).createMultiblock();
			}
		}
	}
	
	public void energySend(){
		if(Master != null){
			for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS){
				TileEntity tile = this.worldObj.getTileEntity(xCoord + side.offsetX, yCoord + side.offsetY, zCoord + side.offsetZ);
				
				if (tile instanceof TileCableWIP){
					continue;
				}else if (tile instanceof IEnergyReceiver){
					this.Master.storage.extractEnergy(((IEnergyReceiver) tile).receiveEnergy(side.getOpposite(), storage.extractEnergy(storage.getMaxExtract(), true), false), false);
					System.out.println("IEnergyReceiver found, and it's a : " + tile.getClass().toString());
				}else if (tile instanceof IEnergyHandler){
					this.Master.storage.extractEnergy(((IEnergyHandler) tile).receiveEnergy(side.getOpposite(), storage.extractEnergy(storage.getMaxExtract(), true), false), false);
					System.out.println("IEnergyHandler found, and it's a : " + tile.getClass().toString());
				}
			}
		}
	}
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		if(Master != null){
			return this.Master.storage.receiveEnergy(maxReceive, false);
		}else return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if(Master != null){
			return this.Master.storage.extractEnergy(maxExtract, simulate);
		}
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		if(Master != null){
			return this.Master.storage.getEnergyStored();
		}
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		if(Master != null){
		return this.Master.storage.getMaxEnergyStored();
		}
		return 0;
	}
	
	//		Packet Managing
	
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
	
	//		NBT Managing
	
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		
		//Setting the energy related stuff
		if (nbt.hasKey("storage")){
			this.storage.readFromNBT(nbt.getCompoundTag("storage"));
		}
		
		//Setting the master related stuff
		TileCableWIP master = (TileCableWIP)this.worldObj.getTileEntity(nbt.getInteger("masterX"), nbt.getInteger("masterY"), nbt.getInteger("masterZ"));
		
		this.isMaster = nbt.getBoolean("isMaster");
		this.setMaster(master);
	}
	
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		
		NBTTagCompound energyTag = new NBTTagCompound();
		this.storage.writeToNBT(energyTag);
		nbt.setTag("storage", energyTag);

		if (hasMaster()){			
			nbt.setBoolean("isMaster", isMaster);
			nbt.setInteger("masterX", Master.xCoord);
			nbt.setInteger("masterY", Master.yCoord);
			nbt.setInteger("masterZ", Master.zCoord);
		}
	}
	
}
