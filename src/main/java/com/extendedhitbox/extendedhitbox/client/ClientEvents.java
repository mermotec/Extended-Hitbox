package com.extendedhitbox.extendedhitbox.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;

public class ClientEvents {
    private static boolean hitboxExpanded = false;
    private static double widthMultiplier = 1.5;
    private static double heightMultiplier = 1.5;
    private static final Map<Integer, AABB> originalHitboxes = new HashMap<>();

    public static void onClientTick(Minecraft client) {
        if (client.player == null || client.level == null) return;

        while (KeyBindings.TOGGLE_HITBOX.consumeClick()) {
            hitboxExpanded = !hitboxExpanded;
            if (!hitboxExpanded) {
                restoreAllHitboxes(client);
                client.player.displayClientMessage(Component.literal("§cHitbox expansion disabled."), true);
            } else {
                client.player.displayClientMessage(Component.literal("§aHitbox expansion enabled."), true);
            }
        }

        if (hitboxExpanded) {
            expandAllHitboxes(client);
        }
    }

    private static void expandAllHitboxes(Minecraft mc) {
        if (mc.level == null || mc.player == null) return;
        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity instanceof LivingEntity && entity.getId() != mc.player.getId()) {
                int entityId = entity.getId();
                if (!originalHitboxes.containsKey(entityId)) {
                    originalHitboxes.put(entityId, entity.getBoundingBox());
                }
                entity.noPhysics = true;
                expandEntityHitbox(entity);
            }
        }
    }

    private static void restoreAllHitboxes(Minecraft mc) {
        if (mc.level == null || mc.player == null) return;
        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity instanceof LivingEntity && entity.getId() != mc.player.getId()) {
                int entityId = entity.getId();
                entity.noPhysics = false;
                if (originalHitboxes.containsKey(entityId)) {
                    entity.setBoundingBox(originalHitboxes.get(entityId));
                } else {
                    resetEntityHitbox(entity);
                }
            }
        }
        originalHitboxes.clear();
    }

    private static void expandEntityHitbox(Entity entity) {
        Vec3 entityPos = entity.position();
        float baseWidth = entity.getBbWidth();
        float baseHeight = entity.getBbHeight();
        float expandedWidth = (float) (baseWidth * widthMultiplier);
        float expandedHeight = (float) (baseHeight * heightMultiplier);
        double halfWidth = expandedWidth / 2.0;

        AABB expandedBox = new AABB(
                entityPos.x - halfWidth, entityPos.y, entityPos.z - halfWidth,
                entityPos.x + halfWidth, entityPos.y + expandedHeight, entityPos.z + halfWidth
        );
        entity.setBoundingBox(expandedBox);
    }

    private static void resetEntityHitbox(Entity entity) {
        Vec3 entityPos = entity.position();
        float baseWidth = entity.getBbWidth();
        float baseHeight = entity.getBbHeight();
        double halfWidth = baseWidth / 2.0;

        AABB normalBox = new AABB(
                entityPos.x - halfWidth, entityPos.y, entityPos.z - halfWidth,
                entityPos.x + halfWidth, entityPos.y + baseHeight, entityPos.z + halfWidth
        );
        entity.setBoundingBox(normalBox);
    }

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
    public static void reset() {
        widthMultiplier = 1.0;
        heightMultiplier = 1.0;
        hitboxExpanded = false;
    }
    public static double getWidthMultiplier() { return widthMultiplier; }
    public static double getHeightMultiplier() { return heightMultiplier; }
    public static double getHitboxMultiplier() { return widthMultiplier; }
    public static boolean isHitboxExpanded() { return hitboxExpanded; }
}