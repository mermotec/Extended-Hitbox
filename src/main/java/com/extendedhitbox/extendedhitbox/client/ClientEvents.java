package com.extendedhitbox.extendedhitbox.client;

import com.extendedhitbox.extendedhitbox.ExtendedHitbox;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

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

        // Store disabled entity UUIDs (for specific entities)
        private static final Set<UUID> disabledEntityUUIDs = new HashSet<>();

        // Store disabled entity types (for @e[type=x])
        private static final Set<ResourceLocation> disabledEntityTypes = new HashSet<>();

        // Store negated entity types (for @e[type=!x])
        private static final Set<ResourceLocation> negatedEntityTypes = new HashSet<>();

        // Store disabled selectors (@a, @e, @p, @r, @s)
        private static final Set<String> disabledSelectors = new HashSet<>();

        // Store disabled player names
        private static final Set<String> disabledPlayerNames = new HashSet<>();

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
                    // Check if this entity should be disabled
                    if (shouldDisableEntity(entity, mc)) {
                        continue;
                    }

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

        private static boolean shouldDisableEntity(Entity entity, Minecraft mc) {
            ResourceLocation entityTypeId = EntityType.getKey(entity.getType());

            // Check negated entity types first (e.g., type=!player means disable if NOT player)
            for (ResourceLocation negatedType : negatedEntityTypes) {
                if (!entityTypeId.equals(negatedType)) {
                    return true; // Disable all entities EXCEPT the negated type
                }
            }

            // Check if specific UUID is disabled
            if (disabledEntityUUIDs.contains(entity.getUUID())) {
                return true;
            }

            // Check if entity type is disabled
            if (disabledEntityTypes.contains(entityTypeId)) {
                return true;
            }

            // Check if player name is disabled
            if (entity instanceof Player) {
                String playerName = entity.getName().getString();
                if (disabledPlayerNames.contains(playerName.toLowerCase())) {
                    return true;
                }
            }

            // Check if selector matches
            for (String selector : disabledSelectors) {
                switch (selector) {
                    case "@a":
                        if (entity instanceof Player) {
                            return true;
                        }
                        break;
                    case "@e":
                        return true; // All living entities
                    case "@p":
                        if (mc.level != null && mc.player != null) {
                            Player nearest = mc.level.getNearestPlayer(mc.player, -1.0);
                            if (nearest != null && entity.getUUID().equals(nearest.getUUID())) {
                                return true;
                            }
                        }
                        break;
                    case "@s":
                        if (mc.player != null && entity.getUUID().equals(mc.player.getUUID())) {
                            return true;
                        }
                        break;
                }
            }

            return false;
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

            // Expand both width and height by multiplier
            float expandedWidth = (float) (baseWidth * hitboxMultiplier);
            float expandedHeight = (float) (baseHeight * hitboxMultiplier);
            double halfWidth = expandedWidth / 2.0;

            // Calculate the eye height offset (head position)
            float eyeHeightOffset = 0;
            if (entity instanceof LivingEntity livingEntity) {
                eyeHeightOffset = livingEntity.getEyeHeight(livingEntity.getPose());
            }

            // Expand eye height as well
            float expandedEyeHeight = (float) (eyeHeightOffset * hitboxMultiplier);
            float eyeHeightDifference = expandedEyeHeight - eyeHeightOffset;

            AABB expandedBox = new AABB(
                    entityPos.x - halfWidth,
                    entityPos.y,
                    entityPos.z - halfWidth,
                    entityPos.x + halfWidth,
                    entityPos.y + expandedHeight + eyeHeightDifference, // Add extra height for head
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

        // Disable by UUID (for specific entities)
        public static void disableEntityByUUID(UUID uuid) {
            disabledEntityUUIDs.add(uuid);
        }

        public static void enableEntityByUUID(UUID uuid) {
            disabledEntityUUIDs.remove(uuid);
        }

        // Disable by entity type
        public static void disableEntityType(ResourceLocation entityType) {
            disabledEntityTypes.add(entityType);
        }

        public static void enableEntityType(ResourceLocation entityType) {
            disabledEntityTypes.remove(entityType);
        }

        // Disable by negated entity type (type=!x)
        public static void disableNegatedEntityType(ResourceLocation entityType) {
            negatedEntityTypes.add(entityType);
        }

        public static void enableNegatedEntityType(ResourceLocation entityType) {
            negatedEntityTypes.remove(entityType);
        }

        // Disable by selector
        public static void disableSelector(String selector) {
            disabledSelectors.add(selector);
        }

        public static void enableSelector(String selector) {
            disabledSelectors.remove(selector);
        }

        // Disable by player name
        public static void disablePlayerName(String playerName) {
            disabledPlayerNames.add(playerName.toLowerCase());
        }

        public static void enablePlayerName(String playerName) {
            disabledPlayerNames.remove(playerName.toLowerCase());
        }

        // Clear all disabled rules
        public static void clearAllDisabled() {
            disabledEntityUUIDs.clear();
            disabledEntityTypes.clear();
            negatedEntityTypes.clear();
            disabledSelectors.clear();
            disabledPlayerNames.clear();
        }
    }
}
