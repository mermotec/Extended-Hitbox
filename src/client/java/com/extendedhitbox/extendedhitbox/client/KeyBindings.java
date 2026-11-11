package com.extendedhitbox.extendedhitbox.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

    public static final KeyMapping.Category KEY_CATEGORY = KeyMapping.Category.register(
            ResourceLocation.fromNamespaceAndPath("extendedhitbox", "main")
    );

    public static final KeyMapping TOGGLE_HITBOX = new KeyMapping(
            "key.extendedhitbox.toggle_hitbox",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            KEY_CATEGORY
    );
}
