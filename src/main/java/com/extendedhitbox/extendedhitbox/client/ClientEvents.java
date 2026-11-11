package com.extendedhitbox.extendedhitbox.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ClientEvents {

    private static boolean hitboxExpanded = false;
    private static double widthMultiplier = 1.5;
    private static double heightMultiplier = 1.5;
    private static final Map<Integer, AABB> originalHitboxes = new HashMap<>();

    // Forge fires ClientTickEvent twice per tick (START and END); we only act on END
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft client = Minecraft.getInstance();
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

    // -------------------------------------------------------------------------
    // Internal helpers
    // -------------------------------------------------------------------------

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
        Vec3 pos = entity.position();
        float expandedWidth  = (float) (entity.getBbWidth()  * widthMultiplier);
        float expandedHeight = (float) (entity.getBbHeight() * heightMultiplier);
        double hw = expandedWidth / 2.0;
        entity.setBoundingBox(new AABB(
                pos.x - hw, pos.y, pos.z - hw,
                pos.x + hw, pos.y + expandedHeight, pos.z + hw
        ));
    }

    private static void resetEntityHitbox(Entity entity) {
        Vec3 pos = entity.position();
        double hw = entity.getBbWidth() / 2.0;
        entity.setBoundingBox(new AABB(
                pos.x - hw, pos.y, pos.z - hw,
                pos.x + hw, pos.y + entity.getBbHeight(), pos.z + hw
        ));
    }

    // -------------------------------------------------------------------------
    // Public API (called by ModCommands)
    // -------------------------------------------------------------------------

    public static void setHitboxMultiplier(double multiplier) {
        widthMultiplier  = multiplier;
        heightMultiplier = multiplier;
        hitboxExpanded   = true;
    }

    public static void setWidthMultiplier(double multiplier) {
        widthMultiplier = multiplier;
        hitboxExpanded  = true;
    }

    public static void setHeightMultiplier(double multiplier) {
        heightMultiplier = multiplier;
        hitboxExpanded   = true;
    }

    public static void reset() {
        widthMultiplier  = 1.0;
        heightMultiplier = 1.0;
        hitboxExpanded   = false;
    }

    public static double getWidthMultiplier()  { return widthMultiplier; }
    public static double getHeightMultiplier() { return heightMultiplier; }
    public static double getHitboxMultiplier() { return widthMultiplier; } // backward compat
    public static boolean isHitboxExpanded()   { return hitboxExpanded; }
}