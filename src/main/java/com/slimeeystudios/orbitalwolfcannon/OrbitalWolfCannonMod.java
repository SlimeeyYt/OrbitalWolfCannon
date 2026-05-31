package com.slimeeystudios.orbitalwolfcannon;

import com.slimeeystudios.orbitalwolfcannon.config.OrbitalWolfCannonConfig;
import com.slimeeystudios.orbitalwolfcannon.entity.WolfStrikeManager;
import com.slimeeystudios.orbitalwolfcannon.recipe.RecipeBookUnlocker;
import com.slimeeystudios.orbitalwolfcannon.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrbitalWolfCannonMod implements ModInitializer {
    public static final String MOD_ID = "orbital_wolf_cannon";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        OrbitalWolfCannonConfig.load();
        ModItems.register();
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register(entries -> entries.accept(ModItems.ORBITAL_WOLF_ROD));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SEARCH)
                .register(entries -> entries.accept(ModItems.ORBITAL_WOLF_ROD));
        RecipeBookUnlocker.register();
        WolfStrikeManager.registerTickHandler();
        LOGGER.info("Orbital Wolf Cannon initialized");
    }
}
