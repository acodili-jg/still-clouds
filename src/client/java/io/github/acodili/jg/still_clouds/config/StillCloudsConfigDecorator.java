package io.github.acodili.jg.still_clouds.config;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;

import io.github.acodili.jg.still_clouds.engine.CloudRepositionStrategy;
import io.github.acodili.jg.still_clouds.util.Ease;

public abstract class StillCloudsConfigDecorator implements StillCloudsConfig {
    /**
     * The decorated instance.
     */
    private StillCloudsConfig decoratend;

    /**
     * Constructs a new {@code StillCloudsConfigDecorator}.
     */
    protected StillCloudsConfigDecorator(final StillCloudsConfig decoratend) {
        Objects.requireNonNull(decoratend, "Parameter decoratend is null");

        this.decoratend = decoratend;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract StillCloudsConfigDecorator clone();

    /**
     * Returns the decorated instance.
     *
     * @return the decorated instance
     */
    protected final StillCloudsConfig decoratend() {
        return this.decoratend;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Object, Object> getRepositionParameters() {
        return this.decoratend.getRepositionParameters();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CloudRepositionStrategy getRepositionStrategy() {
        return this.decoratend.getRepositionStrategy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Duration getTransitionDuration() {
        return this.decoratend.getTransitionDuration();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ease getTransitionEase() {
        return this.decoratend.getTransitionEase();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StillCloudsConfig setAll(final StillCloudsConfig other) {
        this.decoratend.setAll(other);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StillCloudsConfig setRepositionStrategy(final CloudRepositionStrategy repositionStrategy) {
        this.decoratend.setRepositionStrategy(repositionStrategy);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StillCloudsConfig setTransitionDuration(final Duration transitionDuration) {
        this.decoratend.setTransitionDuration(transitionDuration);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StillCloudsConfig setTransitionEase(final Ease transitionEase) {
        this.decoratend.setTransitionEase(transitionEase);
        return this;
    }
}
