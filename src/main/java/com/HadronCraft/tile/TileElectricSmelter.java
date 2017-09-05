package com.HadronCraft.tile;

import com.HadronCraft.block.BlockElectricSmelter;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileElectricSmelter extends TileMachine implements IEnergyReceiver{
	
	public int requiredEnergy = 30; // RF per Tick
	public int energyPerSmelting = 3000; //RF
	public int smeltTime = 100; // Ticks
	public int currentSmeltTime;
	int pausedSmeltingTicks;
	
	public TileElectricSmelter() {
		super("Electric Smelter", 30000, 10000, 2);
	}
	
	@Override
	public void updateEntity(){
		super.updateEntity();

		if (canSmelt()){
			pausedSmeltingTicks = 0;
			currentSmeltTime += 1;
			storage.extractEnergy(requiredEnergy, false);
			if (currentSmeltTime >= smeltTime){
				currentSmeltTime = 0;
				doSmelting();
			}
		}else if (currentSmeltTime > 0 && pausedSmeltingTicks >= 10){
			currentSmeltTime -= 1;
		}else {
			pausedSmeltingTicks += 1;
		}
		
		if (currentSmeltTime > 0 || canSmelt()) {
			BlockElectricSmelter.updateBlockState(true, worldObj, xCoord, yCoord, zCoord);
		}else{
			BlockElectricSmelter.updateBlockState(false, worldObj, xCoord, yCoord, zCoord);
		}
		markDirty();
	}
	
	
	public void doSmelting(){
		ItemStack itemstack = getOutput(slots[0]);
		if (slots[1] == null){
			slots[1] = itemstack.copy();
		}else if (slots[1].isItemEqual(itemstack)){
			slots[1].stackSize += itemstack.stackSize;
		}
		slots[0].stackSize -= itemstack.stackSize;
		
		if (slots[0].stackSize <= 0){
			slots[0] = null;
		}
	}
	
	public boolean canSmelt(){
		if (slots[0] == null) {
			flag = false;
			return false;
		}
		if (currentSmeltTime >= smeltTime) {
			flag = true;
			return true;
		}
		if (storage.getEnergyStored() < requiredEnergy) {
			flag = false;
			return false;
		}

		ItemStack itemstack = getOutput(slots[0]);
		if (itemstack == null) {
			flag = false;
			return false;
		}
		if (slots[1] != null) {
			if (!slots[1].isItemEqual(itemstack)) {
				flag = false;
				return false;
			}
			if (slots[1].stackSize + itemstack.stackSize > slots[1].getMaxStackSize()) {
				flag = false;
				return false;
			}
		}
		flag = true;
		return true;
	}
	
	public ItemStack getOutput(ItemStack itemstack){
		return FurnaceRecipes.smelting().getSmeltingResult(itemstack);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		
		this.requiredEnergy = nbt.getInteger("requiredEnergy");
		this.energyPerSmelting = nbt.getInteger("energyPerSmelting");
		this.smeltTime = nbt.getInteger("smeltTime");
		this.currentSmeltTime = nbt.getInteger("currentSmeltTime");
		this.pausedSmeltingTicks = nbt.getInteger("pausedSmeltingTicks");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		
		nbt.setInteger("requiredEnergy", this.requiredEnergy);
		nbt.setInteger("energyPerSmelting", this.energyPerSmelting);
		nbt.setInteger("smeltTime", this.smeltTime);
		nbt.setInteger("currentSmeltTime", this.currentSmeltTime);
		nbt.setInteger("pausedSmeltingTicks", this.pausedSmeltingTicks);
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack){
		if (slot == 1) return false;
		else return true;
	}
	
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from == ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();
	}
	
	
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
	}
		
		
	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}
		
		
	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return storage.getMaxEnergyStored();
	}
}
