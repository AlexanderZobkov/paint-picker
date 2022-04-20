package com.github.paints;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SimpleColorMatcher implements ColorMatcher {

    private final ColorDistanceMeasurer distanceMeasurer;
    private final int samples;

    public SimpleColorMatcher(final ColorDistanceMeasurer distanceMeasurer) {
        this.distanceMeasurer = distanceMeasurer;
        this.samples = 3;
    }

    public SimpleColorMatcher(final ColorDistanceMeasurer distanceMeasurer, final int samples) {
        Objects.requireNonNull(distanceMeasurer);
        this.distanceMeasurer = distanceMeasurer;
        this.samples = samples;
    }

    @Override
    public List<Pair<NamedColor, Float>> findClosestMatch(final NamedColor namedColor, final List<NamedColor> palette) {
        Objects.requireNonNull(namedColor);
        Objects.requireNonNull(palette);
        return palette.stream()
                .map(fromPalette -> Pair.of(fromPalette,
                        distanceMeasurer.measure(namedColor.getColor(), fromPalette.getColor())))
                .sorted(Comparator.comparing(Pair::getRight))
                .limit(samples)
                .collect(Collectors.toList());
    }

}
