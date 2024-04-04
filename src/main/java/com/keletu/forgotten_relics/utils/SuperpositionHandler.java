package com.keletu.forgotten_relics.utils;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import com.google.common.base.Predicates;
import com.keletu.forgotten_relics.Main;
import com.keletu.forgotten_relics.packets.NotificationMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.casters.ICaster;
import thaumcraft.common.entities.monster.boss.EntityThaumcraftBoss;
import vazkii.botania.common.block.subtile.functional.SubTileHeiseiDream;
import vazkii.botania.common.entity.EntityDoppleganger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SuperpositionHandler {

	/**
	 * Basically, does the same thing as Heisei Dream.
	 */

	public static void cryHavoc(World world, EntityPlayer player, int RANGE) {
		List<IMob> mobs = (List) world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(player.posX - RANGE, player.posY - RANGE, player.posZ - RANGE, player.posX + RANGE + 1, player.posY + RANGE + 1, player.posZ + RANGE + 1), Predicates.instanceOf(IMob.class));
		if(mobs.size() > 1)
			for(IMob mob : mobs) {
				if(mob instanceof EntityLiving) {
					EntityLiving entity1 = (EntityLiving) mob;
					if(SubTileHeiseiDream.brainwashEntity(entity1, mobs)) {
						break;
					}
				}
			}

	}

	/**
	 * Sends custom notification to a player,
	 * @param player
	 * @param type
	 */

	public static void sendNotification(EntityPlayer player, int type) {
		if (!player.world.isRemote)
			Main.packetInstance.sendTo(new NotificationMessage(type), (EntityPlayerMP) player);
	}


	/**
	 * Checks if given Damage Source is instance of absolute damage.
	 * Absolute damage types include True Damage, Soul Drain Damage,
	 * damage dealt by Amulet of Oblivion or Tome of Broken Fates,
	 * and vanilla ones like Void Damage.
	 *
	 * This is used by some features to prevent them from decreasing
	 * those damage types or negating them completely.
	 */

	public static boolean isDamageTypeAbsolute(DamageSource source) {
		if (source == DamageSource.OUT_OF_WORLD || source == DamageSource.STARVE || source instanceof DamageRegistryHandler.DamageSourceFate || source instanceof DamageRegistryHandler.DamageSourceOblivion || source instanceof DamageRegistryHandler.DamageSourceSoulDrain || source instanceof DamageRegistryHandler.DamageSourceTrueDamage || source instanceof DamageRegistryHandler.DamageSourceTrueDamageUndef)
			return true;
		else
			return false;
	}

	public static boolean isEntityBlacklistedFromTelekinesis(EntityLivingBase entity) {
		if (entity instanceof EntityThaumcraftBoss || entity instanceof EntityDoppleganger)
			return true;
		else
			return false;
	}

	public static String getBaubleTooltip(BaubleType type) {
		String str = "";

		switch (type) {

			case AMULET: str = I18n.format("item.FRAmulet.lore");
				break;

			case BELT: str = I18n.format("item.FRBelt.lore");
				break;

			case RING: str = I18n.format("item.FRRing.lore");
				break;

			default: str = "";
				break;

		}

		return str;
	}

	/**
	 * Performs ray trace for blocks in the direction of player's look, within given range.
	 * @return First block in the line of sight, null if none found.
	 */

	public static RayTraceResult getPointedBlock(EntityPlayer player, World world, float range) {

		double d0 = player.posX;
		double d1 = player.posY + 1.62D - (double) player.posY;
		double d2 = player.posZ;

		Vec3d position = new Vec3d(d0, d1, d2);
		Vec3d look = player.getLook(1.0F);
		Vec3d finalvec = position.add(look.x * range, look.y * range, look.z * range);

		RayTraceResult mop = world.rayTraceBlocks(position, finalvec);

		return mop;
	}

	/**
	 * Searches for all players that have specified bauble equipped.
	 * @return List of players that have specified... you should have understood by now, didn't you?
	 */

	public static List <EntityPlayer> getBaubleOwnersList(World world, Item baubleItem) {

		List<EntityPlayer> returnList = new LinkedList();

		if (!world.isRemote) {

			List<EntityPlayer> playersList = new ArrayList(Minecraft.getMinecraft().getIntegratedServer().getPlayerList().getPlayers());

			for (int counter = playersList.size() - 1; counter >= 0; counter --) {

				if (SuperpositionHandler.hasBauble(playersList.get(counter), baubleItem)) {
					returnList.add(playersList.get(counter));
				}

			}

		}

		return returnList;
	}

	/**
	 * Searches for players within given radius from given entity
	 * that have specified item within their bauble inventory, and
	 * constructs a list of them.
	 * @return Random player from constructed list, null if none were found.
	 */

	public static EntityPlayer findPlayerWithBauble(World world, int radius, Item baubleItem, EntityLivingBase entity) {

		List<EntityPlayer> returnList = new LinkedList();

		if (!world.isRemote) {
			List<EntityPlayer> playerList = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(entity.posX-radius, entity.posY-radius, entity.posZ-radius, entity.posX+radius, entity.posY+radius, entity.posZ+radius));

			if (playerList.contains(entity))
				playerList.remove(entity);

			for (int counter = playerList.size() - 1; counter >= 0; counter --) {

				if (SuperpositionHandler.hasBauble(playerList.get(counter), baubleItem)) {
					returnList.add(playerList.get(counter));
				}

			}

			if (returnList.size() > 0) {
				return returnList.get((int) ((returnList.size() - 1) * Math.random()));
			} else {
				return null;
			}


		}

		return null;
	}

	/**
	 * Checks if given player has specified item equipped as bauble.
	 * @return True if item is equipped, false otherwise.
	 */

	public static boolean hasBauble(EntityPlayer player, Item theBauble) {

		IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
		List<Item> baubleList = new ArrayList<>();
		if (baubles.getStackInSlot(0) != ItemStack.EMPTY)
			baubleList.add(baubles.getStackInSlot(0).getItem());
		if (baubles.getStackInSlot(1) != ItemStack.EMPTY)
			baubleList.add(baubles.getStackInSlot(1).getItem());
		if (baubles.getStackInSlot(2) != ItemStack.EMPTY)
			baubleList.add(baubles.getStackInSlot(2).getItem());
		if (baubles.getStackInSlot(3) != ItemStack.EMPTY)
			baubleList.add(baubles.getStackInSlot(3).getItem());

		return baubleList.contains(theBauble);
	}

	/**
	 * @param list - ItemStack array. All elements must be instances of ItemWandCasting.
	 * @return Random wand from given ItemStack list that is not fully charged with given Aspect. Null if none found.
	 */

	public static ItemStack getRandomValidWand(List<ItemStack> list, Aspect aspect) {

		return ItemStack.EMPTY;
	}

	/**
	 * @return An array containing all instances of ItemWandCasting within player inventory.
	 */

	public static List wandSearch(EntityPlayer player) {

		List<ItemStack> itemStackList = new LinkedList<ItemStack>();

		for (int slot = 0; slot < player.inventory.mainInventory.size(); slot++) {
			if (player.inventory.mainInventory.get(slot) == ItemStack.EMPTY)
				continue;
			if (player.inventory.mainInventory.get(slot).getItem() instanceof ICaster) {
				itemStackList.add(player.inventory.mainInventory.get(slot));
			}
		}



		return itemStackList;
	}

	/**
	 * @return An array containing all ItemStacks of given Item within player inventory.
	 */

	public static List itemSearch(EntityPlayer player, Item researchItem) {

		List<ItemStack> itemStackList = new LinkedList<ItemStack>();

		for (int slot = 0; slot < player.inventory.mainInventory.size(); slot++) {
			if (player.inventory.mainInventory.get(slot) == ItemStack.EMPTY)
				continue;
			if (player.inventory.mainInventory.get(slot).getItem() == researchItem) {

				itemStackList.add(player.inventory.mainInventory.get(slot));

			}
		}



		return itemStackList;
	}

	/**
	 * Searches for ItemStacks of given Item within player's inventory.
	 * @param searchItem - Item to be searched.
	 * @return First available ItemStack of given Item, null if none found.
	 */

	public static ItemStack findFirst(EntityPlayer player, Item searchItem) {
		for (int slot = 0; slot < player.inventory.mainInventory.size(); slot++) {
			if (player.inventory.mainInventory.get(slot) == ItemStack.EMPTY)
				continue;
			if (player.inventory.mainInventory.get(slot).getItem() == searchItem) {
				return player.inventory.mainInventory.get(slot);
			}
		}

		return null;
	}


	/**
	 * Basically, expands given int array to write given Integer into there.
	 */

	public static int[] addInt(int[] series, int newInt) {
		int[] newSeries = new int[series.length + 1];

		for (int i = 0; i < series.length; i++) {
			newSeries[i] = series[i];
		}

		newSeries[newSeries.length - 1] = newInt;
		return newSeries;

	}

}
