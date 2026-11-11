package com.extendedhitbox.extendedhitbox;

import com.extendedhitbox.extendedhitbox.client.ClientEvents;
import com.extendedhitbox.extendedhitbox.client.KeyBindings;
import com.extendedhitbox.extendedhitbox.client.ModCommands;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(ExtendedhitboxMod.MOD_ID)
public class ExtendedhitboxMod {

    public static final String MOD_ID = "extendedhitbox";

    // Forge 54.x (1.21.4) requires a no-arg constructor.
    // IEventBus is no longer injected automatically — fetch it manually.
    public ExtendedhitboxMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        if (FMLEnvironment.dist == Dist.CLIENT) {
            // Key bindings must go on the MOD event bus
            modEventBus.addListener(KeyBindings::onRegisterKeyMappings);

            // Tick + command listeners go on the FORGE (game) event bus
            MinecraftForge.EVENT_BUS.register(ClientEvents.class);
            MinecraftForge.EVENT_BUS.register(ModCommands.class);
        }
    }
}