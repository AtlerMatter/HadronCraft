package com.HadronCraft.tile;

import com.HadronCraft.block.BlockGeneratorCombustion;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileGeneratorCombustion extends TileGenerator {
	public int lastE;
	public int E;
	public int fuellevel;
	public int fuelMax = 1000000; //Talvez mudar para dar balance
	public int requiredFuel = 100; //Talvez mudar
	public int power = 15; //Mudar se mudar o RequiredFuel
	//Com estas stats queima durante 8.3min se estiver full. Para meter full é preciso 25 carvão
	//cada smelting custa 3000 RF (ou com mekanism 3000 RF)
	// em 8.3min gera 150000 RF, que dá para 50 Smeltings. (3/4 de batteryBox?)
	
	//Generates 15 RF/t
	
	public TileGeneratorCombustion() {
		super("combustionGenerator", 6000, 500, 2);
	}
	
	
	@Override
	public void updateEntity(){
		super.updateEntity();
		energy();
		fuel();
		if (flag != flag1) {flag1 = flag; BlockGeneratorCombustion.updateBlockState(flag, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		}
		markDirty();
	}
	
		
	public void energy(){
		lastE = storage.getEnergyStored();
		if (this.fuellevel >= this.requiredFuel && this.storage.getEnergyStored() <= this.storage.getMaxEnergyStored() - this.power){
			this.storage.receiveEnergy(this.power, false);
			this.fuellevel -= this.requiredFuel;
			flag = true;
		}
		else {flag = false;}
		E = storage.getEnergyStored();
		
		if (E != lastE) BlockGeneratorCombustion.updateBlockState(flag, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
	}
	
	
	public void fuel(){
		ItemStack stack = this.getStackInSlot(0);
		
		if (stack != null && isFuel(stack) && this.fuellevel + fuelValue(stack) <= this.fuelMax){
			this.addFuel(fuelValue(stack));
			--this.slots[0].stackSize;
			
			if (this.slots[0].stackSize <= 0){
				this.slots[0] = null;
			}
		}
	}
	
	public static boolean isFuel(ItemStack stack){
		return fuelValue(stack) > 0;
	}
	
	public static int fuelValue(ItemStack stack){
		if (stack == null) return 0;
		else{
			Item item = stack.getItem();
									
			if(item == new ItemStack (Items.coal).getItem()) return 40000;
			else if (item == new ItemStack (Items.stick).getItem()) return 1500;
			else if (item instanceof ItemHoe && ((ItemHoe) item).getToolMaterialName().equals("WOOD")) return 11000;
			else if (item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD")) return 11000;
			else if (item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD")) return 11000;
			else if (item == new ItemStack (Blocks.coal_block).getItem()) return 360000;
			else if (item == new ItemStack (Blocks.log).getItem()) return 10000;
			else if (item == new ItemStack (Blocks.log2).getItem()) return 10000;
			else if (item == new ItemStack (Blocks.planks).getItem()) return 3000;
			else if (item == new ItemStack (Blocks.wooden_slab).getItem()) return 1500;
			else if (item == new ItemStack (Blocks.acacia_stairs).getItem()) return 6000;
			else if (item == new ItemStack (Blocks.oak_stairs).getItem()) return 6000;
			else if (item == new ItemStack (Blocks.birch_stairs).getItem()) return 6000;
			else if (item == new ItemStack (Blocks.spruce_stairs).getItem()) return 6000;
			else if (item == new ItemStack (Blocks.dark_oak_stairs).getItem()) return 6000;
			else if (item == new ItemStack (Blocks.jungle_stairs).getItem()) return 6000;
			
			else return 0;
		}
	}
		
	public void addFuel(int add){
		this.fuellevel += add;
	}
	
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		
		if (nbt.hasKey("storage")){
			this.storage.readFromNBT(nbt.getCompoundTag("storage"));
		}
		this.fuellevel = nbt.getInteger("fuel");
		this.E = nbt.getInteger("E");
		this.lastE = nbt.getInteger("lE");
	}
	
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		
		NBTTagCompound energyTag = new NBTTagCompound();
		this.storage.writeToNBT(energyTag);
		nbt.setTag("storage", energyTag);
		nbt.setInteger("fuel", this.fuellevel);
		nbt.setInteger("E", this.E);
		nbt.setInteger("lE", this.lastE);
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack){
		if (slot == 0) return isFuel(stack);
		else return true;
	}


	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
}
