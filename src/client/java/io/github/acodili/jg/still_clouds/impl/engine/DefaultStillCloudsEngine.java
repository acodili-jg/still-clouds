package io.github.acodili.jg.still_clouds.impl.engine;

import static io.github.acodili.jg.still_clouds.util.Units.SECONDS_PER_NANO;
import static io.github.acodili.jg.still_clouds.util.Units.TICKS_PER_SECOND;

import java.time.Duration;

import io.github.acodili.jg.still_clouds.config.MutableStillCloudsConfig;
import io.github.acodili.jg.still_clouds.config.StillCloudsConfig;
import io.github.acodili.jg.still_clouds.config.StillCloudsConfigDecorator;
import io.github.acodili.jg.still_clouds.engine.CloudRepositionStrategy;
import io.github.acodili.jg.still_clouds.engine.StillCloudsEngine;
import io.github.acodili.jg.still_clouds.util.Clouds;
import io.github.acodili.jg.still_clouds.util.Ease;
import net.minecraft.util.Mth;

/**
 * The default implementation of {@link StillCloudsEngine}.
 */
public class DefaultStillCloudsEngine implements StillCloudsEngine {
    protected class TransitionInterruptingStillCloudsConfigDecorator extends StillCloudsConfigDecorator {
        public TransitionInterruptingStillCloudsConfigDecorator(StillCloudsConfig decoratend) {
            super(decoratend);
        }

        @Override
        public StillCloudsConfigDecorator clone() {
            return new TransitionInterruptingStillCloudsConfigDecorator(decoratend().clone());
        }

        @Override
        public StillCloudsConfig setAll(StillCloudsConfig other) {
            interruptTransition();
            return super.setAll(other);
        }

        @Override
        public StillCloudsConfig setRepositionStrategy(CloudRepositionStrategy repositionStrategy) {
            interruptTransition();
            return super.setRepositionStrategy(repositionStrategy);
        }

        @Override
        public StillCloudsConfig setTransitionDuration(Duration transitionDuration) {
            interruptTransition();
            return super.setTransitionDuration(transitionDuration);
        }

        @Override
        public StillCloudsConfig setTransitionEase(Ease transitionEase) {
            interruptTransition();
            return super.setTransitionEase(transitionEase);
        }
    }

    private final StillCloudsConfig config;

    private double recentCloudsCenter;

    private double recentTicks;

    private double startingCloudsCenter;

    private double startingTicks;

    /**
     * Constructs a new default implementation of {@code StillCloudsEngine}.
     */
    public DefaultStillCloudsEngine() {
        this.config = new TransitionInterruptingStillCloudsConfigDecorator(
                new MutableStillCloudsConfig());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculateReposition(final double cameraPosition, final double currentTicks,
            final double absoluteCloudsCenter) {
        final double repositionedCenter; {
            final var config = getConfig();
            final var lastCenter = Clouds.wrapInBounds(this.startingCloudsCenter);
            final var nextCenter = Clouds.wrapInBounds(config.getRepositionStrategy().apply(
                    cameraPosition, this.startingCloudsCenter, absoluteCloudsCenter,
                    config.getRepositionParameters())); 
            final double transitionProgress; {
                final var deltaTicks = currentTicks - this.startingTicks;
                final var transitionTicks = config.getTransitionDuration().toNanos() * SECONDS_PER_NANO *
                        TICKS_PER_SECOND;
                final var absoluteTransitionProgress = Mth.clamp(deltaTicks / transitionTicks, 0, 1);

                transitionProgress = config.getTransitionEase().apply(absoluteTransitionProgress);
            }

            repositionedCenter = Mth.lerp(transitionProgress, lastCenter, nextCenter);
        }

        this.recentCloudsCenter = repositionedCenter;
        this.recentTicks = currentTicks;

        return repositionedCenter;
    }

    @Override
    public StillCloudsConfig getConfig() {
        return this.config;
    }

    @Override
    public void interruptTransition() {
        this.startingCloudsCenter = this.recentCloudsCenter;
        this.startingTicks = this.recentTicks;
    }
}
