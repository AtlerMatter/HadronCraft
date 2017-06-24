package com.HadronCraft.gui;

import com.HadronCraft.container.ContainerElectricSmelter;
import com.HadronCraft.container.ContainerGeneratorCombustion;
import com.HadronCraft.tile.TileElectricSmelter;
import com.HadronCraft.tile.TileGeneratorCombustion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiElectricSmelter extends GuiContainer{
	
	public static final ResourceLocation bground = new ResourceLocation("hadroncraft:textures/gui/ElectricSmelter.png");
	public TileElectricSmelter entity;

	public GuiElectricSmelter(InventoryPlayer inventoryPlayer, TileElectricSmelter entity) {
		super(new ContainerElectricSmelter(inventoryPlayer, entity));
		this.entity = entity;
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2){
		//To Finish
		String name = "Electric Smelter";
		String requiredPower = this.entity.requiredEnergy + " RF/s";
		String energy = this.entity.energy + " RF";
		
		this.fontRendererObj.FONT_HEIGHT = 2;
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
	}
	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		//To Finish
		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
}
