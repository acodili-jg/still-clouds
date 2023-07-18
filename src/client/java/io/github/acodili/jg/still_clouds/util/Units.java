package io.github.acodili.jg.still_clouds.util;

/**
 * {@code Units} is a utility class around units and unit conversion. 
 */
public final class Units {
    /**
     * The amount of seconds over one nanosecond.
     */
    public static final double SECONDS_PER_NANO = (double) 1 / 1_000_000_000;

    /**
     * The amount of ticks over one second.
     */
    public static final double TICKS_PER_SECOND = (double) 20 / 1;

    /**
     * Constructs a new {@code Units} instance.
     */
    private Units() {}
}
