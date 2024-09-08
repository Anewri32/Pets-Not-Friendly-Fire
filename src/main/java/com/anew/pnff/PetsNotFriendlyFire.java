package com.anew.pnff;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PetsNotFriendlyFire implements ModInitializer {
	public static final String MOD_ID = "pets-not-friendly-fire";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing PetsNotFriendlyFire Mod");

		// Registrar el evento para interceptar ataques de entidad
		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (isFriendlyFire(player, entity)) {
				LOGGER.info("Prevented friendly fire between pets.");
				return ActionResult.FAIL; // Cancela el ataque
			}
			return ActionResult.PASS; // Permite el ataque si no es fuego amigo
		});
	}

	// Método para verificar si es fuego amigo
	private boolean isFriendlyFire(Entity attacker, Entity target) {
		// Verificar si tanto el atacante como la víctima son mobs domesticados
		if (attacker instanceof PlayerEntity && target instanceof TameableEntity) {
			TameableEntity tameableTarget = (TameableEntity) target;

			// Verificar si el atacante es el dueño del mob o si el mob tiene el mismo dueño
			if (tameableTarget.getOwner() != null && tameableTarget.getOwner().equals(attacker)) {
				return true; // Es fuego amigo
			}
		}
		return false; // No es fuego amigo
	}
}
