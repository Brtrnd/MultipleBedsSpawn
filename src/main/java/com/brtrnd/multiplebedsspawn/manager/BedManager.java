package com.brtrnd.multiplebedsspawn.manager;

import com.brtrnd.multiplebedsspawn.storage.BedStorage;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Bed;
import com.brtrnd.multiplebedsspawn.util.DebugLogger;

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

    public void addBed(UUID playerId, Location loc) {
        beds.putIfAbsent(playerId, new ArrayDeque<>());

        Deque<Location> list = beds.get(playerId);

        int maxBeds = config.getBedCount(playerId);

        // Avoid duplicates
        list.removeIf(l -> l.equals(loc));

        list.addFirst(loc);
        DebugLogger.log("Adding bed for " + playerId + " at " + loc);

        // Trim list
        while (list.size() > maxBeds) {
            DebugLogger.log("Invalid bed removed for " + playerId + " at " + loc);
            list.removeLast();
        }
    }

    public Location getValidRespawn(UUID playerId) {
        Deque<Location> list = beds.get(playerId);
        if (list == null) return null;

        Iterator<Location> it = list.iterator();

        while (it.hasNext()) {
            Location loc = it.next();

            if (isValidBed(loc)) {
                return loc;
            } else {
                it.remove(); // cleanup broken beds
                DebugLogger.log("Invalid bed removed for " + playerId + " at " + loc);
            }
        }

        return null;
    }

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