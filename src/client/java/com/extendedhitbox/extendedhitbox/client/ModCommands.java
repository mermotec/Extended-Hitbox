package com.extendedhitbox.extendedhitbox.client;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.minecraft.network.chat.Component;

public class ModCommands {
    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommands.literal("hitbox")
                    .then(ClientCommands.literal("set")
                            .then(ClientCommands.argument("multiplier", DoubleArgumentType.doubleArg(0.1, 10.0))
                                    .executes(context -> {
                                        double value = DoubleArgumentType.getDouble(context, "multiplier");
                                        ClientEvents.setHitboxMultiplier(value);
                                        context.getSource().sendFeedback(Component.literal("§aHitbox set to " + value + "x"));
                                        return 1;
                                    })
                            )
                    )
                    .then(ClientCommands.literal("width")
                            .then(ClientCommands.argument("val", DoubleArgumentType.doubleArg(0.1, 10.0))
                                    .executes(context -> {
                                        double value = DoubleArgumentType.getDouble(context, "val");
                                        ClientEvents.setWidthMultiplier(value);
                                        context.getSource().sendFeedback(Component.literal("§aWidth multiplier set to " + value + "x"));
                                        return 1;
                                    })
                            )
                    )
                    .then(ClientCommands.literal("height")
                            .then(ClientCommands.argument("val", DoubleArgumentType.doubleArg(0.1, 10.0))
                                    .executes(context -> {
                                        double value = DoubleArgumentType.getDouble(context, "val");
                                        ClientEvents.setHeightMultiplier(value);
                                        context.getSource().sendFeedback(Component.literal("§aHeight multiplier set to " + value + "x"));
                                        return 1;
                                    })
                            )
                    )
                    .then(ClientCommands.literal("reset")
                            .executes(context -> {
                                ClientEvents.reset();
                                context.getSource().sendFeedback(Component.literal("§eHitbox expansion reset and disabled."));
                                return 1;
                            })
                    )
                    .then(ClientCommands.literal("get")
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