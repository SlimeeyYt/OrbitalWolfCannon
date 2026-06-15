package com.slimeeystudios.orbitalwolfcannon.registry;

import com.slimeeystudios.orbitalwolfcannon.OrbitalWolfCannonMod;
import com.slimeeystudios.orbitalwolfcannon.item.OrbitalWolfRodItem;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

public final class ModItems {
    private static final Identifier ORBITAL_WOLF_ROD_ID = Identifier.fromNamespaceAndPath(OrbitalWolfCannonMod.MOD_ID, "orbital_wolf_rod");
    private static final ResourceKey<Item> ORBITAL_WOLF_ROD_KEY = ResourceKey.create(Registries.ITEM, ORBITAL_WOLF_ROD_ID);

    public static final Item ORBITAL_WOLF_ROD = Registry.register(
            BuiltInRegistries.ITEM,
            ORBITAL_WOLF_ROD_ID,
            new OrbitalWolfRodItem(
                    new Item.Properties()
                            .setId(ORBITAL_WOLF_ROD_KEY)
                            .stacksTo(1)
            )
    );

    private ModItems() {
    }

    public static void register() {
        // Add to the Tools & Utilities creative tab
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register(entries -> entries.accept(ORBITAL_WOLF_ROD));
    }
}

