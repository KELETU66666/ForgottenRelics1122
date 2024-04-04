package com.keletu.forgotten_relics.items;

import com.keletu.forgotten_relics.Main;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.items.IWarpingGear;

import java.util.List;

public class ItemChaosCore extends Item implements IWarpingGear {

	public void registerRenderers() {}

	public ItemChaosCore() {

		this.maxStackSize = 1;
		this.setTranslationKey("chaos_core");
		this.setRegistryName("chaos_core");
		this.setCreativeTab(Main.tabForgottenRelics);

	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean b) {
		if (!world.isRemote & Math.random() <= 0.000208) {
			int randomizedPotionID = 1 + (int)(Math.random() * 21);
			if (randomizedPotionID == 6 || randomizedPotionID == 7) {
				randomizedPotionID = 20;
			}
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.getPotionById(randomizedPotionID), 100 + (int)(Math.random() * 2400), (int)(Math.random() * 3), false, false));
		}
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4)
	{
		if(GuiScreen.isShiftKeyDown()){
			par3List.add(I18n.format("item.ItemChaosCore1.lore"));
			par3List.add(I18n.format("item.ItemChaosCore2.lore"));
			par3List.add(I18n.format("item.ItemChaosCore3.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.ItemChaosCore4.lore"));
			par3List.add(I18n.format("item.ItemChaosCore5.lore"));
			par3List.add(I18n.format("item.ItemChaosCore6.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.ItemChaosCore7.lore"));
		}
		else {
			par3List.add(I18n.format("item.FRShiftTooltip.lore"));

		}

		par3List.add(I18n.format("item.FREmpty.lore"));
	}


	@Override
	public EnumRarity getRarity(ItemStack itemStack)
	{
		return EnumRarity.EPIC;
	}

	@Override
	public int getWarp(ItemStack arg0, EntityPlayer arg1) {
		return 2;
	}

	@SideOnly(Side.CLIENT)
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
	}
}
