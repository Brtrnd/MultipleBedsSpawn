package com.Brtrnd.multiplebedsspawn;

import com.Brtrnd.multiplebedsspawn.listener.BedEnterListener;
import com.Brtrnd.multiplebedsspawn.listener.RespawnListener;
import com.Brtrnd.multiplebedsspawn.manager.BedManager;
import com.Brtrnd.multiplebedsspawn.manager.ConfigManager;
import com.Brtrnd.multiplebedsspawn.storage.BedStorage;
import com.Brtrnd.multiplebedsspawn.command.MultibedsCommand;
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

        saveDefaultConfig();

        configManager = new ConfigManager(this);
        storage = new BedStorage(this);
        bedManager = new BedManager(configManager, storage);

        storage.load();

        getServer().getPluginManager().registerEvents(
                new BedEnterListener(bedManager), this
        );

        getServer().getPluginManager().registerEvents(
                new RespawnListener(bedManager, configManager), this
        );
                
        getCommand("multibeds").setExecutor(
                new MultibedsCommand(bedManager)
        );

    }

    @Override
    public void onDisable() {
        storage.save();
    }

    public static MultipleBedsSpawn getInstance() {
        return instance;
    }

    public BedManager getBedManager() {
        return bedManager;
    }
}
``