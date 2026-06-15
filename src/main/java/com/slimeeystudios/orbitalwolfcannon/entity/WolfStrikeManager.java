package com.slimeeystudios.orbitalwolfcannon.entity;

import com.slimeeystudios.orbitalwolfcannon.OrbitalWolfCannonMod;
import com.slimeeystudios.orbitalwolfcannon.config.OrbitalWolfCannonConfig;
import com.slimeeystudios.orbitalwolfcannon.util.CirclePositionUtil;
import com.slimeeystudios.orbitalwolfcannon.util.WolfDyeColorUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class WolfStrikeManager {

    private static final Map<UUID, Long> TRACKED_WOLVES = new HashMap<>();

    private WolfStrikeManager() {
    }

    public static void registerTickHandler() {
        ServerTickEvents.END_SERVER_TICK.register(WolfStrikeManager::onServerTick);
    }

    public static void executeStrike(ServerPlayer player) {
        ServerLevel world = (ServerLevel) player.level();

        spawnActivationRing(world, player);
        world.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.ENDER_DRAGON_GROWL,
                SoundSource.PLAYERS,
                OrbitalWolfCannonConfig.summonSoundVolume,
                OrbitalWolfCannonConfig.summonSoundPitch
        );

        List<double[]> positions = CirclePositionUtil.evenlyDistributedXZ(
                player.getX(),
                player.getZ(),
                OrbitalWolfCannonConfig.circleRadius,
                OrbitalWolfCannonConfig.wolfCount
        );

        for (int i = 0; i < positions.size(); i++) {
            double[] position = positions.get(i);
            summonSingleWolf(world, player, position[0], player.getY(), position[1], i);
        }
    }

    private static void summonSingleWolf(ServerLevel world, ServerPlayer owner, double x, double y, double z, int wolfIndex) {
        Wolf wolf = EntityType.WOLF.create(world, EntitySpawnReason.EVENT);
        if (wolf == null) {
            OrbitalWolfCannonMod.LOGGER.warn("Failed to create wolf entity during Orbital Wolf Rod activation.");
            return;
        }

        wolf.snapTo(x, y, z, owner.getYRot(), 0.0F);
        wolf.setOwner(owner);
        wolf.setTame(true, true);
        wolf.setOrderedToSit(false);
        
        DyeColor collarColor = WolfDyeColorUtil.getColorForWolf(wolfIndex);
        try {
            java.lang.reflect.Method method = Wolf.class.getDeclaredMethod("setCollarColor", net.minecraft.world.item.DyeColor.class);
            method.setAccessible(true);
            method.invoke(wolf, collarColor);
        } catch (Exception e) {
            OrbitalWolfCannonMod.LOGGER.debug("Could not set wolf collar color: {}", e.getMessage());
        }
        
        wolf.setItemSlot(EquipmentSlot.BODY, new ItemStack(Items.WOLF_ARMOR));
        wolf.addEffect(new MobEffectInstance(MobEffects.STRENGTH, OrbitalWolfCannonConfig.strengthDurationTicks, OrbitalWolfCannonConfig.strengthAmplifier));

        world.addFreshEntity(wolf);
        retargetToNearbyHostile(wolf);

        world.sendParticles(ParticleTypes.EXPLOSION, x, y + 0.2D, z, 2, 0.15D, 0.05D, 0.15D, 0.01D);
        world.sendParticles(ParticleTypes.CLOUD, x, y + 0.1D, z, 5, 0.2D, 0.06D, 0.2D, 0.01D);

        if (OrbitalWolfCannonConfig.autoDespawnEnabled) {
            TRACKED_WOLVES.put(wolf.getUUID(), world.getGameTime() + OrbitalWolfCannonConfig.wolfDespawnTicks);
        }
    }

    private static void spawnActivationRing(ServerLevel world, ServerPlayer player) {
        List<double[]> ring = CirclePositionUtil.evenlyDistributedXZ(player.getX(), player.getZ(), OrbitalWolfCannonConfig.circleRadius, 72);
        double y = player.getY() + 0.05D;

        for (double[] position : ring) {
            world.sendParticles(ParticleTypes.CLOUD, position[0], y, position[1], 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private static void retargetToNearbyHostile(Wolf wolf) {
        AABB scanBox = wolf.getBoundingBox().inflate(16.0D);
        List<Monster> hostiles = wolf.level().getEntitiesOfClass(Monster.class, scanBox, LivingEntity::isAlive);
        if (!hostiles.isEmpty()) {
            wolf.setTarget(hostiles.get(0));
        }
    }

    private static void onServerTick(MinecraftServer server) {
        if (TRACKED_WOLVES.isEmpty()) {
            return;
        }

        for (ServerLevel world : server.getAllLevels()) {
            cleanupAndRetarget(world);
        }
    }

    private static void cleanupAndRetarget(ServerLevel world) {
        long worldTime = world.getGameTime();
        Iterator<Map.Entry<UUID, Long>> iterator = TRACKED_WOLVES.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<UUID, Long> entry = iterator.next();
            Entity entity = world.getEntity(entry.getKey());

            if (!(entity instanceof Wolf wolf) || !wolf.isAlive()) {
                iterator.remove();
                continue;
            }

            if (OrbitalWolfCannonConfig.autoDespawnEnabled && worldTime >= entry.getValue()) {
                wolf.discard();
                iterator.remove();
                continue;
            }

            // Keep wolves standing (prevent sitting)
            if (wolf.isOrderedToSit()) {
                wolf.setOrderedToSit(false);
            }

            if (worldTime % 20L == 0L && (wolf.getTarget() == null || !wolf.getTarget().isAlive())) {
                retargetToNearbyHostile(wolf);
            }
        }
    }
}

