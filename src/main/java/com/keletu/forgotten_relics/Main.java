package com.keletu.forgotten_relics;

import com.keletu.forgotten_relics.config.RelicsConfigHandler;
import com.keletu.forgotten_relics.entities.EntityShinyEnergy;
import com.keletu.forgotten_relics.network.PlayerVariables;
import com.keletu.forgotten_relics.packets.BurstMessage;
import com.keletu.forgotten_relics.packets.ICanSwingMySwordMessage;
import com.keletu.forgotten_relics.packets.NotificationMessage;
import com.keletu.forgotten_relics.proxy.CommonProxy;
import com.keletu.forgotten_relics.recipes.RecipeOblivionStone;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.RecipeSorter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thaumcraft.Thaumcraft;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchEvent;
import thaumcraft.api.research.ScanItem;
import thaumcraft.api.research.ScanningManager;
import thaumcraft.common.lib.research.ResearchManager;
import vazkii.botania.common.item.ModItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mod(
        modid = Main.MOD_ID,
        name = Main.MOD_NAME,
        version = Main.VERSION
)
public class Main {

    public static final String MOD_ID = "forgotten_relics";
    public static final String MOD_NAME = "Forgotten Relics Kedition";
    public static final String VERSION = "0.6.0";

    @CapabilityInject(PlayerVariables.class)
    public static Capability<PlayerVariables> PLAYER = null;

    public static SimpleNetworkWrapper packetInstance;
    public static final Logger log = LogManager.getLogger("ForgottenRelics");
    public RelicsConfigHandler configHandler = new RelicsConfigHandler();

    @SidedProxy(clientSide = "com.keletu.forgotten_relics.proxy.ClientProxy", serverSide = "com.keletu.forgotten_relics.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static List<String> darkRingDamageNegations = new ArrayList<>();
    public static HashMap<EntityPlayer, Integer> castingCooldowns = new HashMap<EntityPlayer, Integer>();
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configHandler.configDisposition(event);

        packetInstance = NetworkRegistry.INSTANCE.newSimpleChannel("RelicsChannel");
        packetInstance.registerMessage(BurstMessage.Handler.class, BurstMessage.class, 2, Side.CLIENT);
        packetInstance.registerMessage(ICanSwingMySwordMessage.Handler.class, ICanSwingMySwordMessage.class, 8, Side.CLIENT);
        packetInstance.registerMessage(NotificationMessage.Handler.class, NotificationMessage.class, 15, Side.CLIENT);

        EntityRegistry.registerModEntity(new ResourceLocation(MOD_ID, "shiny_energy"), EntityShinyEnergy.class, "shiny_energy", 0, MOD_ID, 64, 20, true);

        MinecraftForge.EVENT_BUS.register(new RelicsEventHandler());

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        proxy.init(event);

        ScanningManager.addScannableThing(new ScanItem("!NetherStar", new ItemStack(Items.NETHER_STAR)));
        ScanningManager.addScannableThing(new ScanItem("!VoidSeerCharm", new ItemStack(ItemsTC.charmVoidseer)));
        ScanningManager.addScannableThing(new ScanItem("!EternalLifeEssence", new ItemStack(ModItems.manaResource, 1, 5)));
        ScanningManager.addScannableThing(new ScanItem("!MiningCharm", new ItemStack(CommonProxy.MiningCharm)));
        ScanningManager.addScannableThing(new ScanItem("!PixieDust", new ItemStack(ModItems.manaResource, 1, 8)));
        ScanningManager.addScannableThing(new ScanItem("!DragonStone", new ItemStack(ModItems.manaResource, 1, 9)));
        ScanningManager.addScannableThing(new ScanItem("!VoidIngot", new ItemStack(ItemsTC.ingots, 1, 1)));
        ScanningManager.addScannableThing(new ScanItem("!EnchantedGoldenApple", new ItemStack(Items.GOLDEN_APPLE, 1, 1)));
        ScanningManager.addScannableThing(new ScanItem("!ChaosCore", new ItemStack(CommonProxy.chaosCore)));
        ScanningManager.addScannableThing(new ScanItem("!BloodPendant", new ItemStack(ModItems.bloodPendant)));
        ScanningManager.addScannableThing(new ScanItem("!BlazeRod", new ItemStack(Items.BLAZE_ROD)));
        ScanningManager.addScannableThing(new ScanItem("!SuperpositionRing", new ItemStack(CommonProxy.superpositionRing)));
        ScanningManager.addScannableThing(new ScanItem("!ThaumiumIngot", new ItemStack(ItemsTC.ingots, 1, 0)));
        ResearchCategories.registerCategory("FORGOTTEN_RELICS", null, new AspectList(), new ResourceLocation(MOD_ID, "textures/items/omega_core.png"), new ResourceLocation(Thaumcraft.MODID, "textures/gui/gui_research_back_5.jpg"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(MOD_ID, "research/baubles"));
        //RecipeSorter.register("forge:oblivionstone", RecipeOblivionStone.class, RecipeSorter.Category.SHAPELESS, "after:forge:shapelessorenbt");

        darkRingDamageNegations.add(DamageSource.LAVA.damageType);
        darkRingDamageNegations.add(DamageSource.IN_FIRE.damageType);
        darkRingDamageNegations.add(DamageSource.ON_FIRE.damageType);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {

        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {
            proxy.registerItems(event);
        }

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void modelRegistryEvent(ModelRegistryEvent event) {
            proxy.modelRegistryEvent(event);
        }
    }

    public static CreativeTabs tabForgottenRelics = new CreativeTabs("tabForgottenRelics") {
        @SideOnly(Side.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(CommonProxy.terrorCrown);
        }
    };
}
