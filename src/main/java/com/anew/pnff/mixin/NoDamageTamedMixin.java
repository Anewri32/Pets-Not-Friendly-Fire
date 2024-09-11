package com.anew.pnff.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.TameableEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class NoDamageTamedMixin {

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;

        // Verifica si la entidad es una mascota domesticada
        if (entity instanceof TameableEntity) {
            TameableEntity tameableEntity = (TameableEntity) entity;

            if (tameableEntity.isTamed()) {
                // Si la entidad está domesticada, cancela el daño
                cir.setReturnValue(false);
            }
        }
    }
}
