package com.extendedhitbox.extendedhitbox.client;

import com.extendedhitbox.extendedhitbox.ExtendedHitboxClient;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;


// ✅ NEW NeoForge imports
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;


import java.util.HashMap;
import java.util.Map;

// ✅ NEW: Auto-registers all @SubscribeEvent methods to NeoForge.EVENT_BUS, CLIENT-only
@EventBusSubscriber(modid = ExtendedHitboxClient.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    private static boolean hitboxExpanded = false;
    private static double widthMultiplier = 1.5;
    private static double heightMultiplier = 1.5;
    // ✅ FIXED: Raw type Map → properly typed Map<Integer, AABB>
    private static final Map<Integer, AABB> originalHitboxes = new HashMap<>();

    // ✅ CHANGED: (Minecraft client) → (ClientTickEvent.Post event)
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft client = Minecraft.getInstance(); // ✅ Get instance manually

        if (client.player == null || client.level == null) return;

        while (KeyBindings.TOGGLE_HITBOX.consumeClick()) {
            hitboxExpanded = !hitboxExpanded;

            if (!hitboxExpanded) {
                restoreAllHitboxes(client);
                client.player.displayClientMessage(
                        Component.literal("§cHitbox expansion disabled."), true);
            } else {
                client.player.displayClientMessage(
                        Component.literal("§aHitbox expansion enabled."), true);
            }
        }

        if (hitboxExpanded) {
            expandAllHitboxes(client);
        }
    }

    // ✅ NO CHANGES BELOW — Pure Minecraft API, works on both Fabric & NeoForge

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
        float expandedWidth = (float) (entity.getBbWidth() * widthMultiplier);
        float expandedHeight = (float) (entity.getBbHeight() * heightMultiplier);
        double halfWidth = expandedWidth / 2.0;
        entity.setBoundingBox(new AABB(
                entityPos.x - halfWidth, entityPos.y, entityPos.z - halfWidth,
                entityPos.x + halfWidth, entityPos.y + expandedHeight, entityPos.z + halfWidth
        ));
    }

    private static void resetEntityHitbox(Entity entity) {
        Vec3 entityPos = entity.position();
        double halfWidth = entity.getBbWidth() / 2.0;
        entity.setBoundingBox(new AABB(
                entityPos.x - halfWidth, entityPos.y, entityPos.z - halfWidth,
                entityPos.x + halfWidth, entityPos.y + entity.getBbHeight(), entityPos.z + halfWidth
        ));
    }

    // --- Configuration Methods --- (unchanged)
    public static void setHitboxMultiplier(double multiplier) { widthMultiplier = multiplier; heightMultiplier = multiplier; hitboxExpanded = true; }
    public static void setWidthMultiplier(double multiplier) { widthMultiplier = multiplier; hitboxExpanded = true; }
    public static void setHeightMultiplier(double multiplier) { heightMultiplier = multiplier; hitboxExpanded = true; }
    public static void reset() { widthMultiplier = 1.0; heightMultiplier = 1.0; hitboxExpanded = false; }

    // --- Getters --- (unchanged)
    public static double getWidthMultiplier() { return widthMultiplier; }
    public static double getHeightMultiplier() { return heightMultiplier; }
    public static double getHitboxMultiplier() { return widthMultiplier; }
    public static boolean isHitboxExpanded() { return hitboxExpanded; }
}
