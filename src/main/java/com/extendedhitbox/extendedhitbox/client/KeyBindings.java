package com.extendedhitbox.extendedhitbox.client;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static final String KEY_CATEGORY = "key.categories.extendedhitbox";

    public static final KeyBinding TOGGLE_HITBOX = new KeyBinding(
        "key.extendedhitbox.toggle_hitbox",
        InputUtil.Type.KEYSYM,
        GLFW.GLFW_KEY_H,
        KEY_CATEGORY
    );
}
