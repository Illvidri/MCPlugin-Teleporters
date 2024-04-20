# Minecraft Plugin: Teleporters | Latest Version: 0.0.1
### Made by Illvidri
</br>

### About:
This plugin was adapted from a datapack I had made which allowed for the creation of teleporters, since plugins are less resource intensive on the minecraft client than datapacks are.

The coordinates can be input using stained glass blocks in the correct configuration.

There are two settings for the large teleporters: Absolute coordinate teleportation; Relative coordinate teleportation.
</br>

Teleporter Construction (Pictures yet to be added):
The Core:
light block (Beacon, Sea Lantern, Glowstone, Redstone Lamp, Shroomlight, Froglight)
Lodestone
Redstone Block
Downwards Facing Piston
Hopper
Crying Obsidian
Cut Copper Slab (Can not be waxed, and can not be oxidized)

Coordinate Determiners:
It is in base 16, so pull up a calculator if you aren't used to converting from b10 to b16

0 - Tinted Glass (Represents Negative Coordinates and Boolean value `False`)

1 - Black Stained Glass

2 - Gray Stained Glass

3 - Light Gray Stained Glass

4 - White Stained Glass

5 - Purple Stained Glass

6 - Magenta Stained Glass

7 - Blue Stained Glass

8 - Light Blue Stained Glass

9 - Cyan Stained Glass

A - Green Stained Glass

B - Lime Stained Glass

C - Yellow Stained Glass

D - Orange Stained Glass

E - Red Stained Glass

F - Glass (Represents Positive Coordinates and Boolean value `True`)

Example: 0x4A6 (White, Green, Magenta (Bottom to Top)) = 1190

The `x` coordinate blocks are placed on the East side

The `y` coordinate blocks are placed on the South side

The `z` coordinate blocks are placed on the West side

Additional setting blocks are placed on the North side

Possible Settings:

First Block: x-Coordinate sign: Positive (Glass) or Negative (Tinted Glass); Any other block cancels the value

Second Block: y-Coordinate sign: Positive or Negative

Third Block: z-Coordinate sign: Positive or Negative

Fifth Block: Teleportation Type: Relative (Glass) or Absolute (Tinted Glass)

Sixth Block: Activity: Glass is active; Any other block is inactive

### Compatibilities:
| Plugin Version | Minecraft Versions |
| -------------- | ------------------ |
| 0.0.1          | 1.20.4             |

