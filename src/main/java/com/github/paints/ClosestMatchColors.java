package com.github.paints;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Objects;

public class ClosestMatchColors {

     private NamedColor baseColor;
     private List<Pair<NamedColor,Float>> similarColorsWithDistance;

     public ClosestMatchColors(NamedColor baseColor, List<Pair<NamedColor, Float>> similarColorsWithDistance) {
          Objects.requireNonNull(baseColor);
          this.baseColor = baseColor;
          Objects.requireNonNull(similarColorsWithDistance);
          this.similarColorsWithDistance = similarColorsWithDistance;
     }


     public NamedColor getBaseColor() {
          return baseColor;
     }

     public List<Pair<NamedColor, Float>> getSimilarColorsWithDistance() {
          return similarColorsWithDistance;
     }
}
