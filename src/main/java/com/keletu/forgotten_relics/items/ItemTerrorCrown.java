package com.keletu.forgotten_relics.items;

import com.keletu.forgotten_relics.Main;
import com.keletu.forgotten_relics.utils.SuperpositionHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.items.IGoggles;
import thaumcraft.api.items.IRevealer;
import thaumcraft.api.items.IWarpingGear;
import thaumcraft.common.lib.utils.EntityUtils;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import java.util.List;

public class ItemTerrorCrown extends ItemArmor implements IWarpingGear, IGoggles, IRevealer {

	public ItemTerrorCrown(EntityEquipmentSlot type, ArmorMaterial mat) {
		super(mat, 0, type);
		this.setCreativeTab(Main.tabForgottenRelics);
		this.setTranslationKey("terror_crown");
		this.setRegistryName("terror_crown");
		this.setMaxDamage(1000);
	}

	@SideOnly(Side.CLIENT)
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		SuperpositionHandler.cryHavoc(world, player, 24);
		this.onUpdate(itemStack, world, player, 0, false);

		if (!world.isRemote) {

			Entity scannedEntity = EntityUtils.getPointedEntity(world, player, 0.0D, 32.0D, 3F, false);
			if (scannedEntity instanceof EntityLivingBase) {
				EntityLivingBase targetEntity = (EntityLivingBase) scannedEntity;


				try {
					targetEntity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100, 2, true, false));
					if (!targetEntity.isPotionActive(MobEffects.WITHER))
						targetEntity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 40, 0, false, false));
					targetEntity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 100, 1, true, false));
					targetEntity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 30, 1, true, false));
					targetEntity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 80, 2, true, false));
				} catch (Exception ex) {}

			}

		}

		// Don't try to understand a stuff down below. It's just a huge mess.

		/*
		if (true) {
		Entity scannedEntity = EntityUtils.getPointedEntity(world, player, 0.0D, 128.0D, 4F);
		if (scannedEntity instanceof EntityLivingBase & !(scannedEntity instanceof EntityPlayer)) {
			EntityLivingBase frozenEntity = (EntityLivingBase) scannedEntity;
			int fID = frozenEntity.getEntityId();
			int nbtID = ItemNBTHelper.getInt(itemStack, "StoredID", -1);

			if (nbtID == fID) {
				frozenEntity.rotationPitch = ItemNBTHelper.getFloat(itemStack, "StoredRotationPitch", frozenEntity.rotationPitch);
				frozenEntity.rotationYaw = ItemNBTHelper.getFloat(itemStack, "StoredRotationYaw", frozenEntity.rotationYaw);
				frozenEntity.rotationYawHead = ItemNBTHelper.getFloat(itemStack, "StoredRotationYawHead", frozenEntity.rotationYawHead);

				double tempY = frozenEntity.posY;

				if (tempY >= ItemNBTHelper.getDouble(itemStack, "StoredY", frozenEntity.posY)) {
					tempY = ItemNBTHelper.getDouble(itemStack, "StoredY", frozenEntity.posY);
				} else {
					ItemNBTHelper.setDouble(itemStack, "StoredY", frozenEntity.posY);
				}

				frozenEntity.setPositionAndRotation(ItemNBTHelper.getDouble(itemStack, "StoredX", frozenEntity.posX), tempY, ItemNBTHelper.getDouble(itemStack, "StoredZ", frozenEntity.posZ), ItemNBTHelper.getFloat(itemStack, "StoredRotationYaw", frozenEntity.rotationYaw), ItemNBTHelper.getFloat(itemStack, "StoredRotationPitch", frozenEntity.rotationPitch));
			} else {
				 ItemNBTHelper.setInt(itemStack, "StoredID", fID);

				 ItemNBTHelper.setFloat(itemStack, "StoredRotationPitch", frozenEntity.rotationPitch);
				 ItemNBTHelper.setFloat(itemStack, "StoredRotationYaw", frozenEntity.rotationYaw);
				 ItemNBTHelper.setFloat(itemStack, "StoredRotationYawHead", frozenEntity.rotationYawHead);

				 ItemNBTHelper.setDouble(itemStack, "StoredX", frozenEntity.posX);
				 ItemNBTHelper.setDouble(itemStack, "StoredY", frozenEntity.posY);
				 ItemNBTHelper.setDouble(itemStack, "StoredZ", frozenEntity.posZ);
			}

			frozenEntity.motionX = 0D;
			if (frozenEntity.motionY > 0)
			frozenEntity.motionY = 0D;
			frozenEntity.motionZ = 0D;

			frozenEntity.velocityChanged = true;


			if (!world.isRemote)
			Main.packetInstance.sendToAllAround(new EntityStateMessage(fID, ItemNBTHelper.getDouble(itemStack, "StoredX", frozenEntity.posX), ItemNBTHelper.getDouble(itemStack, "StoredY", frozenEntity.posY), ItemNBTHelper.getDouble(itemStack, "StoredZ", frozenEntity.posZ), ItemNBTHelper.getFloat(itemStack, "StoredRotationYaw", frozenEntity.rotationYaw), ItemNBTHelper.getFloat(itemStack, "StoredRotationPitch", frozenEntity.rotationPitch), ItemNBTHelper.getFloat(itemStack, "StoredRotationYawHead", frozenEntity.rotationYawHead), true), new TargetPoint(frozenEntity.dimension, frozenEntity.posX, frozenEntity.posY, frozenEntity.posZ, 64.0D));

		} else {
			 ItemNBTHelper.setInt(itemStack, "StoredID", -1);

			 ItemNBTHelper.setFloat(itemStack, "StoredRotationPitch", 0);
			 ItemNBTHelper.setFloat(itemStack, "StoredRotationYaw", 0);
			 ItemNBTHelper.setFloat(itemStack, "StoredRotationYawHead", 0);

			 ItemNBTHelper.setDouble(itemStack, "StoredX", 0);
			 ItemNBTHelper.setDouble(itemStack, "StoredY", 0);
			 ItemNBTHelper.setDouble(itemStack, "StoredZ", 0);
		}

		}
		*/

	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack) {
		return false;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean b) {

		if (itemstack.isItemEnchanted()) {

			NBTTagCompound nbt = ItemNBTHelper.getNBT(itemstack);
			nbt.removeTag("ench");
			itemstack.setTagCompound(nbt);

		}

		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;

			if(!world.isRemote && itemstack.getItemDamage() > 0 && ManaItemHandler.requestManaExact(itemstack, player, 200, true))
				itemstack.setItemDamage(itemstack.getItemDamage() - 1);

		}

	}


	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4)
	{
		if(GuiScreen.isShiftKeyDown()){
			par3List.add(I18n.format("item.ItemTerrorCrown1.lore"));
			par3List.add(I18n.format("item.ItemTerrorCrown2.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.ItemTerrorCrown3.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.ItemTerrorCrown4.lore"));
		}
		else {
			par3List.add(I18n.format("item.FRShiftTooltip.lore"));

		}

		par3List.add(I18n.format("item.FREmpty.lore"));
	}


	@Override
	public EnumRarity getRarity(ItemStack itemStack) {
		return EnumRarity.EPIC;
	}

	@Override
	public int getWarp(ItemStack arg0, EntityPlayer arg1) {
		return 3;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return "forgotten_relics:textures/armor/crown_prs.png";
	}

	public boolean getIsRepairable(final ItemStack par1ItemStack, final ItemStack par2ItemStack) {
		return par2ItemStack.isItemEqual(new ItemStack(Items.GOLD_INGOT)) || super.getIsRepairable(par1ItemStack, par2ItemStack);
	}

	@Override
	public boolean showIngamePopups(ItemStack arg0, EntityLivingBase arg1) {
		return true;
	}

	@Override
	public boolean showNodes(ItemStack arg0, EntityLivingBase arg1) {
		return true;
	}

}
