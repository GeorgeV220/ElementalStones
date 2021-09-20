package com.lennertsoffers.elementalstones.stones.waterStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.SetBlockTools;
import com.lennertsoffers.elementalstones.customClasses.tools.StringListTools;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WaterStone {

    // MOVE 1
    // Ocean splitter
    // -> Splits the water in front of the player

    // MOVE 2
    // Water Arms
    // -> Create two arms of water next to the player
    // -> Only further use in combination with other moves
    public static void move2(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                String[] armsForm0 = {
                        "AAAAAAAAA",
                        "AAAAAAAAA",
                        "AABAAABAA",
                        "AABAAABAA",
                        "AABA*ABAA",
                        "AABAAABAA",
                        "AAAAAAAAA",
                        "AAAAAAAAA"
                };
                String[] armsForm1 = {
                        "AAAAAAAAAAAAA",
                        "AAAAAAAAAAAAA",
                        "AAABAAABAAAAA",
                        "AAABAAABAAAAA",
                        "AAAABA*ABAAAA",
                        "AAAABAAABAAAA",
                        "AAAAAAAAAAAAA",
                        "AAAAAAAAAAAAA"
                };
                String[] armsForm2 = {
                        "AAAAAAAAAAAAA",
                        "AAAAAAAAAAAAA",
                        "AAAAABAAAAAAA",
                        "AAAAAABAAAAAA",
                        "AAAAAAABAAAAA",
                        "AABAAAAABAAAA",
                        "AAABAA*AAAAAA",
                        "AAAABAAAAAAAA",
                        "AAAAABAAAAAAA",
                        "AAAAAAAAAAAAA",
                        "AAAAAAAAAAAAA"
                };
                Location location = player.getLocation();
                float yaw = Math.abs(location.getYaw());
                double startVal = 11.25;
                if (yaw >= (startVal * 31) || yaw < (startVal)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorY(StringListTools.rotate(armsForm0)));
                } else if (yaw >= (startVal) && yaw < (startVal * 3)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorY(StringListTools.rotate(armsForm1)));
                } else if (yaw >= (startVal * 3) && yaw < (startVal * 5)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorY(StringListTools.rotate(armsForm2)));
                } else if (yaw >= (startVal * 5) && yaw < (startVal * 7)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorY(armsForm1));
                } else if (yaw >= (startVal * 7) && yaw < (startVal * 9)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorY(armsForm0));
                } else if (yaw >= (startVal * 9) && yaw < (startVal * 11)) {
                    placeWaterArms(activePlayer, armsForm1);
                } else if (yaw >= (startVal * 11) && yaw < (startVal * 13)) {
                    placeWaterArms(activePlayer, armsForm2);
                } else if (yaw >= (startVal * 13) && yaw < (startVal * 15)) {
                    placeWaterArms(activePlayer, StringListTools.rotate(armsForm1));
                } else if (yaw >= (startVal * 15) && yaw < (startVal * 17)) {
                    placeWaterArms(activePlayer, StringListTools.rotate(armsForm0));
                } else if (yaw >= (startVal * 17) && yaw < (startVal * 19)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorX(StringListTools.rotate(armsForm1)));
                } else if (yaw >= (startVal * 19) && yaw < (startVal * 21)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorX(StringListTools.rotate(armsForm2)));
                } else if (yaw >= (startVal * 21) && yaw < (startVal * 23)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorX(armsForm1));
                } else if (yaw >= (startVal * 23) && yaw < (startVal * 25)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorX(armsForm0));
                } else if (yaw >= (startVal * 25) && yaw < (startVal * 27)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorX(StringListTools.mirrorY(armsForm1)));
                } else if (yaw >= (startVal * 27) && yaw < (startVal * 29)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorX(StringListTools.mirrorY(armsForm2)));
                } else if (yaw >= (startVal * 29) && yaw < (startVal * 31)) {
                    placeWaterArms(activePlayer, StringListTools.rotate(StringListTools.mirrorX(StringListTools.mirrorY(armsForm1))));
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }

    // place water arms in world
    private static void placeWaterArms(ActivePlayer activePlayer, String[] armsForm) {
        Player player = activePlayer.getPlayer();
        if (player.getVelocity().length() < 0.5) {
            Location location = player.getLocation().add(0, 1, 0);
            String[] airLayer = {
                    "AAAAAAAAAAA",
                    "AAAAAAAAAAA",
                    "AAAAAAAAAAA",
                    "AAAAAAAAAAA",
                    "AAAAAAAAAAA",
                    "AAAAA*AAAAA",
                    "AAAAAAAAAAA",
                    "AAAAAAAAAAA",
                    "AAAAAAAAAAA",
                    "AAAAAAAAAAA",
                    "AAAAAAAAAAA"
            };
            Map<Character, Material> characterMaterialMap = new HashMap<>();
            characterMaterialMap.put('A', Material.AIR);
            characterMaterialMap.put('B', Material.WATER);

            SetBlockTools.setBlocks(location.clone().add(0, -1, 0), airLayer, characterMaterialMap, true, Material.AIR, activePlayer, activePlayer.getOverrideLocations());
            SetBlockTools.setBlocks(location, airLayer, characterMaterialMap, true, Material.AIR, activePlayer, activePlayer.getOverrideLocations());
            SetBlockTools.setBlocks(location.clone().add(0, 1, 0), armsForm, characterMaterialMap, true, Material.AIR, activePlayer, activePlayer.getOverrideLocations());
            SetBlockTools.setBlocks(location.clone().add(0, 2, 0), airLayer, characterMaterialMap, true, Material.AIR, activePlayer, activePlayer.getOverrideLocations());
            SetBlockTools.setBlocks(location.clone().add(0, 3, 0), airLayer, characterMaterialMap, true, Material.AIR, activePlayer, activePlayer.getOverrideLocations());
        } else {
            for (Location location : activePlayer.getOverrideLocations()) {
                location.getBlock().setType(Material.AIR);
            }
        }
    }

    // MOVE 3
    // Water Spear
    // -> Throw one of your water arms that damages entities on impact
    // -> Creates splash damage
    public static void move3(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        ArrayList<Location> spearLocations = new ArrayList<>();
        Location startLocation = player.getLocation().add(player.getLocation().getDirection());
        Vector direction = player.getLocation().getDirection().multiply(0.5);

        new BukkitRunnable() {
            Location location = startLocation.clone();
            int distance = 0;
            @Override
            public void run() {
                spearLocations.add(location);
                location.getBlock().setType(Material.STONE);
                location.add(direction);
                if (spearLocations.size() > 4) {
                    spearLocations.get(spearLocations.size() - 1).getBlock().setType(Material.GOLD_BLOCK);
                    System.out.println("Lenght: " + spearLocations.size());
                    spearLocations.remove(spearLocations.size() - 1);
                }
                if (distance > 50) {
                    this.cancel();
                    for (Location location : spearLocations) {
                        location.getBlock().setType(Material.AIR);
                    }
                }
                distance++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }
}

























