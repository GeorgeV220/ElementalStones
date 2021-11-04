package com.lennertsoffers.elementalstones.stones.windStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AirbendingStone extends AirStone {

    // PASSIVE
    // Passive 1: Feather Falling
    public static void passive1(ActivePlayer activePlayer, EntityDamageEvent event) {
        Player player = activePlayer.getPlayer();
        if (
                player.getInventory().contains(ItemStones.airStoneBending0) ||
                player.getInventory().contains(ItemStones.airStoneBending1) ||
                player.getInventory().contains(ItemStones.airStoneBending2) ||
                player.getInventory().contains(ItemStones.airStoneBending3) ||
                player.getInventory().contains(ItemStones.airStoneBending4)
        ) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);
            }
        }
    }

    // Passive 2: Speed Boost
    public static void passive2(ActivePlayer activePlayer, EntityToggleGlideEvent event) {
        Player player = activePlayer.getPlayer();
        if (
                player.getInventory().contains(ItemStones.airStoneBending0) ||
                        player.getInventory().contains(ItemStones.airStoneBending1) ||
                        player.getInventory().contains(ItemStones.airStoneBending2) ||
                        player.getInventory().contains(ItemStones.airStoneBending3) ||
                        player.getInventory().contains(ItemStones.airStoneBending4)
        ) {
            if (!event.isGliding()) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2, true, true, true));
            }
        }
    }

    // MOVE 4
    // Air Slash
    // -> A high speed high damage air slash
    public static Runnable move4(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();

            boolean critical = !player.isOnGround();
            if (critical) {
                player.setVelocity(player.getVelocity().add(new Vector(0, -1, 0)));
            } else {
                player.setVelocity(new Vector(0, 0.1, 0));
            }
            player.setGliding(true);

            new BukkitRunnable() {
                @Override
                public void run() {
                    Location location = player.getLocation();
                    Vector direction = location.getDirection();
                    location.add(0, 2, 0);

                    new BukkitRunnable() {
                        int amountOfTicks = 0;

                        @Override
                        public void run() {

                            Location runnableLocation = location.clone();

                            for (int i = 70; i >= (70 - (amountOfTicks * 46.66)); i--) {
                                Location particleLocation = runnableLocation.clone().add(direction.clone().rotateAroundY(i / 100f).multiply(2));

                                double nominator = Math.abs(i) / 2.5;

                                if (nominator < 10) {
                                    nominator = 10;
                                }

                                double locationX = particleLocation.getX() + StaticVariables.random.nextGaussian() / nominator;
                                double locationY = particleLocation.getY() + StaticVariables.random.nextGaussian() / nominator;
                                double locationZ = particleLocation.getZ() + StaticVariables.random.nextGaussian() / nominator;

                                world.spawnParticle(Particle.SPELL_MOB, locationX, locationY, locationZ, 0, 1, 1, 1);

                                if (i % 10 == 0) {
                                    int damage = 3;
                                    if (critical) {
                                        world.spawnParticle(Particle.CRIT, particleLocation.clone().add(0, 0.3, 0), 0);
                                        damage = 6;
                                    }
                                    if (!world.getNearbyEntities(particleLocation, 0.3, 0.3, 0.3).isEmpty()) {
                                        for (Entity entity : world.getNearbyEntities(particleLocation, 0.3, 0.3, 0.3)) {
                                            if (entity != null) {
                                                if (entity instanceof LivingEntity) {
                                                    LivingEntity livingEntity = (LivingEntity) entity;
                                                    if (entity != player) {
                                                        livingEntity.damage(damage);
                                                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 2, false, false, false));
                                                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 100, false, false, false));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                runnableLocation.add(0, -1 / 100f, 0);
                            }

                            if (amountOfTicks > 3) {
                                this.cancel();
                            }
                            amountOfTicks++;
                        }
                    }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
                }
            }.runTaskLater(StaticVariables.plugin, 2L);
        };
    }


    // MOVE 5
    // Tracking blade
    // -> Shoots a little blade of air in the looking direction tracking the closest entity
    public static Runnable move5(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            Location playerLocation = player.getLocation();
            Location bladeStartLocation = playerLocation.clone();
            Vector bladeStartDirection = bladeStartLocation.getDirection();
            bladeStartLocation.add(bladeStartDirection).add(0, 1, 0);

            LivingEntity target = null;
            for (int i = 1; i < 50; i++) {
                if (!world.getNearbyEntities(playerLocation.clone().add(bladeStartDirection.clone().multiply(i)), 5, 2, 5).isEmpty()) {
                    for (Entity entity : world.getNearbyEntities(playerLocation.clone().add(bladeStartDirection.clone().multiply(i)), 5, 2, 5)) {
                        if (entity != null) {
                            if (entity instanceof LivingEntity) {
                                LivingEntity livingEntity = (LivingEntity) entity;
                                if (entity != player) {
                                    if (target != null) {
                                        if (MathTools.calculate3dDistance(playerLocation, entity.getLocation()) < MathTools.calculate3dDistance(playerLocation, target.getLocation())) {
                                            target = livingEntity;
                                        }
                                    } else {
                                        target = livingEntity;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (target != null) {
                target.setGlowing(true);
                LivingEntity finalTarget = target;
                Location currentLocation = bladeStartLocation.clone();

                new BukkitRunnable() {
                    int amountOfTicks = 0;

                    @Override
                    public void run() {

                        Location targetLocation = finalTarget.getLocation().add(0, 1, 0);
                        Vector pathDirection = MathTools.getDirectionNormVector3d(currentLocation, targetLocation);

                        for (int i = 0; i < 20; i++) {
                            double particleLocationX = currentLocation.getX() + StaticVariables.random.nextGaussian() / 10;
                            double particleLocationZ = currentLocation.getZ() + StaticVariables.random.nextGaussian() / 10;
                            world.spawnParticle(Particle.END_ROD, particleLocationX, currentLocation.getY(), particleLocationZ, 0);
                        }

                        currentLocation.add(pathDirection.multiply(0.2));

                        if (targetLocation.getX() > currentLocation.getX() - 0.1 && targetLocation.getX() < currentLocation.getX() + 0.1 ||
                                targetLocation.getX() > currentLocation.getX() - 0.1 && targetLocation.getX() < currentLocation.getX() + 0.1 ||
                                targetLocation.getX() > currentLocation.getX() - 0.1 && targetLocation.getX() < currentLocation.getX() + 0.1
                        ) {
                            this.cancel();
                            finalTarget.damage(5);
                            finalTarget.setGlowing(false);
                            for (int i = 0; i < 200; i++) {
                                world.spawnParticle(Particle.END_ROD, currentLocation, 0, StaticVariables.random.nextGaussian() / 10, StaticVariables.random.nextGaussian() / 10, StaticVariables.random.nextGaussian() / 10);
                            }
                        } else if (amountOfTicks > 400 || finalTarget.isDead()) {
                            this.cancel();
                        }
                        amountOfTicks++;
                    }
                }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
            }
        };
    }


    // MOVE 6
    // Wind Cloak
    // -> Gives the player a cloak of wind
    // -> If the player gets damaged by an entity, the entity gets knocked back hard

    // activation
    public static Runnable move6(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            activePlayer.setWindCloak(true);

            new BukkitRunnable() {
                int amountOfTicks = 0;

                @Override
                public void run() {

                    Location location = player.getLocation().add(0, 1.1, 0);
                    for (int i = 0; i < 10; i++) {
                        world.spawnParticle(Particle.SPIT, MathTools.locationOnCircle(location, 0.5, i, world), 0);
                    }

                    if (amountOfTicks > 200) {
                        this.cancel();
                        activePlayer.setWindCloak(false);
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }

    // knockback entities on damage
    public static void move6knockback(ActivePlayer activePlayer, EntityDamageByEntityEvent event) {
        if (activePlayer.hasWindCloak()) {
            Player player = activePlayer.getPlayer();
            Entity entity = event.getDamager();
            if (entity != player) {
                if (entity instanceof LivingEntity) {
                    LivingEntity damager = (LivingEntity) entity;
                    Vector direction = MathTools.getDirectionNormVector(player.getLocation(), damager.getLocation());
                    damager.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 3, true, true, true));
                    damager.setVelocity(direction.clone().multiply(5).setY(0.5));
                    damager.damage(3, player);
                }
            }
        }
    }


    // MOVE 7
    // Tornado
    // -> Shoots a tornado launching entities up
    public static Runnable move7(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            final Location location = player.getLocation();
            final Vector direction = location.getDirection();
            location.add(direction);

            new BukkitRunnable() {
                int amountOfTicks = 0;
                int angle1 = 0;

                @Override
                public void run() {

                    Location centerLocation = location.clone();
                    for (int i = 10; i < 100; i++) {
                        Location particle1Location = MathTools.locationOnCircle(centerLocation, i / 20f, angle1, world);
                        world.spawnParticle(Particle.SPELL_MOB, particle1Location, 0, 1, 1, 1);
                        angle1 += 37;
                        centerLocation.add(0, 0.1, 0);
                        if (i % 10 == 0) {
                            if (!world.getNearbyEntities(centerLocation, i / 20f, 1, i / 20f).isEmpty()) {
                                for (Entity entity : world.getNearbyEntities(centerLocation, i / 20f, 1, i / 20f)) {
                                    if (entity != null) {
                                        if (entity instanceof LivingEntity) {
                                            LivingEntity livingEntity = (LivingEntity) entity;
                                            if (livingEntity != player) {
                                                livingEntity.setVelocity(new Vector(0, 2, 0));
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                    location.add(direction.clone().multiply(0.2));

                    if (amountOfTicks > 200) {
                        this.cancel();
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }


    // MOVE 8
    // Levitate
    // -> Gives player the ability to select an entity and move it in the air for 10 seconds
    public static Runnable move8(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            Location location = player.getLocation().add(0, 1, 0);
            Vector direction = location.getDirection();

            if (!activePlayer.hasPossibleTarget() && activePlayer.isNotLevitatingTarget()) {
                // Finding target
                Entity target = null;
                for (int i = 1; i < 40; i++) {
                    if (!world.getNearbyEntities(location.clone().add(direction.clone().multiply(i)), 1, 1, 1).isEmpty()) {
                        for (Entity entity : world.getNearbyEntities(location.clone().add(direction.clone().multiply(i)), 1, 1, 1)) {
                            if (entity != null) {
                                if (!(entity instanceof Item || entity instanceof Arrow)) {
                                    if (entity != player) {
                                        if (target != null) {
                                            if (MathTools.calculate3dDistance(location, entity.getLocation()) < MathTools.calculate3dDistance(location, target.getLocation())) {
                                                target = entity;
                                            }
                                        } else {
                                            target = entity;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (target != null) {
                    target.setGlowing(true);
                    activePlayer.setPossibleTarget(target);
                }
            } else if (activePlayer.isNotLevitatingTarget()) {
                // Levitating target
                activePlayer.clearTarget();
                activePlayer.setMove8from(null);
                activePlayer.setMove8to(null);
                Entity target = activePlayer.getPossibleTarget();
                activePlayer.setTarget(target);
                activePlayer.setLevitatingTask(new BukkitRunnable() {
                    int amountOfTicks = 0;

                    @Override
                    public void run() {
                        Location playerLocation = player.getLocation();
                        Vector playerDirection = playerLocation.getDirection();
                        playerLocation.add(playerDirection.clone().multiply(10));

                        if (playerLocation.getBlock().getType().isSolid()) {
                            playerLocation.setY(world.getHighestBlockYAt(playerLocation) + 1);
                        }

                        activePlayer.setMove8from(activePlayer.getMove8to());
                        activePlayer.setMove8to(playerLocation);
                        target.teleport(playerLocation);
                        for (int i = 0; i < 10; i++) {
                            double particleX = playerLocation.clone().getX() + StaticVariables.random.nextGaussian() / 5;
                            double particleY = playerLocation.clone().getY() + StaticVariables.random.nextGaussian() / 5 + 1;
                            double particleZ = playerLocation.clone().getZ() + StaticVariables.random.nextGaussian() / 5;
                            int grayValue = (1 + StaticVariables.random.nextInt(30));
                            world.spawnParticle(Particle.SPELL_MOB, particleX, particleY, particleZ, 0, grayValue, grayValue, grayValue);
                        }
                        if (amountOfTicks > 100) {
                            activePlayer.stopLevitatingTask();
                        }
                        amountOfTicks++;
                    }
                }.runTaskTimer(StaticVariables.plugin, 0L, 1L));
            } else {
                activePlayer.stopLevitatingTask();
                Entity target = activePlayer.getTarget();
                if (
                        activePlayer.getMove8from().getX() != activePlayer.getMove8to().getX() &&
                                activePlayer.getMove8from().getY() != activePlayer.getMove8to().getY() &&
                                activePlayer.getMove8from().getZ() != activePlayer.getMove8to().getZ()
                ) {
                    target.setVelocity(MathTools.getDirectionNormVector3d(activePlayer.getMove8from(), activePlayer.getMove8to()).multiply(4));
                }
            }
        };
    }
}



















