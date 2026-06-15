package com.slimeeystudios.orbitalwolfcannon.item;

import com.slimeeystudios.orbitalwolfcannon.config.OrbitalWolfCannonConfig;
import com.slimeeystudios.orbitalwolfcannon.entity.WolfStrikeManager;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class OrbitalWolfRodItem extends Item {

    public OrbitalWolfRodItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResult use(Level level, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (!(user instanceof ServerPlayer serverPlayer)) {
            return InteractionResult.FAIL;
        }

        if (serverPlayer.getCooldowns().isOnCooldown(stack)) {
            serverPlayer.sendOverlayMessage(Component.translatable("item.orbital_wolf_cannon.orbital_wolf_rod.cooldown"));
            return InteractionResult.FAIL;
        }

        int cooldownTicks = OrbitalWolfCannonConfig.cooldownSeconds * 20;
        serverPlayer.getCooldowns().addCooldown(stack, cooldownTicks);
        WolfStrikeManager.executeStrike(serverPlayer);

        return InteractionResult.SUCCESS;
    }
}
