package com.keletu.forgotten_relics.proxy;

import com.keletu.forgotten_relics.items.ItemModBase;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public void modelRegistryEvent(ModelRegistryEvent event) {
        AdvancedMiningCharm.registerModels();
        MiningCharm.registerModels();
        AncientAegis.registerModels();
        chaosCore.registerModels();
        darkSunRing.registerModels();
        shinyStone.registerModels();
        terrorCrown.registerModels();
        paradox.registerModels();
        DeificAmulet.registerModels();
        XPTome.registerModels();
        weatherStone.registerModels();
        superpositionRing.registerModels();
        oblivionStone.registerModels();
    }
}
