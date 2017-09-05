package com.HadronCraft.main;

import com.HadronCraft.block.HcBlocks;
import com.HadronCraft.creativetabs.HcTabs;
import com.HadronCraft.handler.CraftingManager;
import com.HadronCraft.item.HcItems;
import com.HadronCraft.lib.RefStrings;
import com.HadronCraft.tile.HcTileEntity;
import com.HadronCraft.worldgen.World;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

	@Mod(modid = RefStrings.MODID , name = RefStrings.NAME , version = RefStrings.VERSION)
	
	public class MainRegistry {
		
		//GUI ID's
		public static final int guiIdGeneratorCombustion = 0;
		public static final int guiIdElectricSmelter = 1;
		public static final int guiIdFissionReactor = 2;
		

	@SidedProxy(clientSide = RefStrings.CLIENTSIDE , serverSide = RefStrings.SERVERSIDE)
	public static CommonProxy proxy;
	
	@Instance(RefStrings.MODID)
	public static MainRegistry modInstance;
	
	@EventHandler
	public static void PreLoad(FMLPreInitializationEvent PreEvent){
		HcTabs.initializeTabs();
		HcBlocks.mainRegistry();
		HcItems.mainRegistry();
		HcTileEntity.mainRegistry();
		World.mainRegistry();
		CraftingManager.mainRegistry();
		proxy.registerRenderInfo();
	}
	@EventHandler
	public static void Load(FMLInitializationEvent event){
		proxy.registerNetworkStuff();
	}
	@EventHandler
	public static void PostLoad(FMLPostInitializationEvent PostEvent){
		
	}
}
