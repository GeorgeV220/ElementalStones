package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;

public abstract class Grenade extends BukkitRunnable {

    private final Player player;
    private final World world;
    private final Location startLocation;
    private final Vector startVelocity;
    private final Particle particle;
    private final Particle.DustOptions dustOptions;

    private double amountOfTicks = 0;

    public Grenade(Player player, Particle particle, Particle.DustOptions dustOptions) {
        this.player = player;
        this.world = player.getWorld();

        this.startLocation = player.getLocation();
        Vector direction = this.startLocation.getDirection().setY(0);
        this.startLocation.add(direction).add(0, 1, 0).add(direction.clone().rotateAroundY(-90).multiply(2));

        this.startVelocity = player.getVelocity();
        if (this.startVelocity.getY() < 0) {
            this.startVelocity.setY(0);
        }

        this.particle = particle;
        this.dustOptions = dustOptions;
    }

    @Override
    public void run() {
        HashMap<String, Double> result = MathTools.calculatePointOnThrowFunction(15, 1, this.startLocation.getYaw(), -this.startLocation.getPitch(), this.amountOfTicks, this.startVelocity);

        int offset = 15;
        double x = this.startLocation.getX() + result.get("x");
        double y = this.startLocation.getY() + result.get("y");
        double z = this.startLocation.getZ() + result.get("z");

        for (int i = 0; i < 5; i++) {
            double randomX = x + (StaticVariables.random.nextGaussian() / offset);
            double randomY = y + (StaticVariables.random.nextGaussian() / offset);
            double randomZ = z + (StaticVariables.random.nextGaussian() / offset);
            this.world.spawnParticle(this.particle, randomX, randomY, randomZ, 0, this.dustOptions);
        }

        Location particleLocation = new Location(this.world, x, y, z);
        if (CheckLocationTools.isSolidBlock(this.world.getBlockAt(particleLocation))) {
            this.cancel();
            this.explode(particleLocation);
        }

        this.amountOfTicks += 0.05;
        if (this.amountOfTicks > 4) {
            this.cancel();
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public World getWorld() {
        return world;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public Vector getStartVelocity() {
        return startVelocity;
    }

    abstract public void explode(Location impactLocation);
}
