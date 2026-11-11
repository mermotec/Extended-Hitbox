package com.extendedhitbox.extendedhitbox.client;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Registers client-side commands on the Forge event bus.
 * Forge's RegisterClientCommandsEvent is the direct equivalent of
 * Fabric's ClientCommandRegistrationCallback.
 */
@OnlyIn(Dist.CLIENT)
public class ModCommands {

    @SubscribeEvent
    public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
        var dispatcher = event.getDispatcher();

        dispatcher.register(
                net.minecraft.commands.Commands.literal("hitbox")

                        // /hitbox set <multiplier>
                        .then(net.minecraft.commands.Commands.literal("set")
                                .then(net.minecraft.commands.Commands.argument("multiplier", DoubleArgumentType.doubleArg(0.1, 10.0))
                                        .executes(ctx -> {
                                            double value = DoubleArgumentType.getDouble(ctx, "multiplier");
                                            ClientEvents.setHitboxMultiplier(value);
                                            ctx.getSource().sendSuccess(
                                                    () -> Component.literal("§aHitbox set to " + value + "x"), false);
                                            return 1;
                                        })
                                )
                        )

                        // /hitbox width <val>
                        .then(net.minecraft.commands.Commands.literal("width")
                                .then(net.minecraft.commands.Commands.argument("val", DoubleArgumentType.doubleArg(0.1, 10.0))
                                        .executes(ctx -> {
                                            double value = DoubleArgumentType.getDouble(ctx, "val");
                                            ClientEvents.setWidthMultiplier(value);
                                            ctx.getSource().sendSuccess(
                                                    () -> Component.literal("§aWidth multiplier set to " + value + "x"), false);
                                            return 1;
                                        })
                                )
                        )

                        // /hitbox height <val>
                        .then(net.minecraft.commands.Commands.literal("height")
                                .then(net.minecraft.commands.Commands.argument("val", DoubleArgumentType.doubleArg(0.1, 10.0))
                                        .executes(ctx -> {
                                            double value = DoubleArgumentType.getDouble(ctx, "val");
                                            ClientEvents.setHeightMultiplier(value);
                                            ctx.getSource().sendSuccess(
                                                    () -> Component.literal("§aHeight multiplier set to " + value + "x"), false);
                                            return 1;
                                        })
                                )
                        )

                        // /hitbox reset
                        .then(net.minecraft.commands.Commands.literal("reset")
                                .executes(ctx -> {
                                    ClientEvents.reset();
                                    ctx.getSource().sendSuccess(
                                            () -> Component.literal("§eHitbox expansion reset and disabled."), false);
                                    return 1;
                                })
                        )

                        // /hitbox get
                        .then(net.minecraft.commands.Commands.literal("get")
                                .executes(ctx -> {
                                    double w = ClientEvents.getWidthMultiplier();
                                    double h = ClientEvents.getHeightMultiplier();
                                    boolean enabled = ClientEvents.isHitboxExpanded();
                                    String status = enabled ? "§a(ENABLED)" : "§c(DISABLED)";
                                    ctx.getSource().sendSuccess(
                                            () -> Component.literal(
                                                    String.format("Hitbox: %s | Width: %.2fx | Height: %.2fx", status, w, h)),
                                            false);
                                    return 1;
                                })
                        )
        );
    }
}