package com.extendedhitbox.extendedhitbox.client;

import com.extendedhitbox.extendedhitbox.ExtendedHitbox;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = ExtendedHitbox.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(KeyBindings.TOGGLE_HITBOX);
    }

    @Mod.EventBusSubscriber(modid = ExtendedHitbox.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        private static boolean hitboxExpanded = false;
        private static double hitboxMultiplier = 1.5;
        private static final Map<Integer, AABB> originalHitboxes = new HashMap<>();

        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                Minecraft mc = Minecraft.getInstance();

                if (mc.player == null || mc.level == null) return;

                // Check if hitbox toggle key was pressed
                while (KeyBindings.TOGGLE_HITBOX.consumeClick()) {
                    hitboxExpanded = !hitboxExpanded;

                    // If disabling, immediately restore all hitboxes
                    if (!hitboxExpanded) {
                        restoreAllHitboxes(mc);
                    }
                }

                // Continuously expand hitboxes every tick if enabled
                if (hitboxExpanded && mc.level != null) {
                    expandAllHitboxes(mc);
                }
            }
        }

        private static void expandAllHitboxes(Minecraft mc) {
            if (mc.level == null || mc.player == null) return;

            for (Entity entity : mc.level.entitiesForRendering()) {
                if (entity instanceof LivingEntity && entity.getId() != mc.player.getId()) {
                    int entityId = entity.getId();

                    // Store original hitbox if not already stored
                    if (!originalHitboxes.containsKey(entityId)) {
                        originalHitboxes.put(entityId, entity.getBoundingBox());
                    }

                    // Disable collision/pushing when hitbox is expanded
                    entity.noPhysics = true;

                    expandEntityHitbox(entity);
                }
            }
        }

        private static void restoreAllHitboxes(Minecraft mc) {
            if (mc.level == null || mc.player == null) return;

            // Restore all hitboxes to original size and re-enable collision
            for (Entity entity : mc.level.entitiesForRendering()) {
                if (entity instanceof LivingEntity && entity.getId() != mc.player.getId()) {
                    int entityId = entity.getId();

                    // Re-enable collision/pushing
                    entity.noPhysics = false;

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
            Vec3 entityPos = entity.position();

            float baseWidth = entity.getBbWidth();
            float baseHeight = entity.getBbHeight();

            float expandedWidth = (float) (baseWidth * hitboxMultiplier);
            float expandedHeight = (float) (baseHeight * hitboxMultiplier);

            double halfWidth = expandedWidth / 2.0;

            AABB expandedBox = new AABB(
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
            Vec3 entityPos = entity.position();

            float baseWidth = entity.getBbWidth();
            float baseHeight = entity.getBbHeight();

            double halfWidth = baseWidth / 2.0;

            AABB normalBox = new AABB(
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
            hitboxMultiplier = multiplier;
            hitboxExpanded = true;
        }

        public static double getHitboxMultiplier() {
            return hitboxMultiplier;
        }

        public static boolean isHitboxExpanded() {
            return hitboxExpanded;
        }
    }
}
