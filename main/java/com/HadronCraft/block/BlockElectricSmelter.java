package com.HadronCraft.block;

import java.util.Random;

import com.HadronCraft.main.MainRegistry;
import com.HadronCraft.tile.TileElectricSmelter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockElectricSmelter extends BlockContainer{
	
	private IIcon iconMain;
	private IIcon iconFront;
	private IIcon iconBack;
	
	private boolean isActive;
	private static boolean keepInventory;
	private Random rand = new Random();

	protected BlockElectricSmelter(boolean isActive) {
		super(Material.iron);
		this.setHarvestLevel("pickaxe" , 1);
		this.setHardness(3.5F);
		this.setResistance(15.0F);
		
		this.isActive = isActive;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister){
		this.iconBack = iconRegister.registerIcon("hadroncraft:Machine_Input");
		this.iconFront = iconRegister.registerIcon("hadroncraft:ElectricSmelter");
		this.iconMain = iconRegister.registerIcon("hadroncraft:Machine");
	}
	
	@Override
	public IIcon getIcon (int side, int metadata){
		//Setting the block's inventory icon
		if (metadata == 0 && side == 3) return this.iconFront;
		if (metadata == 0 && side == 2) return this.iconBack;
		
		if (metadata == 5 && side == 4 || metadata == 4 && side == 5 || metadata == 2 && side == 3 || metadata == 3 && side == 2){
			return this.iconBack;
		}
		else if (side == metadata){
			return this.iconFront;
		}
		if (side == 0 || side == 1){
			return this.iconMain;
		}
		return this.iconMain;
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z){
		super.onBlockAdded(world, x, y, z);
		this.setDefaultDirection(world, x, y, z);
	}
	
	@Override
	public Item getItemDropped(int par1, Random random, int par3){
		return Item.getItemFromBlock(HcBlocks.electricSmelterIdle);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block oldblock, int oldmetadata) {
		if (!keepInventory){
			TileElectricSmelter tileentity = (TileElectricSmelter) world.getTileEntity(x, y, z);
			
			if (tileentity != null){
				for (int i = 0; i < tileentity.getSizeInventory(); i++){
					ItemStack itemstack = tileentity.getStackInSlot(i);
					
					if(itemstack != null){
						float f = this.rand.nextFloat() * 0.8F + 0.1F;
						float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
						float f2 = this.rand.nextFloat() * 0.8F + 0.1F;
						
						while (itemstack.stackSize > 0){
							int j = this.rand.nextInt(21) + 10;
							
							if (j > itemstack.stackSize){
								j = itemstack.stackSize;
							}
							itemstack.stackSize -= j;
							
							EntityItem item = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));
							
							if (itemstack.hasTagCompound()){
								item.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
							}
							world.spawnEntityInWorld(item);
						}
					}
				}
				world.func_147453_f(x, y, z, oldblock);
			}
		}
		super.breakBlock(world, x, y, z, oldblock, oldmetadata);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileElectricSmelter();
	}
	
	public static void updateBlockState(boolean active, World worldObj, int xCoord, int yCoord, int zCoord){
		int i = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		
		TileEntity tileentity = worldObj.getTileEntity(xCoord, yCoord, zCoord);
		keepInventory = true;
		
		if(active)
		{
			worldObj.setBlock(xCoord, yCoord, zCoord, HcBlocks.electricSmelterActive);
		}
		else
		{
			worldObj.setBlock(xCoord, yCoord, zCoord, HcBlocks.electricSmelterIdle);
		}
		
		keepInventory = false;
		
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, i, 2);
		
		if(tileentity != null)
		{
			tileentity.validate();
			worldObj.setTileEntity(xCoord, yCoord, zCoord, tileentity);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
		if(world.isRemote){
			return true;
		}else{
			player.openGui(MainRegistry.modInstance, MainRegistry.guiIdElectricSmelter, world, x, y, z);
		}
		return true;
	}
	
	private void setDefaultDirection(World world, int x, int y, int z){
		if(!world.isRemote){
			 Block block = world.getBlock(x, y, z - 1);
			 Block block1 = world.getBlock(x, y, z + 1);
			 Block block2 = world.getBlock(x - 1, y, z);
			 Block block3 = world.getBlock(x + 1, y, z);
			 byte b0 = 3;
			 
			 if(block.func_149730_j() && !block.func_149730_j()){
				 b0 = 3;
			 }
			 if(block1.func_149730_j() && !block1.func_149730_j()){
				 b0 = 2;
			 }
			 if(block2.func_149730_j() && !block2.func_149730_j()){
				 b0 = 5;
			 }
			 if(block3.func_149730_j() && !block3.func_149730_j()){
				 b0 = 4;
			 }
			
			world.setBlockMetadataWithNotify(x, y, z, b0, 2);
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack){
		int direction = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360F) + 0.5D) & 3;
		
		if(direction == 0){
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(direction == 1){
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(direction == 2){
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(direction == 3){
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		
		if(itemstack.hasDisplayName()){
			((TileElectricSmelter) world.getTileEntity(x, y, z)).setGuiDisplayName(itemstack.getDisplayName());
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random){
		if(this.isActive){
			int direction = world.getBlockMetadata(x, y, z);
			
			float xx = (float) x + 0.5F, yy= (float) y + random.nextFloat() * 6.0F / 16.0F, zz = (float) z + 0.5F, f = 0.5F, f1 = random.nextFloat() * 0.3F - 0.2F;
					
					if(direction == 4){
						world.spawnParticle("reddust", (double) (xx - f), (double) yy, (double) (zz + f1), 0.0F, 0.0F, 0.0F);
						world.spawnParticle("reddust", (double) (xx - f), (double) yy, (double) (zz + f1), 0.0F, 0.0F, 0.0F);
					}else if(direction == 5){
						world.spawnParticle("reddust", (double) (xx + f), (double) yy, (double) (zz + f1), 0.0F, 0.0F, 0.0F);
						world.spawnParticle("reddust", (double) (xx + f), (double) yy, (double) (zz + f1), 0.0F, 0.0F, 0.0F);
					}else if(direction == 2){
						world.spawnParticle("reddust", (double) (xx + f1), (double) yy, (double) (zz - f), 0.0F, 0.0F, 0.0F);
						world.spawnParticle("reddust", (double) (xx + f1), (double) yy, (double) (zz - f), 0.0F, 0.0F, 0.0F);
					}else if(direction == 3){
						world.spawnParticle("reddust", (double) (xx + f1), (double) yy, (double) (zz + f), 0.0F, 0.0F, 0.0F);
						world.spawnParticle("reddust", (double) (xx + f1), (double) yy, (double) (zz + f), 0.0F, 0.0F, 0.0F);
					}
		}
	}
}
