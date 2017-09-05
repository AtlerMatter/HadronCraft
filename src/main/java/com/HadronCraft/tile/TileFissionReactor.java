package com.HadronCraft.tile;

import com.HadronCraft.item.HcItems;
import com.HadronCraft.item.ItemContainmentCell;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;

public class TileFissionReactor extends TileGenerator{
	
	int currentHeat = 0;
	int moderateHeat = 80;
	int maxHeat = 100;
	int meltDownHeat = 110;
	int currentRadOutput = 0;
	int fissionTickRate = 1;
	int fissionCompleteTicks = 10000; // Normally 8.20 mins [Deprecated]

	/*
	 * Each cell of Uranium-235 generates 3000000X more energy than a piece of coal. So each U-235 generates 18000000000 RF
	 */
	
	public TileFissionReactor() {
		super("fissionReactor", 1800000000, 180000000, 3);
		// Slots: 0-Fuel Input; 1-Product Output; 2-Batteries or upgrades or something
		// like that

	}
	
	@Override
	public void updateEntity(){
		super.updateEntity();
		
		generatePower();
	}
	
	
	void checkStructure(){
		//TODO Finish the structure
		int waterFound = 0;
		for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS){
			Block block = worldObj.getBlock(xCoord + side.offsetX, yCoord + side.offsetY, zCoord + side.offsetZ);
			
			if (block.getMaterial() == Material.water){
				waterFound++;
			}
		}
		// Change the number and do somthing when true
		if (waterFound == 10) {

		}
	}
	
	void generatePower(){		
		if (slots[0] != null && this.canGeneratePower()){
			int power = this.getFissionFuelPower(slots[0]);
			
			this.storage.receiveEnergy(power, false);
		}
	}
	
	boolean canGeneratePower(){
		return this.slots[0] != null && isFissionFuel(this.slots[0]) && (this.storage.getEnergyStored()
				+ this.getFissionFuelPower(slots[0]) <= this.storage.getMaxEnergyStored());
	}
	
	/*void addFuel(){
		ItemStack stack = this.getStackInSlot(0);
		
		if (stack != null && isFissionFuel(stack)){
			--this.slots[0].stackSize;
			
			if (this.slots[0].stackSize <= 0){
				this.slots[0] = null;
			}
		}
	}*/
	
	// TODO Come up with numbers for plutonium fisson and for U-233 fission
	int getFissionFuelPower(ItemStack stack){
		Item item = stack.getItem();
		
		if(item == HcItems.containmentCell && ((ItemContainmentCell)item).getStorageType(stack) == "U-235"){
			return 1500000;
		} else if (item == HcItems.containmentCell && ((ItemContainmentCell) item).getStorageType(stack) == "U-233") {
			return 0;
		} else if (item == HcItems.containmentCell && ((ItemContainmentCell) item).getStorageType(stack) == "Pu-239") {
			return 0;
		} else if (item == HcItems.universalTestingFuel) {
			return 1500000;
		} else return 0;
	}
	
	// TODO Come up with numbers for plutonium fisson and for U-233 fission
	int getFissionFuelTime(ItemStack stack) {
		Item item = stack.getItem();
		
		if (item == HcItems.containmentCell && ((ItemContainmentCell) item).getStorageType(stack) == "U-235") {
			return 10000;
		} else if (item == HcItems.containmentCell && ((ItemContainmentCell) item).getStorageType(stack) == "U-233") {
			return 0;
		} else if (item == HcItems.containmentCell && ((ItemContainmentCell) item).getStorageType(stack) == "Pu-239") {
			return 0;
		} else if (item == HcItems.universalTestingFuel) {
			return 10000;
		} else return 0;
	}
	
	boolean isFissionFuel(ItemStack stack){
		return stack != null && getFissionFuelPower(stack) > 0 && getFissionFuelTime(stack) > 0;
	}
	

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot == 0){
			return isFissionFuel(stack);
		}else return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

}
