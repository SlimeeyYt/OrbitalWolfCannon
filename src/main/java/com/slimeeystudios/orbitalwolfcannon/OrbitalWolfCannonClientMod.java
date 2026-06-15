package com.slimeeystudios.orbitalwolfcannon;

import com.slimeeystudios.orbitalwolfcannon.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = OrbitalWolfCannonMod.MOD_ID, value = Dist.CLIENT)
public class OrbitalWolfCannonClientMod {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().is(ModItems.ORBITAL_WOLF_ROD.get())) {
            event.getToolTip().add(Component.translatable("item.orbital_wolf_cannon.orbital_wolf_rod.tooltip").withStyle(ChatFormatting.GRAY));
        }
    }
}
