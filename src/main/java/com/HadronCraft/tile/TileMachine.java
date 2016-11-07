package com.HadronCraft.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileMachine extends TileEntity implements IEnergyHandler, IEnergyConnection, IInventory{
	public boolean flag;
	public boolean flag1 = false;
	public EnergyStorage storage;
	public String localizedName;
	public ItemStack[] slots;
	public int energy;
	
	
	public TileMachine(String localName, int energyMax, int transferMax, int slotsNumber){
		storage = new EnergyStorage(energyMax, transferMax);
		localizedName = localName;
		slots = new ItemStack[slotsNumber];
	}
	
	public void updateEntity(){
		super.updateEntity();
		addEnergy();
	}
	
	public void addEnergy(){
		for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity tile = this.worldObj.getTileEntity(xCoord + side.offsetX, yCoord + side.offsetY, zCoord + side.offsetZ);
			
			if (tile instanceof IEnergyProvider){
				storage.receiveEnergy(((IEnergyProvider)tile).extractEnergy(side.getOpposite(), storage.extractEnergy(storage.getMaxExtract(), true), false), false);
			}else if (tile instanceof IEnergyHandler){
				storage.receiveEnergy(((IEnergyHandler)tile).extractEnergy(side.getOpposite(), storage.extractEnergy(storage.getMaxExtract(), true), false), false);
			}
		}
	}

	@Override
	public String getInventoryName() {
		return this.isInventoryNameLocalized() ? this.localizedName : "HC Tile Entity";
	}
	
	public boolean isInventoryNameLocalized() {
		return this.localizedName != null && this.localizedName.length() > 0;
	}
	
	@Override
	public int getSizeInventory() {
		return this.slots.length;
	}
	
	public String getName() {
		return this.getBlockType().getUnlocalizedName();
	}
	
	public int getType() {
		return getBlockMetadata();
	}
	
	public void setGuiDisplayName(String name){
		this.localizedName = name;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return this.slots[var1];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		if (this.slots[var1] != null){
			ItemStack itemstack;
			
			if (this.slots[var1].stackSize <= var2){
				itemstack = this.slots[var1];
				this.slots[var1] = null;
				return itemstack;
			}else{
				itemstack = this.slots[var1].splitStack(var2);
				
				if (this.slots[var1].stackSize == 0){
					this.slots[var1] = null;
				}
				return itemstack;
			}
		}else{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (this.slots[i] != null){
			ItemStack itemstack = this.slots[i];
			this.slots[i] = null;
			return itemstack;
		}else{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.slots[i] = itemstack;
		
		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()){
			itemstack.stackSize = this.getInventoryStackLimit();
		}markDirty();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return this.worldObj.getTileEntity(xCoord, yCoord, zCoord) == this;
	}

	@Override
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
		
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}
	
	public Packet getDescriptionPacket(){
		NBTTagCompound nbtTag = new NBTTagCompound();
		nbtTag.setInteger("Energy", this.storage.getEnergyStored());
		this.energy = nbtTag.getInteger("Energy");
		this.writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbtTag);
	}
	
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet){
		super.onDataPacket(net, packet);
		this.readFromNBT(packet.func_148857_g());
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {		
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {		
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {		
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {		
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {		
		return storage.getMaxEnergyStored();
	}
	
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		
		if (nbt.hasKey("storage")){
			this.storage.readFromNBT(nbt.getCompoundTag("storage"));
		}
		this.flag = nbt.getBoolean("flag");
		this.flag1 = nbt.getBoolean("flag1");
		NBTTagList list = nbt.getTagList("Items", 10);
		this.slots = new ItemStack[this.getSizeInventory()];
		
		for (int i = 0; i < list.tagCount(); i++){
			NBTTagCompound compound = list.getCompoundTagAt(i);
			byte b = compound.getByte("slot");
			
			if (b >= 0 && b < this.slots.length){
				this.slots[b] = ItemStack.loadItemStackFromNBT(compound);
			}
		}
	}
	
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		
		NBTTagCompound energyTag = new NBTTagCompound();
		this.storage.writeToNBT(energyTag);
		nbt.setTag("storage", energyTag);
		nbt.setBoolean("flag", this.flag);
		nbt.setBoolean("flag1", this.flag1);
		NBTTagList list = new NBTTagList();
		
		for (int i = 0; i < this.slots.length; i++){
			if (this.slots[i] != null){
				NBTTagCompound compound = new NBTTagCompound();
				compound.setByte("slot", (byte)i);
				this.slots[i].writeToNBT(compound);
				list.appendTag(compound);
			}
		}
		nbt.setTag("Items", list);
		
		if (this.isInventoryNameLocalized()) {
			nbt.setString("CustomName", this.localizedName);
		}
	}
}
