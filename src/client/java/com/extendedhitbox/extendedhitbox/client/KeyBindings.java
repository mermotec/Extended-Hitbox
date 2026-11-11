package com.extendedhitbox.extendedhitbox.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

/** @author MermoTEC */
public class KeyBindings {

    // 1. In 26.1.1+, ResourceLocation is now Identifier
    public static final KeyMapping.Category KEY_CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath("extendedhitbox", "main")
    );

    // 2. Fabric API now uses KeyMappingHelper instead of KeyBindingHelper
    public static final KeyMapping TOGGLE_HITBOX = new KeyMapping(
            "key.extendedhitbox.toggle_hitbox",
            InputConstants.Type.KEYBOARD,
            InputConstants.KEY_H,
            KEY_CATEGORY
    );

    public static void register() {
        KeyMappingHelper.registerKeyMapping(TOGGLE_HITBOX);
    }
}
