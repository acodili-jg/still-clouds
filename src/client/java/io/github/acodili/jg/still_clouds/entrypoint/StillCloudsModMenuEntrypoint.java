package io.github.acodili.jg.still_clouds.entrypoint;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import io.github.acodili.jg.still_clouds.StillCloudsClient;

/**
 * The {@code StillCloudsModMenuEntrypoint} class handles Mod Menu compatibility.
 */
public class StillCloudsModMenuEntrypoint implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return StillCloudsClient.getInstance()::createConfigScreen;
    }
}
