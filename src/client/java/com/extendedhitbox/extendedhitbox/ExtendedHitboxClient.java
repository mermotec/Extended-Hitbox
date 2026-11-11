package com.extendedhitbox.extendedhitbox;

import com.extendedhitbox.extendedhitbox.client.ClientEvents;
import com.extendedhitbox.extendedhitbox.client.KeyBindings;
import com.extendedhitbox.extendedhitbox.client.ModCommands;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper; // Updated import

public class ExtendedHitboxClient implements ClientModInitializer {

    // CRITICAL FIX: Mod IDs should never contain "assets/". It should just be the ID itself.
    public static final String MOD_ID = "extendedhitbox";

    @Override
    public void onInitializeClient() {
        // 1. Updated for 1.21.1 Mojmap: KeyMappingHelper instead of KeyBindingHelper
        // Note: If you already used KeyMappingHelper.registerKeyMapping inside your KeyBindings.java
        // as shown in the earlier step, you can safely delete this line!
        KeyMappingHelper.registerKeyMapping(KeyBindings.TOGGLE_HITBOX);

        // 2. Since we added a register() method to ClientEvents in the last step,
        // we can just call that here to keep things clean.
        ClientEvents.register();

        // 3. Register your commands
        ModCommands.register();
    }
}