package com.extendedhitbox.extendedhitbox.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

    // It's just a translation string now. That's it.
    public static final String KEY_CATEGORY = "category.extendedhitbox.main";

    public static final KeyMapping TOGGLE_HITBOX = new KeyMapping(
            "key.extendedhitbox.toggle_hitbox",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            KEY_CATEGORY
    );
}