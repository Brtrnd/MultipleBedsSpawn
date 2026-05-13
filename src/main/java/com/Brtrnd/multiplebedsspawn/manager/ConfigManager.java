package com.Brtrnd.multiplebedsspawn.manager;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

/**
 * Handles config values and permissions.
 */
public class ConfigManager {

    private final Plugin plugin;

    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public int getDefaultBedCount() {
        return plugin.getConfig().getInt("default-bed-count", 4);
    }

    /**
     * Supports permissions like:
     * MultipleBedsSpawn.beds.6
     */
    public int getBedCount(UUID uuid) {

        Player player = plugin.getServer().getPlayer(uuid);
        if (player == null) return getDefaultBedCount();

        for (int i = 20; i >= 1; i--) {
            if (player.hasPermission("MultipleBedsSpawn.beds." + i)) {
                return i;
            }
        }

        return getDefaultBedCount();
    }
    /**
     * Whether debug mode is enabled.
     */
    public boolean isDebug() {
        return plugin.getConfig().getBoolean("debug", false);
    }
}