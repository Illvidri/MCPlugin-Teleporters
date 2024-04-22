# Minecraft Plugin: Teleporters | Latest Version: 0.1.1
### Made by Illvidri

| Table of Contents:                                     |
|--------------------------------------------------------|
| 1. [About](#About)                                     |
| 2. [Teleporter Construction](#Teleporter-Construction) |
| 3. [Compatibilities](#Compatibilities)                 |
| 4. [Future Plans](#Future-Plans)                       |
| 5. [Miscellaneous](#Miscellaneous-Nonsense)            |

## About:
This plugin was adapted from a datapack I had made, since plugins are less resource intensive on the Minecraft client than datapacks and command blocks are.

The coordinates can be input using stained-glass blocks in the correct configuration.

## Teleporter Construction:
(Pictures yet to be added)

The Core:
- light block (Beacon, Sea Lantern, Glowstone, Lit Redstone Lamp, Shroomlight, Any Froglight)
- Lodestone
- Redstone Block
- Downwards Facing Piston
- Hopper
- Crying Obsidian
- Cut Copper Slab (Can not be waxed, and can not be oxidized)

### Coordinate Determiners:
It is in base 16, so pull up a calculator if you aren't used to converting from b10 to b16

| Hex Code | Glass       |
|----------|-------------|
| 0x0      | Tinted      |
| 0x1      | Black       |
| 0x2      | Gray        |
| 0x3      | Light Gray  |
| 0x4      | White       |
| 0x5      | Purple      |
| 0x6      | Magenta     |
| 0x7      | Blue        |
| 0x8      | Light Blue  |
| 0x9      | Cyan        |
| 0xA      | Green       |
| 0xB      | Lime        |
| 0xC      | Yellow      |
| 0xD      | Orange      |
| 0xE      | Red         |
| 0xF      | Plain Glass |

Example: 0x4A6 (White, Green, Magenta (Bottom to Top)) = 1190; (This example does not include the required leading zeroes)

Additionally, tinted glass represents negative for the coordinate signs, and a boolean false;
glass represents positive for the coordinate signs, and a boolean true

### Glass Arrangement:
The x-coordinate blocks are placed on the East side

The y-coordinate blocks are placed on the South side

The z-coordinate blocks are placed on the West side

Additional setting blocks are placed on the North side

Note: There must not be any non-glass block on the sides, otherwise the
teleporter will either cease to function or teleport the player
into the void. This is an intended feature.

#### Settings:

**First Block**: x-Coordinate sign: Positive (Glass) or Negative (Tinted Glass); Any other block cancels the value

**Second Block**: y-Coordinate sign: Positive or Negative

**Third Block**: z-Coordinate sign: Positive or Negative

**Fourth Block**: User Teleportation: Passive (Glass) or Active (Tinted Glass)
- Passive is forced teleportation which only requires the user to step on the teleporter
- Active is not forced, and requires the user to stand on top of the teleporter and right-click the light block

**Fifth Block**: Teleportation Type: Relative (Glass) or Absolute (Tinted Glass)
- Relative teleportation is with the origin at the player's coordinates
  - Example:
  - Player coordinates are (23,64,-95)
  - Teleporter target coordinates are (4,-2,9)
  - Player gets teleported to (27,62,-86)
- Absolute teleportation is with the origin at the coordinates (0,0,0)
  - Example:
  - Player coordinates don't matter
  - Teleporter target coordinates are (4,-2,9)
  - Player gets teleported to (4,-2,9)

**Sixth Block**: Activity: Glass is active; Any other block is inactive

## Compatibilities:
This list only includes the most up-to-date plugin version
for each Minecraft version.

| Plugin Version | Minecraft Versions |
|----------------|--------------------|
| v0.1.1         | 1.20.4             |

## Future Plans:
- Number to Hex-Block Command (and vice versa)
- Small Teleporter (Elevator)
- config.yml
  - For controlling the construction of the large teleporter or possible arrangements
  - For controlling the size of the large teleporter
  - For controlling the blocks used to represent the coordinates
  - For controlling the uppermost blocks (the light blocks)
- Entity Teleportation
- Cross-Dimension Teleportation (Might be too OP)
- Possibly an item you can right-click the teleporter with to see information about the teleporter (to avoid teleportation traps set by others)

## Miscellaneous Nonsense:
### Lore (Excuses why I chose the arrangement (I made all of this up on the spot)):
Just as obsidian has the capability to create portals, crying obsidian is capable of teleportation (example: respawn anchor). By pressing the crying obsidian into copper, a resulting electromagnetic field formed. A lodestone in real life is a magnetic rock, and in Minecraft, it acts as a guide for compasses, meaning that it does in fact have magnetic properties; the magnetic properties help guide and tune the EMF, and with the netherite within it, the light from the surrounding glass blocks tunes it, and returns it back to the crying obsidian, which teleports the player.

### Actual reasons why I chose the arrangement:
I initially wanted the crying obsidian to be a beacon for it to be more balanced, but that would be really hard to get. Both the lodestone and the crying obsidian require diamond tools, and getting the lodestone requires a small amount of grinding. The copper slab, which can not be waxed, is intended to force the player to perform occasional maintenance on the teleporter, so that it isn't overly convenient; early concepts of this were to deplete some kind of resource on each teleport, but I wasn't sure what would be good. Lastly, the light blocks: I like them, and my first teleporters made in Minecraft with commands used them.