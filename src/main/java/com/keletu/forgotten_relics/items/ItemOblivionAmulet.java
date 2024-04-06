package com.keletu.forgotten_relics.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.keletu.forgotten_relics.Main;
import com.keletu.forgotten_relics.utils.DamageRegistryHandler;
import com.keletu.forgotten_relics.utils.SuperpositionHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.items.IRechargable;
import thaumcraft.api.items.IWarpingGear;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import java.util.List;

public class ItemOblivionAmulet extends ItemBaubleBase implements IBauble, IWarpingGear, IRechargable {

	public void registerRenderers() {}

	public ItemOblivionAmulet() {
		super("oblivion_amulet");
		this.maxStackSize = 1;
		this.setTranslationKey("oblivion_amulet");
		this.setCreativeTab(Main.tabForgottenRelics);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4)
	{
		if(GuiScreen.isShiftKeyDown()){
			par3List.add(I18n.format("item.ItemOblivionAmulet1.lore"));
			par3List.add(I18n.format("item.ItemOblivionAmulet2.lore"));
			par3List.add(I18n.format("item.ItemOblivionAmulet3.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.ItemOblivionAmulet4.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.ItemOblivionAmulet5.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(SuperpositionHandler.getBaubleTooltip(this.getBaubleType(par1ItemStack)));
		}
		else {
			par3List.add(I18n.format("item.FRShiftTooltip.lore"));

		}
		par3List.add(I18n.format("item.FREmpty.lore"));

		if (par1ItemStack.hasTagCompound()) {

			par3List.add(I18n.format("item.ItemOblivionAmuletDamage.lore") + " " + Math.round(par1ItemStack.getTagCompound().getFloat("IDamageStored") * 100.0) / 100.0);
			par3List.add(I18n.format("item.FREmpty.lore"));
		}
	}


	@Override
	public EnumRarity getRarity(ItemStack itemStack)
	{
		return EnumRarity.EPIC;
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.AMULET;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
		super.onWornTick(itemstack, entity);

		if (!entity.world.isRemote & itemstack.hasTagCompound() & Math.random() <= 0.0008) {

			if (ItemNBTHelper.getInt(itemstack, "IDamageStored", 0) > 0) {

				float getDamage = (float) (ItemNBTHelper.getFloat(itemstack, "IDamageStored", 0) * Math.random());

				if (getDamage > 100 & Math.random() <= 0.9) {
					getDamage = (float) (100 * Math.random());
				}

				ItemNBTHelper.setFloat(itemstack, "IDamageStored", ItemNBTHelper.getFloat(itemstack, "IDamageStored", 0F) - getDamage);

				entity.attackEntityFrom(new DamageRegistryHandler.DamageSourceOblivion(), getDamage);

			}

		} else if (!entity.world.isRemote & Math.random() <= 0.0004) {
			double omega = Math.random();
			int PotionID = 0;

			if (omega <= 0.25)
				PotionID = 4;
			else if (omega <= 0.5)
				PotionID = 15;
			else if (omega <= 0.75)
				PotionID = 18;
			else
				PotionID = 20;

			entity.addPotionEffect(new PotionEffect(Potion.getPotionById(PotionID), (int) (100 + Math.random()*2000), (int) (Math.random()*3), true, false));

		}

	}

	@Override
	public int getWarp(ItemStack arg0, EntityPlayer arg1) {
		return 4;
	}


	@Override
	public int getMaxCharge(ItemStack itemStack, EntityLivingBase entityLivingBase) {
		return 400;
	}

	@Override
	public EnumChargeDisplay showInHud(ItemStack itemStack, EntityLivingBase entityLivingBase) {
		return EnumChargeDisplay.NORMAL;
	}
}
