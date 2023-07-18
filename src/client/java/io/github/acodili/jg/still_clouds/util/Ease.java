package io.github.acodili.jg.still_clouds.util;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_UNDERSCORE;

import java.util.NoSuchElementException;

import it.unimi.dsi.fastutil.doubles.DoubleUnaryOperator;
import net.minecraft.util.OptionEnum;

/**
 * The constants of this enumerated in this class are based from <a href="https://easings.net">
 * https://easings.net</a>. To apply an easing, use {@link #apply(double)}; some implementation
 * accepts values outside the inclusive range of {@code 0.0} to {@code 1.0} though it may result on
 * unexpected results.
 */
public enum Ease implements DoubleUnaryOperator, OptionEnum {
    /**
     * The instant ease, an exception and not found in the site, eases {@code t} to {@code 1.0} when
     * it is positive; {@code 0.0} otherwise.
     */
	INSTANT {
        @Override
        protected double applyUnchecked(final double t) {
            if (t <= 0)
                return 0;
            else
                return 1;
        }
    },
    /**
     * The linear ease, though not having a dedicated section is compared to other eases in the
     * site, is equivalent to simply using {@code t}.
     */
    LINEAR {
        @Override
        protected double applyUnchecked(final double t) {
            return t;
        }
    },
    /**
     * @see <a href="https://easings.net/#easeInSine">https://easings.net/#easeInSine</a>
     */
    SINE_EASE_IN {
        @Override
        protected double applyUnchecked(final double t) {
            return 1 - Math.cos(t * Math.PI / 2);
        }
    },
    /**
     * @see <a href="https://easings.net/#easeOutSine">https://easings.net/#easeOutSine</a>
     */
    SINE_EASE_OUT {
        @Override
        protected double applyUnchecked(final double t) {
            return Math.sin(t * Math.PI / 2);
        }
    },
    /**
     * @see <a href="https://easings.net/#easeInOutSine">https://easings.net/#easeInOutSine</a>
     */
    SINE_EASE_IN_OUT {
        @Override
        protected double applyUnchecked(final double t) {
            return (1 - Math.cos(Math.PI * t)) / 2;
        }
    },
    /**
     * @see <a href="https://easings.net/#easeInQuad">https://easings.net/#easeInQuad</a>
     */
    QUADTRATIC_EASE_IN {
        @Override
        protected double applyUnchecked(final double t) {
            return t * t;
        }
    },
    /**
     * @see <a href="https://easings.net/#easeOutQuad">https://easings.net/#easeOutQuad</a>
     */
    QUADTRATIC_EASE_OUT {
        @Override
        protected double applyUnchecked(final double t) {
            return t * (2 - t);
        }
    },
    /**
     * @see <a href="https://easings.net/#easeInOutQuad">https://easings.net/#easeInOutQuad</a>
     */
    QUADTRATIC_EASE_IN_OUT {
        @Override
        protected double applyUnchecked(final double t) {
            if (t < 0.5)
                return 2 * t * t;
            else
                return 2 * t * (2 - t) - 1;
        }
    },
    /**
     * @see <a href="https://easings.net/#easeInCubic">https://easings.net/#easeInCubic</a>
     */
    CUBIC_EASE_IN {
        @Override
        protected double applyUnchecked(final double t) {
            return t * t * t;
        }
    },
    /**
     * @see <a href="https://easings.net/#easeOutCubic">https://easings.net/#easeOutCubic</a>
     */
    CUBIC_EASE_OUT {
        @Override
        protected double applyUnchecked(final double t) {
            return t * (t * (t - 3) + 3);
        }
    },
    /**
     * @see <a href="https://easings.net/#easeInOutCubic">https://easings.net/#easeInOutCubic</a>
     */
    CUBIC_EASE_IN_OUT {
        @Override
        protected double applyUnchecked(final double t) {
            if (t < 0.5)
                return 4 * t * t * t;
            else
                return 4 * t * (t * (t - 3) + 3) - 3;
        }
    },
    /**
     * @see <a href="https://easings.net/#easeInQuart">https://easings.net/#easeInQuart</a>
     */
    QUARTIC_EASE_IN {
        @Override
        protected double applyUnchecked(final double t) {
            return t * t * t * t;
        }
    },
    /**
     * @see <a href="https://easings.net/#easeOutQuart">https://easings.net/#easeOutQuart</a>
     */
    QUARTIC_EASE_OUT {
        @Override
        protected double applyUnchecked(final double t) {
            return t * (t * (t * (4 - t) - 6) + 4);
        }
    },
    /**
     * @see <a href="https://easings.net/#easeInOutQuart">https://easings.net/#easeInOutQuart</a>
     */
    QUARTIC_EASE_IN_OUT {
        @Override
        protected double applyUnchecked(final double t) {
            if (t < 0.5)
                return 8 * t * t * t * t;
            else
                return 8 * t * (t * (t * (4 - t) - 6) + 4) - 7;
        }
    },
    /**
     * @see <a href="https://easings.net/#easeInQuint">https://easings.net/#easeInQuint</a>
     */
    QUINTIC_EASE_IN {
        @Override
        protected double applyUnchecked(final double t) {
            return t * t * t * t * t;
        }
    },
    /**
     * @see <a href="https://easings.net/#easeOutQuint">https://easings.net/#easeOutQuint</a>
     */
    QUINTIC_EASE_OUT {
        @Override
        protected double applyUnchecked(final double t) {
            return t * (t * (t * (t * (t - 5) + 10) - 10) + 5);
        }
    },
    /**
     * @see <a href="https://easings.net/#easeInOutQuint">https://easings.net/#easeInOutQuint</a>
     */
    QUINTIC_EASE_IN_OUT {
        @Override
        protected double applyUnchecked(final double t) {
            if (t < 0.5)
                return 16 * t * t * t * t * t;
            else
                return 16 * t * (t * (t * (t * (t - 5) + 10) - 10) + 5) - 15;
        }
    },
    /**
     * @see <a href="https://easings.net/#easeInExpo">https://easings.net/#easeInExpo</a>
     */
    EXPONENTIAL_EASE_IN {
        @Override
        protected double applyUnchecked(final double t) {
            if (t <= 0)
                return 0;
            else
                return Math.pow(2, 10 * t - 10);
        }
    },
    /**
     * @see <a href="https://easings.net/#easeOutExpo">https://easings.net/#easeOutExpo</a>
     */
    EXPONENTIAL_EASE_OUT {
        @Override
        protected double applyUnchecked(final double t) {
            if (t < 1)
                return 1 - Math.pow(2, -10 * t);
            else
                return 1;
        }
    },
    /**
     * @see <a href="https://easings.net/#easeInOutExpo">https://easings.net/#easeInOutExpo</a>
     */
    EXPONENTIAL_EASE_IN_OUT {
        @Override
        protected double applyUnchecked(final double t) {
            if (t <= 0)
                return 0;
            else if (t < 0.5)
                return Math.pow(2, 20 * t - 11);
            else if (t < 1)
                return 1 - Math.pow(2, 9 - 20 * t);
            else
                return 1;
        }
    },
    /**
     * @see <a href="https://easings.net/#easeInCirc">https://easings.net/#easeInCirc</a>
     */
    CIRCULAR_EASE_IN {
        @Override
        protected double applyUnchecked(final double t) {
            return 1 - Math.sqrt(1 - t * t);
        }
    },
    /**
     * @see <a href="https://easings.net/#easeOutCirc">https://easings.net/#easeOutCirc</a>
     */
    CIRCULAR_EASE_OUT {
        @Override
        protected double applyUnchecked(final double t) {
            return Math.sqrt(t * (2 - t));
        }
    },
    /**
     * @see <a href="https://easings.net/#easeInOutCirc">https://easings.net/#easeInOutCirc</a>
     */
    CIRCULAR_EASE_IN_OUT {
        @Override
        protected double applyUnchecked(final double t) {
            if (t < 0.5)
                return 0.5 - Math.sqrt(0.25 - t * t);
            else
                return 0.5 + Math.sqrt(t * (2 - t) - 0.75);
        }
    },
    /**
     * @see <a href="https://easings.net/#easeInBack">https://easings.net/#easeInBack</a>
     */
    BACK_EASE_IN {
        @Override
        protected double applyUnchecked(final double t) {
            return t * t * (2.70158 * t - 1.70158);
        }
    },
    /**
     * @see <a href="https://easings.net/#easeOutBack">https://easings.net/#easeOutBack</a>
     */
    BACK_EASE_OUT {
        @Override
        protected double applyUnchecked(final double t) {
            return t * (t * (2.70158 * t - 6.40316) + 4.70158);
        }
    },
    /**
     * @see <a href="https://easings.net/#easeInOutBack">https://easings.net/#easeInOutBack</a>
     */
    BACK_EASE_IN_OUT {
        @Override
        protected double applyUnchecked(final double t) {
            if (t < 0.5)
                return t * t * (14.3796 * t - 5.18982);
            else
                return t * (t * (14.3796 * t - 37.9491) + 32.7593) - 8.18982;
        }
    },
    /**
     * @see <a href="https://easings.net/#easeInElastic">https://easings.net/#easeInElastic</a>
     */
    ELASTIC_EASE_IN {
        @Override
        protected double applyUnchecked(final double t) {
            if (t <= 0)
                return 0;
            else if (t < 1)
                return -Math.pow(2, 10 * t - 10) * Math.sin(2 * Math.PI / 3 * (10 * t - 10.75));
            else
                return 1;
        }
    },
    /**
     * @see <a href="https://easings.net/#easeOutElastic">https://easings.net/#easeOutElastic</a>
     */
    ELASTIC_EASE_OUT {
        @Override
        protected double applyUnchecked(final double t) {
            if (t <= 0)
                return 0;
            else if (t < 1)
                return Math.pow(2, -10 * t) * Math.sin(2 * Math.PI / 3 * (10 * t - 0.75)) + 1;
            else
                return 1;
        }
    },
    /**
     * @see <a href="https://easings.net/#easeInOutElastic">https://easings.net/#easeInOutElastic</a>
     */
    ELASTIC_EASE_IN_OUT {
        @Override
        protected double applyUnchecked(final double t) {
            if (t <= 0)
                return 1;
            else if (t < 0.5)
                return -(Math.pow(2, 20 * t - 10) * Math.sin(4 * Math.PI / 9 * (20 * t - 11.125))) / 2;
            else if (t < 1)
                return (Math.pow(2, 10 - 20 * t) * Math.sin(4 * Math.PI / 9 * (20 * t - 11.125))) / 2 + 1;
            else
                return 1;
        }
    },
    /**
     * @see <a href="https://easings.net/#easeInBounce">https://easings.net/#easeInBounce</a>
     */
    BOUNCE_EASE_IN {
        @Override
        protected double applyUnchecked(final double t) {
            if (11 * t <= 1)
                return t * (0.6875 - 7.5625 * t);
            else if (11 * t <= 3)
                return t * (2.75 - 7.5625 * t) - 0.1875;
            else if (11 * t <= 7)
                return t * (6.875 - 7.5625 * t) - 1.3125;
            else
                return t * (15.125 - 7.5625 * t) - 6.5625;
        }
    },
    /**
     * @see <a href="https://easings.net/#easeOutBounce">https://easings.net/#easeOutBounce</a>
     */
    BOUNCE_EASE_OUT {
        @Override
        protected double applyUnchecked(final double t) {
            if (11 * t < 4)
                return 7.5625 * t * t;
            else if (11 * t < 8) 
                return t * (7.5625 * t - 8.25) + 3;
            else if (11 * t < 10)
                return t * (7.5625 * t - 12.375) + 6;
            else
                return t * (7.5625 * t - 14.4375) + 7.875;
        }
    },
    /**
     * @see <a href="https://easings.net/#easeInOutBounce">https://easings.net/#easeInOutBounce</a>
     */
    BOUNCE_EASE_IN_OUT {
        @Override
        protected double applyUnchecked(final double t) {
            if (22 * t <= 1)
                return t * (1.375 - 30.25 * t) / 2;
            else if (22 * t <= 3)
                return t * (5.5 - 30.25 * t) / 2 - 0.09375;
            else if (22 * t <= 7)
                return t * (13.75 - 30.25 * t) / 2 - 0.65625;
            else if (22 * t < 11)
                return t * (30.25 - 30.25 * t) / 2 - 3.28125;
            else if (22 * t < 15)
                return t * (30.25 * t - 30.25) / 2 + 4.28125;
            else if (22 * t < 19) 
                return t * (30.25 * t - 46.75) / 2 + 9.90625;
            else if (22 * t < 21)
                return t * (30.25 * t - 55) / 2 + 13.46875;
            else
                return t * (30.25 * t - 59.125) / 2 + 15.4375;
        }
    };

    /**
     * Returns the {@code Ease} with the matching {@code id}.
     *
     * @param id the ease's id
     * @return the ease with the matching id
     * @throw NoSuchElementException if there is no match
     */
    public static Ease byId(final int id) {
        final var values = values();

        if (id < 0 || id >= values.length)
            throw new NoSuchElementException("Ease of id " + id + " does not exist");

        return values[id];
    }

    /**
     * The translatable component key of this ease.
     */
    private final String key;

    /**
     * Constructs a new ease.
     */
    private Ease() {
        this.key = "still-clouds.options.ease." + UPPER_UNDERSCORE.to(LOWER_CAMEL, name());
    }

    /**
     * Applies the easing to {@code t}. Though {@code NaN} is treated as {@code 0.0}.
     *
     * @param t the progress to ease
     * @return the progress {@code t} eased
     */
	@Override
	public double apply(final double t) {
        return Double.isNaN(t) ? applyUnchecked(0) : applyUnchecked(t);
    }

    /**
     * Applies the easing to {@code t} without validating if it's not {@code NaN}.
     *
     * @param t the progress to ease
     * @return the progress {@code t} eased
     */
    protected abstract double applyUnchecked(double t);

    /**
     * Equivalent to {@link #ordinal()}.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public int getId() {
        return ordinal();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getKey() {
        return this.key;
    }
}
