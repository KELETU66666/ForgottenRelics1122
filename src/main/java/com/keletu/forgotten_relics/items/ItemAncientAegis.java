package com.keletu.forgotten_relics.items;

import baubles.api.BaubleType;
import com.google.common.collect.Multimap;
import com.keletu.forgotten_relics.Main;
import com.keletu.forgotten_relics.config.RelicsConfigHandler;
import com.keletu.forgotten_relics.utils.SuperpositionHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemAncientAegis extends ItemBaubleBaseModifier {

	public ItemAncientAegis() {
		super("ancient_aegis");
		this.setCreativeTab(Main.tabForgottenRelics);
		this.setTranslationKey("ancient_aegis");
	}
	
	 @Override
	 @SideOnly(Side.CLIENT)
	 public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4)
	 {
		 if(GuiScreen.isShiftKeyDown()){
			 par3List.add(I18n.format("item.ItemAncientAegis1_1.lore") + " " + (int) (RelicsConfigHandler.ancientAegisDamageReduction*100F) + I18n.format("item.ItemAncientAegis1_2.lore"));
			 par3List.add(I18n.format("item.ItemAncientAegis2.lore"));
			 par3List.add(I18n.format("item.ItemAncientAegis3.lore"));
			 par3List.add(I18n.format("item.FREmpty.lore"));
			 par3List.add(I18n.format("item.ItemAncientAegis4.lore"));
			 par3List.add(I18n.format("item.ItemAncientAegis5.lore"));
			 par3List.add(I18n.format("item.FREmpty.lore"));
			 par3List.add(SuperpositionHandler.getBaubleTooltip(this.getBaubleType(par1ItemStack)));
		 }
		 else {
			 par3List.add(I18n.format("item.FRShiftTooltip.lore"));
			
		 }
	 }
	 
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		super.onWornTick(stack, player);
		
		if (!player.world.isRemote & player.ticksExisted % 20 == 0 & player.getMaxHealth() != player.getHealth()) {
			player.heal(1);
		}
	}
	
	@Override
	public EnumRarity getRarity(ItemStack itemStack) {
		 return EnumRarity.EPIC;
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.BELT;
	}

	@Override
	void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack) {
		attributes.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(getBaubleUUID(stack), "Bauble modifier", 1, 0));
	}

}