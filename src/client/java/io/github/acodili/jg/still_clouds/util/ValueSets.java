package io.github.acodili.jg.still_clouds.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumSet;
import java.util.List;

import com.mojang.serialization.Codec;

import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
import it.unimi.dsi.fastutil.objects.Object2IntFunction;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.OptionInstance.ValueSet;
import net.minecraft.util.OptionEnum;

/**
 * {@code ValueSets} is a utility class around {@link OptionInstance}{@code .ValueSet}.
 */
public final class ValueSets {
    /**
     * Creates a value set containing all the constants of a specific enum type.
     *
     * @param <T>            The enum type to make a value set for
     * @param clazz          the enum class
     * @param fromIdFunction the function to convert an id to an enum constant
     * @param toIdFunction   the function to convert an enum constant to an id
     * @return a value set
     */
    public static <T extends Enum<T>> OptionInstance.Enum<T> allOfEnum(final Class<T> clazz,
            final Int2ObjectFunction<? extends T> fromIdFunction,
            final Object2IntFunction<? super T> toIdFunction) {
        return ofEnum(EnumSet.allOf(clazz), fromIdFunction, toIdFunction);
    }

    /**
     * Creates a value set containing all the constants of a specific option enum type.
     *
     * @param <T>            The enum type to make a value set for
     * @param clazz          the option enum class
     * @param fromIdFunction the function to convert an id to an option enum constant
     * @return a value set
     */
    public static <T extends Enum<T> & OptionEnum> OptionInstance.Enum<T> allOfOptionEnum(
            final Class<T> clazz,
            final Int2ObjectFunction<? extends T> fromIdFunction) {
        return allOfEnum(clazz, fromIdFunction, x -> ((OptionEnum) x).getId());
    }

    /**
     * Creates a value set containing the constant from the enum set for a specific enum type.
     *
     * @param <T>            The enum type to make a value set for
     * @param values         the constants for the value set
     * @param fromIdFunction the function to convert an id to an enum constant
     * @param toIdFunction   the function to convert an enum constant to an id
     * @return a value set
     */
    public static <T extends Enum<T>> OptionInstance.Enum<T> ofEnum(final EnumSet<T> values,
            final Int2ObjectFunction<? extends T> fromIdFunction,
            final Object2IntFunction<? super T> toIdFunction) {
        return new OptionInstance.Enum<>(List.copyOf(values),
                Codec.INT.xmap(fromIdFunction, toIdFunction));
    }

    /**
     * Creates a increasing bounded range value set with a set step.
     *
     * @param start the start of the range
     * @param end   the end of the range
     * @param step  the step of the range
     * @return a value set
     */
    public static ValueSet<BigDecimal> range(final BigDecimal start, final BigDecimal end,
            final BigDecimal step) {
        if (end.compareTo(start) < 0)
            throw new IllegalArgumentException("end < start");
        if (step.signum() < 0)
            throw new IllegalArgumentException("Parameter step is negative");

        final var delta = end.subtract(start);
        final var bigStepCount = delta.divideToIntegralValue(step).add(BigDecimal.ONE);
        final var stepCount = bigStepCount.intValue();

        return new OptionInstance.IntRange(0, stepCount).xmap(
                i -> i <= 0 ? start : i >= stepCount ? end :
                        BigDecimal.valueOf(i)
                                .multiply(delta)
                                .divide(bigStepCount, step.scale(), RoundingMode.HALF_UP)
                                .add(start)
                                .setScale(step.scale(), RoundingMode.HALF_UP),
                x -> x.compareTo(start) <= 0 ? 0 : x.compareTo(end) >= 0 ? stepCount :
                        x.subtract(start).multiply(bigStepCount).divideToIntegralValue(delta)
                                .intValue());
    }

    /**
     * Creates a increasing bounded range value set with a set step.
     *
     * @param start the start of the range
     * @param end   the end of the range
     * @param step  the step of the range
     * @return a value set
     */
    public static ValueSet<Double> range(final double start, final double end, final double step) {
        if (Double.isNaN(start))
            throw new IllegalArgumentException("Parameter start is not a number");
        if (start == Double.NEGATIVE_INFINITY)
            throw new IllegalArgumentException("Parameter start is negatively infinite");
        if (start == Double.POSITIVE_INFINITY)
            throw new IllegalArgumentException("Parameter start is positively infinite");
        if (Double.isNaN(end))
            throw new IllegalArgumentException("Parameter end is not a number");
        if (end == Double.NEGATIVE_INFINITY)
            throw new IllegalArgumentException("Parameter end is negatively infinite");
        if (end == Double.POSITIVE_INFINITY)
            throw new IllegalArgumentException("Parameter end is positively infinite");
        if (end < start)
            throw new IllegalArgumentException("end < start");
        if (Double.isNaN(step))
            throw new IllegalArgumentException("Parameter step is not a number");
        if (step < +0.0)
            throw new IllegalArgumentException("Parameter step is negative");
        if (step == Double.POSITIVE_INFINITY)
            throw new IllegalArgumentException("Parameter step is positively infinite");

        final var delta = end - start;
        final var stepCount = (int) Math.ceil(delta / step);

        return new OptionInstance.IntRange(0, stepCount).xmap(
                i -> i <= 0 ? start : i >= stepCount ? end : start + delta * i / stepCount,
                x -> x <= start ? 0 : x >= end ? stepCount : (int) (stepCount * (x - start) / delta));
    }

    /**
     * Constructs a new {@code ValuesSets} instance.
     */
    private ValueSets() {}
}
