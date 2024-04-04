package com.keletu.forgotten_relics.items;

import baubles.api.BaubleType;
import com.google.common.collect.Multimap;
import com.keletu.forgotten_relics.Main;
import com.keletu.forgotten_relics.config.RelicsConfigHandler;
import com.keletu.forgotten_relics.utils.SuperpositionHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemMiningCharm extends ItemBaubleBaseModifier {

    public void registerRenderers() {}

    public ItemMiningCharm() {
        super("mining_charm");

        this.maxStackSize = 1;
        this.setTranslationKey("mining_charm");
        this.setCreativeTab(Main.tabForgottenRelics);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4)
    {
        if(GuiScreen.isShiftKeyDown()){
            par3List.add(I18n.format("item.ItemMiningCharm1_1.lore") + " " + (int) (RelicsConfigHandler.miningCharmBoost*100F) + I18n.format("item.ItemMiningCharm1_2.lore"));
            par3List.add(I18n.format("item.ItemMiningCharm2_1.lore") + " " + RelicsConfigHandler.miningCharmReach + I18n.format("item.ItemMiningCharm2_2.lore"));
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
        return EnumRarity.UNCOMMON;
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.RING;
    }

    void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack) {
        attributes.put(EntityPlayer.REACH_DISTANCE.getName(), new AttributeModifier(getBaubleUUID(stack), "Mining Charm", RelicsConfigHandler.miningCharmReach, 0).setSaved(false));
    }

}
