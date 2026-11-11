package com.extendedhitbox.extendedhitbox;

import com.extendedhitbox.extendedhitbox.client.ClientEvents;
import com.extendedhitbox.extendedhitbox.client.KeyBindings;
import com.extendedhitbox.extendedhitbox.client.ModCommands;
import net.fabricmc.api.ClientModInitializer;


/** @author MermoTEC */
public class ExtendedHitboxClient implements ClientModInitializer {

    // CRITICAL FIX: Mod IDs should never contain "assets/". It should just be the ID itself.
    public static final String MOD_ID = "extendedhitbox";

    @Override
    public void onInitializeClient() {
        KeyBindings.register();
        ClientEvents.register();
        ModCommands.register();
    }
}