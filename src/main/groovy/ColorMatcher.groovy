/**
 * Allows to find closest matching colors.
 */
interface ColorMatcher {

    /**
     * Finds closest matching colors for the specified color.
     *
     * @param color a color for which need to find closest matches.
     * @param palette a list of colors where to find closest color.
     * @return List of closest matched colors.
     */
    List<Tuple2<NamedColor, Float>> findClosestMatch(NamedColor color, List<NamedColor> palette)

}