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
import net.minecraftforge.common.util.ForgeDirection;

public class GuiElectricSmelter extends GuiContainer{
	
	public static final ResourceLocation bground = new ResourceLocation("hadroncraft:textures/gui/ElectricSmelter.png");
	private TileElectricSmelter entity;

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
		this.fontRendererObj.drawString(energy, 41, 57, 4210752);
	}
	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		int k = entity.energy * 60 / entity.getMaxEnergyStored(ForgeDirection.UP);
		this.drawTexturedModalRect(guiLeft + 7, guiTop + 74 - k, 177, 79 - k, 9, k);

		int j = entity.currentSmeltTime * 24 / entity.smeltTime;
		this.drawTexturedModalRect(guiLeft + 79, guiTop + 34, 176, 0, j, 17);
	}
}
