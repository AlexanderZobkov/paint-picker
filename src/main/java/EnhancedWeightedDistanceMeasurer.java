import java.awt.*;
import java.util.Objects;

/**
 * Calculates a distance between two colors based on the math described at https://www.compuphase.com/cmetric.htm.
 */
class EnhancedWeightedDistanceMeasurer implements ColorDistanceMeasurer {

    @Override
    public float measure(Color color1, Color color2) {
        Objects.requireNonNull(color1);
        Objects.requireNonNull(color2);
        int rmean = (color1.getRed() + color2.getRed()) / 2;
        int redDelta = (color1.getRed() - color2.getRed());
        int greedDelta = (color1.getGreen() - color2.getGreen());
        int blueDelta = (color1.getBlue() - color2.getBlue());
        return (float) Math.sqrt((((512 + rmean) * redDelta * redDelta) >> 8) +
                4 * greedDelta * greedDelta +
                (((767 - rmean) * blueDelta * blueDelta) >> 8));
    }

}
