package com.extendedhitbox.extendedhitbox.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ChatCommandHandler {

    public static void register() {
        // We use ALLOW_COMMAND to intercept slash commands directly.
        // This avoids using the banned "ClientCommandRegistrationCallback".
        net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents.ALLOW_COMMAND.register((String command) -> {

            // The 'command' string comes without the slash (e.g. "hitbox set 2.0")
            if (command.startsWith("hitbox")) {
                handleCommand(command);
                return false; // Return false to STOP the command from going to the server
            }
            return true; // Return true to let other commands (like /gamemode) pass through
        });
    }

    private static void handleCommand(String command) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;

        String[] parts = command.split(" ");

        // Command: /hitbox set <value>
        if (parts.length >= 3 && parts[1].equalsIgnoreCase("set")) {
            try {
                double value = Double.parseDouble(parts[2]);
                if (value < 0.1 || value > 5.0) {
                    minecraft.player.displayClientMessage(Component.literal("§cValue must be between 0.1 and 5.0"), false);
                } else {
                    ClientEvents.setHitboxMultiplier(value);
                    minecraft.player.displayClientMessage(Component.literal("§aHitbox multiplier set to: " + value + "x"), false);
                }
            } catch (NumberFormatException e) {
                minecraft.player.displayClientMessage(Component.literal("§cInvalid number format."), false);
            }
        }
        // Command: /hitbox get
        else if (parts.length >= 2 && parts[1].equalsIgnoreCase("get")) {
            double current = ClientEvents.getHitboxMultiplier();
            boolean enabled = ClientEvents.isHitboxExpanded();
            String status = enabled ? "§a(ENABLED)" : "§c(DISABLED)";
            minecraft.player.displayClientMessage(Component.literal("Hitbox multiplier: " + current + "x " + status), false);
        }
        // Command: /hitbox (Help)
        else {
            minecraft.player.displayClientMessage(Component.literal("§eUsage: /hitbox set <value> | /hitbox get"), false);
        }
    }
}