package io.github.acodili.jg.still_clouds.util;

import net.minecraft.client.OptionInstance.TooltipSupplier;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.util.OptionEnum;
 
public final class TooltipSuppliers {
    /**
     * The same supplier is used for all option enums.
     *
     * @see #forOptionEnum()
     */
    private static final TooltipSupplier<? super OptionEnum> OPTION_ENUM;

    static {
        OPTION_ENUM = object -> Tooltip.create(Component.translatable(object.getKey() + ".tooltip"));
    }

    /**
     * Returns a tooltip supplier for a specific option enum type.
     *
     * @param <T> The type to create a tooltip supplier for
     * @return a tooltip supplier
     */
    @SuppressWarnings("unchecked")
    public static <T extends OptionEnum> TooltipSupplier<T> forOptionEnum() {
        return (TooltipSupplier<T>) OPTION_ENUM;
    }

    /**
     * Returns a tooltip supplier that only supplies the same tooltip.
     * 
     * @param <T>       The type to create a tooltip supplier for
     * @param component the component to wrap in a tooltip
     * @return a tooltip supplier
     */
    public static <T> TooltipSupplier<T> singleton(final Component component) {
        return singleton(Tooltip.create(component));
    }

    /**
     * Returns a tooltip supplier that only supplies the same tooltip.
     * 
     * @param <T> The type to create a tooltip supplier for
     * @param key the translatable component key to wrap in a tooltip
     * @return a tooltip supplier
     */
    public static <T> TooltipSupplier<T> singleton(final String key) {
        return singleton(Component.translatable(key));
    }

    /**
     * Returns a tooltip supplier that only supplies the same tooltip.
     * 
     * @param <T>     The type to create a tooltip supplier for
     * @param tooltip the tooltip
     * @return a tooltip supplier
     */
    public static <T> TooltipSupplier<T> singleton(final Tooltip tooltip) {
        return object -> tooltip;
    }

    /**
     * Constructs a new {@code TooltipSuppliers} instance.
     */
    private TooltipSuppliers() {}
}
