package com.keletu.forgotten_relics.items;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import com.keletu.forgotten_relics.Main;
import com.keletu.forgotten_relics.config.RelicsConfigHandler;
import com.keletu.forgotten_relics.proxy.CommonProxy;
import com.keletu.forgotten_relics.utils.SuperpositionHandler;
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
import thaumcraft.api.items.IVisDiscountGear;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import java.util.List;

public class ItemArcanum extends ItemBaubleBase implements IBauble, IVisDiscountGear {

	public void registerRenderers() {}

	public ItemArcanum() {
		super("arcanum");
		this.maxStackSize = 1;
		this.setTranslationKey("arcanum");
		this.setCreativeTab(Main.tabForgottenRelics);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4)
	{
		par3List.add(I18n.format("item.ItemArcanum1.lore") + " " + 35 + "%");
		par3List.add(I18n.format("item.FREmpty.lore"));

		if(GuiScreen.isShiftKeyDown()){

			par3List.add(I18n.format("item.ItemArcanum2.lore"));
			par3List.add(I18n.format("item.ItemArcanum3.lore"));
			par3List.add(I18n.format("item.ItemArcanum4.lore"));
			par3List.add(I18n.format("item.ItemArcanum5.lore"));
			par3List.add(I18n.format("item.ItemArcanum6.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.ItemArcanum7.lore"));
			par3List.add(I18n.format("item.ItemArcanum8.lore"));
			par3List.add(I18n.format("item.ItemArcanum9.lore"));
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
		return BaubleType.CHARM;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
		super.onWornTick(itemstack, entity);

		if (entity instanceof EntityPlayer & Math.random() <= 0.000208 & !entity.world.isRemote) {

			for (int counter = 0; counter <=32; counter++) {
				if (SuperpositionHandler.validTeleportRandomly(entity, entity.world, 32)) {
					break;
				}
			}

		}

		else if (Math.random() <= 0.000027 & !entity.world.isRemote & entity instanceof EntityPlayer) {

			ItemStack replacedStack = new ItemStack(CommonProxy.dormantArcanum);
			ItemNBTHelper.setInt(replacedStack, "ILifetime", (int) ((12 + (Math.random() * 60)) * RelicsConfigHandler.dormantArcanumVisMult));

			IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(((EntityPlayer)entity));
			baubles.setStackInSlot(6, replacedStack);

		}

	}

	@Override
	public int getVisDiscount(ItemStack itemStack, EntityPlayer entityPlayer) {
		return 35;
	}
}
