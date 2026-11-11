package com.extendedhitbox.extendedhitbox;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue SHOW_COMMAND_FEEDBACK;

    static {
        BUILDER.push("General Settings");

        SHOW_COMMAND_FEEDBACK = BUILDER
                .comment("Show command feedback messages in chat (does not affect /hitbox get)")
                .define("showCommandFeedback", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    @SuppressWarnings("deprecation")
    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SPEC, "extendedhitbox-client.toml");
    }

    public static boolean shouldShowFeedback() {
        return SHOW_COMMAND_FEEDBACK.get();
    }
}
