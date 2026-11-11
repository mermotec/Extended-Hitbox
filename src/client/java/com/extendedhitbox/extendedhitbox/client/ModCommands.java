package com.extendedhitbox.extendedhitbox.client;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.network.chat.Component;

public class ModCommands {

    public static void register() {
        // Registering the command using the official Fabric Client Command API (via FQN)
        // This makes the command show up in the suggestion list and appear white.
        net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {

            dispatcher.register(net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal("hitbox")

                    // Subcommand: /hitbox set <value> (Sets both W and H)
                    .then(net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal("set")
                            .then(net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument("multiplier", DoubleArgumentType.doubleArg(0.1, 10.0))
                                    .executes(context -> {
                                        double value = DoubleArgumentType.getDouble(context, "multiplier");
                                        ClientEvents.setHitboxMultiplier(value);
                                        context.getSource().sendFeedback(Component.literal("§aHitbox set to " + value + "x"));
                                        return 1;
                                    })
                            )
                    )

                    // Subcommand: /hitbox width <value> (Sets Width only)
                    .then(net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal("width")
                            .then(net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument("val", DoubleArgumentType.doubleArg(0.1, 10.0))
                                    .executes(context -> {
                                        double value = DoubleArgumentType.getDouble(context, "val");
                                        ClientEvents.setWidthMultiplier(value);
                                        context.getSource().sendFeedback(Component.literal("§aWidth multiplier set to " + value + "x"));
                                        return 1;
                                    })
                            )
                    )

                    // Subcommand: /hitbox height <value> (Sets Height only)
                    .then(net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal("height")
                            .then(net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument("val", DoubleArgumentType.doubleArg(0.1, 10.0))
                                    .executes(context -> {
                                        double value = DoubleArgumentType.getDouble(context, "val");
                                        ClientEvents.setHeightMultiplier(value);
                                        context.getSource().sendFeedback(Component.literal("§aHeight multiplier set to " + value + "x"));
                                        return 1;
                                    })
                            )
                    )

                    // Subcommand: /hitbox reset (Resets to 1.0 and disables)
                    .then(net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal("reset")
                            .executes(context -> {
                                ClientEvents.reset();
                                context.getSource().sendFeedback(Component.literal("§eHitbox expansion reset and disabled."));
                                return 1;
                            })
                    )

                    // Subcommand: /hitbox get (Shows info)
                    .then(net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal("get")
                            .executes(context -> {
                                double w = ClientEvents.getWidthMultiplier();
                                double h = ClientEvents.getHeightMultiplier();
                                boolean enabled = ClientEvents.isHitboxExpanded();
                                String status = enabled ? "§a(ENABLED)" : "§c(DISABLED)";
                                context.getSource().sendFeedback(Component.literal(
                                        String.format("Hitbox: %s | Width: %.2fx | Height: %.2fx", status, w, h)
                                ));
                                return 1;
                            })
                    )
            );
        });
    }
}