import com.github.paints.ColorPaletteLoader
import com.github.paints.NamedColor
import com.github.paints.SimpleAutocadColorBookLoader
import spock.lang.Specification

class AutocadColorBookLoaderTest extends Specification {


    def "Load color book with valid data"() {
        given:
        ColorPaletteLoader loader = new SimpleAutocadColorBookLoader()
        when:
        List<NamedColor> colors = loader.load(new FileInputStream('src/test/resources/test.acb'))
        then:
        colors.size() == 3
        colors.get(0).name == 'Color 1'
        colors.get(0).color.red == 1
        colors.get(0).color.green == 2
        colors.get(0).color.blue == 3
    }

}
