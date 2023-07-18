package io.github.acodili.jg.still_clouds.engine;

import io.github.acodili.jg.still_clouds.config.StillCloudsConfig;

/**
 * Still Clouds' engine handles the communication between this mod's state and the renderer's state.
 */
public interface StillCloudsEngine {
    double calculateReposition(double cameraPosition, double currentTicks,
            double absoluteCloudsCenter);

    /**
     * Returns the config.
     *
     * @return the config
     * @implSpec Should never return {@code null}.
     */
    StillCloudsConfig getConfig();

    /**
     * Stops the clouds' center transition and use the current progress as the new start of the next
     * transition.
     */
    void interruptTransition();
}
