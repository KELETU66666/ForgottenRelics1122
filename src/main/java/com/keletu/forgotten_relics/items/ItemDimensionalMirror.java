package com.keletu.forgotten_relics.items;

import com.keletu.forgotten_relics.Main;
import com.keletu.forgotten_relics.config.RelicsConfigHandler;
import com.keletu.forgotten_relics.utils.ExtradimensionalTeleporter;
import com.keletu.forgotten_relics.utils.SuperpositionHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.common.lib.SoundsTC;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.core.helper.Vector3;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDimensionalMirror extends Item {

	public void registerRenderers() {}

	public ItemDimensionalMirror() {

		this.maxStackSize = 1;
		this.setRegistryName("dimensional_mirror");
		this.setTranslationKey("dimensional_mirror");
		this.setCreativeTab(Main.tabForgottenRelics);

	}

	@SideOnly(Side.CLIENT)
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, @Nullable World worldIn, List<String> par3List, ITooltipFlag flagIn)
	{
		if(GuiScreen.isShiftKeyDown()){
			par3List.add(I18n.format("item.ItemDimensionalMirror1.lore"));
			par3List.add(I18n.format("item.ItemDimensionalMirror2.lore"));
			par3List.add(I18n.format("item.ItemDimensionalMirror3.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.ItemDimensionalMirror4.lore"));
		}
		else {
			par3List.add(I18n.format("item.FRShiftTooltip.lore"));

		}

		if (par1ItemStack.hasTagCompound()) {
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.MirrorLoc.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.MirrorX.lore") + ItemNBTHelper.getInt(par1ItemStack, "IStoredX", 0));
			par3List.add(I18n.format("item.MirrorY.lore") + ItemNBTHelper.getInt(par1ItemStack, "IStoredY", 0));
			par3List.add(I18n.format("item.MirrorZ.lore") + ItemNBTHelper.getInt(par1ItemStack, "IStoredZ", 0));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.MirrorDimension.lore") + ItemNBTHelper.getInt(par1ItemStack, "IDimensionID", 0));
		}
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 80;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {

		boolean effect = stack.hasTagCompound();

		if (effect)
			return true;
		else
			return false;

	}

	@Override
	public boolean isFull3D() {
		return false;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {

		Vector3 vec = Vector3.fromEntityCenter(player);

		for (int counter = 0; counter <= 3; counter++)
			player.world.spawnParticle(EnumParticleTypes.PORTAL, vec.x, vec.y, vec.z, (Math.random() - 0.5D) * 3, (Math.random() - 0.5D) * 3, (Math.random() - 0.5D) * 3);

		if (count == 1) {
			int dimension = ItemNBTHelper.getInt(stack, "IDimensionID", 0);
			int x = ItemNBTHelper.getInt(stack, "IStoredX", 0);
			int y = ItemNBTHelper.getInt(stack, "IStoredY", 0);
			int z = ItemNBTHelper.getInt(stack, "IStoredZ", 0);

			SuperpositionHandler.imposeBurst(player.world, player.dimension, vec.x, vec.y, vec.z, 1.25F);

			if (!player.world.isRemote & player.dimension != dimension) {
				((EntityPlayerMP)player).server.getPlayerList().transferPlayerToDimension((EntityPlayerMP) player, dimension, new ExtradimensionalTeleporter(((EntityPlayerMP)player).server.getWorld(dimension), x+0.5, y+0.5, z+0.5));
			} else
				player.setPositionAndUpdate(x+0.5, y+0.5, z+0.5);

			player.world.playSound(vec.x, vec.y, vec.z, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.0F, (float) (0.8F + (Math.random() * 0.2)), false);
			player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.0F, (float) (0.8F + (Math.random() * 0.2)), false);

			for (int counter = 0; counter <= 128; counter++)
				player.world.spawnParticle(EnumParticleTypes.PORTAL, player.posX, player.posY-1.0D, player.posZ, (Math.random() - 0.5D) * 3, (Math.random() - 0.5D) * 3, (Math.random() - 0.5D) * 3);
		}

	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);

		boolean written = false;

		written = stack.hasTagCompound();




		if (written & !player.isSneaking()) {

			if (!RelicsConfigHandler.interdimensionalMirror & player.dimension != ItemNBTHelper.getInt(stack, "IDimensionID", 0))
				return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
			if (player.dimension == 1 & ItemNBTHelper.getInt(stack, "IDimensionID", 0) != 1)
				return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);

			player.setActiveHand(hand);

		} else if (player.isSneaking()) {

			ItemNBTHelper.setInt(stack, "IStoredX", (int) player.posX);
			ItemNBTHelper.setInt(stack, "IStoredY", (int) player.posY);
			ItemNBTHelper.setInt(stack, "IStoredZ", (int) player.posZ);

			ItemNBTHelper.setInt(stack, "IDimensionID", player.dimension);

			world.playSound(player.posX, player.posY, player.posZ, SoundsTC.jar, SoundCategory.PLAYERS, 1.0f, 2.0f, false);

			player.swingArm(hand);

		}

		return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
	}


	@Override
	public EnumRarity getRarity(ItemStack itemStack)
	{
		return EnumRarity.EPIC;
	}


}
