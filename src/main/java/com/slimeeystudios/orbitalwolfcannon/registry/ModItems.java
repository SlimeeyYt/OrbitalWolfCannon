package com.slimeeystudios.orbitalwolfcannon.registry;

import com.slimeeystudios.orbitalwolfcannon.OrbitalWolfCannonMod;
import com.slimeeystudios.orbitalwolfcannon.item.OrbitalWolfRodItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, OrbitalWolfCannonMod.MOD_ID);
    
    private static final Identifier ORBITAL_WOLF_ROD_ID = Identifier.fromNamespaceAndPath(OrbitalWolfCannonMod.MOD_ID, "orbital_wolf_rod");
    private static final ResourceKey<Item> ORBITAL_WOLF_ROD_KEY = ResourceKey.create(Registries.ITEM, ORBITAL_WOLF_ROD_ID);

    public static final Supplier<Item> ORBITAL_WOLF_ROD = ITEMS.register(
            "orbital_wolf_rod",
            () -> new OrbitalWolfRodItem(
                    new Item.Properties()
                            .setId(ORBITAL_WOLF_ROD_KEY)
                            .stacksTo(1)
            )
    );

    private ModItems() {
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        modEventBus.addListener(ModItems::addCreative);
    }
    
    private static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ORBITAL_WOLF_ROD.get());
        }
    }
}
