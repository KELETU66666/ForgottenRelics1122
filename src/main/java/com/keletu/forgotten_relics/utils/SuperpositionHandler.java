package com.keletu.forgotten_relics.utils;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import com.google.common.base.Predicates;
import com.keletu.forgotten_relics.Main;
import com.keletu.forgotten_relics.packets.BurstMessage;
import com.keletu.forgotten_relics.packets.ICanSwingMySwordMessage;
import com.keletu.forgotten_relics.packets.NotificationMessage;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
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
	 * Attempts to teleport entity at given coordinates, or nearest valid location on Y axis.
	 * @return True if successfull, false otherwise.
	 */

	public static boolean validTeleport(Entity entity, double x_init, double y_init, double z_init, World world) {

		int x = (int) x_init;
		int y = (int) y_init;
		int z = (int) z_init;

		Block block = world.getBlockState(new BlockPos(x, y-1, z)).getBlock();

		if (block != Blocks.AIR & block.isCollidable()) {

			for (int counter = 0; counter <= 32; counter++) {

				if (!world.isAirBlock(new BlockPos(x, y+counter-1, z)) & world.getBlockState(new BlockPos(x, y+counter-1, z)).getBlock().isCollidable() & world.isAirBlock(new BlockPos(x, y+counter, z)) & world.isAirBlock(new BlockPos(x, y+counter+1, z))) {

					SuperpositionHandler.imposeBurst(entity.world, entity.dimension, entity.posX, entity.posY+1, entity.posZ, 1.25f);

					entity.world.playSound(entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F, false);
					entity.setPositionAndUpdate(x+0.5, y+counter, z+0.5);
					entity.world.playSound(x, y+counter, z, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F, false);

					return true;
				}

			}

		} else {

			for (int counter = 0; counter <= 32; counter++) {

				if (!world.isAirBlock(new BlockPos(x, y-counter-1, z)) & world.getBlockState(new BlockPos(x, y-counter-1, z)).getBlock().isCollidable() & world.isAirBlock(new BlockPos(x, y-counter, z)) & world.isAirBlock(new BlockPos(x, y-counter+1, z))) {

					SuperpositionHandler.imposeBurst(entity.world, entity.dimension, entity.posX, entity.posY+1, entity.posZ, 1.25f);

					entity.world.playSound(entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F, false);
					entity.setPositionAndUpdate(x+0.5, y-counter, z+0.5);
					entity.world.playSound(x, y-counter, z, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F, false);

					return true;
				}

			}

		}

		return false;
	}


	/**
	 * Attempts to find valid location within given radius and teleport entity there.
	 * @return True if teleportation were successfull, false otherwise.
	 */
	public static boolean validTeleportRandomly(Entity entity, World world, int radius) {
		int d = radius*2;

		double x = entity.posX + ((Math.random()-0.5D)*d);
		double y = entity.posY + ((Math.random()-0.5D)*d);
		double z = entity.posZ + ((Math.random()-0.5D)*d);
		return SuperpositionHandler.validTeleport(entity, x, y, z, world);
	}

	/**
	 * Sets the player casting cooldown to given number and, optionally, swings the item player is holding.
	 */

	public static void setCasted(EntityPlayer player, int cooldown, boolean swing) {
		if(!player.world.isRemote) {
			Main.castingCooldowns.put(player, cooldown);

			if (swing) {
				player.swingArm(player.getActiveHand());
				Main.packetInstance.sendTo(new ICanSwingMySwordMessage(true), (EntityPlayerMP) player);
			}

		}

	}

	/**
	 * Checks if given player has active casting cooldown.
	 * @return True if yes, false if no.
	 */

	public static boolean isOnCoodown(EntityPlayer player) {
		if (player.world.isRemote)
			return false;

		int cooldown;

		try {
			cooldown = Main.castingCooldowns.get(player);
		}
		catch (NullPointerException ex) {
			Main.castingCooldowns.put(player, 0);
			cooldown = 0;
		}

		if (cooldown != 0)
			return true;
		else
			return false;

	}

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

	public static void imposeBurst(World world, int dimension, double x, double y, double z, float size) {
		if (!world.isRemote)
			Main.packetInstance.sendToAllAround(new BurstMessage(x, y, z, size), new NetworkRegistry.TargetPoint(dimension, x, y, z, 128.0D));
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

		if (!world.isRemote && world.getMinecraftServer() != null) {

			List<EntityPlayer> playersList = new ArrayList(world.getMinecraftServer().getPlayerList().getPlayers());

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
		return BaublesApi.isBaubleEquipped(player, theBauble) != -1;
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
