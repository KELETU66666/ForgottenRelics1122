package com.keletu.forgotten_relics.items;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import com.keletu.forgotten_relics.Main;
import com.keletu.forgotten_relics.proxy.CommonProxy;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.common.items.casters.CasterManager;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import java.util.List;

public class ItemDormantArcanum extends ItemBaubleBase implements IBauble {

	public void registerRenderers() {}

	public ItemDormantArcanum() {
		super("dormant_arcanum");
		this.maxStackSize = 1;
		this.setTranslationKey("dormant_arcanum");
		this.setCreativeTab(Main.tabForgottenRelics);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4)
	{

		if(GuiScreen.isShiftKeyDown()){

			par3List.add(I18n.format("item.ItemDormantArcanum1.lore"));
		}
		else {
			par3List.add(I18n.format("item.FRShiftTooltip.lore"));

		}

		if (par1ItemStack.hasTagCompound()) {
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.FRCode6.lore") + (ItemNBTHelper.getInt(par1ItemStack, "ILifetime", 0)*2) + I18n.format("item.ItemDormantArcanum2.lore"));

		}
	}


	@Override
	public EnumRarity getRarity(ItemStack itemStack)
	{
		return EnumRarity.EPIC;
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.CHARM;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
		super.onWornTick(itemstack, entity);

		if (itemstack.hasTagCompound() & !entity.world.isRemote & entity instanceof EntityPlayer) {

			if (ItemNBTHelper.getInt(itemstack, "ILifetime", 0) > 0 ) {

				if (CasterManager.consumeVisFromInventory((EntityPlayer) entity, 3)) {
					ItemNBTHelper.setInt(itemstack, "ILifetime", ItemNBTHelper.getInt(itemstack, "ILifetime", 0) - 1);
				}

			} else {
				IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(((EntityPlayer)entity));
				baubles.setStackInSlot(6, new ItemStack(CommonProxy.arcanum));
			}

		}

	}


}
