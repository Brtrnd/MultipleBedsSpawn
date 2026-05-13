package com.Brtrnd.multiplebedsspawn.listener;

import com.Brtrnd.multiplebedsspawn.manager.BedManager;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import com.Brtrnd.multiplebedsspawn.util.DebugLogger;

/**
 * Overrides respawn logic.
 */
public class RespawnListener implements Listener {

    private final BedManager bedManager;

    public RespawnListener(BedManager bedManager,
                           com.Brtrnd.multiplebedsspawn.manager.ConfigManager config) {
        this.bedManager = bedManager;
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {

        if (!event.getPlayer().hasPermission("MultipleBedsSpawn")) return;
                
        DebugLogger.log("Respawn check for " + event.getPlayer().getName());

        if (loc != null) {
            DebugLogger.log("Respawning at " + loc);
        } else {
            DebugLogger.log("No valid beds found, using world spawn");
        }

        Location loc = bedManager.getValidRespawn(
                event.getPlayer().getUniqueId()
        );

        if (loc != null) {
            event.setRespawnLocation(loc);
        }
    }
}
``