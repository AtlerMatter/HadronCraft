package com.HadronCraft.container;

import com.HadronCraft.tile.TileElectricSmelter;
import com.HadronCraft.tile.TileGeneratorCombustion;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerElectricSmelter extends Container{
	
	private TileElectricSmelter entity;
	public int lastEnergy;
	public int lastCurrentSmeltTime;
	public int lastSmeltTime;
	public int lastCycledTicks;
	
	public ContainerElectricSmelter (InventoryPlayer playerInv, TileElectricSmelter entity) {
		this.entity = entity;
		this.addSlotToContainer(new Slot(entity, 0, 56, 35));
		this.addSlotToContainer(new Slot(entity, 1, 116, 35));
		
		for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
        }
	}
	
	public void addCraftingToCrafters(ICrafting icrafting){
		super.addCraftingToCrafters(icrafting);
		icrafting.sendProgressBarUpdate(this, 0, this.entity.energy);
       	icrafting.sendProgressBarUpdate(this, 1, this.entity.smeltTime);
       	icrafting.sendProgressBarUpdate(this, 2, this.entity.currentSmeltTime);
	}
	
	public void detectAndSendChanges(){
		super.detectAndSendChanges();
		
		for (int i = 0; i < this.crafters.size(); i++){
			ICrafting icrafting = (ICrafting) this.crafters.get(i);
			
			icrafting.sendProgressBarUpdate(this, 1, this.entity.energy);
			icrafting.sendProgressBarUpdate(this, 2, this.entity.currentSmeltTime);
			icrafting.sendProgressBarUpdate(this, 3, this.entity.smeltTime);
			
			icrafting.sendProgressBarUpdate(this, 4, this.entity.energy >> 16);
			icrafting.sendProgressBarUpdate(this, 5, this.entity.currentSmeltTime >> 16);
			icrafting.sendProgressBarUpdate(this, 6, this.entity.smeltTime >> 16);
		}
	}
		
	public void updateProgressBar(int slot, int value){
		super.updateProgressBar(slot, value);
		if (slot == 1){this.lastEnergy = this.upcastShort(value);}
		if (slot == 2){this.lastCurrentSmeltTime = this.upcastShort(value);}
		if (slot == 3){this.lastSmeltTime = this.upcastShort(value);}
		
		if (slot == 4){this.entity.energy = this.lastEnergy | value << 16;}
		if (slot == 5){this.entity.currentSmeltTime = this.lastCurrentSmeltTime | value << 16;}
		if (slot == 6){this.entity.smeltTime = this.lastSmeltTime | value << 16;}
	}

	
	public ItemStack transferStackInSlot(EntityPlayer player, int clickedSlotNumber){
		ItemStack itemstack = null;
		Slot slot =(Slot)this.inventorySlots.get(clickedSlotNumber);
		
		if (slot != null && slot.getHasStack()){
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			if(clickedSlotNumber != 1 && clickedSlotNumber != 0){
					if (!this.mergeItemStack(itemstack1, 0, 1, false)){
						return null;
					}
				else if (clickedSlotNumber >= 2 && clickedSlotNumber > 29){
					if (!this.mergeItemStack(itemstack1, 29, 38, false)){
						return null;
					}
				}
				else if (clickedSlotNumber >= 29 && clickedSlotNumber < 38 && !this.mergeItemStack(itemstack1, 2, 29, false)){
					return null;
				}
			}
			else if(!this.mergeItemStack(itemstack1, 2, 38, false)){
				return null;
			}
			if (itemstack1.stackSize == 0){
				slot.putStack((ItemStack)null);
			}
			else{
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize == itemstack.stackSize){
				return null;
			}
			slot.onPickupFromSlot(player, itemstack1);
		}
		return itemstack;
	}
	
	public int upcastShort(int input){
		if (input < 0) input += 65536;
		return input;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.entity.isUseableByPlayer(player);
	}

}
