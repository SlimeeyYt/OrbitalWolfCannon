package com.slimeeystudios.orbitalwolfcannon.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.slimeeystudios.orbitalwolfcannon.OrbitalWolfCannonMod;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configuration class for Orbital Wolf Cannon mod.
 * Config is stored in .minecraft/config/orbital_wolf_cannon.json
 */
public class OrbitalWolfCannonConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_DIR = Paths.get("config");
    private static final File CONFIG_FILE = CONFIG_DIR.resolve("orbital_wolf_cannon.json").toFile();

    // Configuration values
    public static int wolfCount = 48;
    public static double circleRadius = 6.0D;
    public static int cooldownSeconds = 60;
    public static long wolfDespawnTicks = 20L * 60L * 5L; // 5 minutes
    public static boolean autoDespawnEnabled = true;
    public static int strengthDurationTicks = 6_000; // 5 minutes
    public static int strengthAmplifier = 1;
    public static float summonSoundVolume = 1.8F;
    public static float summonSoundPitch = 0.85F;

    /**
     * Load configuration from file, or create default if missing.
     */
    public static void load() {
        if (!CONFIG_FILE.exists()) {
            createDefaultConfig();
        } else {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                ConfigData data = GSON.fromJson(reader, ConfigData.class);
                if (data != null) {
                    applyConfig(data);
                    OrbitalWolfCannonMod.LOGGER.info("Loaded config from {}", CONFIG_FILE.getAbsolutePath());
                }
            } catch (IOException e) {
                OrbitalWolfCannonMod.LOGGER.error("Failed to load config file, using defaults", e);
            }
        }
    }

    /**
     * Create a default configuration file with examples.
     */
    private static void createDefaultConfig() {
        try {
            Files.createDirectories(CONFIG_DIR);

            ConfigData defaultData = new ConfigData();
            defaultData.wolf_count = wolfCount;
            defaultData.circle_radius = circleRadius;
            defaultData.cooldown_seconds = cooldownSeconds;
            defaultData.wolf_despawn_seconds = 300; // 5 minutes
            defaultData.auto_despawn_enabled = autoDespawnEnabled;
            defaultData.strength_duration_ticks = strengthDurationTicks;
            defaultData.strength_amplifier = strengthAmplifier;
            defaultData.summon_sound_volume = summonSoundVolume;
            defaultData.summon_sound_pitch = summonSoundPitch;

            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                GSON.toJson(defaultData, writer);
            }

            OrbitalWolfCannonMod.LOGGER.info("Created default config at {}", CONFIG_FILE.getAbsolutePath());
        } catch (IOException e) {
            OrbitalWolfCannonMod.LOGGER.error("Failed to create default config file", e);
        }
    }

    /**
     * Apply loaded configuration to static fields.
     */
    private static void applyConfig(ConfigData data) {
        wolfCount = Math.max(1, data.wolf_count);
        circleRadius = Math.max(0.5D, data.circle_radius);
        cooldownSeconds = Math.max(0, data.cooldown_seconds);
        wolfDespawnTicks = data.wolf_despawn_seconds * 20L;
        autoDespawnEnabled = data.auto_despawn_enabled;
        strengthDurationTicks = Math.max(0, data.strength_duration_ticks);
        strengthAmplifier = Math.max(0, data.strength_amplifier);
        summonSoundVolume = Math.max(0.0F, data.summon_sound_volume);
        summonSoundPitch = Math.max(0.1F, data.summon_sound_pitch);

        OrbitalWolfCannonMod.LOGGER.info("Applied configuration: {} wolves, radius={}, cooldown={}s, despawn={}s",
                wolfCount, circleRadius, cooldownSeconds, data.wolf_despawn_seconds);
    }

    /**
     * Inner class for JSON serialization/deserialization.
     */
    public static class ConfigData {
        public int wolf_count;
        public double circle_radius;
        public int cooldown_seconds;
        public int wolf_despawn_seconds;
        public boolean auto_despawn_enabled;
        public int strength_duration_ticks;
        public int strength_amplifier;
        public float summon_sound_volume;
        public float summon_sound_pitch;
    }
}

