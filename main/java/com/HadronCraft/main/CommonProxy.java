package com.HadronCraft.main;

import com.HadronCraft.handler.GuiHandler;

import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy {
	
	public void registerRenderInfo(){
		
	}
	
	public int addArmor(String armor){
		return 0;
	}
	
	public void registerNetworkStuff(){
		NetworkRegistry.INSTANCE.registerGuiHandler(MainRegistry.modInstance, new GuiHandler());
	}
}
