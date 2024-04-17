package io.github.illvidri.teleporters.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.entity.Player;
import org.bukkit.Location;

public class BlockChecker implements Listener {
    String teleporterArrangement[] = {
            "ANY",
            "LODESTONE",
            "REDSTONE_BLOCK",
            "PISTON",
            "PISTON_HEAD",
            "HOPPER",
            "BEACON",
            "CUT_COPPER_SLAB"
    };
    @EventHandler
    public void playerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Location loc = player.getLocation();
        int checkCount = 0;
        for (String s : teleporterArrangement) {
            loc.setY(loc.getY() - 1);
            if (loc.getBlock().getType().toString().equals(s) || s.equals("ANY")) {
                checkCount++;
            }
        }
        if(checkCount == teleporterArrangement.length) {
            player.sendMessage("Valid Teleporter");
        }
    }
}
