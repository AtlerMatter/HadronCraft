package com.HadronCraft.tile;

import com.HadronCraft.block.BlockElectricSmelter;

import cofh.api.energy.IEnergyReceiver;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileElectricSmelter extends TileMachine implements IEnergyReceiver{
	
	public int smeltTime = 100;
	public int currentSmeltTime;
	public int requiredEnergy;

	public TileElectricSmelter() {
		super("Electric Smelter", 30000, 10000, 2);
	}
	
	public void updateEntity(){
		super.updateEntity();
		
		if (canSmelt()){
			currentSmeltTime += 1;
			storage.extractEnergy(requiredEnergy, false);
			if (currentSmeltTime >= smeltTime){
				currentSmeltTime = 0;
				doSmelting();
			}
		}else {
			currentSmeltTime = 0;
		}
		if (flag != flag1) {flag1 = flag; BlockElectricSmelter.updateBlockState(flag, worldObj, xCoord, yCoord, zCoord);}
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
		if(storage.getEnergyStored() >= requiredEnergy && slots[0] != null && currentSmeltTime <= 0 && slots[1].stackSize < slots[1].getMaxStackSize()){
			flag = true;
			return true;
		}
		flag = false;
		return false;
	}
	
	public ItemStack getOutput(ItemStack itemstack){
		return FurnaceRecipes.smelting().getSmeltingResult(itemstack);
	}
	
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
	}
	
	
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
	}
	
	public boolean isItemValidForSlot(int slot, ItemStack stack){
		if (slot == 1) return false;
		else return true;
	}
	
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}
		
		public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
	}
		
		
		public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return 0;
	}
		
		
		public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}
		
		
		public int getMaxEnergyStored(ForgeDirection from) {
		return storage.getMaxEnergyStored();
	}
}
