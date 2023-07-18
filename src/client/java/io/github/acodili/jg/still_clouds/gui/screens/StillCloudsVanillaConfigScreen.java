package io.github.acodili.jg.still_clouds.gui.screens;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Objects;

import io.github.acodili.jg.still_clouds.config.StillCloudsConfig;
import io.github.acodili.jg.still_clouds.engine.CloudRepositionStrategy;
import io.github.acodili.jg.still_clouds.util.Clouds;
import io.github.acodili.jg.still_clouds.util.Ease;
import io.github.acodili.jg.still_clouds.util.TooltipSuppliers;
import io.github.acodili.jg.still_clouds.util.ValueLabels;
import io.github.acodili.jg.still_clouds.util.ValueSets;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

/**
 * Still Clouds' config screen.
 */
public class StillCloudsVanillaConfigScreen extends OptionsSubScreen {
    protected OptionInstance<BigDecimal> absolutePositionOption;

    /**
     * The config {@code this} screen will configure and display options for.
     */
    protected final StillCloudsConfig config;

    /**
     * {@code This} screen's list of options.
     */
    protected OptionsList list;

    protected OptionInstance<BigDecimal> relativePositionOption;

    protected OptionInstance<CloudRepositionStrategy> repositionStrategyOption;

    protected OptionInstance<Duration> transitionDurationOption;

    protected OptionInstance<Ease> transitionEaseOption;

    /**
     * Constructs a new config screen given the last screen, and the config.
     *
     * @param lastScreen the last screen preceeding this
     * @param config     the config to configure
     * @throws NullPointerException if {@code config} is {@code null}
     */
    public StillCloudsVanillaConfigScreen(final Screen lastScreen, final StillCloudsConfig config) {
        super(lastScreen, null, Component.translatable("still-clouds.options.configTitle"));

        Objects.requireNonNull(config, "Parameter config is null");

        this.config = config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void init() {
        this.list = new OptionsList(this.minecraft, this.width, this.height, 32, this.height - 32,
                25);

        this.list.addBig(this.repositionStrategyOption = new OptionInstance<>(
                "still-clouds.options.repositionStrategy",
                TooltipSuppliers.forOptionEnum(),
                OptionInstance.forOptionEnum(),
                ValueSets.allOfOptionEnum(CloudRepositionStrategy.class,
                        CloudRepositionStrategy::byId),
                config.getRepositionStrategy(),
                config::setRepositionStrategy));
        this.list.addSmall(
                absolutePositionOption = new OptionInstance<>(
                        "still-clouds.options.absolutePosition",
                        OptionInstance.noTooltip(),
                        (component, offset) -> Options.genericValueLabel(component,
                                Component.translatable(
                                        "still-clouds.options.absolutePosition.value", offset)),
                        ValueSets.range(BigDecimal.valueOf(-Clouds.SIZE_HALVED),
                                BigDecimal.valueOf(Clouds.SIZE_HALVED),
                                BigDecimal.ONE.movePointLeft(1).setScale(1)),
                        config.getRepositionParameters()
                              .get("absolutePosition") instanceof final Number absolutePosition ?
                                new BigDecimal(absolutePosition.toString()) : BigDecimal.ZERO,
                        absolutePosition -> config.getRepositionParameters().put("absolutePosition",
                                absolutePosition.doubleValue())),
                relativePositionOption = new OptionInstance<BigDecimal>(
                        "still-clouds.options.relativePosition",
                        OptionInstance.noTooltip(),
                        (component, offset) -> Options.genericValueLabel(component,
                                Component.translatable(
                                        "still-clouds.options.relativePosition.value", offset)),
                        ValueSets.range(BigDecimal.valueOf(-Clouds.SIZE_HALVED),
                                BigDecimal.valueOf(Clouds.SIZE_HALVED),
                                BigDecimal.ONE.movePointLeft(1).setScale(1)),
                        config.getRepositionParameters()
                              .get("relativePosition") instanceof final Number relativePosition ?
                                new BigDecimal(relativePosition.toString()) : BigDecimal.ZERO,
                        relativePosition -> config.getRepositionParameters().put("relativePosition",
                                relativePosition.doubleValue())));
        this.list.addSmall(
                this.transitionDurationOption = new OptionInstance<Duration>(
                        "still-clouds.options.transitionDuration",
                        OptionInstance.noTooltip(),
                        ValueLabels.DURATION_CAPTIONS,
                        new OptionInstance.IntRange(0, 50).xmap(i -> Duration.ofMillis(100 * i),
                                duration -> (int) (duration.toMillis() / 100)),
                        config.getTransitionDuration(),
                        config::setTransitionDuration),
                this.transitionEaseOption = new OptionInstance<Ease>(
                        "still-clouds.options.transitionEase",
                        OptionInstance.noTooltip(),
                        OptionInstance.forOptionEnum(),
                        ValueSets.allOfOptionEnum(Ease.class, Ease::byId),
                        config.getTransitionEase(),
                        config::setTransitionEase));

        addWidget(this.list);
        addRenderableWidget(
                Button.builder(Component.translatable("still-clouds.options.reload"),
                        button -> onReload())
                      .bounds(this.width / 2 - 205, this.height - 27, 200, 20)
                      .build());
        addRenderableWidget(
                Button.builder(CommonComponents.GUI_DONE, button -> onClose())
                      .bounds(this.width / 2 + 5, this.height - 27, 200, 20)
                      .build());
    }

    @Override
    public void onClose() {
        onDone();
        super.onClose();
    }

    protected void onDone() {
    }

    protected void onReload() {
        rebuildWidgets();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removed() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final GuiGraphics guiGraphics, final int mouseX, final int mouseY,
            final float partialTick) {
        basicListRender(guiGraphics, this.list, mouseX, mouseY, partialTick);
    }
}
