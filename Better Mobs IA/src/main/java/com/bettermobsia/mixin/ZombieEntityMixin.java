package com.bettermobsia.mixin;

import com.bettermobsia.goal.AlertNearbyZombiesGoal;
import com.bettermobsia.goal.CustomBreakDoorGoal;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin extends MobEntity {

    protected ZombieEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("TAIL"))
    private void injectCustomGoals(CallbackInfo ci) {
        // Attaque les poulets
        this.targetSelector.add(3, new ActiveTargetGoal<>(
            (MobEntity)(Object)this,
            ChickenEntity.class,
            true
        ));

        // Casse les portes en toute difficulté
        this.goalSelector.add(4, new CustomBreakDoorGoal((ZombieEntity)(Object)this));
        
        this.goalSelector.add(5, new AlertNearbyZombiesGoal((ZombieEntity)(Object)this));

    }
}
