class SimpleColorMatcher implements ColorMatcher {

    ColorDistanceMeasurer distance

    @Override
    List<Tuple2<NamedColor, Float>> findClosestMatch(NamedColor color, List<NamedColor> palette) {
        palette.collect { NamedColor fromPalette ->
            new Tuple2<>(fromPalette, distance.measure(color, fromPalette))
        }.sort { Tuple2 tuple ->
            tuple.second
        }.take(3)

    }

}
