package com.HadronCraft.gui;

import org.lwjgl.opengl.GL11;

import com.HadronCraft.container.ContainerGeneratorCombustion;
import com.HadronCraft.tile.TileGeneratorCombustion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiGeneratorCombustion extends GuiContainer{
	
	public static final ResourceLocation bground = new ResourceLocation("hadroncraft:textures/gui/CombustionGenerator.png");
	public TileGeneratorCombustion entity;

	public GuiGeneratorCombustion(InventoryPlayer inventoryPlayer, TileGeneratorCombustion entity) {
		super(new ContainerGeneratorCombustion(inventoryPlayer, entity));
		this.entity = entity;
		this.xSize = 176;
		this.ySize = 166;
	}
	
	protected void drawGuiContainerForegroundLayer(int par1, int par2){
		String name = "Combustion Generator";
		String power = this.entity.power * 20 + " RF/s";
		String energy = this.entity.energy + " RF";
		//String fuel = this.entity.fuellevel / 10000 + "%";
		int fuel = this.entity.fuellevel;
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.FONT_HEIGHT = 5;
		this.fontRendererObj.drawString("Energy: " + energy, 50, 26, 16777215);
		this.fontRendererObj.drawString("Power: " + power, 50, 35, 16777215);
		//this.fontRendererObj.drawString("Fuel: " + fuel, 50, 44, 16777215);
		this.fontRendererObj.drawString("Fuel: " + fuel, 50, 44, 16777215);
	}
	
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		int k = this.entity.energy * 2 / 100;
		this.drawTexturedModalRect(this.guiLeft + 7, this.guiTop + 74 - k, 177, 62 - k, 13, k);
	}

}
