package com.slimeeystudios.orbitalwolfcannon;

import com.slimeeystudios.orbitalwolfcannon.config.OrbitalWolfCannonConfig;
import com.slimeeystudios.orbitalwolfcannon.entity.WolfStrikeManager;
import com.slimeeystudios.orbitalwolfcannon.recipe.RecipeBookUnlocker;
import com.slimeeystudios.orbitalwolfcannon.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(OrbitalWolfCannonMod.MOD_ID)
public class OrbitalWolfCannonMod {
    public static final String MOD_ID = "orbital_wolf_cannon";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public OrbitalWolfCannonMod(IEventBus modEventBus) {
        OrbitalWolfCannonConfig.load();
        ModItems.register(modEventBus);
        RecipeBookUnlocker.register();
        WolfStrikeManager.registerTickHandler();
        LOGGER.info("Orbital Wolf Cannon initialized");
    }
}
