package com.keletu.forgotten_relics.recipes;

import com.keletu.forgotten_relics.Main;
import static com.keletu.forgotten_relics.proxy.CommonProxy.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import static net.minecraft.init.Items.*;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.ThaumcraftApi;
import static thaumcraft.api.ThaumcraftApiHelper.makeCrystal;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.items.ItemsTC;
import static thaumcraft.api.items.ItemsTC.*;
import vazkii.botania.common.item.ModItems;
import static vazkii.botania.common.item.ModItems.*;

public class FRRecipes {
    public static void initRecipes(IForgeRegistry<IRecipe> forgeRegistry) {
        initInfusionRecipes();
        initNormalRecipes(forgeRegistry);
    }

    private static void initInfusionRecipes(){
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Main.MOD_ID, "terror_crown"), new InfusionRecipe(
                "TERROR_CROWN",
                new ItemStack(terrorCrown),
                10,
                new AspectList().add(Aspect.ELDRITCH, 75).add(Aspect.MOTION, 75).add(Aspect.MAGIC, 175).add(Aspect.DARKNESS, 30).add(Aspect.VOID, 30),
                ItemsTC.goggles,
                Items.NETHER_STAR,
                "runePrideB",
                "ingotGold",
                "eternalLifeEssence",
                ENDER_EYE,
                "eternalLifeEssence",
                "ingotGold",
                "runeWrathB"));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Main.MOD_ID, "superposition_ring"), new InfusionRecipe(
                "SUPERPOSITION_RING",
                new ItemStack(superpositionRing),
                4,
                new AspectList().add(Aspect.ELDRITCH, 125).add(Aspect.EXCHANGE, 100).add(Aspect.MOTION, 75).add(Aspect.DARKNESS, 75).add(Aspect.PROTECT, 30),
                new ItemStack(ItemsTC.baubles, 1, 1),
                ENDER_EYE, new ItemStack(ModItems.manaResource, 1, 15), voidSeed, salisMundus, "gemEmerald", salisMundus, voidSeed, new ItemStack(ModItems.manaResource, 1, 15) ));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Main.MOD_ID, "weather_stone"), new InfusionRecipe("WEATHER_STONE",
                new ItemStack(weatherStone),
                4,
                new AspectList().add(Aspect.MOTION, 65).add(Aspect.AIR, 85).add(Aspect.WATER, 35).add(Aspect.EXCHANGE, 30).add(Aspect.ENERGY, 75),
                BlocksTC.stoneArcane,
                "eternalLifeEssence", GHAST_TEAR, "runeAirB", new ItemStack(ItemsTC.curio, 1, 5), "nitor", new ItemStack(ItemsTC.curio, 1, 5), "runeAirB", GHAST_TEAR ));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Main.MOD_ID, "mining_charm"),  new InfusionRecipe("MINING_CHARM",
                new ItemStack(MiningCharm),
                1,
                new AspectList().add(Aspect.TOOL, 200).add(Aspect.MOTION, 200).add(Aspect.METAL, 175).add(Aspect.MAGIC, 200),
                ModItems.reachRing,
                elementalPick, makeCrystal(Aspect.EARTH), salisMundus, "ingotGold", PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.LONG_SWIFTNESS), makeCrystal(Aspect.EARTH), salisMundus, "ingotGold" ));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Main.MOD_ID, "IDimensionalMirror"), new InfusionRecipe(
                "DimensionalMirror",
                new ItemStack(dimensionalMirror),
                6, 
                new AspectList().add(Aspect.MOTION, 150).add(Aspect.DARKNESS, 20).add(Aspect.MAGIC, 100).add(Aspect.VOID, 200).add(Aspect.ELDRITCH, 10),
                handMirror,
                "eternalLifeEssence", new ItemStack(Blocks.GLOWSTONE), "elvenDragonstone", new ItemStack(ModItems.manaResource, 1, 15), focus2, new ItemStack(ModItems.manaResource, 1, 15), "elvenDragonstone", new ItemStack(Blocks.GLOWSTONE) ));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Main.MOD_ID, "IAdvancedMiningCharm"),  new InfusionRecipe(
                "AdvancedMiningCharm",
                new ItemStack(AdvancedMiningCharm),
                8, 
                new AspectList().add(Aspect.TOOL, 250).add(Aspect.AURA, 150).add(Aspect.CRYSTAL, 185).add(Aspect.EXCHANGE, 150).add(Aspect.MOTION, 125).add(Aspect.MAGIC, 140).add(Aspect.TOOL, 175),
                new ItemStack(MiningCharm),
                ItemsTC.visResonator, crystalEssence, "ingotElvenElementium", "elvenDragonstone", "eternalLifeEssence", crystalEssence, voidPick, crystalEssence, "eternalLifeEssence", "elvenDragonstone", "ingotElvenElementium", crystalEssence ));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Main.MOD_ID, "IAncientAegis"), new InfusionRecipe("AncientAegis",
                new ItemStack(AncientAegis),
                5,
                new AspectList().add(Aspect.PROTECT, 175).add(Aspect.EXCHANGE, 125).add(Aspect.LIFE, 100).add(Aspect.MAGIC, 30).add(Aspect.METAL, 20),
                new ItemStack(baubles, 1, 6), //belt
                "elvenDragonstone", PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.STRONG_HEALING), "ingotGold", fabric, new ItemStack(baubles, 1, 6) /*belt*/, fabric, "ingotGold", PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.STRONG_HEALING) ));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Main.MOD_ID, "IDarkSunRing"), new InfusionRecipe("DarkSunRing",
                new ItemStack(darkSunRing), 8, new AspectList()
                        .add(Aspect.FIRE, 200).add(Aspect.PROTECT, 185).add(Aspect.EXCHANGE, 140).add(Aspect.DARKNESS, 150).add(Aspect.MAGIC, 100),
                new ItemStack(baubles, 1, 4), //ring
                superLavaPendant, "ingotElvenElementium", BLAZE_ROD, new ItemStack(BlocksTC.cinderpearl), voidSeed, "nitor", voidSeed, new ItemStack(BlocksTC.cinderpearl), BLAZE_ROD, "ingotElvenElementium" ));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Main.MOD_ID, "IDeificAmulet"), new InfusionRecipe("DeificAmulet",
                new ItemStack(DeificAmulet), 4, new AspectList()
                        .add(Aspect.MAN, 125).add(Aspect.LIGHT, 165).add(Aspect.AURA, 200).add(Aspect.MAGIC, 100).add(Aspect.LIFE, 30).add(Aspect.EXCHANGE, 20),
                new ItemStack(baubles, 1, 5), //amulet
                lavaPendant, "eternalLifeEssence", "elvenPixieDust", "eternalLifeEssence", ItemsTC.visResonator, "eternalLifeEssence", "elvenPixieDust", "eternalLifeEssence" ));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Main.MOD_ID, "IChaosCore"), new InfusionRecipe("ChaosCore",
                new ItemStack(chaosCore), 10, new AspectList()
                        .add(Aspect.ENTROPY, 100).add(Aspect.ORDER, 100).add(Aspect.EXCHANGE, 200).add(Aspect.MAGIC, 125),
                ItemsTC.visResonator,
                "elvenDragonstone", "elvenPixieDust", "ingotVoid", "elvenPixieDust", alumentum, "elvenPixieDust", "ingotVoid", "elvenPixieDust" ));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Main.MOD_ID, "ITheParadox"), new InfusionRecipe("TheParadox",
                new ItemStack(paradox), 32, new AspectList()
                        .add(Aspect.AIR, 250).add(Aspect.FIRE, 250).add(Aspect.WATER, 250).add(Aspect.EARTH, 250).add(Aspect.ORDER, 250).add(Aspect.ENTROPY, 250).add(Aspect.VOID, 185).add(Aspect.AVERSION, 125).add(Aspect.MAGIC, 100).add(Aspect.EXCHANGE, 140),
                voidSword,
                new ItemStack(chaosCore), makeCrystal(Aspect.AIR), makeCrystal(Aspect.FIRE), makeCrystal(Aspect.WATER), primordialPearl, makeCrystal(Aspect.EARTH), makeCrystal(Aspect.ORDER), makeCrystal(Aspect.ENTROPY) ));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Main.MOD_ID, "IShinyStone"), new InfusionRecipe("ShinyStone",
                new ItemStack(shinyStone), 8,
                        new AspectList().add(Aspect.LIFE, 375).add(Aspect.TRAP, 100).add(Aspect.EXCHANGE, 100).add(Aspect.MAGIC, 100).add(Aspect.CRYSTAL, 200),
                "elvenDragonstone",
                ItemsTC.visResonator, "ingotGold", "eternalLifeEssence", "nitor", new ItemStack(GOLDEN_APPLE, 1, 1), "nitor", "eternalLifeEssence", "ingotGold" ));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Main.MOD_ID, "IOblivionAmulet"), new InfusionRecipe("OblivionAmulet",
                new ItemStack(oblivionAmulet), 16, new AspectList()
                        .add(Aspect.DEATH, 250).add(Aspect.EXCHANGE, 175).add(Aspect.VOID, 300).add(Aspect.DARKNESS, 100).add(Aspect.ELDRITCH, 100).add(Aspect.MAGIC, 125).add(Aspect.FIRE, 200),
                new ItemStack(ModItems.bloodPendant, 1, 0),
                NETHER_STAR, BLAZE_POWDER, "ingotVoid", BLAZE_POWDER, charmVoidseer, BLAZE_POWDER, "ingotVoid", BLAZE_POWDER ));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Main.MOD_ID, "IXPTome"), new InfusionRecipe("XPTome",
                new ItemStack(XPTome), 4, new AspectList()
                        .add(Aspect.SOUL, 125).add(Aspect.MIND, 150).add(Aspect.EXCHANGE, 30).add(Aspect.MAGIC, 100),
                WRITABLE_BOOK,
                new ItemStack(BlocksTC.jarBrain), salisMundus, amber, salisMundus, "nitor", salisMundus, amber, salisMundus ));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Main.MOD_ID, "INebulousCore"), new InfusionRecipe("NebulousCore",
                new ItemStack(arcanum), 12, new AspectList()
                        .add(Aspect.AURA, 80).add(Aspect.MAGIC, 500).add(Aspect.VOID, 125).add(Aspect.ELDRITCH, 175).add(Aspect.DARKNESS, 150).add(Aspect.MOTION, 100).add(Aspect.EXCHANGE, 185),
                primordialPearl,
                new ItemStack(amuletVis, 1, 1), crystalEssence, "eternalLifeEssence", "ingotThaumium", new ItemStack(ModItems.manaResource, 1, 15), crystalEssence, new ItemStack(superpositionRing), crystalEssence, new ItemStack(ModItems.manaResource, 1, 15), "ingotThaumium", "eternalLifeEssence", crystalEssence ));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Main.MOD_ID, "IOblivionStone"), new InfusionRecipe("OblivionStone",
                new ItemStack(oblivionStone), 4, new AspectList()
                        .add(Aspect.VOID, 75).add(Aspect.DARKNESS, 75).add(Aspect.ENTROPY, 60).add(Aspect.EXCHANGE, 30).add(Aspect.MAGIC, 30),
                charmVoidseer,
                ENDER_EYE, voidSeed, "ingotVoid", voidSeed, "nitor", voidSeed, "ingotVoid", voidSeed ));
    }

    private static void initNormalRecipes(IForgeRegistry<IRecipe> forgeRegistry) {
        forgeRegistry.register(new RecipeOblivionStone());
    }


}
