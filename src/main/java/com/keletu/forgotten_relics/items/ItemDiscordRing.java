package com.keletu.forgotten_relics.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.keletu.forgotten_relics.Main;
import com.keletu.forgotten_relics.utils.RelicsKeybindHandler;
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
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ItemDiscordRing extends ItemBaubleBase implements IBauble {

	public ItemDiscordRing() {
		super("discord_ring");
		this.maxStackSize = 1;
		this.setTranslationKey("discord_ring");
		this.setCreativeTab(Main.tabForgottenRelics);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4)
	{
		String keyName = "???";

		try {
			keyName = Keyboard.getKeyName(RelicsKeybindHandler.discordRingKey.getKeyCode());
		} catch (Exception ex) {}

		if(GuiScreen.isShiftKeyDown()){
			par3List.add(I18n.format("item.ItemDiscordRing1.lore"));
			par3List.add(I18n.format("item.ItemDiscordRing2.lore"));
			par3List.add(I18n.format("item.ItemDiscordRing3.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.ItemDiscordRing4.lore") + " " + I18n.format("item.FRCode6.lore") + keyName);
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
		return BaubleType.RING;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
		super.onWornTick(itemstack, entity);
	}


}
