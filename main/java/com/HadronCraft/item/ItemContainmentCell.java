package com.HadronCraft.item;

import java.util.List;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemContainmentCell extends Item{

	protected int maxStorage = 1000; // Pseudo-mB for now i guess
	public int currentStorage = 0;
	public String currentStorageType; // Maybe change later from a string-based to an int-based id system or Enum based or something else
	
	public ItemContainmentCell() {
		this.setMaxStackSize(1);
	}
	

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag){
		super.addInformation(itemstack, entityplayer, list, flag);
		String type = this.currentStorageType != null ? this.getCurrentStorage(itemstack) > 0 ? this.currentStorageType : "Nothing" : "Nothing";
		
		list.add("Currently Storing: " + type);
		list.add("Storage: " + this.currentStorage + " / " + this.maxStorage);
	}
	
	public int addToStorage(ItemStack stack, int Quantity){
		if(stack.getTagCompound() == null){
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		if (this.getCurrentStorage(stack) + Quantity <= maxStorage){
			this.currentStorage = this.getCurrentStorage(stack) + Quantity;
		}else {
			Quantity = this.maxStorage - this.currentStorage;
			this.currentStorage = this.getCurrentStorage(stack) + Quantity;
		}
		
		stack.getTagCompound().setInteger("storage", this.currentStorage);
		return Quantity;
	}
	
	public int removeFromStorage(ItemStack stack, int Quantity){
		if(this.getCurrentStorage(stack) > 0){
			
			if (this.getCurrentStorage(stack) - Quantity >= 0){
				this.currentStorage = this.getCurrentStorage(stack) + Quantity;
			}else {
				Quantity = this.currentStorage;
				this.currentStorage = this.getCurrentStorage(stack) + Quantity;
			}
		}
		
		stack.getTagCompound().setInteger("storage", this.currentStorage);
		return Quantity;
	}
	
	public int setCurrentStorage(ItemStack stack, int numToSet) {
		if (stack.getTagCompound() == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}

		if (numToSet > this.getMaxStorage())
			numToSet = this.getMaxStorage();
		stack.getTagCompound().setInteger("storage", numToSet);
		return numToSet;
	}

	public String setStorageType(ItemStack stack, String type){
		if (stack.getTagCompound() == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}

		if(type == "Empty"){
			this.currentStorageType = null;
		}else this.currentStorageType = type;
		
		stack.getTagCompound().setString("type", this.currentStorageType);
		return type;
	}
	
	public boolean isEmpty(ItemStack stack){
		return stack.getTagCompound().getInteger("storage") == 0;
	}
	
	public int getMaxStorage(){
		return this.maxStorage;
	}
	
	public int getCurrentStorage(ItemStack stack){
		return stack.getTagCompound().getInteger("storage");
	}
	
	public String getStorageType(ItemStack stack){
		return stack.getTagCompound().getString("storageType");
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack itemstack){
		if (itemstack.getTagCompound() != null){
			return itemstack.getTagCompound().getInteger("storage") > 0;
		}else return false;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack itemstack){
		return this.getCurrentStorage(itemstack) / this.getMaxStorage();
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		ItemStack empty = new ItemStack(this);
		list.add(empty);
		
		ItemStack fullU235 = new ItemStack(this);
		setStorageType(fullU235, "U-235");
		setCurrentStorage(fullU235, ((ItemContainmentCell) fullU235.getItem()).getMaxStorage());
		list.add(fullU235);
	}
}
