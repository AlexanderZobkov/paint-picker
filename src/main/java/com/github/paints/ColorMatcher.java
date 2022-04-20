package com.github.paints;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * Allows to find the closest matching colors.
 */
public interface ColorMatcher {

    /**
     * Finds the closest matching colors for the specified color.
     *
     * @param color   a color for which need to find the closest matches. Must not be null.
     * @param palette a list of colors where to find the closest color. Must not be null.
     * @return List of the closest matched colors ordered by: from the closest to distant. Never null.
     */
    List<Pair<NamedColor, Float>> findClosestMatch(NamedColor color, List<NamedColor> palette);

}
