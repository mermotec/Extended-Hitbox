package com.extendedhitbox.extendedhitbox.client;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.extendedhitbox.extendedhitbox.ExtendedHitbox.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class HitboxCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterClientCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("hitbox")
                .then(Commands.literal("set")
                        .then(Commands.argument("multiplier", DoubleArgumentType.doubleArg(0.1, 5.0))
                                .executes(context -> {
                                    double multiplier = DoubleArgumentType.getDouble(context, "multiplier");
                                    ClientEvents.ClientForgeEvents.setHitboxMultiplier(multiplier);
                                    context.getSource().sendSuccess(() -> Component.literal("Hitbox multiplier set to: " + multiplier + "x"), false);
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("get")
                        .executes(context -> {
                            double current = ClientEvents.ClientForgeEvents.getHitboxMultiplier();
                            boolean enabled = ClientEvents.ClientForgeEvents.isHitboxExpanded();

                            if (enabled) {
                                context.getSource().sendSuccess(() -> Component.literal("Hitbox multiplier: " + current + "x (ENABLED)"), false);
                            } else {
                                context.getSource().sendSuccess(() -> Component.literal("Hitbox multiplier: " + current + "x (DISABLED)"), false);
                            }
                            return 1;
                        })
                )
        );
    }
}
