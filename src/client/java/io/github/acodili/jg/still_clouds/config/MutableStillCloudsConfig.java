package io.github.acodili.jg.still_clouds.config;

import java.io.Serializable;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;

import io.github.acodili.jg.still_clouds.engine.CloudRepositionStrategy;
import io.github.acodili.jg.still_clouds.util.Ease;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

/**
 * Still Clouds' config class.
 */
public class MutableStillCloudsConfig implements Serializable, StillCloudsConfig {
    private final Object2ObjectLinkedOpenHashMap<Object, Object> repositionParameters;

    /**
     * The reposition strategy.
     */
    private CloudRepositionStrategy repositionStrategy;

    /**
     * The transition duration.
     */
    private Duration transitionDuration;

    /**
     * The transition ease.
     */
    private Ease transitionEase;

    /**
     * Constructs a new config.
     */
    public MutableStillCloudsConfig() {
        this.repositionParameters = new Object2ObjectLinkedOpenHashMap<>();
        this.repositionStrategy = CloudRepositionStrategy.NONE;
        this.transitionDuration = Duration.ofSeconds(1);
        this.transitionEase = Ease.SINE_EASE_IN_OUT;
    }

    /**
     * Constructs a new config with the fields set to the values of another.
     *
     * @throws NullPointerException thrown when {@code other} is {@code null}
     */
    public MutableStillCloudsConfig(final StillCloudsConfig other) {
        Objects.requireNonNull(other, "Parameter other is null");

        this.repositionParameters = new Object2ObjectLinkedOpenHashMap<>(
                other.getRepositionParameters());
        this.repositionStrategy = other.getRepositionStrategy();
        this.transitionDuration = other.getTransitionDuration();
        this.transitionEase = other.getTransitionEase();
    }

    /**
     * {@inheritDoc}
     */ 
    @Override
    public MutableStillCloudsConfig clone() {
        return new MutableStillCloudsConfig(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        else if (obj instanceof final StillCloudsConfig other)
            return getRepositionParameters().equals(other.getRepositionParameters()) &&
                    getRepositionStrategy().equals(other.getRepositionStrategy()) &&
                    getTransitionDuration().equals(other.getTransitionDuration()) &&
                    getTransitionEase() == other.getTransitionEase();
        else
            return false;
    }

    /**
     * {@inheritDoc}
     */
    public Map<Object, Object> getRepositionParameters() {
        return this.repositionParameters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CloudRepositionStrategy getRepositionStrategy() {
        return this.repositionStrategy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Duration getTransitionDuration() {
        return this.transitionDuration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ease getTransitionEase() {
        return this.transitionEase;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getRepositionStrategy(), getTransitionDuration(), getTransitionEase());
    }

    /**
     * {@inheritDoc}
     *
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public StillCloudsConfig setRepositionStrategy(final CloudRepositionStrategy repositionStrategy) {
        this.repositionStrategy = repositionStrategy;

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @throws NullPointerException {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public StillCloudsConfig setTransitionDuration(final Duration transitionDuration) {
        Objects.requireNonNull(transitionDuration, "Parameter transitionDuration is null");
        if (transitionDuration.isNegative())
            throw new IllegalArgumentException("Parameter transitionDuration is negative");

        this.transitionDuration = transitionDuration;

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public StillCloudsConfig setTransitionEase(final Ease transitionEase) {
        Objects.requireNonNull(transitionEase, "Parameter transitionEase is null");

        this.transitionEase = transitionEase;

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getClass().getName() + "[repositionStrategy=" + getRepositionStrategy() +
                ",transitionDuration=" + getTransitionDuration() + ",transitionEase=" +
                getTransitionEase() + "]";
    }
}
