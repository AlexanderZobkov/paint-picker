import java.awt.*
import java.util.List

/**
 * Just a test to see how all things work together,
 */
class PaintsMatcher {

    static void main(String[] args) {

        ColorPaletteLoader colorLoader = new AutocadColorBookLoader()
        List<NamedColor> palette = colorLoader.load(new File('src/main/resources/Tikkurila-Symphony.acb'))

        ColorDistanceMeasurer distance = new EnhancedWeightedDistanceMeasurer()

        ColorMatcher matcher = new SimpleColorMatcher(distance: distance)

        [
                new NamedColor(name: 'Greene LG181, Welcome Dark',
                        color: new Color(186, 176, 176))
                ,
                new NamedColor(name: 'Little Greene LG31, Roman Plaster',
                        color: new Color(191, 176, 143))
        ].each {
            println matcher.findClosestMatch(it, palette)
        }


    }

}
