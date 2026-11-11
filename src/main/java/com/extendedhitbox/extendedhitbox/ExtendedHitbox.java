package com.extendedhitbox.extendedhitbox;

import com.extendedhitbox.extendedhitbox.client.ClientEvents;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(ExtendedHitbox.MOD_ID)
public class ExtendedHitbox {
    public static final String MOD_ID = "extendedhitbox";

    public ExtendedHitbox() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register config
        Config.register();

        // Register client-only events
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.register(ClientEvents.class);
        }
    }
}
