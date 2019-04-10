import java.awt.Color

class AutocadColorBookLoader implements ColorPaletteLoader {

    @Override
    List<NamedColor> load(File palette) {
        def list = new XmlSlurper().parseText(palette.text)
        list.'**'.findAll { node -> node.name() == 'colorEntry' } collect asColor
    }

    private Closure<NamedColor> asColor = { def paletteColor ->
        new NamedColor().tap {
            name = paletteColor.colorName
            int r = paletteColor.RGB8.red.text() as int
            int g = paletteColor.RGB8.green.text() as int
            int b = paletteColor.RGB8.blue.text() as int
            color = new Color(r, g, b)
        }

    }
}
