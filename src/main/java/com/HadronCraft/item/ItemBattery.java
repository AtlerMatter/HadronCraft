package com.HadronCraft.item;

import java.util.List;

import com.HadronCraft.creativetabs.HcTabs;

import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.ItemEnergyContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemBattery extends Item implements IEnergyContainerItem{
	protected int capacity;
	protected int maxReceive;
	protected int maxExtract;
	
	public ItemBattery() {
	}

	public ItemBattery(int capacity) {

		this(capacity, capacity, capacity);
	}

	public ItemBattery(int capacity, int maxTransfer) {

		this(capacity, maxTransfer, maxTransfer);
	}

	public ItemBattery(int capacity, int maxReceive, int maxExtract) {

		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
		this.setMaxStackSize(1);
	}

	public ItemBattery setCapacity(int capacity) {

		this.capacity = capacity;
		return this;
	}

	public void setMaxTransfer(int maxTransfer) {

		setMaxReceive(maxTransfer);
		setMaxExtract(maxTransfer);
	}

	public void setMaxReceive(int maxReceive) {

		this.maxReceive = maxReceive;
	}

	public void setMaxExtract(int maxExtract) {

		this.maxExtract = maxExtract;
	}
	
	@Override
	public int getEnergyStored(ItemStack container) {

		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Energy")) {
			return 0;
		}
		return container.stackTagCompound.getInteger("Energy");
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {

		return capacity;
	}
	
	
	public void setEnergy(ItemStack stack, int Energy){
		if (stack.stackTagCompound == null){
		stack.stackTagCompound = new NBTTagCompound();
		}
		int energy = stack.stackTagCompound.getInteger("Energy"); /*This line may not be necessary*/
		stack.stackTagCompound.setInteger("Energy", Energy);
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack itemstack){
		NBTTagCompound tag = itemstack.getTagCompound();
		if (tag != null){
			return tag.getInteger("Energy") > 0;
		}else return false;
	}
	
	
	@Override
	public double getDurabilityForDisplay(ItemStack itemstack){
		NBTTagCompound tag = itemstack.getTagCompound();
		if(tag != null){
			return 1 - (double) tag.getInteger("Energy") / capacity;
		} else return 1;
	}
	
	
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {

		if (container.stackTagCompound == null) {
			container.stackTagCompound = new NBTTagCompound();
		}
		int energy = container.stackTagCompound.getInteger("Energy");
		int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

		if (!simulate) {
			energy += energyReceived;
			container.stackTagCompound.setInteger("Energy", energy);
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {

		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Energy")) {
			return 0;
		}
		int energy = container.stackTagCompound.getInteger("Energy");
		int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
			container.stackTagCompound.setInteger("Energy", energy);
		}
		return energyExtracted;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag){
		super.addInformation(itemstack, entityplayer, list, flag);
		
		list.add("Energy: " + this.getEnergyStored(itemstack) + "RF");
		list.add("Max Energy: " + this.getMaxEnergyStored(itemstack) + "RF");
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list){
		ItemStack discharged = new ItemStack(this);
		list.add(discharged);
		ItemStack charged = new ItemStack(this);
		setEnergy(charged, ((IEnergyContainerItem)charged.getItem()).getMaxEnergyStored(charged));
		list.add(charged);
	}
}
