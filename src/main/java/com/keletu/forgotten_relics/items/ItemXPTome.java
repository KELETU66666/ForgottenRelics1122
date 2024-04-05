package com.keletu.forgotten_relics.items;

import com.keletu.forgotten_relics.Main;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.common.lib.SoundsTC;
import vazkii.botania.common.core.helper.ExperienceHelper;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import java.util.List;

public class ItemXPTome extends Item {

	public final String TAG_ABSORPTION = "AbsorptionMode";
	public final int xpPortion = 5;

	public ItemXPTome() {

		this.maxStackSize = 1;
		this.setTranslationKey("xp_tome");
		this.setRegistryName("xp_tome");
		this.setCreativeTab(Main.tabForgottenRelics);

	}

	@SideOnly(Side.CLIENT)
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4) {

		String cMode;
		if (!ItemNBTHelper.getBoolean(par1ItemStack, "IsActive", false))
			cMode = I18n.format("item.ItemXPTomeDeactivated.lore");
		else if (ItemNBTHelper.getBoolean(par1ItemStack, this.TAG_ABSORPTION, true))
			cMode = I18n.format("item.ItemXPTomeAbsorption.lore");
		else
			cMode = I18n.format("item.ItemXPTomeExtraction.lore");

		if(GuiScreen.isShiftKeyDown()){
			par3List.add(I18n.format("item.ItemXPTome1.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.ItemXPTome2.lore"));
			par3List.add(I18n.format("item.ItemXPTome3.lore"));
			par3List.add(I18n.format("item.ItemXPTome4.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.ItemXPTome5.lore"));
			par3List.add(I18n.format("item.ItemXPTome6.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.ItemXPTome7.lore"));
			par3List.add(I18n.format("item.ItemXPTome8.lore"));
			par3List.add(I18n.format("item.ItemXPTome9.lore"));

		}
		else {
			par3List.add(I18n.format("item.FRShiftTooltip.lore"));

		}
		par3List.add(I18n.format("item.FREmpty.lore"));
		par3List.add(I18n.format("item.ItemXPTomeMode.lore") + " " + cMode);
		par3List.add(I18n.format("item.FREmpty.lore"));
		par3List.add(I18n.format("item.ItemXPTomeExp.lore"));
		par3List.add(I18n.format("item.FRCode6.lore") + ItemNBTHelper.getInt(par1ItemStack, "XPStored", 0) + " " + I18n.format("item.ItemXPTomeUnits.lore") + " " + ExperienceHelper.getLevelForExperience(ItemNBTHelper.getInt(par1ItemStack, "XPStored", 0)) + " " + I18n.format("item.ItemXPTomeLevels.lore"));

	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean b) {

		if (!(entity instanceof EntityPlayer) || world.isRemote || !ItemNBTHelper.getBoolean(itemstack, "IsActive", false))
			return;

		boolean action = false;

		EntityPlayer player = (EntityPlayer) entity;

		if (ItemNBTHelper.getBoolean(itemstack, this.TAG_ABSORPTION, true)) {

			if (ExperienceHelper.getPlayerXP(player) >= this.xpPortion) {
				ExperienceHelper.drainPlayerXP(player, this.xpPortion);
				ItemNBTHelper.setInt(itemstack, "XPStored", ItemNBTHelper.getInt(itemstack, "XPStored", 0) + this.xpPortion);
				action = true;
			}
			else if (ExperienceHelper.getPlayerXP(player) > 0 & ExperienceHelper.getPlayerXP(player) < this.xpPortion) {
				int exp = ExperienceHelper.getPlayerXP(player);
				ExperienceHelper.drainPlayerXP(player, exp);
				ItemNBTHelper.setInt(itemstack, "XPStored", ItemNBTHelper.getInt(itemstack, "XPStored", 0) + exp);
				action = true;
			}


		} else {

			int xp = ItemNBTHelper.getInt(itemstack, "XPStored", 0);

			if (xp >= this.xpPortion) {
				ItemNBTHelper.setInt(itemstack, "XPStored", xp-this.xpPortion);
				ExperienceHelper.addPlayerXP(player, this.xpPortion);
				action = true;
			} else if (xp > 0 & xp < this.xpPortion) {
				ItemNBTHelper.setInt(itemstack, "XPStored", 0);
				ExperienceHelper.addPlayerXP(player, xp);
				action = true;
			}

		}

		if (action)
			player.inventoryContainer.detectAndSendChanges();

	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);

		if (!player.isSneaking()) {

			if (ItemNBTHelper.getBoolean(stack, this.TAG_ABSORPTION, true)) {
				ItemNBTHelper.setBoolean(stack, this.TAG_ABSORPTION, false);
				world.playSound(null, player.getPosition(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 1.0F, (float) (0.4F + (Math.random() * 0.1F)));
			}
			else {
				ItemNBTHelper.setBoolean(stack, this.TAG_ABSORPTION, true);
				world.playSound(null, player.getPosition(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 1.0F, (float) (0.4F + (Math.random() * 0.1F)));
			}
		} else {

			if (ItemNBTHelper.getBoolean(stack, "IsActive", false)) {
				ItemNBTHelper.setBoolean(stack, "IsActive", false);
				world.playSound(null, player.getPosition(), SoundsTC.fly, SoundCategory.PLAYERS, 1.0F, (float) (0.8F + (Math.random() * 0.2F)));
			}
			else {
				ItemNBTHelper.setBoolean(stack, "IsActive", true);
				world.playSound(null, player.getPosition(), SoundsTC.fly, SoundCategory.PLAYERS, 1.0F, (float) (0.8F + (Math.random() * 0.2F)));
			}
		}



		player.swingArm(hand);



		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public boolean isFull3D() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {

		return ItemNBTHelper.getBoolean(stack, "IsActive", false);

	}


	@Override
	public EnumRarity getRarity(ItemStack itemStack)
	{
		return EnumRarity.EPIC;
	}


}
