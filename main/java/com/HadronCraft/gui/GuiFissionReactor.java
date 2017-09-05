/**
 * This file and it's contents belong to AtlerMatter aka lukeDC70 for use in the mod HadronCraft.
 * All of HadronCraft is under the GNU General Public License v3.0
 *
 */
package com.HadronCraft.gui;

import com.HadronCraft.container.ContainerFissionReactor;
import com.HadronCraft.tile.TileFissionReactor;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

/**
 * @author AtlerMatter aka lukeDC70
 *
 */
public class GuiFissionReactor extends GuiContainer {

	public static final ResourceLocation bground = new ResourceLocation("hadroncraft:textures/gui/FissionReactor.png");
	private TileFissionReactor entity;

	public GuiFissionReactor(InventoryPlayer inventoryPlayer, TileFissionReactor entity) {
		super(new ContainerFissionReactor(inventoryPlayer, entity));
		this.entity = entity;
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		// TODO Auto-generated method stub

	}

}
