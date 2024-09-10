package com.keletu.forgotten_relics.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.keletu.forgotten_relics.Main;
import com.keletu.forgotten_relics.proxy.CommonProxy;
import com.keletu.forgotten_relics.utils.SuperpositionHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.common.core.helper.Vector3;

import java.util.List;

public class ItemSuperpositionRing extends ItemBaubleBase implements IBauble {

	public ItemSuperpositionRing() {
		super("superposition_ring");
		this.maxStackSize = 1;
		this.setTranslationKey("superposition_ring");
		this.setCreativeTab(Main.tabForgottenRelics);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4)
	{
		if(GuiScreen.isShiftKeyDown()){
			par3List.add(I18n.format("item.ItemSuperpositionRing1.lore"));
			par3List.add(I18n.format("item.ItemSuperpositionRing2.lore"));
			par3List.add(I18n.format("item.ItemSuperpositionRing3.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(I18n.format("item.ItemSuperpositionRing4.lore"));
			par3List.add(I18n.format("item.ItemSuperpositionRing5.lore"));
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
		return BaubleType.RING;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
		super.onWornTick(itemstack, entity);

		if (entity.ticksExisted % 600 == 0 & !entity.world.isRemote & entity instanceof EntityPlayer) {

			if (Math.random() <= 0.025) {
				List <EntityPlayer> players = SuperpositionHandler.getBaubleOwnersList(entity.world, CommonProxy.superpositionRing);
				players.remove(entity);

				if (players.size() > 0) {
					EntityPlayer randomPlayer = players.get((int) (Math.random() * players.size()));
					Vector3 pos1 = Vector3.fromEntityCenter(entity);
					Vector3 pos2 = Vector3.fromEntityCenter(randomPlayer);

					if (randomPlayer.dimension != entity.dimension && entity.getServer() != null && randomPlayer.getServer() != null) {
						int dim1 = entity.dimension;
						int dim2 = randomPlayer.dimension;
						randomPlayer.getServer().getPlayerList().transferPlayerToDimension((EntityPlayerMP) randomPlayer, dim1, ((WorldServer) randomPlayer.world).getDefaultTeleporter());
						entity.getServer().getPlayerList().transferPlayerToDimension((EntityPlayerMP) entity, dim2, ((WorldServer) entity.world).getDefaultTeleporter());
					}

					entity.setPositionAndUpdate(pos2.x, pos2.y, pos2.z);
					randomPlayer.setPositionAndUpdate(pos1.x, pos1.y, pos1.z);
					entity.world.playSound(pos2.x, pos2.y, pos2.z, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.0F, (float) (0.8F + (Math.random() * 0.2)), false);
					randomPlayer.world.playSound(pos2.x, pos2.y, pos2.z, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.0F, (float) (0.8F + (Math.random() * 0.2)), false);
				}
			}

		}

	}


}
