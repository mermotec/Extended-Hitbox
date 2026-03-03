package com.extendedhitbox.extendedhitbox;

import com.extendedhitbox.extendedhitbox.client.ClientEvents;
import com.extendedhitbox.extendedhitbox.client.KeyBindings;
import com.extendedhitbox.extendedhitbox.client.ModCommands;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class ExtendedHitboxClient implements ClientModInitializer {
    public static final String MOD_ID = "extendedhitbox";

    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(KeyBindings.TOGGLE_HITBOX);
        ClientTickEvents.END_CLIENT_TICK.register(ClientEvents::onClientTick);
        ModCommands.register();
    }
}