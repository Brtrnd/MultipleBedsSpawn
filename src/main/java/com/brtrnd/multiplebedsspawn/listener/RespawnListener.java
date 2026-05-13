package com.brtrnd.multiplebedsspawn.listener;

import com.brtrnd.multiplebedsspawn.manager.BedManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Overrides respawn logic.
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

        // Get best valid bed
        Location respawn = bedManager.getValidRespawn(player.getUniqueId());

        if (respawn != null) {
            event.setRespawnLocation(respawn);
        }
    }
}
