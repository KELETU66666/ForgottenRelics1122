package com.keletu.forgotten_relics.items;

import javax.annotation.Nonnull;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.client.render.IModelRegister;
import vazkii.botania.common.core.BotaniaCreativeTab;

public abstract class ItemModBase extends Item implements IModelRegister {
    public ItemModBase(String name) {
        this.setCreativeTab(BotaniaCreativeTab.INSTANCE);
        this.setRegistryName(new ResourceLocation("forgotten_relics", name));
        this.setTranslationKey(name);
    }

    @Nonnull
    public String getUnlocalizedNameInefficiently(@Nonnull ItemStack par1ItemStack) {
        return super.getUnlocalizedNameInefficiently(par1ItemStack).replaceAll("item\\.", "item.forgotten_relics:");
    }

    @SideOnly(Side.CLIENT)
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }
}
