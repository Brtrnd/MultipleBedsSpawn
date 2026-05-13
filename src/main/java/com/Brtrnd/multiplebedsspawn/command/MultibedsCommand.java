package com.Brtrnd.multiplebedsspawn.command;

import com.Brtrnd.multiplebedsspawn.manager.BedManager;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Deque;

/**
 * Command: /multibeds list
 */
public class MultibedsCommand implements CommandExecutor {

    private final BedManager bedManager;

    public MultibedsCommand(BedManager bedManager) {
        this.bedManager = bedManager;
    }

    @Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String label,
                             String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Players only.");
            return true;
        }

        if (!player.hasPermission("MultipleBedsSpawn.command.list")) {
            player.sendMessage("No permission.");
            return true;
        }

        if (args.length == 0 || !args[0].equalsIgnoreCase("list")) {
            player.sendMessage("/multibeds list");
            return true;
        }

        Deque<Location> beds = bedManager
                .getAllBeds()
                .get(player.getUniqueId());

        if (beds == null || beds.isEmpty()) {
            player.sendMessage("No beds stored.");
            return true;
        }

        player.sendMessage("Your beds:");

        int i = 1;
        for (Location loc : beds) {
            player.sendMessage(i + ": "
                    + loc.getWorld().getName() + " "
                    + loc.getBlockX() + " "
                    + loc.getBlockY() + " "
                    + loc.getBlockZ());
            i++;
        }

        return true;
    }
}
