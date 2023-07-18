package io.github.acodili.jg.still_clouds.util;

import java.time.Duration;
import java.util.Objects;

import net.minecraft.client.OptionInstance;
import net.minecraft.client.OptionInstance.CaptionBasedToString;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;

/**
 * {@code ValueLabels}, the name is based on
 * {@link Options#genericValueLabel(Component, Component)}, is a utility class around
 * {@link CaptionBasedToString}.
 */
public final class ValueLabels {
    /**
     * Caption provider for {@link Duration} objects.
     */
    public static final OptionInstance.CaptionBasedToString<Duration> DURATION_CAPTIONS;

    static {
        DURATION_CAPTIONS = (component, duration) -> Options.genericValueLabel(component,
                ValueLabels.duration(duration));
    }

    /**
     * Creates a value label component for a given {@link Duration} object.
     * 
     * @param duration the duration to create value label for
     * @return the value label component for the given duration object
     * @throws NullPointerException if {@code duration} is {@code null}
     */
    public static Component duration(final Duration duration) {
        Objects.requireNonNull(duration, "Parameter duration is null");
        if (duration.isNegative() || duration.isZero())
            return Component.translatable("still-clouds.options.duration.instant");

        final var hoursPart = duration.toHoursPart();
        final var minutesPart = duration.toMinutesPart();
        final var hours = hoursPart + minutesPart / 60d;

        if (hours > 1)
            return Component.translatable("still-clouds.options.duration.hours", hours);
        if (hours >= 0.1)
            return Component.translatable("still-clouds.options.duration.hour", hours);

        final var secondsPart = duration.toSecondsPart();
        final var minutes = minutesPart + secondsPart / 60d;

        if (minutes > 1)
            return Component.translatable("still-clouds.options.duration.minutes",
                    minutes);
        if (minutes >= 0.1)
            return Component.translatable("still-clouds.options.duration.minute",
                    minutes);

        final var nanosPart = duration.toNanosPart();
        final var seconds = secondsPart + nanosPart / 1_000_000_000d;

        if (seconds > 1)
            return Component.translatable("still-clouds.options.duration.seconds",
                    seconds);
        if (seconds >= 0.1)
            return Component.translatable("still-clouds.options.duration.second",
                    seconds);

        if (nanosPart > 1)
            return Component.translatable("still-clouds.options.duration.nanos", nanosPart);
        else
            return Component.translatable("still-clouds.options.duration.nano", nanosPart);
    }

    /**
     * Constructs a new {@code ValueLabels} instance.
     */
    private ValueLabels() {
    }
}
