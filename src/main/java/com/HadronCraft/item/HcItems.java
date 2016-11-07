package com.HadronCraft.item;

import com.HadronCraft.creativetabs.HcTabs;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public class HcItems {
	
	public static void mainRegistry(){
		initializeItem();
		registerItem();
		registerOreItem();
	}
	
		//Tool Materials
	public static ToolMaterial Steel = EnumHelper.addToolMaterial("Steel", 2, 850, 7.2F, 3.0F, 15);
	public static ToolMaterial Bronze = EnumHelper.addToolMaterial("Bronze", 2, 500, 6.2F, 2.0F, 15);
	
	public static ArmorMaterial SteelArmor = EnumHelper.addArmorMaterial("SteelArmor", 27, new int[] {4, 9, 4, 2}, 15);
	public static ArmorMaterial BronzeArmor = EnumHelper.addArmorMaterial("BronzeArmor", 20, new int[] {4, 8, 3, 2}, 15);
	
		//Ingots
	public static Item ingotCopper;
	public static Item ingotTin;
	public static Item ingotSilver;
	public static Item dustCopper;
	public static Item dustTin;
	public static Item dustSilver;
	public static Item dustUranium;
	public static Item dustPlutonium;
		//Blended Ingots
	public static Item ingotSteel;
	public static Item ingotBronze;
	public static Item dustSteel;
	public static Item dustBronze;
		//Crafting Materials
	public static Item circuitBasic;
	public static Item circuitAdvanced;
	public static Item circuitElite;
	public static Item plateSteel;
	public static Item plateBronze;
		//Tools
	public static Item steelPick;
	public static Item steelAxe;
	public static Item steelShovel;
	public static Item steelSword;
	public static Item steelHoe;
	public static Item bronzePick;
	public static Item bronzeAxe;
	public static Item bronzeShovel;
	public static Item bronzeSword;
	public static Item bronzeHoe;
	//Energy Items
	public static Item batteryBasic;
	public static Item batteryAdvanced;
	public static Item batteryElite;
	public static Item batteryUltimate;
	
	public static void initializeItem(){
		ingotCopper = new Item().setUnlocalizedName("ingotCopper").setCreativeTab(HcTabs.tabHC).setTextureName("hadroncraft:ingotCopper");
		ingotTin = new Item().setUnlocalizedName("ingotTin").setCreativeTab(HcTabs.tabHC).setTextureName("hadroncraft:ingotTin");
		ingotSilver = new Item().setUnlocalizedName("ingotSilver").setCreativeTab(HcTabs.tabHC).setTextureName("hadroncraft:ingotSilver");
		ingotSteel = new Item().setUnlocalizedName("ingotSteel").setCreativeTab(HcTabs.tabHC).setTextureName("hadroncraft:ingotSteel");
		ingotBronze = new Item().setUnlocalizedName("ingotBronze").setCreativeTab(HcTabs.tabHC).setTextureName("hadroncraft:ingotBronze");
		dustCopper = new Item().setUnlocalizedName("dustCopper").setCreativeTab(HcTabs.tabHC).setTextureName("hadroncraft:dustCopper");
		dustTin = new Item().setUnlocalizedName("dustTin").setCreativeTab(HcTabs.tabHC).setTextureName("hadroncraft:dustTin");
		dustSilver = new Item().setUnlocalizedName("dustSilver").setCreativeTab(HcTabs.tabHC).setTextureName("hadroncraft:dustSilver");
		dustSteel = new Item().setUnlocalizedName("dustSteel").setCreativeTab(HcTabs.tabHC).setTextureName("hadroncraft:dustSteel");
		dustBronze = new Item().setUnlocalizedName("dustBronze").setCreativeTab(HcTabs.tabHC).setTextureName("hadroncraft:dustBronze");
		dustUranium = new Item().setUnlocalizedName("dustUranium").setCreativeTab(HcTabs.tabHC);
		dustPlutonium = new Item().setUnlocalizedName("dustPlutonium").setCreativeTab(HcTabs.tabHC);
		plateSteel = new Item().setUnlocalizedName("plateSteel").setCreativeTab(HcTabs.tabHC).setTextureName("hadroncraft:plateSteel");
		plateBronze = new Item().setUnlocalizedName("plateBronze").setCreativeTab(HcTabs.tabHC).setTextureName("hadroncraft:plateBronze");
		circuitBasic = new Item().setUnlocalizedName("circuitBasic").setCreativeTab(HcTabs.tabHC).setTextureName("hadroncraft:circuitBasic");
		circuitAdvanced = new Item().setUnlocalizedName("circuitAdvanced").setCreativeTab(HcTabs.tabHC).setTextureName("hadroncraft:circuitAdvanced");
		circuitElite = new Item().setUnlocalizedName("circuitElite").setCreativeTab(HcTabs.tabHC).setTextureName("hadroncraft:circuitElite");
		steelPick = new SteelPick(Steel).setUnlocalizedName("steelPick").setCreativeTab(HcTabs.tabHC);
		steelAxe = new SteelAxe(Steel).setUnlocalizedName("steelAxe").setCreativeTab(HcTabs.tabHC);
		steelShovel = new SteelShovel(Steel).setUnlocalizedName("steelShovel").setCreativeTab(HcTabs.tabHC);
		steelSword = new SteelSword(Steel).setUnlocalizedName("steelSword").setCreativeTab(HcTabs.tabHC);
		steelHoe = new SteelHoe(Steel).setUnlocalizedName("steelHoe").setCreativeTab(HcTabs.tabHC);
		bronzePick = new BronzePick(Bronze).setUnlocalizedName("bronzePick").setCreativeTab(HcTabs.tabHC);
		bronzeAxe = new BronzeAxe(Bronze).setUnlocalizedName("bronzeAxe").setCreativeTab(HcTabs.tabHC);
		bronzeShovel = new BronzeShovel(Bronze).setUnlocalizedName("bronzeShovel").setCreativeTab(HcTabs.tabHC);
		bronzeSword = new BronzeSword(Bronze).setUnlocalizedName("bronzeSword").setCreativeTab(HcTabs.tabHC);
		bronzeHoe = new BronzeHoe(Bronze).setUnlocalizedName("bronzeHoe").setCreativeTab(HcTabs.tabHC);
		batteryBasic = new Battery(200000, 20000).setUnlocalizedName("batteryBasic").setCreativeTab(HcTabs.tabHC);
		batteryAdvanced = new Battery(800000, 80000).setUnlocalizedName("batteryAdvanced").setCreativeTab(HcTabs.tabHC);
		batteryElite = new Battery(2000000, 200000).setUnlocalizedName("batteryElite").setCreativeTab(HcTabs.tabHC);
		batteryUltimate = new Battery(2000000, 200000).setUnlocalizedName("batteryUltimate").setCreativeTab(HcTabs.tabHC);
	}
	
	public static void registerItem(){
		GameRegistry.registerItem(ingotCopper, ingotCopper.getUnlocalizedName());
		GameRegistry.registerItem(ingotTin, ingotTin.getUnlocalizedName());
		GameRegistry.registerItem(ingotSilver, ingotSilver.getUnlocalizedName());
		GameRegistry.registerItem(ingotSteel, ingotSteel.getUnlocalizedName());
		GameRegistry.registerItem(ingotBronze, ingotBronze.getUnlocalizedName());
		GameRegistry.registerItem(dustCopper, dustCopper.getUnlocalizedName());
		GameRegistry.registerItem(dustTin, dustTin.getUnlocalizedName());
		GameRegistry.registerItem(dustSilver, dustSilver.getUnlocalizedName());
		GameRegistry.registerItem(dustSteel, dustSteel.getUnlocalizedName());
		GameRegistry.registerItem(dustBronze, dustBronze.getUnlocalizedName());
		GameRegistry.registerItem(dustUranium, dustUranium.getUnlocalizedName());
		GameRegistry.registerItem(dustPlutonium, dustPlutonium.getUnlocalizedName());
		GameRegistry.registerItem(plateSteel, plateSteel.getUnlocalizedName());
		GameRegistry.registerItem(plateBronze, plateBronze.getUnlocalizedName());
		GameRegistry.registerItem(circuitBasic, circuitBasic.getUnlocalizedName());
		GameRegistry.registerItem(circuitAdvanced, circuitAdvanced.getUnlocalizedName());
		GameRegistry.registerItem(circuitElite, circuitElite.getUnlocalizedName());
		GameRegistry.registerItem(steelPick, steelPick.getUnlocalizedName());
		GameRegistry.registerItem(steelAxe, steelAxe.getUnlocalizedName());
		GameRegistry.registerItem(steelShovel, steelShovel.getUnlocalizedName());
		GameRegistry.registerItem(steelSword, steelSword.getUnlocalizedName());
		GameRegistry.registerItem(steelHoe, steelHoe.getUnlocalizedName());
		GameRegistry.registerItem(bronzePick, bronzePick.getUnlocalizedName());
		GameRegistry.registerItem(bronzeAxe, bronzeAxe.getUnlocalizedName());
		GameRegistry.registerItem(bronzeShovel, bronzeShovel.getUnlocalizedName());
		GameRegistry.registerItem(bronzeSword, bronzeSword.getUnlocalizedName());
		GameRegistry.registerItem(bronzeHoe, bronzeHoe.getUnlocalizedName());
		GameRegistry.registerItem(batteryBasic, batteryBasic.getUnlocalizedName());
		GameRegistry.registerItem(batteryAdvanced, batteryAdvanced.getUnlocalizedName());
		GameRegistry.registerItem(batteryElite, batteryElite.getUnlocalizedName());
		GameRegistry.registerItem(batteryUltimate, batteryUltimate.getUnlocalizedName());
	}
	
	public static void registerOreItem(){
		OreDictionary.registerOre("ingotCopper", ingotCopper);
		OreDictionary.registerOre("ingotTin", ingotTin);
		OreDictionary.registerOre("ingotSilver", ingotSilver);
		OreDictionary.registerOre("ingotSteel", ingotSteel);
		OreDictionary.registerOre("ingotBronze", ingotBronze);
		OreDictionary.registerOre("dustCopper", dustCopper);
		OreDictionary.registerOre("dustTin", dustTin);
		OreDictionary.registerOre("dustSilver", dustSilver);
		OreDictionary.registerOre("dustSteel", dustSteel);
		OreDictionary.registerOre("dustBronze", dustBronze);
		OreDictionary.registerOre("dustUranium", dustUranium);
		OreDictionary.registerOre("dustPlutonium", dustPlutonium);
		OreDictionary.registerOre("plateSteel", plateSteel);
		OreDictionary.registerOre("plateBronze", plateBronze);
		OreDictionary.registerOre("circuitBasic", circuitBasic);
		OreDictionary.registerOre("circuitAdvanced", circuitAdvanced);
		OreDictionary.registerOre("circuitElite", circuitElite);
	}
}
