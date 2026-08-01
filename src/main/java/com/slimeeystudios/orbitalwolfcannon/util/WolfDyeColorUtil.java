package com.slimeeystudios.orbitalwolfcannon.util;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.DyeColor;

/**
 * Utility class for managing wolf dye colors.
 * Minecraft has 16 dye colors that correspond to wolf collar colors.
 */
public class WolfDyeColorUtil {
    
    // All 16 Minecraft dye colors in order
    public static final DyeColor[] ALL_COLORS = {
        DyeColor.WHITE,
        DyeColor.ORANGE,
        DyeColor.MAGENTA,
        DyeColor.LIGHT_BLUE,
        DyeColor.YELLOW,
        DyeColor.LIME,
        DyeColor.PINK,
        DyeColor.GRAY,
        DyeColor.LIGHT_GRAY,
        DyeColor.CYAN,
        DyeColor.PURPLE,
        DyeColor.BLUE,
        DyeColor.BROWN,
        DyeColor.GREEN,
        DyeColor.RED,
        DyeColor.BLACK
    };

    /**
     * Get the dye color for a wolf based on index.
     * Cycles through all 16 colors evenly.
     * 
     * @param wolfIndex The index of the wolf being spawned
     * @return The DyeColor for this wolf
     */
    public static DyeColor getColorForWolf(int wolfIndex) {
        return ALL_COLORS[wolfIndex % ALL_COLORS.length];
    }

    /**
     * Get the dye item for a specific color.
     * 
     * @param color The dye color
     * @return The corresponding dye item
     */
    public static net.minecraft.world.item.Item getDyeItem(DyeColor color) {
        return Items.DYE.pick(color);
    }
}

