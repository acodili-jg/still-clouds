package io.github.acodili.jg.still_clouds.util;

import net.minecraft.util.Mth;

/**
 * {@code Clouds} is a utility class around Minecraft vanilla clouds.
 */
public final class Clouds {
    /**
     * The size of the clouds texture when rendered in full on the world without tilling or
     * wrapping.
     */
    public static final int SIZE = 3072;

    /**
     * Half of {@link #SIZE}.
     */
    public static final int SIZE_HALVED = SIZE / 2;

    /**
     * Returns the position wrapped in the bounds of the clouds.
     * 
     * @param component the position value on a single axis
     * @return the position wrapped in the bounds of the clouds
     */
    public static double wrapInBounds(final double component) {
        return Mth.positiveModulo(component + SIZE_HALVED, SIZE) - SIZE_HALVED;
    }

    /**
     * Constructs a new {@code Clouds} instace.
     */
    private Clouds() {}
}
