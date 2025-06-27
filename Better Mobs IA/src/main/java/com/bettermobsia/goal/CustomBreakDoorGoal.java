package com.bettermobsia.goal;

import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.Difficulty;

import java.util.function.Predicate;

public class CustomBreakDoorGoal extends BreakDoorGoal {

    public CustomBreakDoorGoal(ZombieEntity zombie) {
        // On passe un prédicat qui retourne toujours true (peu importe la difficulté)
        super(zombie, difficulty -> true);
    }
}
