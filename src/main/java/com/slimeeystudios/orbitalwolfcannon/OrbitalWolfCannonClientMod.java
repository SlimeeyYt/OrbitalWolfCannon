package com.slimeeystudios.orbitalwolfcannon;

import com.slimeeystudios.orbitalwolfcannon.registry.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

public class OrbitalWolfCannonClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            if (stack.is(ModItems.ORBITAL_WOLF_ROD)) {
                lines.add(Component.translatable("item.orbital_wolf_cannon.orbital_wolf_rod.tooltip").withStyle(ChatFormatting.GRAY));
            }
        });
    }
}

