package io.github.acodili.jg.still_clouds.config;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;

import io.github.acodili.jg.still_clouds.engine.CloudRepositionStrategy;
import io.github.acodili.jg.still_clouds.util.Ease;
import io.github.acodili.jg.still_clouds.util.Prototype;

/**
 * Still Clouds' config view serves a purpose to mark configs as view-only, though there is casting
 * allows promotion towards writability the caller is doing so at their own risk.
 */
public interface StillCloudsConfig extends Prototype {
    /**
     * {@inheritDoc}
     */
    StillCloudsConfig clone();

    Map<Object, Object> getRepositionParameters();

    /**
     * Returns the reposition strategy.
     *
     * @return the reposition strategy
     * @implSpec This method should never return {@code null}.
     */
    CloudRepositionStrategy getRepositionStrategy();

    /**
     * Returns the transition duration.
     *
     * @return the transition duration
     * @implSpec This method should never return {@code null} or be negative.
     */
    Duration getTransitionDuration();

    /**
     * Returns the transition ease.
     *
     * @return the transition ease
     * @implSpec This method should never return {@code null}.
     */
    Ease getTransitionEase();

    /**
     * Sets the fields with values copied from another's.
     *
     * @param other the other config
     * @return {@code this}, after chained operations
     */
    default StillCloudsConfig setAll(final StillCloudsConfig other) {
        Objects.requireNonNull(other, "Parameter other is null");
        if (this == other)
            return this;

        getRepositionParameters().clear();
        getRepositionParameters().putAll(other.getRepositionParameters());

        return setRepositionStrategy(other.getRepositionStrategy())
                .setTransitionDuration(other.getTransitionDuration())
                .setTransitionEase(other.getTransitionEase());
    }

    /**
     * Sets the reposition strategy.
     *
     * @param repositionStrategy the reposition strategy
     * @return {@code this}, for builder pattern
     * @throws NullPointerException thrown when {@code repositionStrategy} is {@code null}
     */
    StillCloudsConfig setRepositionStrategy(CloudRepositionStrategy repositionStrategy);

    /**
     * Sets the transition duration.
     *
     * @param transitionDuration the transition duration
     * @return {@code this}, for builder pattern
     * @throws NullPointerException thrown when {@code transitionDuration} is {@code null}
     * @throws IllegalArgumentException thrown when {@code transitionDuration} is negative
     */
    StillCloudsConfig setTransitionDuration(Duration transitionDuration);

    /**
     * Sets the transition ease.
     *
     * @param transitionEase the transition ease
     * @return {@code this}, for builder pattern
     * @throws NullPointerException thrown when {@code transitionEase} is {@code null}
     */
    StillCloudsConfig setTransitionEase(Ease transitionEase);
}
