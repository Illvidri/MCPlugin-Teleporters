package io.github.illvidri.teleporters.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.block.data.Lightable;
import org.joml.Vector3d;


public class BlockChecker implements Listener {
    String[] teleporterArrangement = {
            "LIGHT",
            "LODESTONE",
            "REDSTONE_BLOCK",
            "PISTON",
            "PISTON_HEAD",
            "HOPPER",
            "BEACON",
            "CUT_COPPER_SLAB"
    };

    @EventHandler
    public void validTeleportChecker(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getLocation();

        int checkCount = 0;
        for (String s : teleporterArrangement) {
            loc.setY(loc.getY() - 1); // Check the next block down
            String block = loc.getBlock().getType().toString();

            if (block.equals(s)) checkCount++; // Count if the block matches the corresponding spot in the list
            else if (s.equals("LIGHT")) {
                try { // Try statement is because not all blocks are of the "Lightable" type and may create errors when checking
                    if (block.equals("REDSTONE_LAMP")
                            && ((Lightable) loc.getBlock().getBlockData()).isLit())
                        checkCount++; // Count if the light block is a lit redstone lamp

                    else if (block.equals("BEACON")
                            || block.equals("SEA_LANTERN")
                            || block.equals("GLOWSTONE")
                            || block.equals("SHROOMLIGHT")
                            || block.equals("OCHRE_FROGLIGHT")
                            || block.equals("VERDANT_FROGLIGHT")
                            || block.equals("PEARLESCENT_FROGLIGHT")
                    ) checkCount++; // Count if the light block is any other light block
                }
                catch (Exception ignored) {} // There is no point in returning anything if the block is not a lightable block
            }
        }

        if(checkCount == teleporterArrangement.length) {
            // Put activation conditions here:
            player.sendMessage("Valid Teleporter");
            System.out.println();
            player.teleport(new Location(player.getWorld(),
                    BlockCalculator(player).x,
                    BlockCalculator(player).y,
                    BlockCalculator(player).z)
            );
        }
        else {
            // Put random debugging nonsense here:

        }
    }

    public Vector3d BlockCalculator(Player player) { // This method will calculate the target coordinates given by the teleporter construction
        return new Vector3d(1,2,3);
    }
}
