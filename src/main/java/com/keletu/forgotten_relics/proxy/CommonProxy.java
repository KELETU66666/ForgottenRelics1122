
package com.keletu.forgotten_relics.proxy;


import com.keletu.forgotten_relics.items.*;
import com.keletu.forgotten_relics.network.IPlayerStorage;
import com.keletu.forgotten_relics.network.PlayerVariables;
import com.keletu.forgotten_relics.utils.RelicsMaterialHandler;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CommonProxy {

    public static ItemAdvancedMiningCharm AdvancedMiningCharm = new ItemAdvancedMiningCharm();
    public static ItemMiningCharm MiningCharm = new ItemMiningCharm();
    public static ItemAncientAegis AncientAegis = new ItemAncientAegis();
    public static ItemChaosCore chaosCore = new ItemChaosCore();
    public static ItemDarkSunRing darkSunRing = new ItemDarkSunRing();
    public static ItemShinyStone shinyStone = new ItemShinyStone();
    public static ItemParadox paradox = new ItemParadox(RelicsMaterialHandler.materialParadoxicalStuff);
    public static ItemTerrorCrown terrorCrown = new ItemTerrorCrown(EntityEquipmentSlot.HEAD, ItemArmor.ArmorMaterial.GOLD);
    public static ItemDeificAmulet DeificAmulet = new ItemDeificAmulet();
    public static ItemXPTome XPTome = new ItemXPTome();
    public static ItemWeatherStone weatherStone = new ItemWeatherStone();
    public static ItemSuperpositionRing superpositionRing = new ItemSuperpositionRing();
    public static ItemOblivionStone oblivionStone = new ItemOblivionStone();
    public static ItemOblivionAmulet oblivionAmulet = new ItemOblivionAmulet();
    public static ItemDimensionalMirror dimensionalMirror = new ItemDimensionalMirror();
    public static ItemArcanum arcanum = new ItemArcanum();
    public static ItemDormantArcanum dormantArcanum = new ItemDormantArcanum();

    public void preInit(FMLPreInitializationEvent event) {
        CapabilityManager.INSTANCE.register(PlayerVariables.class, new IPlayerStorage(), () -> null);
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }


    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(AdvancedMiningCharm);
        event.getRegistry().register(MiningCharm);
        event.getRegistry().register(AncientAegis);
        event.getRegistry().register(chaosCore);
        event.getRegistry().register(darkSunRing);
        event.getRegistry().register(shinyStone);
        event.getRegistry().register(terrorCrown);
        event.getRegistry().register(paradox);
        event.getRegistry().register(DeificAmulet);
        event.getRegistry().register(XPTome);
        event.getRegistry().register(weatherStone);
        event.getRegistry().register(superpositionRing);
        event.getRegistry().register(oblivionStone);
        event.getRegistry().register(oblivionAmulet);
        event.getRegistry().register(dimensionalMirror);
        event.getRegistry().register(arcanum);
        event.getRegistry().register(dormantArcanum);
    }

    @SideOnly(Side.CLIENT)
    public void modelRegistryEvent(ModelRegistryEvent event) {

    }
}
