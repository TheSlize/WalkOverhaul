package com.walkoverhaul.configs;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class CommonConfig {
    public static int createConfigInt(Configuration config, String category, String name, String comment, int def) {

        Property prop = config.get(category, name, def);
        prop.setComment(comment);
        return prop.getInt();
    }

    public static int walkMultiplier = 50;
    public static int sprintMultiplier = 80;
    public static int staminaCount = 80;
    public static int recoveryTime = 20;
    public static void loadFromConfig(Configuration config) {
        walkMultiplier = createConfigInt(config, "General", "walkMultiplier", "Walk speed multiplier in percents (default - 50%)", 50);
        sprintMultiplier = createConfigInt(config, "General", "sprintMultiplier", "Sprint speed multiplier in percents (default - 80%)", 80);
        staminaCount = createConfigInt(config, "General", "staminaCount", "This is the maximum amount of time which you can sprint for (default - 4 seconds) ", 80);
        recoveryTime = createConfigInt(config, "General", "recoveryTime", "For how long your character needs to recover before you begin to fill the stamina again (default - 1 second)", 20);
    }


}
