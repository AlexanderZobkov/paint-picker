package com.github.paints;

import java.awt.*;

/**
 * Measures a distance between two colors.
 */
public interface ColorDistanceMeasurer {

    /**
     * Measures a distance between two specified colors.
     *
     * @param color1 a first color to calculate a distance.
     * @param color2 a second color to calculate a distance.
     * @return A values that ranges from 0 to max value, The higher value, the longer the distance.
     */
    float measure(Color color1, Color color2);
}
