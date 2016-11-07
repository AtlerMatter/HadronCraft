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
	public int lastCycledTicks;
	
	public ContainerElectricSmelter (InventoryPlayer playerInv, TileElectricSmelter entity) {
		this.entity = entity;
		this.addSlotToContainer(new Slot(entity, 0, 50, 50));
		this.addSlotToContainer(new Slot(entity, 0, 70, 50));
		
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

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.entity.isUseableByPlayer(player);
	}

}
