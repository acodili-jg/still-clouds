package io.github.acodili.jg.still_clouds.engine;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_UNDERSCORE;

import java.util.Map;
import java.util.NoSuchElementException;

import net.minecraft.util.OptionEnum;

/**
 * The constants of this enumerated class provide the different strategies to reposition the clouds'
 * center. 
 */
public enum CloudRepositionStrategy implements OptionEnum {
    /**
     * A strategy that keeps the clouds' original position.
     */
    NONE() {
        @Override
        public double apply(final double cameraPosition, final double alternatePosition,
                final double originalPosition, final Map<Object, Object> parameters) {
            return originalPosition;
        }
    },
    /**
     * A strategy that keeps the clouds' original position from a single point in time.
     */
    PAUSE() {
        @Override
        public double apply(final double cameraPosition, final double alternatePosition,
                final double originalPosition, final Map<Object, Object> parameters) {
            return alternatePosition;
        }
    },
    /**
     * A strategy that centers the clouds to the camera, with some offset.
     */
    RELATIVE() {
        @Override
        public double apply(final double cameraPosition, final double alternatePosition,
                final double originalPosition, final Map<Object, Object> parameters) {
            return (parameters.get("relativePosition") instanceof final Number relativePosition ?
                    relativePosition.doubleValue() : 0.0) - cameraPosition;
        }
    },
    /**
     * A strategy that fixes the clouds' center on an absolute position.
     */
    ABSOLUTE() {
        @Override
        public double apply(final double cameraPosition, final double alternatePosition,
                final double originalPosition, final Map<Object, Object> parameters) {
            return parameters.get("absolutePosition") instanceof final Number absolutePosition ?
                    absolutePosition.doubleValue() : 0.0;
        }
    };

    /**
     * Returns the {@code CloudRepositionStrategy} with the matching {@code id}.
     *
     * @param id the cloud reposition strategy's id
     * @return the cloud reposition strategy with the matching id
     * @throw NoSuchElementException if there is no match
     */
    public static CloudRepositionStrategy byId(final int id) {
        final var values = values();

        if (id < 0 || id >= values.length)
            throw new NoSuchElementException("Ease of id " + id + " does not exist");

        return values[id];
    }

    /**
     * The translatable component key of this ease.
     */
    private final String key;

    /**
     * Constructs a new reposition mode.
     */
    private CloudRepositionStrategy() {
        this.key = "still-clouds.options.repositionStrategy." + UPPER_UNDERSCORE.to(LOWER_CAMEL, name());
    }

    /**
     * Applies the reposition.
     *
     * @param cameraPosition    the camera position
     * @param alternatePosition the alternate position
     * @param originalPosition  the original position
     * @param parameters        the extra parameters
     * @return the new position
     */
    public abstract double apply(double cameraPosition, double alternatePosition,
            double originalPosition, Map<Object, Object> parameters);

    /**
     * Equivalent to {@link #ordinal()}.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public int getId() {
        return ordinal();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getKey() {
        return this.key;
    }
}
