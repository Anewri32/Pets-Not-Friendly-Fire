package com.anew.pnff.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.DonkeyEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class NoDamageTamedMixin {

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;

        // Check if the entity is a TameableEntity (e.g., wolves, cats)
        if (entity instanceof TameableEntity) {
            TameableEntity tameableEntity = (TameableEntity) entity;
            // If the entity is tamed
            if (tameableEntity.isTamed()) {
                // Check if the attacker is a player
                if (source.getAttacker() instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) source.getAttacker();
                    // If the player attacking is the owner of the entity, cancel the damage
                    if (player.equals(tameableEntity.getOwner())) {
                        cir.setReturnValue(false);
                    }
                }
            }
        }

        // Check if the entity is a HorseEntity
        if (entity instanceof HorseEntity) {
            HorseEntity horseEntity = (HorseEntity) entity;
            // If the horse is tamed
            if (horseEntity.isTame()) {
                // Check if the attacker is a player
                if (source.getAttacker() instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) source.getAttacker();
                    // If the player attacking is the owner of the horse, cancel the damage
                    if (horseEntity.getOwnerUuid() != null && player.getUuid().equals(horseEntity.getOwnerUuid())) {
                        cir.setReturnValue(false);
                    }
                }
            }
        }

        // Check if the entity is a DonkeyEntity
        if (entity instanceof DonkeyEntity) {
            DonkeyEntity donkeyEntity = (DonkeyEntity) entity;
            // If the donkey is tamed
            if (donkeyEntity.isTame()) {
                // Check if the attacker is a player
                if (source.getAttacker() instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) source.getAttacker();
                    // If the player attacking is the owner of the donkey, cancel the damage
                    if (donkeyEntity.getOwnerUuid() != null && player.getUuid().equals(donkeyEntity.getOwnerUuid())) {
                        cir.setReturnValue(false);
                    }
                }
            }
        }
    }
}
