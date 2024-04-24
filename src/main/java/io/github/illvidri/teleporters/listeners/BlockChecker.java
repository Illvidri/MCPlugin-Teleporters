package io.github.illvidri.teleporters.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.joml.Vector3d;
import org.bukkit.block.Block;

public class BlockChecker implements Listener {
    final int CoordinateSize = 6;
    String[] teleporterArrangement = {
            "LODESTONE",
            "REDSTONE_BLOCK",
            "PISTON",
            "PISTON_HEAD",
            "HOPPER",
            "CRYING_OBSIDIAN",
            "CUT_COPPER_SLAB"
    };

    enum elevatorPowerSources {
        @SuppressWarnings({"unused"})
        LAVA_CAULDRON,
        @SuppressWarnings({"unused"})
        REDSTONE_BLOCK,
        @SuppressWarnings({"unused"})
        REDSTONE_TORCH,
        @SuppressWarnings({"unused"})
        REDSTONE_LAMP,
        @SuppressWarnings({"unused"})
        COPPER_BULB,
        @SuppressWarnings({"unused"})
        WAXED_COPPER_BULB;
        public static boolean contains(String val) {
            try {
                lights.valueOf(val);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    enum lights {
        @SuppressWarnings({"unused"})
        BEACON,
        @SuppressWarnings({"unused"})
        SEA_LANTERN,
        @SuppressWarnings({"unused"})
        GLOWSTONE,
        @SuppressWarnings({"unused"})
        REDSTONE_LAMP,
        @SuppressWarnings({"unused"})
        SHROOMLIGHT,
        @SuppressWarnings({"unused"})
        OCHRE_FROGLIGHT,
        @SuppressWarnings({"unused"})
        VERDANT_FROGLIGHT,
        @SuppressWarnings({"unused"})
        PEARLESCENT_FROGLIGHT,
        @SuppressWarnings({"unused"})
        COPPER_BULB,
        @SuppressWarnings({"unused"})
        WAXED_COPPER_BULB;
        public static boolean contains(String val) {
            try {
                lights.valueOf(val);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    enum hexBlockEncoder {
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
        GLASS("F");

        final String val;
        hexBlockEncoder(String val) {
            this.val = val;
        }
    }

    enum TeleporterType {LARGE, ELEVATOR};

    @EventHandler
    public void playerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        TeleportChecker(player);
    }

    @EventHandler
    public void playerClick(PlayerInteractEvent event) {
        if(event.getAction()==Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();
            if(block.getBlockData() instanceof org.bukkit.block.data.Powerable) {
                player.sendMessage(block.getType().toString());
            }
            TeleportChecker(player, event.getClickedBlock(), event);
        }
    }

    public void TeleportChecker(Player player) {
        Location loc = player.getLocation();

        boolean isTeleporter = CoreChecker(loc);
        boolean isElevator = ElevatorChecker(loc);


        if((isTeleporter || isElevator) && LightChecker(loc)) {
            // Put activation conditions here:
            try {
                //player.sendMessage("Teleported"); // Teleportation Debugging
                Vector3d tppos = new Vector3d(0,0,0);
                if(isTeleporter) {
                    tppos = BlockCalculator(player, true);
                } else if(isElevator) {

                }
                player.teleport(new Location(player.getWorld(), tppos.x, tppos.y, tppos.z,
                        player.getLocation().getYaw(),
                        player.getLocation().getPitch())
                );
            } catch(RuntimeException ignored) {}
        }
        else {
            // Put random debugging nonsense here:
            loc = player.getLocation();
            loc.setY(loc.getY()-1);
            //player.sendMessage(hexBlockEncoder.valueOf("AIR").toString());
        }
    }

    public void TeleportChecker(Player player, Block clickedblock, PlayerInteractEvent event) {
        Location loc = player.getLocation();
        Block lightblock = new Location(loc.getWorld(), loc.getX(), loc.getY()-1, loc.getZ()).getBlock();

        if((CoreChecker(loc) || ElevatorChecker(loc)) && LightChecker(loc) && lightblock.getType().equals(clickedblock.getType())) {
            // Put activation conditions here:
            try {
                event.setCancelled(true);
                Vector3d tppos = BlockCalculator(player, false);
                //player.sendMessage("Teleported"); // Teleportation Debugging
                player.teleport(new Location(player.getWorld(), tppos.x, tppos.y, tppos.z,
                        player.getLocation().getYaw(),
                        player.getLocation().getPitch())
                );
            } catch(RuntimeException ignored) {}
        } else {
            // Put random debugging nonsense here:
            loc = player.getLocation();
            loc.setY(loc.getY()-1);
            //player.sendMessage(hexBlockEncoder.valueOf("AIR").toString());
        }
    }

    boolean CoreChecker(Location loc) {
        int checkCount = 0;
        Location temp = new Location(loc.getWorld(),loc.getX(),loc.getY()-1,loc.getZ());
        for (String s : teleporterArrangement) {
            temp.setY(temp.getY() - 1); // Check the next block down
            Block block = temp.getBlock();
            String blockName = block.getType().toString();

            if (blockName.equals(s)) checkCount++; // Count if the block matches the corresponding spot in the list

        }
        return checkCount == teleporterArrangement.length;
    }

    boolean ElevatorChecker(Location loc) {
        Location temp = new Location(loc.getWorld(),loc.getX(),loc.getY()-2,loc.getZ());

        return false;
    }

    boolean LightChecker(Location loc) {
        Location temp = new Location(loc.getWorld(),loc.getX(),loc.getY()-1,loc.getZ());
        Block block = temp.getBlock();
        return block.getLightLevel() == 15 && lights.contains(block.getType().toString()); // Count if the light block is of the above
    }

    public Vector3d BlockCalculator(Player player, boolean forced) { // This method will calculate the target coordinates given by the teleporter construction
        final Location playerloc = player.getLocation();

        double xcoord = 0;
        double ycoord = 0;
        double zcoord = 0;

        StringBuilder settingsval, xenccoord, yenccoord, zenccoord;
        settingsval = new StringBuilder();
        xenccoord = new StringBuilder();
        yenccoord = new StringBuilder();
        zenccoord = new StringBuilder();

        fillEncodedString(new Location(playerloc.getWorld(), playerloc.getX(), playerloc.getY()-1, playerloc.getZ()-1), settingsval, false);
        fillEncodedString(new Location(playerloc.getWorld(), playerloc.getX()+1, playerloc.getY()-1, playerloc.getZ()), xenccoord, true);
        fillEncodedString(new Location(playerloc.getWorld(), playerloc.getX(), playerloc.getY()-1, playerloc.getZ()+1), yenccoord, true);
        fillEncodedString(new Location(playerloc.getWorld(), playerloc.getX()-1, playerloc.getY()-1, playerloc.getZ()), zenccoord, true);

        settingsval.reverse();
        if(!settingsval.substring(5,6).equals("F")) {
            throw new RuntimeException("Teleporter not active");
        }

        if(settingsval.substring(0,1).equals("0")) xcoord = -1;
        else if (settingsval.substring(0,1).equals("F")) xcoord = 1;

        if(settingsval.substring(1,2).equals("0")) ycoord = -1;
        else if (settingsval.substring(1,2).equals("F")) ycoord = 1;

        if(settingsval.substring(2,3).equals("0")) zcoord = -1;
        else if (settingsval.substring(2,3).equals("F")) zcoord = 1;

        if(!forced && !settingsval.substring(3,4).equals("0")) throw new RuntimeException("Not a Forced Teleport");
        else if (forced && !settingsval.substring(3,4).equals("F")) throw new RuntimeException("A Forced Teleport");


        try {
            xcoord *= Integer.parseInt(xenccoord.toString(), 16);
            ycoord *= Integer.parseInt(yenccoord.toString(), 16);
            zcoord *= Integer.parseInt(zenccoord.toString(), 16);
        } catch(Exception ignored) {}

        if(settingsval.substring(4,5).equals("F")) {
            xcoord += playerloc.getX();
            ycoord += playerloc.getY();
            zcoord += playerloc.getZ();
        } else if (!settingsval.substring(4,5).equals("0")) {
            xcoord = 0;
            ycoord = 0;
            zcoord = 0;
        }

        //player.sendMessage("("+xcoord+", "+ycoord+", "+zcoord+")"); // Coordinate Debugging
        return new Vector3d(xcoord,ycoord,zcoord);
    }

    void fillEncodedString(Location loc, StringBuilder target, boolean haltOnFail) {
        for(int i = 0; i < CoordinateSize; i++) {
            loc.setY(loc.getY()-1);
            try {
                target.insert(0, hexBlockEncoder.valueOf(loc.getBlock().getType().toString()).val);
            } catch (Exception e) {
                if(haltOnFail) throw new RuntimeException("Invalid Block Detected");
                target.append("-");
            }
        }
    }
}
