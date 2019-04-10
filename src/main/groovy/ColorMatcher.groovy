interface ColorMatcher {

    List<Tuple2<NamedColor, Float>> findClosestMatch(NamedColor color, List<NamedColor> palette)

}