package io.github.acodili.jg.still_clouds;

import static com.mojang.blaze3d.platform.InputConstants.UNKNOWN;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import io.github.acodili.jg.still_clouds.config.MutableStillCloudsConfig;
import io.github.acodili.jg.still_clouds.config.StillCloudsConfig;
import io.github.acodili.jg.still_clouds.engine.StillCloudsEngine;
import io.github.acodili.jg.still_clouds.gui.screens.StillCloudsVanillaConfigScreen;
import io.github.acodili.jg.still_clouds.impl.engine.DefaultStillCloudsEngine;
import io.github.acodili.jg.still_clouds.util.TypeAdapters;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.Screen;

/**
 * Still Clouds' client class handles initialization, registration, config IO, and logging.
 */
public final class StillCloudsClient {
    /**
     * This class' singleton instance.
     */
    private static volatile StillCloudsClient instance;

    /**
     * The mod's client-side logger
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger("still-clouds");
    }

    /**
     * Returns the config path.
     *
     * @return the config path
     */
    public static Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir().resolve("still-clouds.json");
    }

    /**
     * Returns the singleton instance.
     *
     * @return the singleton instance
     */
    public static StillCloudsClient getInstance() {
        var instance = StillCloudsClient.instance;

        if (instance != null)
            return instance;

        synchronized (StillCloudsClient.class) {
            instance = StillCloudsClient.instance;

            if (instance == null)
                StillCloudsClient.instance = instance = new StillCloudsClient();
        }

        return instance;
    }

    /**
     * Returns the logger.
     *
     * @return the logger
     */
    public static Logger getLogger() {
        return LOGGER;
    }

    /**
     * The engine.
     */
    private StillCloudsEngine engine;

    /**
     * The gson is used in serialization.
     */
    private Gson gson;

    /**
     * The cycle reposition modes key.
     */
    private final KeyMapping cycleRepositionStrategiesKey;

    /**
     * The cycle transition eases key.
     */
    private final KeyMapping cycleTransitionEasesKey;

    /**
     * Creates a new Still Clouds client.
     */
    private StillCloudsClient() {
        this.engine = new DefaultStillCloudsEngine();
        this.gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapterFactory(TypeAdapters.DURATION_FACTORY)
                .create();
        this.cycleRepositionStrategiesKey = new KeyMapping(
                "still-clouds.key.cycleRepositionStrategies",
                UNKNOWN.getValue(),
                "still-clouds.key.categories.still-clouds");
        this.cycleTransitionEasesKey = new KeyMapping("still-clouds.key.cycleTransitionEases",
                UNKNOWN.getValue(), "still-clouds.key.categories.still-clouds");
    }

    /**
     * Creates a new config screen.
     *
     * @param lastScreen the last screen
     * @return a config screen
     */
    public Screen createConfigScreen(final Screen lastScreen) {
        final var engine = getEngine();

        return new StillCloudsVanillaConfigScreen(lastScreen, engine.getConfig().clone()) {
            @Override
            protected void onDone() {
                try {
                    getLogger().debug("Saving config...");
                    StillCloudsClient.this.saveConfig(this.config);
                    engine.getConfig().setAll(this.config);
                    
                    getLogger().debug("Saved config");
                } catch (final IOException ioe) {
                    getLogger().warn("Unable to save config", ioe);
                }
            }

            @Override
            protected void onReload() {
                try {
                    getLogger().debug("Reloading config...");
                    StillCloudsClient.this.loadConfig(this.config::setAll);

                    super.onReload();
                    getLogger().debug("Reloaded config");
                } catch (final JsonParseException | IOException e) {
                    getLogger().warn("Unable to reload config", e);
                }
            }
        };
    }

    /** 
     * Returns the cycle reposition modes key.
     *
     * @return the cycle reposition modes key
     */
    public final KeyMapping cycleRepositionModesKey() {
        return this.cycleRepositionStrategiesKey;
    }

    /**
     * Returns the cycle transition eases key.
     *
     * @return the cycle transition eases key
     */
    public final KeyMapping cycleTransitionEasesKey() {
        return this.cycleTransitionEasesKey;
    }

    /**
     * Returns the engine.
     *
     * @return the engine
     */
    public StillCloudsEngine getEngine() {
        return this.engine;
    }

    /**
     * Returns the gson used in serialization.
     *
     * @return the gson
     */
    public Gson getGson() {
        return this.gson;
    }

    /**
     * Loads the config.
     *
     * @param action the action performed on the loaded config
     * @throws IOException thrown when an IO exception occurred during deserialization
     */
    public void loadConfig(final Consumer<? super MutableStillCloudsConfig> action) throws IOException {
        Objects.requireNonNull(action, "Parameter action is null");

        try (final var buffer = Files.newBufferedReader(getConfigPath())) {
            final var config = getGson().fromJson(buffer, MutableStillCloudsConfig.class);

            action.accept(config);
        }
    }

    /**
     * Saves the config.
     *
     * @param config the config to serialize
     * @throws IOException thrown when an IO exception occurred during serialization 
     */
    public void saveConfig(final StillCloudsConfig config) throws IOException {
        final var configPath = getConfigPath();
        final var mutableConfig = config instanceof MutableStillCloudsConfig ? config :
                new MutableStillCloudsConfig(config);

        Files.createDirectories(configPath.getParent());

        try (final var buffer = Files.newBufferedWriter(configPath)) {
            getGson().toJson(mutableConfig, buffer);
        }
    }

    /**
     * Sets the engine.
     *
     * @param engine the engine
     * @throws NullPointerException thrown when {@code engine} is {@code null}
     */
    public void setEngine(final StillCloudsEngine engine) {
        Objects.requireNonNull(engine, "Parameter engine is null");

        this.engine = engine;
    }

    /**
     * Sets the gson.
     *
     * @param gson the gson
     * @throws NullPointerException thrown when {@code gson} is {@code null}
     */
    public void setGson(final Gson gson) {
        Objects.requireNonNull(gson, "Parameter gson is null");

        this.gson = gson;
    }
}
