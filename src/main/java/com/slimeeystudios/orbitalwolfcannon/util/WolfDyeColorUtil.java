package com.slimeeystudios.orbitalwolfcannon.util;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;

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
        return switch (color) {
            case WHITE -> Items.WHITE_DYE;
            case ORANGE -> Items.ORANGE_DYE;
            case MAGENTA -> Items.MAGENTA_DYE;
            case LIGHT_BLUE -> Items.LIGHT_BLUE_DYE;
            case YELLOW -> Items.YELLOW_DYE;
            case LIME -> Items.LIME_DYE;
            case PINK -> Items.PINK_DYE;
            case GRAY -> Items.GRAY_DYE;
            case LIGHT_GRAY -> Items.LIGHT_GRAY_DYE;
            case CYAN -> Items.CYAN_DYE;
            case PURPLE -> Items.PURPLE_DYE;
            case BLUE -> Items.BLUE_DYE;
            case BROWN -> Items.BROWN_DYE;
            case GREEN -> Items.GREEN_DYE;
            case RED -> Items.RED_DYE;
            case BLACK -> Items.BLACK_DYE;
        };
    }
}
