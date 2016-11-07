package com.HadronCraft.creativetabs;

import com.HadronCraft.block.HcBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabMain extends CreativeTabs {

	public CreativeTabMain(String lable) {
		super(lable);
	}

	@Override
	public Item getTabIconItem() {
		return Item.getItemFromBlock(HcBlocks.oreUranium);
	}

}
