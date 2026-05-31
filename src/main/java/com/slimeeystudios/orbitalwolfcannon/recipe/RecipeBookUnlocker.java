package com.slimeeystudios.orbitalwolfcannon.recipe;

import com.slimeeystudios.orbitalwolfcannon.OrbitalWolfCannonMod;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;

public final class RecipeBookUnlocker {
    private RecipeBookUnlocker() {
    }

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> unlockOrbitalRecipe(handler.player));
    }

    private static void unlockOrbitalRecipe(ServerPlayer player) {
        ServerLevel world = player.serverLevel();
        ResourceLocation recipeId = new ResourceLocation(OrbitalWolfCannonMod.MOD_ID, "orbital_wolf_rod");
        List<RecipeHolder<?>> recipesToUnlock = new ArrayList<>();

        world.getRecipeManager().byKey(recipeId).ifPresent(recipesToUnlock::add);

        if (recipesToUnlock.isEmpty()) {
            for (RecipeHolder<?> entry : world.getRecipeManager().getRecipes()) {
                if (entry.id().getNamespace().equals(OrbitalWolfCannonMod.MOD_ID)) {
                    recipesToUnlock.add(entry);
                }
            }
        }

        if (!recipesToUnlock.isEmpty()) {
            player.awardRecipes(recipesToUnlock);
        } else {
            OrbitalWolfCannonMod.LOGGER.warn("Orbital Wolf Rod recipe {} was not present in recipe manager; recipe book unlock skipped.", recipeId);
        }
    }
}
