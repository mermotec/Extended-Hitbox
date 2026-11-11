package com.extendedhitbox.extendedhitbox.client;

import com.extendedhitbox.extendedhitbox.Config;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.extendedhitbox.extendedhitbox.ExtendedHitbox.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class HitboxCommand {

    // Enhanced suggestion provider with full auto-complete
    private static final SuggestionProvider<CommandSourceStack> TARGET_SUGGESTIONS = (context, builder) -> {
        String remaining = builder.getRemaining();
        String remainingLower = remaining.toLowerCase();
        Minecraft mc = Minecraft.getInstance();

        // Stage 1: Initial suggestions (selectors and player names)
        if (remaining.isEmpty()) {
            List<String> suggestions = new ArrayList<>();
            suggestions.add("@a");
            suggestions.add("@e");
            suggestions.add("@e[type=");
            suggestions.add("@p");
            suggestions.add("@r");
            suggestions.add("@s");

            // Add player names
            if (mc.level != null) {
                for (Entity entity : mc.level.entitiesForRendering()) {
                    if (entity instanceof Player) {
                        suggestions.add(entity.getName().getString());
                    }
                }
            }

            return SharedSuggestionProvider.suggest(suggestions, builder);
        }

        // Stage 2: Handling @e[type= suggestions
        if (remainingLower.startsWith("@e[type=")) {
            String afterType = remaining.substring(8);

            // Check if we're at the negation stage (type=!)
            if (afterType.isEmpty() || afterType.equals("!")) {
                List<String> suggestions = new ArrayList<>();
                // Suggest normal entity types
                for (ResourceLocation entityType : BuiltInRegistries.ENTITY_TYPE.keySet()) {
                    suggestions.add("@e[type=" + entityType.toString() + "]");
                }
                // Also suggest negation
                suggestions.add("@e[type=!");
                return SharedSuggestionProvider.suggest(suggestions, builder);
            }

            // Handle negation: @e[type=!
            if (afterType.startsWith("!")) {
                String afterNegation = afterType.substring(1);

                if (afterNegation.isEmpty() || !afterNegation.contains("]")) {
                    List<String> suggestions = BuiltInRegistries.ENTITY_TYPE.keySet().stream()
                            .map(type -> "@e[type=!" + type.toString() + "]")
                            .filter(s -> s.toLowerCase().contains(afterNegation.toLowerCase()))
                            .collect(Collectors.toList());
                    return SharedSuggestionProvider.suggest(suggestions, builder);
                }
            }

            // Normal entity type suggestions
            if (!afterType.contains("]")) {
                List<String> suggestions = BuiltInRegistries.ENTITY_TYPE.keySet().stream()
                        .filter(type -> type.toString().toLowerCase().startsWith(afterType.toLowerCase()))
                        .map(type -> "@e[type=" + type.toString() + "]")
                        .collect(Collectors.toList());
                return SharedSuggestionProvider.suggest(suggestions, builder);
            }
        }

        // Stage 3: Handling @e[ but not yet type=
        if (remainingLower.startsWith("@e[") && !remainingLower.contains("type=")) {
            return SharedSuggestionProvider.suggest(
                    List.of("@e[type=", "@e[type=!"),
                    builder
            );
        }

        // Stage 4: Selector suggestions
        if (remainingLower.startsWith("@")) {
            List<String> suggestions = new ArrayList<>();
            if ("@a".startsWith(remainingLower)) suggestions.add("@a");
            if ("@e".startsWith(remainingLower)) {
                suggestions.add("@e");
                suggestions.add("@e[type=");
            }
            if ("@p".startsWith(remainingLower)) suggestions.add("@p");
            if ("@r".startsWith(remainingLower)) suggestions.add("@r");
            if ("@s".startsWith(remainingLower)) suggestions.add("@s");

            return SharedSuggestionProvider.suggest(suggestions, builder);
        }

        // Stage 5: Player name suggestions
        if (mc.level != null) {
            List<String> playerNames = new ArrayList<>();
            for (Entity entity : mc.level.entitiesForRendering()) {
                if (entity instanceof Player) {
                    String name = entity.getName().getString();
                    if (name.toLowerCase().startsWith(remainingLower)) {
                        playerNames.add(name);
                    }
                }
            }
            if (!playerNames.isEmpty()) {
                return SharedSuggestionProvider.suggest(playerNames, builder);
            }
        }

        return builder.buildFuture();
    };

    @SubscribeEvent
    public static void onRegisterCommands(RegisterClientCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("hitbox")
                .then(Commands.literal("set")
                        .then(Commands.argument("multiplier", DoubleArgumentType.doubleArg(0.1, 5.0))
                                .executes(context -> {
                                    double multiplier = DoubleArgumentType.getDouble(context, "multiplier");
                                    ClientEvents.ClientForgeEvents.setHitboxMultiplier(multiplier);

                                    // Only show feedback if config allows
                                    if (Config.shouldShowFeedback()) {
                                        context.getSource().sendSuccess(() -> Component.literal("§aHitbox multiplier set to: §e" + multiplier + "x"), false);
                                    }
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("get")
                        .executes(context -> {
                            double current = ClientEvents.ClientForgeEvents.getHitboxMultiplier();
                            boolean enabled = ClientEvents.ClientForgeEvents.isHitboxExpanded();

                            // ALWAYS show feedback for /hitbox get (regardless of config)
                            if (enabled) {
                                context.getSource().sendSuccess(() -> Component.literal("§aHitbox multiplier: §e" + current + "x §a(ENABLED)"), false);
                            } else {
                                context.getSource().sendSuccess(() -> Component.literal("§aHitbox multiplier: §e" + current + "x §c(DISABLED)"), false);
                            }
                            return 1;
                        })
                )
                .then(Commands.literal("disable")
                        .then(Commands.argument("target", StringArgumentType.greedyString())
                                .suggests(TARGET_SUGGESTIONS)
                                .executes(context -> {
                                    String target = StringArgumentType.getString(context, "target");
                                    return handleDisableCommand(context, target);
                                })
                        )
                )
                .then(Commands.literal("enable")
                        .then(Commands.argument("target", StringArgumentType.greedyString())
                                .suggests(TARGET_SUGGESTIONS)
                                .executes(context -> {
                                    String target = StringArgumentType.getString(context, "target");
                                    return handleEnableCommand(context, target);
                                })
                        )
                )
        );
    }

    private static int handleDisableCommand(CommandContext<CommandSourceStack> context, String target) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) {
            context.getSource().sendFailure(Component.literal("§cNo world loaded"));
            return 0;
        }

        // Handle @e[type=!entity_type] - disable ALL entities EXCEPT this type
        if (target.startsWith("@e[type=!") && target.endsWith("]")) {
            String entityTypeName = target.substring(9, target.length() - 1);
            ResourceLocation entityTypeId = ResourceLocation.parse(entityTypeName);
            Optional<EntityType<?>> entityTypeOpt = BuiltInRegistries.ENTITY_TYPE.getOptional(entityTypeId);

            if (entityTypeOpt.isPresent()) {
                ClientEvents.ClientForgeEvents.disableNegatedEntityType(entityTypeId);

                // Only show feedback if config allows
                if (Config.shouldShowFeedback()) {
                    String finalEntityTypeName = entityTypeName;
                    context.getSource().sendSuccess(() -> Component.literal("§aDisabled extended hitbox for ALL entities EXCEPT: §e" + finalEntityTypeName), false);
                }
                return 1;
            } else {
                context.getSource().sendFailure(Component.literal("§cUnknown entity type: " + entityTypeName));
                return 0;
            }
        }

        // Handle @e[type=entity_type] - disable ALL entities of this type
        if (target.startsWith("@e[type=") && target.endsWith("]")) {
            String entityTypeName = target.substring(8, target.length() - 1);
            ResourceLocation entityTypeId = ResourceLocation.parse(entityTypeName);
            Optional<EntityType<?>> entityTypeOpt = BuiltInRegistries.ENTITY_TYPE.getOptional(entityTypeId);

            if (entityTypeOpt.isPresent()) {
                ClientEvents.ClientForgeEvents.disableEntityType(entityTypeId);

                // Only show feedback if config allows
                if (Config.shouldShowFeedback()) {
                    String finalEntityTypeName = entityTypeName;
                    context.getSource().sendSuccess(() -> Component.literal("§aDisabled extended hitbox for ALL entities of type: §e" + finalEntityTypeName), false);
                }
                return 1;
            } else {
                context.getSource().sendFailure(Component.literal("§cUnknown entity type: " + entityTypeName));
                return 0;
            }
        }

        // Handle selectors
        if (target.startsWith("@")) {
            String selector = target.length() >= 2 ? target.substring(0, 2) : target;

            if (selector.equals("@a") || selector.equals("@e") || selector.equals("@p") || selector.equals("@s")) {
                ClientEvents.ClientForgeEvents.disableSelector(selector);
                String selectorName = getSelectorName(selector);

                // Only show feedback if config allows
                if (Config.shouldShowFeedback()) {
                    context.getSource().sendSuccess(() -> Component.literal("§aDisabled extended hitbox for ALL: §e" + selectorName), false);
                }
                return 1;
            } else if (selector.equals("@r")) {
                List<Entity> entities = resolveTargetEntities(mc, target);
                if (!entities.isEmpty()) {
                    for (Entity entity : entities) {
                        ClientEvents.ClientForgeEvents.disableEntityByUUID(entity.getUUID());
                    }

                    // Only show feedback if config allows
                    if (Config.shouldShowFeedback()) {
                        context.getSource().sendSuccess(() -> Component.literal("§aDisabled extended hitbox for random entity"), false);
                    }
                    return 1;
                }
                context.getSource().sendFailure(Component.literal("§cNo entities found"));
                return 0;
            }
        }

        // Handle player name
        ClientEvents.ClientForgeEvents.disablePlayerName(target);

        // Only show feedback if config allows
        if (Config.shouldShowFeedback()) {
            String finalTarget = target;
            context.getSource().sendSuccess(() -> Component.literal("§aDisabled extended hitbox for player: §e" + finalTarget), false);
        }
        return 1;
    }

    private static int handleEnableCommand(CommandContext<CommandSourceStack> context, String target) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) {
            context.getSource().sendFailure(Component.literal("§cNo world loaded"));
            return 0;
        }

        // Handle @e[type=!entity_type]
        if (target.startsWith("@e[type=!") && target.endsWith("]")) {
            String entityTypeName = target.substring(9, target.length() - 1);
            ResourceLocation entityTypeId = ResourceLocation.parse(entityTypeName);

            ClientEvents.ClientForgeEvents.enableNegatedEntityType(entityTypeId);

            // Only show feedback if config allows
            if (Config.shouldShowFeedback()) {
                String finalEntityTypeName = entityTypeName;
                context.getSource().sendSuccess(() -> Component.literal("§aEnabled extended hitbox for entities except: §e" + finalEntityTypeName), false);
            }
            return 1;
        }

        // Handle @e[type=entity_type]
        if (target.startsWith("@e[type=") && target.endsWith("]")) {
            String entityTypeName = target.substring(8, target.length() - 1);
            ResourceLocation entityTypeId = ResourceLocation.parse(entityTypeName);

            ClientEvents.ClientForgeEvents.enableEntityType(entityTypeId);

            // Only show feedback if config allows
            if (Config.shouldShowFeedback()) {
                String finalEntityTypeName = entityTypeName;
                context.getSource().sendSuccess(() -> Component.literal("§aEnabled extended hitbox for ALL entities of type: §e" + finalEntityTypeName), false);
            }
            return 1;
        }

        // Handle selectors
        if (target.startsWith("@")) {
            String selector = target.length() >= 2 ? target.substring(0, 2) : target;

            if (selector.equals("@a") || selector.equals("@e") || selector.equals("@p") || selector.equals("@s")) {
                ClientEvents.ClientForgeEvents.enableSelector(selector);
                String selectorName = getSelectorName(selector);

                // Only show feedback if config allows
                if (Config.shouldShowFeedback()) {
                    context.getSource().sendSuccess(() -> Component.literal("§aEnabled extended hitbox for ALL: §e" + selectorName), false);
                }
                return 1;
            } else if (selector.equals("@r")) {
                List<Entity> entities = resolveTargetEntities(mc, target);
                if (!entities.isEmpty()) {
                    for (Entity entity : entities) {
                        ClientEvents.ClientForgeEvents.enableEntityByUUID(entity.getUUID());
                    }

                    // Only show feedback if config allows
                    if (Config.shouldShowFeedback()) {
                        context.getSource().sendSuccess(() -> Component.literal("§aEnabled extended hitbox for random entity"), false);
                    }
                    return 1;
                }
                context.getSource().sendFailure(Component.literal("§cNo entities found"));
                return 0;
            }
        }

        // Handle player name
        ClientEvents.ClientForgeEvents.enablePlayerName(target);

        // Only show feedback if config allows
        if (Config.shouldShowFeedback()) {
            String finalTarget = target;
            context.getSource().sendSuccess(() -> Component.literal("§aEnabled extended hitbox for player: §e" + finalTarget), false);
        }
        return 1;
    }

    private static String getSelectorName(String selector) {
        return switch (selector) {
            case "@a" -> "players";
            case "@e" -> "entities";
            case "@p" -> "nearest player";
            case "@s" -> "yourself";
            default -> selector;
        };
    }

    private static List<Entity> resolveTargetEntities(Minecraft mc, String target) {
        List<Entity> entities = new ArrayList<>();

        if (target.startsWith("@") && mc.level != null && mc.player != null) {
            String selector = target.length() >= 2 ? target.substring(0, 2) : target;

            switch (selector) {
                case "@a":
                    for (Entity entity : mc.level.entitiesForRendering()) {
                        if (entity instanceof Player) {
                            entities.add(entity);
                        }
                    }
                    break;

                case "@e":
                    for (Entity entity : mc.level.entitiesForRendering()) {
                        if (entity instanceof LivingEntity && entity != mc.player) {
                            entities.add(entity);
                        }
                    }
                    break;

                case "@p":
                    if (mc.level != null && mc.player != null) {
                        Player nearest = mc.level.getNearestPlayer(mc.player, -1.0);
                        if (nearest != null && nearest != mc.player) {
                            entities.add(nearest);
                        }
                    }
                    break;

                case "@s":
                    if (mc.player != null) {
                        entities.add(mc.player);
                    }
                    break;

                case "@r":
                    List<Entity> allEntities = new ArrayList<>();
                    for (Entity entity : mc.level.entitiesForRendering()) {
                        if (entity instanceof LivingEntity && entity != mc.player) {
                            allEntities.add(entity);
                        }
                    }
                    if (!allEntities.isEmpty()) {
                        entities.add(allEntities.get(mc.level.random.nextInt(allEntities.size())));
                    }
                    break;
            }
        }

        return entities;
    }
}
