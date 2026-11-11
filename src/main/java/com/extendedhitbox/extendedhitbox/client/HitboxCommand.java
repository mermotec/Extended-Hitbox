package com.extendedhitbox.extendedhitbox.client;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;

public class HitboxCommand {
    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("hitbox")
                .then(ClientCommandManager.literal("set")
                    .then(ClientCommandManager.argument("multiplier", DoubleArgumentType.doubleArg(0.1, 5.0))
                        .executes(context -> {
                            double multiplier = DoubleArgumentType.getDouble(context, "multiplier");
                            ClientEvents.setHitboxMultiplier(multiplier);
                            context.getSource().sendFeedback(Text.literal("Hitbox multiplier set to: " + multiplier + "x"));
                            return 1;
                        })
                    )
                )
                .then(ClientCommandManager.literal("get")
                    .executes(context -> {
                        double current = ClientEvents.getHitboxMultiplier();
                        boolean enabled = ClientEvents.isHitboxExpanded();
                        if (enabled) {
                            context.getSource().sendFeedback(Text.literal("Hitbox multiplier: " + current + "x (ENABLED)"));
                        } else {
                            context.getSource().sendFeedback(Text.literal("Hitbox multiplier: " + current + "x (DISABLED)"));
                        }
                        return 1;
                    })
                )
            );
        });
    }
}
