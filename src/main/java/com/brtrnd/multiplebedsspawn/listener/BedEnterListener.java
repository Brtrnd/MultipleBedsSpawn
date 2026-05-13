package com.brtrnd.multiplebedsspawn.listener;

import com.brtrnd.multiplebedsspawn.manager.BedManager;
import com.brtrnd.multiplebedsspawn.util.DebugLogger;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

/**
 * Detect when player sleeps in a bed.
 * Stores successful bed usage in BedManager.
 */
public class BedEnterListener implements Listener {

    private final BedManager bedManager;

    /**
     * Constructor injecting BedManager dependency
     */
    public BedEnterListener(BedManager bedManager) {
        this.bedManager = bedManager;
    }

    /**
     * Fires when player attempts to enter a bed
     */
    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {

        // Only register successful sleeps
        if (event.getBedEnterResult() != PlayerBedEnterEvent.BedEnterResult.OK) {
            return;
        }

        Player player = event.getPlayer();

        // Store bed location
        Location bedLocation = event.getBed().getLocation();

        bedManager.addBed(player.getUniqueId(), bedLocation);

        // Debug logging
        DebugLogger.log("Stored bed for player: " + player.getName()
                + " at " + serialize(bedLocation));
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
