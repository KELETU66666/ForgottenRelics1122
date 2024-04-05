package com.keletu.forgotten_relics;

import com.keletu.forgotten_relics.config.RelicsConfigHandler;
import com.keletu.forgotten_relics.entities.EntityShinyEnergy;
import com.keletu.forgotten_relics.network.PlayerVariables;
import com.keletu.forgotten_relics.packets.ICanSwingMySwordMessage;
import com.keletu.forgotten_relics.packets.NotificationMessage;
import com.keletu.forgotten_relics.proxy.CommonProxy;
import com.keletu.forgotten_relics.utils.RecipeOblivionStone;
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
    public static final String VERSION = "0.1";

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
        packetInstance.registerMessage(ICanSwingMySwordMessage.Handler.class, ICanSwingMySwordMessage.class, 8, Side.CLIENT);
        packetInstance.registerMessage(NotificationMessage.Handler.class, NotificationMessage.class, 15, Side.CLIENT);

        EntityRegistry.registerModEntity(new ResourceLocation(MOD_ID, "shiny_energy"), EntityShinyEnergy.class, "shiny_energy", 0, MOD_ID, 64, 20, true);

        MinecraftForge.EVENT_BUS.register(new RelicsEventHandler());

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        proxy.init(event);

        RecipeSorter.register("forge:oblivionstone", RecipeOblivionStone.class, RecipeSorter.Category.SHAPELESS, "after:forge:shapelessorenbt");

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
