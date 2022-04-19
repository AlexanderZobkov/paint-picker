import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

import java.awt.Color

class SimpleColorMatcherTest extends Specification {

    ColorDistanceMeasurer distanceMeasurer = Mock()

    def "FindClosestMatch"() {
        given:
        NamedColor color = new NamedColor('The one', new Color(0,0,0))
        List<NamedColor> palette = [new NamedColor('The one', new Color(0,0,0)),
                                    new NamedColor('The second', new Color(1,1,1)),
                                    new NamedColor('The third', new Color(1,1,2)),
                                    new NamedColor('The forth', new Color(1,1,3)),
                                    ]
        ColorMatcher matcher = new SimpleColorMatcher(distanceMeasurer)
        when:
        List<Pair<NamedColor, Float>> colors = matcher.findClosestMatch(color, palette)
        then:
        colors.size() == 3
        colors[0].left.name == 'The one'
        colors[1].left.name == 'The second'
        colors[2].left.name == 'The third'
        distanceMeasurer.measure(_,_)  >> { args ->
            Math.abs(Color.cast(args[0]).blue - Color.cast(args[1]).blue)
        }
    }

}
