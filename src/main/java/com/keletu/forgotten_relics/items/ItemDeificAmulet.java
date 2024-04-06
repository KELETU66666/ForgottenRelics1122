package com.keletu.forgotten_relics.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.keletu.forgotten_relics.Main;
import com.keletu.forgotten_relics.config.RelicsConfigHandler;
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
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.items.IRechargable;
import thaumcraft.api.items.RechargeHelper;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemDeificAmulet extends ItemBaubleBase implements IBauble, IRechargable {

	public void registerRenderers() {}

	public ItemDeificAmulet() {
		super("deific_amulet");
		this.setMaxStackSize(1);
		this.setTranslationKey("deific_amulet");
		this.setCreativeTab(Main.tabForgottenRelics);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4) {
		if(GuiScreen.isShiftKeyDown()){
			if (RelicsConfigHandler.deificAmuletEffectImmunity) {
				if (RelicsConfigHandler.deificAmuletOnlyNegatesDebuffs)
					par3List.add(I18n.format("item.ItemDeificAmulet1_alt.lore"));
				else
					par3List.add(I18n.format("item.ItemDeificAmulet1.lore"));
			}
			par3List.add(I18n.format("item.ItemDeificAmulet2.lore"));
			if (RelicsConfigHandler.deificAmuletInvincibility)
				par3List.add(I18n.format("item.ItemDeificAmulet3.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.ItemDeificAmulet4.lore"));
			par3List.add(I18n.format("item.ItemDeificAmulet5.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(SuperpositionHandler.getBaubleTooltip(this.getBaubleType(par1ItemStack)));
		}
		else {
			par3List.add(I18n.format("item.FRShiftTooltip.lore"));

		}
	}


	@Override
	public EnumRarity getRarity(ItemStack itemStack) {
		return EnumRarity.EPIC;
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.AMULET;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
		super.onWornTick(itemstack, entity);

		if (!entity.world.isRemote & entity instanceof EntityPlayer) {

			if (entity.getActivePotionEffects() != null & RelicsConfigHandler.deificAmuletEffectImmunity) {

				if (RelicsConfigHandler.deificAmuletOnlyNegatesDebuffs) {
					Collection<PotionEffect> effects = new ArrayList<PotionEffect>(entity.getActivePotionEffects());

					for (PotionEffect effect : effects) {
						Potion id = effect.getPotion();
						boolean badEffect = ((Boolean) ReflectionHelper.getPrivateValue(Potion.class, id, new String[]{"isBadEffect", "isBadEffect"})).booleanValue();

						if (badEffect)
							entity.removePotionEffect(id);
					}
				} else {
					entity.clearActivePotions();
				}
			}

			if(entity.isBurning()) { entity.extinguish(); }

			if (entity.getAir() == 0) {

				if (RechargeHelper.consumeCharge(itemstack, entity, (int) (10*RelicsConfigHandler.deificAmuletVisMult)))
				{ entity.setAir(300); }

			}

			if (RelicsConfigHandler.deificAmuletInvincibility) {

				if (ItemNBTHelper.getInt(itemstack, "ICooldown", 0) == 0 & entity.hurtResistantTime > 10) {
					entity.hurtResistantTime = 40;
					ItemNBTHelper.setInt(itemstack, "ICooldown", 32);
				}

				if (ItemNBTHelper.getInt(itemstack, "ICooldown", 0) > 0) {
					ItemNBTHelper.setInt(itemstack, "ICooldown", ItemNBTHelper.getInt(itemstack, "ICooldown", 0) - 1);
				}

			}
		}

	}


	@Override
	public int getMaxCharge(ItemStack itemStack, EntityLivingBase entityLivingBase) {
		return 200;
	}

	@Override
	public EnumChargeDisplay showInHud(ItemStack itemStack, EntityLivingBase entityLivingBase) {
		return EnumChargeDisplay.NORMAL;
	}
}
