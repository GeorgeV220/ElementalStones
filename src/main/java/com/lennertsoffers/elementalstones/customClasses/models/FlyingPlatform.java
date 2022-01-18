package com.lennertsoffers.elementalstones.customClasses.models;

import org.bukkit.Location;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class FlyingPlatform extends BukkitRunnable {

    private boolean creation = false;
    private int maxAmountOfTicks = 1200;
    private int amountOfTicks = 0;
    private double velocityMultiplier = 1;
    private final Player player;
    private final List<FallingBlock> platform;
    private final Vector platformFallVelocity = new Vector(0, -1, 0);

    public FlyingPlatform(Player player, List<FallingBlock> platform) {
        this.player = player;
        this.platform = platform;
    }

    public FlyingPlatform(boolean creation, int maxAmountOfTicks, double velocityMultiplier, Player player, List<FallingBlock> platform) {
        this.creation = creation;
        this.maxAmountOfTicks = maxAmountOfTicks;
        this.velocityMultiplier = velocityMultiplier;
        this.player = player;
        this.platform = platform;
    }

    @Override
    public void run() {
        List<Location> toLocations = getPlatformLocations(player);

        int fallingBlockIndex = 0;
        for (Location toLocation : toLocations) {
            FallingBlock fallingBlock = platform.get(fallingBlockIndex);

            Location blockLocation = fallingBlock.getLocation();
            double differenceX = toLocation.getX() - blockLocation.getX();
            double differenceY = toLocation.getY() - blockLocation.getY();
            double differenceZ = toLocation.getZ() - blockLocation.getZ();
            Vector velocity = new Vector(differenceX, differenceY, differenceZ);

            fallingBlock.setVelocity(velocity.multiply(velocityMultiplier));
            fallingBlock.setTicksLived(1);

            fallingBlockIndex++;
        }

        if (amountOfTicks >= maxAmountOfTicks) {
            this.cancel();
            if (!creation) {
                for (FallingBlock fallingBlock : platform) {
                    fallingBlock.setVelocity(platformFallVelocity);
                    player.setFlying(false);
                    player.setAllowFlight(false);
                    player.setVelocity(platformFallVelocity);
                }
            }
        }

        amountOfTicks++;
    }

    private static List<Location> getPlatformLocations(Player player) {
        Location playerLocation = player.getLocation().add(0, -1, 0);
        List<Location> toLocations = new ArrayList<>();

        // Layer 1
        toLocations.add(playerLocation.clone().add(0, 0, 0));
        toLocations.add(playerLocation.clone().add(1, 0, 0));
        toLocations.add(playerLocation.clone().add(-1, 0, 0));
        toLocations.add(playerLocation.clone().add(0,  0, 1));
        toLocations.add(playerLocation.clone().add(0, 0, -1));
        toLocations.add(playerLocation.clone().add(0, 0, 0));

        // Layer 2
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (!((i == -2 && j == -2) || (i == 2 && j == 2) || (i == -2 && j == 2) || (i == 2 && j == -2))) {
                    toLocations.add(playerLocation.clone().add(i, -1, j));
                }
            }
        }

        // Layer 3
        toLocations.add(playerLocation.clone().add(2, -2, 0));
        toLocations.add(playerLocation.clone().add(-2, -2, 0));
        toLocations.add(playerLocation.clone().add(0, -2, 2));
        toLocations.add(playerLocation.clone().add(0, -2, -2));
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                toLocations.add(playerLocation.clone().add(i, -2, j));
            }
        }

        // Layer 4
        toLocations.add(playerLocation.clone().add(0, -3, 0));
        toLocations.add(playerLocation.clone().add(1, -3, 0));
        toLocations.add(playerLocation.clone().add(-1, -3, 0));
        toLocations.add(playerLocation.clone().add(0,  -3, 1));
        toLocations.add(playerLocation.clone().add(0, -3, -1));
        toLocations.add(playerLocation.clone().add(0, -3, 0));

        return toLocations;
    }
}
