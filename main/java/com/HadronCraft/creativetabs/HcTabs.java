package com.HadronCraft.creativetabs;

import net.minecraft.creativetab.CreativeTabs;

public class HcTabs {
	
	public static CreativeTabs tabHC;
	
	public static void initializeTabs(){
		tabHC = new CreativeTabMain("HCMainTab");
	}
}
