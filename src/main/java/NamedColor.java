import java.awt.*;
import java.util.Objects;

/**
 * An object that represents a color itself and its human-logical name.
 */
class NamedColor {

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

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "name='" + name + '\'' +
                ", color=" + color +
                '}';
    }
}
