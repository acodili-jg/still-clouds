package io.github.acodili.jg.still_clouds.entrypoint;

import static io.github.acodili.jg.still_clouds.StillCloudsClient.getLogger;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Iterator;

import com.google.common.collect.Iterators;
import com.google.gson.JsonParseException;

import io.github.acodili.jg.still_clouds.StillCloudsClient;
import io.github.acodili.jg.still_clouds.config.MutableStillCloudsConfig;
import io.github.acodili.jg.still_clouds.config.StillCloudsConfig;
import io.github.acodili.jg.still_clouds.engine.CloudRepositionStrategy;
import io.github.acodili.jg.still_clouds.util.Ease;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.EndTick;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public class StillCloudsClientEntrypoint implements ClientModInitializer {
    /**
     * Converts unprocessed key clicks to an iterator iteration each.
     *
     * @param <T> The iterated type.
     * @param keyMapping the key mapping
     * @param iterator   the iterator
     * @param start      the starting value
     * @return the last value from the last iteration
     */
    private static <T> T consumeClick(final KeyMapping keyMapping, final Iterator<T> iterator,
            final T start) {
        if (!keyMapping.consumeClick())
            return start;

        var end = Iterators.find(iterator, start::equals);

        do {
            end = iterator.next();
        } while (keyMapping.consumeClick());

        return end;
    }

    protected StillCloudsClient client;

    /**
     * Constructs a new {@code StillCloudsClientEntrypoint} instance.
     */
    public StillCloudsClientEntrypoint() {
    }

    /**
     * The config is (re)created if necessary on its first load.
     */
    protected void configFirstLoad() {
        final var engine = this.client.getEngine();

        try {
            getLogger().debug("Loading config...");
            this.client.loadConfig(engine.getConfig()::setAll);
            getLogger().debug("Loaded config");
        } catch (final JsonParseException jpe) {
            getLogger().debug("Config json parse exception");
            firstLoadRecreatesConfig(engine.getConfig());
        } catch (final NoSuchFileException nsfe) {
            getLogger().debug("Unable to load a non-existent config");
            firstLoadCreatesConfig(engine.getConfig());
        } catch (final IOException ioe) {
            if (ioe.getCause() instanceof NullPointerException npe) {
                getLogger().warn("Loaded config was null");
                firstLoadRecreatesConfig(engine.getConfig());
            } else
                getLogger().warn("Unable to load config", ioe);
        }
    }

    /**
     * Creates the config.
     *
     * @param config the config to serialize
     */
    protected void firstLoadCreatesConfig(final StillCloudsConfig config) {
        try {
            getLogger().debug("Creating config...");
            this.client.saveConfig(config);
            getLogger().info("Created config");
        } catch (final IOException ioe) {
            getLogger().warn("Unable to create config", ioe);
        }
    }

    /**
     * Rereates the config.
     *
     * @param config the config to serialize
     */
    protected void firstLoadRecreatesConfig(final StillCloudsConfig config) {
        try {
            getLogger().debug("Recreating config...");
            this.client.saveConfig(config);
            getLogger().info("Recreated config");
        } catch (final IOException ioe) {
            getLogger().warn("Unable to recreate config", ioe);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onInitializeClient() {
        this.client = StillCloudsClient.getInstance();

        getLogger().debug("Initializing StillCloudsClient...");

        configFirstLoad();
        registerKeyMappings();

        getLogger().info("Initialized StillCloudsClient");
    }

    /**
     * Registers the key listener for the registered keys.
     */
    protected void registerKeyListener() {
        getLogger().debug("Registering key listener...");

        ClientTickEvents.END_CLIENT_TICK.register(new EndTick() {
            private final Iterator<CloudRepositionStrategy> repositionStrategyIterator;

            private final Iterator<Ease> transitionEaseIterator;

            {
                this.repositionStrategyIterator = Iterators.cycle(CloudRepositionStrategy.values());
                this.transitionEaseIterator = Iterators.cycle(Ease.values());
            }

            @Override
            public void onEndTick(Minecraft minecraft) {
                final var engine = StillCloudsClientEntrypoint.this.client.getEngine();
                final var config = new MutableStillCloudsConfig(engine.getConfig());

                config.setRepositionStrategy(
                        StillCloudsClientEntrypoint.consumeClick(
                                StillCloudsClientEntrypoint.this.client.cycleRepositionModesKey(),
                                this.repositionStrategyIterator,
                                config.getRepositionStrategy()));
                config.setTransitionEase(
                        StillCloudsClientEntrypoint.consumeClick(
                                StillCloudsClientEntrypoint.this.client.cycleTransitionEasesKey(),
                                this.transitionEaseIterator,
                                config.getTransitionEase()));
                engine.getConfig().setAll(config);
            }
        });

        getLogger().debug("Registered key listener");
    }

    /**
     * Registers a key mapping.
     */
    protected void registerKeyMapping(final KeyMapping key) {
        getLogger().debug("Registering key mapping " + key.getName() + "...");
        KeyBindingHelper.registerKeyBinding(key);
        getLogger().debug("Registered key mapping " + key.getName());
    }

    /**
     * Registers all the key mappings
     */
    protected void registerKeyMappings() {
        getLogger().debug("Registering key mappings...");

        if (!FabricLoader.getInstance().isModLoaded("fabric-key-binding-api-v1") ||
                !FabricLoader.getInstance().isModLoaded("fabric-lifecycle-events-v1")) {
            getLogger().warn("Unable to register key mappings due to the absence of either fabric-key-binding-api-v1, fabric-lifecycle-events-v1, or both");
            return;
        }

        registerKeyMapping(this.client.cycleRepositionModesKey());
        registerKeyMapping(this.client.cycleTransitionEasesKey());
        registerKeyListener();

        getLogger().debug("Registered key mappings");
    }
}
