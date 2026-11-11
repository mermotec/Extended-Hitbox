package com.extendedhitbox.extendedhitbox.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;

public class ClientEvents {

    private static boolean hitboxExpanded = false;
    private static double widthMultiplier = 1.5;
    private static double heightMultiplier = 1.5;
    private static final Map<Integer, Box> originalHitboxes = new HashMap<>();

    public static void onClientTick(MinecraftClient client) {
        if (client.player == null || client.world == null) return;

        // Check if hitbox toggle key was pressed
        while (KeyBindings.TOGGLE_HITBOX.wasPressed()) {
            hitboxExpanded = !hitboxExpanded;

            // If disabling, immediately restore all hitboxes
            if (!hitboxExpanded) {
                restoreAllHitboxes(client);
            }
        }

        // Continuously expand hitboxes every tick if enabled
        if (hitboxExpanded && client.world != null) {
            expandAllHitboxes(client);
        }
    }

    private static void expandAllHitboxes(MinecraftClient mc) {
        if (mc.world == null || mc.player == null) return;

        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof LivingEntity && entity.getId() != mc.player.getId()) {
                int entityId = entity.getId();

                // Store original hitbox if not already stored
                if (!originalHitboxes.containsKey(entityId)) {
                    originalHitboxes.put(entityId, entity.getBoundingBox());
                }

                // Disable collision/pushing when hitbox is expanded
                entity.noClip = true;
                expandEntityHitbox(entity);
            }
        }
    }

    private static void restoreAllHitboxes(MinecraftClient mc) {
        if (mc.world == null || mc.player == null) return;

        // Restore all hitboxes to original size and re-enable collision
        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof LivingEntity && entity.getId() != mc.player.getId()) {
                int entityId = entity.getId();

                // Re-enable collision/pushing
                entity.noClip = false;

                // Restore original hitbox if stored
                if (originalHitboxes.containsKey(entityId)) {
                    entity.setBoundingBox(originalHitboxes.get(entityId));
                } else {
                    // Force recalculation by setting to base dimensions
                    resetEntityHitbox(entity);
                }
            }
        }

        // Clear stored hitboxes
        originalHitboxes.clear();
    }

    private static void expandEntityHitbox(Entity entity) {
        Vec3d entityPos = entity.getPos();
        float baseWidth = entity.getWidth();
        float baseHeight = entity.getHeight();

        // Calculate the expanded dimensions
        float expandedWidth = (float) (baseWidth * widthMultiplier);
        float expandedHeight = (float) (baseHeight * heightMultiplier);

        double halfWidth = expandedWidth / 2.0;

        Box expandedBox = new Box(
                entityPos.x - halfWidth,
                entityPos.y,
                entityPos.z - halfWidth,
                entityPos.x + halfWidth,
                entityPos.y + expandedHeight,
                entityPos.z + halfWidth
        );

        entity.setBoundingBox(expandedBox);
    }

    private static void resetEntityHitbox(Entity entity) {
        Vec3d entityPos = entity.getPos();
        float baseWidth = entity.getWidth();
        float baseHeight = entity.getHeight();

        double halfWidth = baseWidth / 2.0;

        Box normalBox = new Box(
                entityPos.x - halfWidth,
                entityPos.y,
                entityPos.z - halfWidth,
                entityPos.x + halfWidth,
                entityPos.y + baseHeight,
                entityPos.z + halfWidth
        );

        entity.setBoundingBox(normalBox);
    }

    // Methods for command access
    public static void setHitboxMultiplier(double multiplier) {
        widthMultiplier = multiplier;
        heightMultiplier = multiplier;
        hitboxExpanded = true;
    }

    public static void setWidthMultiplier(double multiplier) {
        widthMultiplier = multiplier;
        hitboxExpanded = true;
    }

    public static void setHeightMultiplier(double multiplier) {
        heightMultiplier = multiplier;
        hitboxExpanded = true;
    }

    public static double getHitboxMultiplier() {
        return widthMultiplier; // Return width for backward compatibility
    }

    public static double getWidthMultiplier() {
        return widthMultiplier;
    }

    public static double getHeightMultiplier() {
        return heightMultiplier;
    }

    public static boolean isHitboxExpanded() {
        return hitboxExpanded;
    }
}
