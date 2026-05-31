package com.slimeeystudios.orbitalwolfcannon.registry;

import com.slimeeystudios.orbitalwolfcannon.OrbitalWolfCannonMod;
import com.slimeeystudios.orbitalwolfcannon.item.OrbitalWolfRodItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public final class ModItems {
    private static final ResourceLocation ORBITAL_WOLF_ROD_ID = new ResourceLocation(OrbitalWolfCannonMod.MOD_ID, "orbital_wolf_rod");

    public static final Item ORBITAL_WOLF_ROD = Registry.register(
            Registries.ITEM,
            ORBITAL_WOLF_ROD_ID,
            new OrbitalWolfRodItem(
                    new Item.Properties()
                            .stacksTo(1)
            )
    );

    private ModItems() {
    }

    public static void register() {
        // Classload registration holder.
    }
}
