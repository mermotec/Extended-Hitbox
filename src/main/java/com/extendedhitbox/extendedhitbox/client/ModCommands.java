package com.extendedhitbox.extendedhitbox.client;

import com.extendedhitbox.extendedhitbox.ExtendedHitboxClient;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;

// ✅ NeoForge: Use net.minecraft.commands.Commands instead of ClientCommandManager
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

// ✅ NeoForge event imports
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

// ✅ Auto-registers @SubscribeEvent methods — no need to call ModCommands.register() manually
@EventBusSubscriber(modid = ExtendedHitboxClient.MOD_ID, value = Dist.CLIENT)
public class ModCommands {

    // ✅ REPLACES: ClientCommandRegistrationCallback.EVENT.register(...)
    @SubscribeEvent
    public static void onRegisterCommands(RegisterClientCommandsEvent event) {
        register(event.getDispatcher());
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("hitbox")

                // /hitbox set <multiplier>
                .then(Commands.literal("set")
                        .then(Commands.argument("multiplier", DoubleArgumentType.doubleArg(0.1, 10.0))
                                .executes(context -> {
                                    double value = DoubleArgumentType.getDouble(context, "multiplier");
                                    ClientEvents.setHitboxMultiplier(value);
                                    // ✅ CHANGED: sendFeedback(Component) → sendSuccess(() -> Component, false)
                                    context.getSource().sendSuccess(
                                            () -> Component.literal("§aHitbox set to " + value + "x"), false);
                                    return 1;
                                })))

                // /hitbox width <val>
                .then(Commands.literal("width")
                        .then(Commands.argument("val", DoubleArgumentType.doubleArg(0.1, 10.0))
                                .executes(context -> {
                                    double value = DoubleArgumentType.getDouble(context, "val");
                                    ClientEvents.setWidthMultiplier(value);
                                    context.getSource().sendSuccess(
                                            () -> Component.literal("§aWidth multiplier set to " + value + "x"), false);
                                    return 1;
                                })))

                // /hitbox height <val>
                .then(Commands.literal("height")
                        .then(Commands.argument("val", DoubleArgumentType.doubleArg(0.1, 10.0))
                                .executes(context -> {
                                    double value = DoubleArgumentType.getDouble(context, "val");
                                    ClientEvents.setHeightMultiplier(value);
                                    context.getSource().sendSuccess(
                                            () -> Component.literal("§aHeight multiplier set to " + value + "x"), false);
                                    return 1;
                                })))

                // /hitbox reset
                .then(Commands.literal("reset")
                        .executes(context -> {
                            ClientEvents.reset();
                            context.getSource().sendSuccess(
                                    () -> Component.literal("§eHitbox expansion reset and disabled."), false);
                            return 1;
                        }))

                // /hitbox get
                .then(Commands.literal("get")
                        .executes(context -> {
                            double w = ClientEvents.getWidthMultiplier();
                            double h = ClientEvents.getHeightMultiplier();
                            boolean enabled = ClientEvents.isHitboxExpanded();
                            String status = enabled ? "§a(ENABLED)" : "§c(DISABLED)";
                            context.getSource().sendSuccess(
                                    () -> Component.literal(
                                            String.format("Hitbox: %s | Width: %.2fx | Height: %.2fx", status, w, h)),
                                    false);
                            return 1;
                        }))
        );
    }
}
