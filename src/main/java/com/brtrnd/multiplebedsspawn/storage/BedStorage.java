package com.brtrnd.multiplebedsspawn.storage;

import com.brtrnd.multiplebedsspawn.MultipleBedsSpawn;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Saves bed data in beds.yml.
 */
public class BedStorage {

    private final MultipleBedsSpawn plugin;
    private final File file;
    private final YamlConfiguration config;

    public BedStorage(MultipleBedsSpawn plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "beds.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            plugin.getBedManager().getAllBeds().forEach((uuid, list) -> {

                List<String> serialized = new ArrayList<>();

                for (Location loc : list) {
                    serialized.add(serialize(loc));
                }

                config.set(uuid.toString(), serialized);
            });

            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        Map<UUID, Deque<Location>> data = new HashMap<>();

        for (String key : config.getKeys(false)) {

            UUID uuid = UUID.fromString(key);
            List<String> list = config.getStringList(key);

            Deque<Location> locations = new ArrayDeque<>();

            for (String s : list) {
                locations.add(deserialize(s));
            }

            data.put(uuid, locations);
        }

        plugin.getBedManager().load(data);
    }

    private String serialize(Location loc) {
        return loc.getWorld().getName() + ":" +
                loc.getBlockX() + ":" +
                loc.getBlockY() + ":" +
                loc.getBlockZ();
    }

    private Location deserialize(String s) {
        String[] p = s.split(":");

        World w = Bukkit.getWorld(p[0]);

        return new Location(w,
                Integer.parseInt(p[1]),
                Integer.parseInt(p[2]),
                Integer.parseInt(p[3]));
    }
}