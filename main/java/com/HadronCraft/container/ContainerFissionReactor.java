/**
 * This file and it's contents belong to AtlerMatter aka lukeDC70 for use in the mod HadronCraft.
 * All of HadronCraft is under the GNU General Public License v3.0
 *
 */
package com.HadronCraft.container;

import com.HadronCraft.tile.TileFissionReactor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

/**
 * @author AtlerMatter aka lukeDC70
 *
 */
public class ContainerFissionReactor extends Container {

	private TileFissionReactor entity;

	public ContainerFissionReactor(InventoryPlayer playerInv, TileFissionReactor entity) {
		this.entity = entity;
		// Add the three slots to their spots

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.entity.isUseableByPlayer(player);
	}

}
