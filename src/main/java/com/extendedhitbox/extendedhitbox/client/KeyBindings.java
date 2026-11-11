package com.extendedhitbox.extendedhitbox.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class KeyBindings {

    public static final String KEY_CATEGORY = "key.categories.extendedhitbox";

    public static final KeyMapping TOGGLE_HITBOX = new KeyMapping(
            "key.extendedhitbox.toggle_hitbox",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            KEY_CATEGORY
    );

    // Called from mod event bus in ExtendedhitboxMod
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_HITBOX);
    }
}