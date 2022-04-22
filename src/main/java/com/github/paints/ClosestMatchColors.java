package com.github.paints;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Objects;

public class ClosestMatchColors {

     private NamedColor baseColor;
     private List<Pair<NamedColor,Float>> similarColorsWithDistance;

     public ClosestMatchColors(final NamedColor baseColor, final List<Pair<NamedColor, Float>> similarColorsWithDistance) {
          this.baseColor = Objects.requireNonNull(baseColor);
          this.similarColorsWithDistance = Objects.requireNonNull(similarColorsWithDistance);
     }

     public NamedColor getBaseColor() {
          return baseColor;
     }

     public List<Pair<NamedColor, Float>> getSimilarColorsWithDistance() {
          return similarColorsWithDistance;
     }
}
