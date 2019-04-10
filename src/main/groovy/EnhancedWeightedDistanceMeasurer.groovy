/**
 * https://www.compuphase.com/cmetric.htm
 */
class EnhancedWeightedDistanceMeasurer implements ColorDistanceMeasurer {

    @Override
    float measure(NamedColor color1, NamedColor color2) {
        int rmean = (color1.color.red + color2.color.red) / 2
        int redDelta = (color1.color.red - color2.color.red)
        int greedDelta = (color1.color.green - color2.color.green)
        int blueDelta = (color1.color.blue - color2.color.blue)
        Math.sqrt((((512 + rmean) * redDelta * redDelta) >> 8) + 4 * greedDelta * greedDelta + (((767 - rmean) * blueDelta * blueDelta) >> 8))
    }

}
