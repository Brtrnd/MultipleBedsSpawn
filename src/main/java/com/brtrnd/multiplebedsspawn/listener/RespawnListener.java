package com.brtrnd.multiplebedsspawn.listener;

import com.brtrnd.multiplebedsspawn.manager.BedManager;
import com.brtrnd.multiplebedsspawn.util.DebugLogger;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Overrides respawn logic.
 * Uses BedManager to find a valid fallback bed.
 */
public class RespawnListener implements Listener {

    private final BedManager bedManager;

    /**
     * Constructor injecting BedManager
     */
    public RespawnListener(BedManager bedManager) {
        this.bedManager = bedManager;
    }

    /**
     * Modify respawn location after death
     */
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {

        Player player = event.getPlayer();

        Location respawn = bedManager.getValidRespawn(player.getUniqueId());

        if (respawn == null) {
            DebugLogger.log("No stored bed for " + player.getName());
            return;
        }

        // Safety: ensure world exists
        World world = respawn.getWorld();
        if (world == null) {
            DebugLogger.log("Invalid world for " + player.getName());
            return;
        }

        // Safety: do NOT force-load chunks (avoids lag spikes)
        Chunk chunk = respawn.getChunk();
        if (!chunk.isLoaded()) {
            DebugLogger.log("Chunk not loaded, skipping respawn override for "
                    + player.getName());
            return;
        }

        // Safe to override respawn
        event.setRespawnLocation(respawn);

        DebugLogger.log("Respawning " + player.getName()
                + " at stored bed: " + serialize(respawn));
    }

    /**
     * Utility method to print readable locations
     */
    private String serialize(Location loc) {
        return loc.getWorld().getName() + " "
                + loc.getBlockX() + ","
                + loc.getBlockY() + ","
                + loc.getBlockZ();
    }
}
