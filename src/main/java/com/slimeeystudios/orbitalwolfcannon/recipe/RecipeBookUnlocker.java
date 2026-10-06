package com.slimeeystudios.orbitalwolfcannon.recipe;

import com.slimeeystudios.orbitalwolfcannon.OrbitalWolfCannonMod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;

public final class RecipeBookUnlocker {
    private RecipeBookUnlocker() {
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(RecipeBookUnlocker::onPlayerJoin);
    }

    private static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            unlockOrbitalRecipe(player);
        }
    }

    private static void unlockOrbitalRecipe(ServerPlayer player) {
        ServerLevel world = (ServerLevel) player.level();
        Identifier recipeId = Identifier.fromNamespaceAndPath(OrbitalWolfCannonMod.MOD_ID, "orbital_wolf_rod");
        ResourceKey<Recipe<?>> recipeKey = ResourceKey.create(Registries.RECIPE, recipeId);
        List<RecipeHolder<?>> recipesToUnlock = new ArrayList<>();

        world.getServer().getRecipeManager().byKey(recipeKey).ifPresent(recipesToUnlock::add);

        if (recipesToUnlock.isEmpty()) {
            for (RecipeHolder<?> entry : world.getServer().getRecipeManager().getRecipes()) {
                if (entry.id().identifier().getNamespace().equals(OrbitalWolfCannonMod.MOD_ID)) {
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

