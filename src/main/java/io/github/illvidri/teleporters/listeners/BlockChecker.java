package io.github.illvidri.teleporters.listeners;

import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.bukkit.block.Block;


public class BlockChecker implements Listener {
    String[] teleporterArrangement = {
            "LIGHT",
            "LODESTONE",
            "REDSTONE_BLOCK",
            "PISTON",
            "PISTON_HEAD",
            "HOPPER",
            "CRYING_OBSIDIAN",
            "CUT_COPPER_SLAB"
    };

    @EventHandler
    public void validTeleportChecker(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getLocation();

        int checkCount = 0;
        for (String s : teleporterArrangement) {
            loc.setY(loc.getY() - 1); // Check the next block down
            Block block = loc.getBlock();
            String blockname = block.getType().toString();

            if (blockname.equals(s)) checkCount++; // Count if the block matches the corresponding spot in the list
            else if (s.equals("LIGHT")) {
                if (block.getLightLevel() == 15
                        && (blockname.equals("BEACON")
                        || blockname.equals("SEA_LANTERN")
                        || blockname.equals("GLOWSTONE")
                        || blockname.equals("REDSTONE_LAMP")
                        || blockname.equals("SHROOMLIGHT")
                        || blockname.equals("OCHRE_FROGLIGHT")
                        || blockname.equals("VERDANT_FROGLIGHT")
                        || blockname.equals("PEARLESCENT_FROGLIGHT")
                        || blockname.equals("COPPER_BULB"))
                ) checkCount++; // Count if the light block is of the above
            }
        }

        if(checkCount == teleporterArrangement.length) {
            // Put activation conditions here:
            // player.sendMessage("Valid Teleporter");
            System.out.println();
            player.teleport(new Location(player.getWorld(),
                    BlockCalculator(player).x,
                    BlockCalculator(player).y,
                    BlockCalculator(player).z,
                    player.getLocation().getYaw(),
                    player.getLocation().getPitch())
            );
        }
        else {
            // Put random debugging nonsense here:
            loc = player.getLocation();
            loc.setY(loc.getY()-1);
            //player.sendMessage(loc.getBlock().getBlockData().toString());
        }
    }

    public Vector3d BlockCalculator(Player player) { // This method will calculate the target coordinates given by the teleporter construction
        final Location playerloc = player.getLocation();
        Location blockloc = new Location(playerloc.getWorld(), playerloc.getX(), playerloc.getY()-1, playerloc.getZ());
        Location temp = blockloc;

        double xcoord = 0;
        double ycoord = 0;
        double zcoord = 0;

        String[] NorthBlocks = new String[6];
        temp.setZ(blockloc.getZ()-1);
        for(int i = 0; i < 6; i++) {
            temp.setY(temp.getY()-1);
            NorthBlocks[i] = temp.getBlock().getType().toString();
        }
        for(String i : NorthBlocks) {
            player.sendMessage(i);
        }
        player.sendMessage(NorthBlocks.length+"");
        return new Vector3d(playerloc.getX(),77,playerloc.getZ()-5);
    }
}
