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
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemDarkSunRing extends ItemBaubleBase implements IBauble {

	public void registerRenderers() {}

	public ItemDarkSunRing() {
		super("dark_sun_ring");
		this.maxStackSize = 1;
		this.setTranslationKey("dark_sun_ring");
		this.setCreativeTab(Main.tabForgottenRelics);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4)
	{
		if(GuiScreen.isShiftKeyDown()){
			par3List.add(I18n.format("item.ItemDarkSunRing1.lore"));
			par3List.add(I18n.format("item.ItemDarkSunRing2_1.lore") + " " + (int) RelicsConfigHandler.darkSunRingDamageCap + I18n.format("item.ItemDarkSunRing2_2.lore"));
			par3List.add(I18n.format("item.ItemDarkSunRing3.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.ItemDarkSunRing4_1.lore") + " " + (int) (RelicsConfigHandler.darkSunRingDeflectChance*100F) + I18n.format("item.ItemDarkSunRing4_2.lore"));
			par3List.add(I18n.format("item.ItemDarkSunRing5.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.ItemDarkSunRing6.lore"));
			par3List.add(I18n.format("item.ItemDarkSunRing7.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.ItemDarkSunRing8.lore"));
			par3List.add(I18n.format("item.ItemDarkSunRing9.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(SuperpositionHandler.getBaubleTooltip(this.getBaubleType(par1ItemStack)));
		}
		else {
			par3List.add(I18n.format("item.FRShiftTooltip.lore"));

		}
	}

	@Override
	public EnumRarity getRarity(ItemStack itemStack)
	{
		return EnumRarity.EPIC;
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.RING;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
		super.onWornTick(itemstack, entity);
		if (entity.isBurning()) {
			entity.extinguish();
		}

	}


}
