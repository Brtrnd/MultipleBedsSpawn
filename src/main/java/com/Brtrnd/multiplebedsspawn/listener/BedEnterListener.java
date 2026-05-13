package com.Brtrnd.multiplebedsspawn.listener;

import com.Brtrnd.multiplebedsspawn.manager.BedManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

/**
 * Detect when player sleeps in a bed.
 */
public class BedEnterListener implements Listener {

    private final BedManager bedManager;

    public BedEnterListener(BedManager bedManager) {
        this.bedManager = bedManager;
    }

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {

        if (!event.getPlayer().hasPermission("MultipleBedsSpawn")) return;

        if (event.getBedEnterResult() != PlayerBedEnterEvent.BedEnterResult.OK)
            return;

        bedManager.addBed(
                event.getPlayer().getUniqueId(),
                event.getBed().getLocation()
        );
    }
}