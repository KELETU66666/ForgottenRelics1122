package com.keletu.forgotten_relics;

import com.keletu.forgotten_relics.config.RelicsConfigHandler;
import com.keletu.forgotten_relics.proxy.CommonProxy;
import com.keletu.forgotten_relics.utils.DamageRegistryHandler;
import com.keletu.forgotten_relics.utils.SuperpositionHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import java.util.List;

public class RelicsEventHandler {

    @SubscribeEvent(priority= EventPriority.HIGHEST)
    public void onEntityAttacked(LivingAttackEvent event) {

        /*
         * Handler for redirecting damage dealt BY player who possesses Chaos Core.
         */
        if (event.getSource().getTrueSource() instanceof EntityPlayer & !event.isCanceled()) {
            EntityPlayer attackerPlayer = (EntityPlayer) event.getSource().getTrueSource();

            if (attackerPlayer.inventory.hasItemStack(new ItemStack(CommonProxy.chaosCore)) & Math.random() < 0.45) {
                List<Entity> entityList = event.getEntity().world.getEntitiesWithinAABBExcludingEntity(event.getEntity(), new AxisAlignedBB(event.getEntity().posX-16, event.getEntity().posY-16, event.getEntity().posZ-16, event.getEntity().posX+16, event.getEntity().posY+16, event.getEntity().posZ+16));

                if (!(entityList == null) & entityList.size() > 0) {
                    Entity randomEntity = entityList.get((int) (Math.random() * (entityList.size() - 1)));

                    float redirectedAmount = event.getAmount() * ((float) (Math.random() * 2));

                    if (Math.random() < 0.15) {
                        attackerPlayer.attackEntityFrom(event.getSource(), redirectedAmount);
                    } else {
                        randomEntity.attackEntityFrom(event.getSource(), redirectedAmount);
                    }

                    event.setCanceled(true);
                }
            }

        }

        if (event.getEntity() instanceof EntityPlayer) {

            EntityPlayer player = (EntityPlayer) event.getEntity();

            /*
             * Handler for redirecting damage dealt TO player who possesses Chaos Core.
             */

            if (!event.isCanceled() & player.inventory.hasItemStack(new ItemStack(CommonProxy.chaosCore)) & Math.random() < 0.42D) {

                List<Entity> entityList = event.getEntity().world.getEntitiesWithinAABBExcludingEntity(event.getEntity(), new AxisAlignedBB(event.getEntity().posX-16, event.getEntity().posY-16, event.getEntity().posZ-16, event.getEntity().posX+16, event.getEntity().posY+16, event.getEntity().posZ+16));

                if (!(entityList == null) & entityList.size() > 0) {
                    Entity randomEntity = entityList.get((int) (Math.random() * (entityList.size() - 1)));

                    //if (event.getEntity().hurtResistantTime == 0) {

                    float redirectedAmount = event.getAmount() * ((float) (Math.random() * 2));

                    randomEntity.attackEntityFrom(event.getSource(), redirectedAmount);

                    event.setCanceled(true);
                }

            }
/*
            /*
             * Handler for randomly teleport player who has Nebulous Core equipped,
             * instead of taking damage from attack.


            if (!event.isCanceled() & SuperpositionHandler.hasBauble(player, Main.itemArcanum) & Math.random() < RelicsConfigHandler.nebulousCoreDodgeChance & !SuperpositionHandler.isDamageTypeAbsolute(event.getSource())) {

                for (int counter = 0; counter <= 32; counter ++) {
                    if (SuperpositionHandler.validTeleportRandomly(event.getEntity(), event.getEntity().world, 16)) {
                        event.getEntity().hurtResistantTime = 20;
                        event.setCanceled(true);
                        break;
                    }
                }
            }
*/
            /*
             * Handler for converting fire damage into healing for Ring of The Seven Suns.
             */

            if (!event.isCanceled() & SuperpositionHandler.hasBauble(player, CommonProxy.darkSunRing) & Main.darkRingDamageNegations.contains(event.getSource().damageType)) {

                if (RelicsConfigHandler.darkSunRingHealLimit) {
                    if (event.getEntity().hurtResistantTime == 0) {
                        player.heal(event.getAmount());
                        event.getEntity().hurtResistantTime = 20;
                    }
                } else {
                    player.heal(event.getAmount());
                }

                event.setCanceled(true);

            }

            /*
             * Handler for deflecting incoming attack to it's source for Ring of The Seven Suns.
             */

            else if (!event.isCanceled() & SuperpositionHandler.hasBauble(player, CommonProxy.darkSunRing) & event.getSource().getTrueSource() != null & Math.random() <= RelicsConfigHandler.darkSunRingDeflectChance) {

                if (player.hurtResistantTime == 0) {
                    player.hurtResistantTime = 20;
                    event.getSource().getTrueSource().attackEntityFrom(event.getSource(), event.getAmount());
                    event.setCanceled(true);
                }

            }

        }

    }

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event) {

        /*
         * Handler for converting damage dealt TO owners of False Justice.
         */
/*
        if (event.getEntity() instanceof EntityPlayer & !event.isCanceled()) {
            EntityPlayer player = (EntityPlayer) event.getEntity();

            if (player.inventory.hasItemStack(Main.itemFalseJustice) & !SuperpositionHandler.isDamageTypeAbsolute(event.getSource())) {
                event.setCanceled(true);
                if (event.getSource().getTrueSource() == null) {
                    player.attackEntityFrom(new DamageRegistryHandler.DamageSourceTrueDamageUndef(), event.getAmount()*2);
                }
                else {
                    player.attackEntityFrom(new DamageRegistryHandler.DamageSourceTrueDamage(event.getSource().getTrueSource()), event.getAmount()*2);
                }
            }

        }
*/
        /*
         * Hanlder for converting damage dealt BY owners of False Justice.
         */
/*
        if (event.getSource().getTrueSource() instanceof EntityPlayer & !event.isCanceled()) {
            EntityPlayer attackerPlayer = (EntityPlayer) event.getSource().getTrueSource();

            if (attackerPlayer.inventory.hasItemStack(Main.itemFalseJustice) & !SuperpositionHandler.isDamageTypeAbsolute(event.getSource())) {
                event.setCanceled(true);
                event.getEntity().attackEntityFrom(new DamageRegistryHandler.DamageSourceTrueDamage(event.getSource().getTrueSource()), event.getAmount() * 2);
            }
        }
*/


        if (event.getEntity() instanceof EntityPlayer & !event.isCanceled()) {
            EntityPlayer player = (EntityPlayer) event.getEntity();

            /*
             * Handler for multiplication of damage dealt to owners of Chaos Core by value in range
             * between 0.0 and 2.0.
             */

            if (!event.isCanceled() & player.inventory.hasItemStack(new ItemStack(CommonProxy.chaosCore))) {
                event.setAmount(event.getAmount() * ((float) (Math.random() * 2)));
            }

            /*
             * Handler for blocking attacks that exceed damage cap for Ring of The Seven Suns.
             */

            if (!event.isCanceled() & event.getAmount() > 100.0F & !SuperpositionHandler.isDamageTypeAbsolute(event.getSource()) & SuperpositionHandler.hasBauble(player, CommonProxy.darkSunRing)) {
                SuperpositionHandler.sendNotification(player, 2);
                event.setCanceled(true);
            }

            /*
             * Handler for increasing strenght of regular attacks on wearers of Ring of The Seven Suns.
             */

            if (SuperpositionHandler.hasBauble(player, CommonProxy.darkSunRing) & !event.isCanceled() & Math.random() <= 0.25 & !SuperpositionHandler.isDamageTypeAbsolute(event.getSource())) {
                event.setAmount(event.getAmount() + (event.getAmount() * ((float) Math.random())));
            }

            /*
             * Handler for redirecting damage received by player to owner of Ancient Aegis
             * nearby, if one is present.
             */
/*
            if(!event.getEntity().world.isRemote & !SuperpositionHandler.hasBauble(player, Main.itemAncientAegis) & !event.isCanceled()) {
                EntityPlayer aegisOwner = SuperpositionHandler.findPlayerWithBauble(event.getEntity().world, 32, Main.itemAncientAegis, player);

                if (aegisOwner != null) {
                    aegisOwner.attackEntityFrom(event.getSource(), event.getAmount() * 0.4F);
                    event.setAmount(event.getAmount() * 0.6F);
                }
            }
*/
            ///*
            // * Handler for Ancient Aegis damage reduction.
            // */
//
            //if (SuperpositionHandler.hasBauble(player, Main.itemAncientAegis) & !event.isCanceled() & !SuperpositionHandler.isDamageTypeAbsolute(event.getSource())) {
            //    event.setAmount(event.getAmount() - RelicsConfigHandler.ancientAegisDamageReduction);
            //}

            /*
             * Handler for splitting damage dealt to wearer of Superposition Ring
             * among all other wearers, if any exist.
             */

            /*if (!(event.getSource() instanceof DamageRegistryHandler.DamageSourceSuperposition) & !(event.getSource() instanceof DamageRegistryHandler.DamageSourceSuperpositionDefined))
                if (SuperpositionHandler.hasBauble(player, Main.itemSuperpositionRing) & !event.isCanceled()) {

                    DamageSource altSource;

                    if (event.getSource().getTrueSource() != null)
                        altSource = new DamageRegistryHandler.DamageSourceSuperpositionDefined(event.getSource().getTrueSource());
                    else
                        altSource = new DamageRegistryHandler.DamageSourceSuperposition();

                    if (event.getSource().isUnblockable())
                        altSource.setDamageBypassesArmor();
                    if (event.getSource().isDamageAbsolute())
                        altSource.setDamageIsAbsolute();

                    altSource.damageType = event.getSource().getDamageType();

                    List superpositioned = SuperpositionHandler.getBaubleOwnersList(player.world, Main.itemSuperpositionRing);
                    if (superpositioned.contains(player))
                        superpositioned.remove(player);

                    if (superpositioned.size() > 0) {
                        double percent = 0.12 + (Math.random()*0.62);
                        float splitAmount = (float) (event.getAmount() * percent);

                        for (int counter = superpositioned.size() - 1; counter >= 0; counter--) {
                            EntityPlayer cPlayer = (EntityPlayer) superpositioned.get(counter);
                            cPlayer.attackEntityFrom(altSource, splitAmount/superpositioned.size());
                        }

                        event.setAmount(event.getAmount() - splitAmount);
                    }
                }
*/
            ///*
            // * Handler for damage absorption by Oblivion Amulet.
            // */
//
            //if (!event.isCanceled() & SuperpositionHandler.hasBauble(player, Main.itemOblivionAmulet) & !SuperpositionHandler.isDamageTypeAbsolute(event.getSource())) {
            //    if (WandManager.consumeVisFromInventory(player, new AspectList().add(Aspect.FIRE, (int)(event.getAmount()*8*RelicsConfigHandler.oblivionAmuletVisMult)).add(Aspect.ENTROPY, (int)(event.getAmount()*8*RelicsConfigHandler.oblivionAmuletVisMult)))) {
//
            //        ItemStack oblivionAmulet = PlayerHandler.getPlayerBaubles(player).getStackInSlot(0);
//
            //        ItemNBTHelper.setFloat(oblivionAmulet, "IDamageStored", ItemNBTHelper.getFloat(oblivionAmulet, "IDamageStored", 0) + event.getAmount());
//
            //        event.setCanceled(true);
//
            //    }
            //}


        }


    }
}
