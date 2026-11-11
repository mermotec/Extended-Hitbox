package com.extendedhitbox.extendedhitbox.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static final String KEY_CATEGORY = "key.categories.extendedhitbox";
    public static final KeyMapping TOGGLE_HITBOX = new KeyMapping(
            "key.extendedhitbox.toggle_hitbox",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            KEY_CATEGORY
    );
}