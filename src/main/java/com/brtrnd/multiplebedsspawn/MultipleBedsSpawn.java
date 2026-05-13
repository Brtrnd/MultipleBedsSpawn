package com.brtrnd.multiplebedsspawn;

import com.brtrnd.multiplebedsspawn.listener.BedEnterListener;
import com.brtrnd.multiplebedsspawn.listener.RespawnListener;
import com.brtrnd.multiplebedsspawn.manager.BedManager;
import com.brtrnd.multiplebedsspawn.manager.ConfigManager;
import com.brtrnd.multiplebedsspawn.storage.BedStorage;
import com.brtrnd.multiplebedsspawn.command.MultibedsCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main entry point for plugin.
 */
public class MultipleBedsSpawn extends JavaPlugin {

    private static MultipleBedsSpawn instance;

    private BedManager bedManager;
    private ConfigManager configManager;
    private BedStorage storage;

    @Override
    public void onEnable() {
        instance = this;

        // Init config
        saveDefaultConfig();
        configManager = new ConfigManager(this);

        // Init storage
        storage = new BedStorage(this);

        // Init manager
        bedManager = new BedManager(configManager, storage);

        // Load stored data
        bedManager.load(storage.load());

        // Register listeners
        getServer().getPluginManager().registerEvents(
                new BedEnterListener(bedManager), this);
        getServer().getPluginManager().registerEvents(
                new RespawnListener(bedManager), this);

        // Register command
        getCommand("multibeds").setExecutor(
                new MultibedsCommand(bedManager));
    }

    @Override
    public void onDisable() {
        if (storage != null) {
            storage.save();
        }
    }

    public static MultipleBedsSpawn getInstance() {
        return instance;
    }

    public BedManager getBedManager() {
        return bedManager;
    }
}
