package com.github.paints;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.awt.Color;
import java.util.Objects;

/**
 * An object that represents a color itself and its human-logical name.
 */
public class NamedColor {

    private final String name;
    private final Color color;

    /**
     * The constructor.
     *
     * @param name  Human-logical name for the {@link color}. Must not be null.
     * @param color The color. Must not be null.
     */
    public NamedColor(final String name, final Color color) {
        this.name = Objects.requireNonNull(name);
        this.color = Objects.requireNonNull(color);
    }

    /**
     * The constructor.
     *
     * @param name  Human-logical name for the {@link color}. Must not be null.
     * @param hexColor The color as hex value. Must not be null and must be understood by {@link Color#decode(String)}.
     */
    public NamedColor(final String name, final String hexColor) {
        this.name = Objects.requireNonNull(name);
        Objects.requireNonNull(hexColor);
        this.color = Color.decode(hexColor);
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("name", name).
                append("color", color).
                toString();
    }
}
