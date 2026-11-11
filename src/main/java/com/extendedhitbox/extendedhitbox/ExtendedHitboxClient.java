package com.extendedhitbox.extendedhitbox;

import com.extendedhitbox.extendedhitbox.client.KeyBindings;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
// ✅ NEW — required when using dist = Dist.CLIENT on @Mod
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@Mod(value = ExtendedHitboxClient.MOD_ID, dist = Dist.CLIENT)
public class ExtendedHitboxClient {

    public static final String MOD_ID = "extendedhitbox";

    // ✅ FMLModContainer is now the FIRST argument — NeoForge injects it automatically
    public ExtendedHitboxClient(FMLModContainer container, IEventBus modEventBus) {
        modEventBus.addListener(this::registerKeyMappings);
        // @EventBusSubscriber handles ClientEvents and ModCommands automatically ✅
    }

    private void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KeyBindings.TOGGLE_HITBOX);
    }
}
