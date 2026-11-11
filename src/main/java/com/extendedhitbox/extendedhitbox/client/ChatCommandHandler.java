package com.extendedhitbox.extendedhitbox.client;

import com.extendedhitbox.extendedhitbox.ExtendedHitboxClient;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

// ✅ NeoForge imports
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientChatEvent;

// ✅ Auto-registers on the game bus, client-only
@EventBusSubscriber(modid = ExtendedHitboxClient.MOD_ID, value = Dist.CLIENT)
public class ChatCommandHandler {

    // ✅ REPLACES: ClientSendMessageEvents.ALLOW_COMMAND.register(...)
    @SubscribeEvent
    public static void onClientChat(ClientChatEvent event) {
        String message = event.getMessage(); // comes WITHOUT the slash

        if (message.startsWith("hitbox")) {
            handleCommand(message);
            event.setCanceled(true); // ✅ REPLACES: return false — stops message reaching server
        }
        // If not "hitbox", event is NOT cancelled — other messages pass through normally
    }

    // ✅ ZERO CHANGES — pure Java + Minecraft API, identical in both loaders
    private static void handleCommand(String command) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;

        String[] parts = command.split(" ");

        // Command: hitbox set <value>
        if (parts.length >= 3 && parts[1].equalsIgnoreCase("set")) {
            try {
                double value = Double.parseDouble(parts[2]);
                if (value < 0.1 || value > 5.0) {
                    minecraft.player.displayClientMessage(
                            Component.literal("§cValue must be between 0.1 and 5.0"), false);
                } else {
                    ClientEvents.setHitboxMultiplier(value);
                    minecraft.player.displayClientMessage(
                            Component.literal("§aHitbox multiplier set to: " + value + "x"), false);
                }
            } catch (NumberFormatException e) {
                minecraft.player.displayClientMessage(
                        Component.literal("§cInvalid number format."), false);
            }

            // Command: hitbox get
        } else if (parts.length >= 2 && parts[1].equalsIgnoreCase("get")) {
            double current = ClientEvents.getHitboxMultiplier();
            boolean enabled = ClientEvents.isHitboxExpanded();
            String status = enabled ? "§a(ENABLED)" : "§c(DISABLED)";
            minecraft.player.displayClientMessage(
                    Component.literal("Hitbox multiplier: " + current + "x " + status), false);

            // Fallback: show help
        } else {
            minecraft.player.displayClientMessage(
                    Component.literal("§eUsage: /hitbox set | /hitbox get"), false);
        }
    }
}
