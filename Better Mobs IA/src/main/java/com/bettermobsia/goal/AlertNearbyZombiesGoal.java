package com.bettermobsia.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;

import java.util.EnumSet;
import java.util.List;

public class AlertNearbyZombiesGoal extends Goal {

    private final ZombieEntity zombie;

    public AlertNearbyZombiesGoal(ZombieEntity zombie) {
        this.zombie = zombie;
        this.setControls(EnumSet.of(Control.TARGET));
    }

    @Override
    public boolean canStart() {
        // Démarre uniquement si le zombie a une cible
        return this.zombie.getTarget() != null;
    }

    @Override
    public void start() {
        LivingEntity target = zombie.getTarget();

        // Rayon dans lequel les autres zombies seront alertés
        double radius = 50.0;
        Box box = zombie.getBoundingBox().expand(radius);

        List<ZombieEntity> nearbyZombies = zombie.getWorld().getEntitiesByClass(
            ZombieEntity.class,
            box,
            otherZombie -> otherZombie != zombie && otherZombie.getTarget() == null
        );

        for (ZombieEntity otherZombie : nearbyZombies) {
            otherZombie.setTarget(target);
        }

        // Générer des particules visibles (côté client)
        if (!zombie.getWorld().isClient) {
            ((ServerWorld) zombie.getWorld()).spawnParticles(
                ParticleTypes.ANGRY_VILLAGER,
                zombie.getX(), zombie.getY() + 1.5, zombie.getZ(),
                5,       // nombre de particules
                0.3, 0.5, 0.3, // éparpillement (X, Y, Z)
                0.0      // vitesse
            );
        }
    }
}
