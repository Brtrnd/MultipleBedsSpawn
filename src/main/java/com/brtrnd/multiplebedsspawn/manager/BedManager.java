package com.brtrnd.multiplebedsspawn.manager;

import com.brtrnd.multiplebedsspawn.storage.BedStorage;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Bed;

import java.util.*;

/**
 * Stores and validates player bed history.
 */
public class BedManager {

    private final ConfigManager config;
    private final BedStorage storage;

    // UUID -> last beds (newest first)
    private final Map<UUID, Deque<Location>> beds = new HashMap<>();

    public BedManager(ConfigManager config, BedStorage storage) {
        this.config = config;
        this.storage = storage;
    }

    /**
     * Adds a new bed for player
     */
    public void addBed(UUID playerId, Location loc) {
        beds.putIfAbsent(playerId, new ArrayDeque<>());

        Deque<Location> list = beds.get(playerId);

        // Remove duplicate if exists
        list.remove(loc);

        // Add newest first
        list.addFirst(loc);

        // Enforce max beds
        int maxBeds = config.getMaxBeds(playerId); // assuming method
        while (list.size() > maxBeds) {
            list.removeLast();
        }
    }

    /**
     * Returns first valid respawn bed
     */
    public Location getValidRespawn(UUID playerId) {
        Deque<Location> list = beds.get(playerId);
        if (list == null) return null;

        Iterator<Location> iterator = list.iterator();

        while (iterator.hasNext()) {
            Location loc = iterator.next();

            if (isValidBed(loc)) {
                return loc;
            } else {
                // Clean invalid bed
                iterator.remove();
            }
        }
        return null;
    }

    /**
     * Validates if block is still a bed
     */
    private boolean isValidBed(Location loc) {
        Block block = loc.getBlock();
        return block.getBlockData() instanceof Bed;
    }

    public Map<UUID, Deque<Location>> getAllBeds() {
        return beds;
    }

    public void load(Map<UUID, Deque<Location>> data) {
        beds.clear();
        beds.putAll(data);
    }
}
