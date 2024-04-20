package io.github.illvidri.teleporters.listeners;

import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
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

    final int CoordinateSize = 6;

    enum hexblockencoder {
        @SuppressWarnings({"unused"})
        TINTED_GLASS("0"),
        @SuppressWarnings({"unused"})
        BLACK_STAINED_GLASS("1"),
        @SuppressWarnings({"unused"})
        GRAY_STAINED_GLASS("2"),
        @SuppressWarnings({"unused"})
        LIGHT_GRAY_STAINED_GLASS("3"),
        @SuppressWarnings({"unused"})
        WHITE_STAINED_GLASS("4"),
        @SuppressWarnings({"unused"})
        PURPLE_STAINED_GLASS("5"),
        @SuppressWarnings({"unused"})
        MAGENTA_STAINED_GLASS("6"),
        @SuppressWarnings({"unused"})
        BLUE_STAINED_GLASS("7"),
        @SuppressWarnings({"unused"})
        LIGHT_BLUE_STAINED_GLASS("8"),
        @SuppressWarnings({"unused"})
        CYAN_STAINED_GLASS("9"),
        @SuppressWarnings({"unused"})
        GREEN_STAINED_GLASS("A"),
        @SuppressWarnings({"unused"})
        LIME_STAINED_GLASS("B"),
        @SuppressWarnings({"unused"})
        YELLOW_STAINED_GLASS("C"),
        @SuppressWarnings({"unused"})
        ORANGE_STAINED_GLASS("D"),
        @SuppressWarnings({"unused"})
        RED_STAINED_GLASS("E"),
        @SuppressWarnings({"unused"})
        GLASS("F")

        ;final String val;
        hexblockencoder(String val) {
            this.val = val;
        }
    }



    @EventHandler
    public void playerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        TeleportChecker(player);
    }

    public void TeleportChecker(Player player) {
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
            try {
                Vector3d teleportpos = BlockCalculator(player);
                //player.sendMessage("Teleported"); // Teleportation Debugging
                player.teleport(new Location(player.getWorld(),
                        teleportpos.x,
                        teleportpos.y,
                        teleportpos.z,
                        player.getLocation().getYaw(),
                        player.getLocation().getPitch())
                );
            }
            catch(RuntimeException ignored) {}
        }
        else {
            // Put random debugging nonsense here:
            loc = player.getLocation();
            loc.setY(loc.getY()-1);
            //player.sendMessage(hexblockencoder.valueOf("AIR").toString());
        }
    }

    public Vector3d BlockCalculator(Player player) { // This method will calculate the target coordinates given by the teleporter construction
        final Location playerloc = player.getLocation();
        Location temp;

        double xcoord = 0;
        double ycoord = 0;
        double zcoord = 0;
        StringBuilder xenccoord, yenccoord, zenccoord, settingsval;
        xenccoord = new StringBuilder();
        yenccoord = new StringBuilder();
        zenccoord = new StringBuilder();
        settingsval = new StringBuilder();

        temp = new Location(playerloc.getWorld(), playerloc.getX(), playerloc.getY()-1, playerloc.getZ()-1);
        for(int i = 0; i < CoordinateSize; i++) {
            temp.setY(temp.getY()-1);
            try {
                settingsval.append(hexblockencoder.valueOf(temp.getBlock().getType().toString()).val);
            }
            catch (Exception e) {settingsval.append("-");}
        }

        fillencodedstring(new Location(playerloc.getWorld(), playerloc.getX()+1, playerloc.getY()-1, playerloc.getZ()), xenccoord);
        fillencodedstring(new Location(playerloc.getWorld(), playerloc.getX(), playerloc.getY()-1, playerloc.getZ()+1), yenccoord);
        fillencodedstring(new Location(playerloc.getWorld(), playerloc.getX()-1, playerloc.getY()-1, playerloc.getZ()), zenccoord);

        if(settingsval.substring(0,1).equals("0")) {
            xcoord = -1;
        } else if (settingsval.substring(0,1).equals("F")) {
            xcoord = 1;
        }

        if(settingsval.substring(1,2).equals("0")) {
            ycoord = -1;
        } else if (settingsval.substring(1,2).equals("F")) {
            ycoord = 1;
        }

        if(settingsval.substring(2,3).equals("0")) {
            zcoord = -1;
        } else if (settingsval.substring(2,3).equals("F")) {
            zcoord = 1;
        }

        try {
            xcoord *= Integer.parseInt(xenccoord.toString(), 16);
            ycoord *= Integer.parseInt(yenccoord.toString(), 16);
            zcoord *= Integer.parseInt(zenccoord.toString(), 16);
        }
        catch(Exception ignored) {}

        if(settingsval.substring(4,5).equals("F")) {
            xcoord += playerloc.getX();
            ycoord += playerloc.getY();
            zcoord += playerloc.getZ();
        } else if (!settingsval.substring(4,5).equals("0")) {
            xcoord = 0;
            ycoord = 0;
            zcoord = 0;
        }

        if(!settingsval.substring(5,6).equals("F")) {
            throw new RuntimeException("Teleporter not active");
        }

        //player.sendMessage("("+xcoord+", "+ycoord+", "+zcoord+")"); // Coordinate Debugging
        return new Vector3d(xcoord,ycoord,zcoord);
    }

    void fillencodedstring(Location loc, StringBuilder target) {
        for(int i = 0; i < CoordinateSize; i++) {
            loc.setY(loc.getY()-1);
            try {
                target.insert(0, hexblockencoder.valueOf(loc.getBlock().getType().toString()).val);
            }
            catch (Exception e) { throw new RuntimeException("Invalid Block Detected"); }
        }
    }
}
