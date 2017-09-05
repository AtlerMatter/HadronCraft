package com.HadronCraft.handler;

import com.HadronCraft.container.ContainerElectricSmelter;
import com.HadronCraft.container.ContainerFissionReactor;
import com.HadronCraft.container.ContainerGeneratorCombustion;
import com.HadronCraft.gui.GuiElectricSmelter;
import com.HadronCraft.gui.GuiFissionReactor;
import com.HadronCraft.gui.GuiGeneratorCombustion;
import com.HadronCraft.tile.TileElectricSmelter;
import com.HadronCraft.tile.TileFissionReactor;
import com.HadronCraft.tile.TileGeneratorCombustion;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
	

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		
		switch(ID){
			case 0:
				if (entity instanceof TileGeneratorCombustion)
					return new ContainerGeneratorCombustion(player.inventory, (TileGeneratorCombustion) entity);
				return null;
			case 1:
				if (entity instanceof TileElectricSmelter)
					return new ContainerElectricSmelter(player.inventory, (TileElectricSmelter) entity);
				return null;
			case 2:
				if (entity instanceof TileFissionReactor)
					return new ContainerFissionReactor(player.inventory, (TileFissionReactor) entity);
				return null;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		
		switch(ID){
			case 0:
				if (entity instanceof TileGeneratorCombustion)
					return new GuiGeneratorCombustion(player.inventory, (TileGeneratorCombustion) entity);
				return null;		
			case 1:
				if (entity instanceof TileElectricSmelter)
					return new GuiElectricSmelter(player.inventory, (TileElectricSmelter) entity);
				return null;
			case 2:
				if (entity instanceof TileFissionReactor)
					return new GuiFissionReactor(player.inventory, (TileFissionReactor) entity);
				return null;
		}
		return null;
	}
}
