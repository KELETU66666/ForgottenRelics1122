package com.keletu.forgotten_relics.items;

import com.keletu.forgotten_relics.Main;
import static com.keletu.forgotten_relics.config.RelicsConfigHandler.weatherStoneVisMult;
import com.keletu.forgotten_relics.utils.SuperpositionHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
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
import thaumcraft.api.items.IRechargable;
import static thaumcraft.api.items.IRechargable.EnumChargeDisplay.NORMAL;
import thaumcraft.api.items.RechargeHelper;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.handler.ModSounds;
import vazkii.botania.common.core.helper.Vector3;

import java.util.List;

public class ItemWeatherStone extends Item implements IRechargable {

	public int VisCost = (int) (100 * weatherStoneVisMult);

	public ItemWeatherStone() {

		this.setMaxStackSize(1);
		this.setTranslationKey("weather_stone");
		this.setRegistryName("weather_stone");
		this.setCreativeTab(Main.tabForgottenRelics);

	}

	@SideOnly(Side.CLIENT)
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4)
	{
		if(GuiScreen.isShiftKeyDown()){
			par3List.add(I18n.format("item.ItemWeatherStone1.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.ItemWeatherStone2_1.lore") + " " + this.VisCost + " " + I18n.format("item.ItemWeatherStone2_2.lore"));
		}
		else {
			par3List.add(I18n.format("item.FRShiftTooltip.lore"));

		}
	}
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);

		if (world.isRaining() & !SuperpositionHandler.isOnCoodown(player))
			player.setActiveHand(hand);

		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 60;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {

		Vector3 vec = Vector3.fromEntityCenter(player);

		if(player instanceof EntityPlayer)
			if (count == 1 & player.world.getWorldInfo().isRaining())
				if (RechargeHelper.consumeCharge(stack, player, VisCost)) {

					for(int i = 0; i <= 24; i++) {
						float r = 0.0F;
						float g = 0.3F + (float) Math.random() * 0.5F;
						float b = 0.8F + (float) Math.random() * 0.2F;
						float s = 0.2F + (float) Math.random() * 0.2F;
						float m = 0.15F;
						float xm = ((float) Math.random() - 0.5F) * m;
						float ym = ((float) Math.random() - 0.5F) * m;
						float zm = ((float) Math.random() - 0.5F) * m;


						Botania.proxy.wispFX(vec.x, vec.y, vec.z, r, g, b, s, xm, ym, zm);
					}

					player.world.playSound(vec.x, vec.y, vec.z, ModSounds.altarCraft, SoundCategory.PLAYERS, 1.0F, (float) (0.8F + (Math.random() * 0.2F)), false);

					player.world.getWorldInfo().setRaining(false);
					player.world.getWorldInfo().setRainTime(24000 + ((int) (Math.random()*976000)));

					SuperpositionHandler.setCasted((EntityPlayer) player, 100, false);

				}
	}


	@Override
	public EnumRarity getRarity(ItemStack itemStack)
	{
		return EnumRarity.EPIC;
	}


	@Override
	public int getMaxCharge(ItemStack itemStack, EntityLivingBase entityLivingBase) {
		return (int) (100 * weatherStoneVisMult);
	}

	@Override
	public EnumChargeDisplay showInHud(ItemStack itemStack, EntityLivingBase entityLivingBase) {
		return NORMAL;
	}
}
