package com.HadronCraft.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemContainmentCell extends Item{
	protected int maxStorage = 1000;//Pseudo-mB
	public int currentStorage;
	public String currentStorageType; //Maybe change later from a string-based to an int-based id system or something
	
	public ItemContainmentCell(){
		this.setMaxStackSize(1);
	}
	
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag){
		super.addInformation(itemstack, entityplayer, list, flag);
		
		list.add("Currently Storing: " + this.currentStorageType);
		list.add("Storage: " + this.currentStorage + " / " + this.maxStorage);
	}
	
	void addToStorage(int Quantity){
		
	}
	
	void removeFromStorage(int Quantity){
		
	}
	
	String getStorageType(){
		return this.currentStorageType;
	}
}
