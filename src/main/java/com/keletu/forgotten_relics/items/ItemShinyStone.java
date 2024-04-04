package com.keletu.forgotten_relics.items;

import java.util.List;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.keletu.forgotten_relics.Main;
import com.keletu.forgotten_relics.config.RelicsConfigHandler;
import com.keletu.forgotten_relics.entities.EntityShinyEnergy;
import com.keletu.forgotten_relics.utils.SuperpositionHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.core.helper.Vector3;

public class ItemShinyStone extends ItemBaubleBase implements IBauble {

	public ItemShinyStone() {
		super("shiny_stone");
		this.maxStackSize = 1;
		this.setTranslationKey("shiny_stone");
		this.setCreativeTab(Main.tabForgottenRelics);

	}


	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4)
	{
		if(GuiScreen.isShiftKeyDown()){
			par3List.add(I18n.format("item.ItemShinyStone1.lore"));
			par3List.add(I18n.format("item.ItemShinyStone2.lore"));
			par3List.add(I18n.format("item.FREmpty.lore"));
			par3List.add(SuperpositionHandler.getBaubleTooltip(this.getBaubleType(par1ItemStack)));
		}
		else {
			par3List.add(I18n.format("item.FRShiftTooltip.lore"));

		}
	}

	public void spawnEnergyParticle(EntityLivingBase entity) {
		EntityShinyEnergy energy = new EntityShinyEnergy(entity.world, entity, entity, entity.posX, entity.posY, entity.posZ);

		Vector3 position = Vector3.fromEntityCenter(entity);


		Vector3 motVec = new Vector3(((Math.random() - 0.5D)*3.0D), ((Math.random() - 0.5D)*3.0D), ((Math.random() - 0.5D)*3.0D));
		position.add(motVec);
		motVec.normalize().negate().multiply(0.1);

		energy.setPosition(position.x, position.y, position.z);
		energy.motionX = motVec.x;
		energy.motionY = motVec.y;
		energy.motionZ = motVec.z;

		entity.world.spawnEntity(energy);
	}


	@Override
	public EnumRarity getRarity(ItemStack itemStack)
	{
		return EnumRarity.EPIC;
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.AMULET;
	}


	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
		super.onWornTick(itemstack, entity);

		if (!entity.world.isRemote & entity.ticksExisted % RelicsConfigHandler.shinyStoneCheckrate == 0) {

			double posX = ItemNBTHelper.getDouble(itemstack, "LastX", 0.0D);
			double posY = ItemNBTHelper.getDouble(itemstack, "LastY", 0.0D);
			double posZ = ItemNBTHelper.getDouble(itemstack, "LastZ", 0.0D);

			int Static = ItemNBTHelper.getInt(itemstack, "Static", 0);

			if (entity.posX == posX & entity.posY == posY & entity.posZ == posZ) {

				int particleNumber = 3;

				ItemNBTHelper.setInt(itemstack, "HealRate", 1);

				if (Static >= 40) {
					ItemNBTHelper.setInt(itemstack, "HealRate", 2);
					particleNumber = 2;
				}
				if (Static >= 80) {
					ItemNBTHelper.setInt(itemstack, "HealRate", 3);
					particleNumber = 1;
				}
				if (Static >= 200) {
					ItemNBTHelper.setInt(itemstack, "HealRate", 4);
					particleNumber = 0;
				}

				for (int counter = particleNumber; counter <= 3; counter++)
					this.spawnEnergyParticle(entity);


				ItemNBTHelper.setInt(itemstack, "Static", Static+4);
			} else {
				ItemNBTHelper.setInt(itemstack, "Static", 0);
				ItemNBTHelper.setInt(itemstack, "HealRate", 0);
			}

			//System.out.println("Stack: " + itemstack.getTagCompound() + " " + entity.posX + " " + entity.posY + "" + entity.posZ);

			ItemNBTHelper.setDouble(itemstack, "LastX", entity.posX);
			ItemNBTHelper.setDouble(itemstack, "LastY", entity.posY);
			ItemNBTHelper.setDouble(itemstack, "LastZ", entity.posZ);

		}

		int healRate = ItemNBTHelper.getInt(itemstack, "HealRate", 0);
		int healCheckrate = (int) (RelicsConfigHandler.shinyStoneCheckrate/4.0D);

		if (healRate == 1 & entity.ticksExisted % (10*healCheckrate) == 0)
			entity.heal(1.0F);
		else if (healRate == 2 & entity.ticksExisted % (5*healCheckrate) == 0)
			entity.heal(1.0F);
		else if (healRate == 3 & entity.ticksExisted % (2*healCheckrate) == 0)
			entity.heal(1.0F);
		else if (healRate == 4 & entity.ticksExisted % (1*healCheckrate) == 0)
			entity.heal(1.0F);

		//energy.setPosition(position.x+, position.y+((Math.random() - 0.5D)*3.0D), position.z+((Math.random() - 0.5D)*3.0D));
	}


}
