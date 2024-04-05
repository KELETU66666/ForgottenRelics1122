package com.keletu.forgotten_relics.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.keletu.forgotten_relics.Main;
import com.keletu.forgotten_relics.config.RelicsConfigHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.items.IWarpingGear;

import java.util.List;

public class ItemParadox extends ItemSword implements IWarpingGear
{

	public ItemParadox (ToolMaterial m)
	{
		super(m);
		this.setCreativeTab(Main.tabForgottenRelics);
		this.setRegistryName("paradox");
		this.setTranslationKey("paradox");
	}

	@Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {

		double currentDamage = Math.random() * RelicsConfigHandler.paradoxDamageCap;

		entity.attackEntityFrom(DamageSource.causePlayerDamage(player), (float) currentDamage);
		player.attackEntityFrom(DamageSource.causePlayerDamage(player), (float) (RelicsConfigHandler.paradoxDamageCap - currentDamage));

		return true;
	}

	 @Override
	 @SideOnly(Side.CLIENT)
	 public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4)
	 {
		 if(GuiScreen.isShiftKeyDown()){
			 par3List.add(I18n.format("item.ItemParadox1.lore"));
			 par3List.add(I18n.format("item.ItemParadox2.lore"));
			 par3List.add(I18n.format("item.ItemParadox3.lore"));
			 par3List.add(I18n.format("item.ItemParadox4.lore"));
			 par3List.add(I18n.format("item.ItemParadox5.lore"));
		 }
		 else {
			 par3List.add(I18n.format("item.FRShiftTooltip.lore"));

		 }
		 par3List.add(I18n.format("item.FREmpty.lore"));
	 }

	@Override
	public EnumRarity getRarity (ItemStack stack) {
		return EnumRarity.EPIC;
	}

	@Override
	public void onUpdate (ItemStack stack, World world, Entity entity, int i, boolean b) {
		if ( (!world.isRemote) && (stack.isItemDamaged()) && (entity.ticksExisted % 20 == 0))
		{
			stack.damageItem(-1, (EntityLivingBase) entity);
		}

	}

	@Override
	public int getWarp(ItemStack arg0, EntityPlayer arg1) {
		return 8;
	}

	@SideOnly(Side.CLIENT)
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
	}
}