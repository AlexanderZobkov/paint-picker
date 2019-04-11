import groovy.xml.MarkupBuilder

/**
 * Class that demonstrates usage of api to compare palettes.
  */
class PalettesMatcher {

    ColorMatcher matcher

    void compareAndReport(List<NamedColor> palette1, List<NamedColor> palette2){

        def writer = new FileWriter('build/paints-match-report.html')
        def xml = new MarkupBuilder(writer)

        Closure<MarkupBuilder> colorSwatch = { MarkupBuilder builder, NamedColor color, Object ...more ->

            String hex = Integer.toHexString(color.color.getRGB()).substring(2)

            builder.div (class:'swatch') {
                div(class:'swatch', style:"background-color: #${hex};", ' ')
                span("${color.name} ${more.join(' ')}")
            }
            builder
        }

        xml.html() {
            style("""

    div.swatch {
        width: 150px; height: 150px;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 23px;
        display: table-cell;
    }
    div.row {
        display: table-row;
    }
    div.spacer {
        width: 50px; height: 150px;
        display: table-cell;
    }

            """)
            body() {
                div() {
                    palette1.each { NamedColor color1 ->
                        div( class: 'row') {
                            colorSwatch(xml, color1)
                            div (class:'spacer' , ' ')
                            matcher.findClosestMatch(color1, palette2).each {Tuple2<NamedColor, Float> color2 ->
                                colorSwatch(xml, color2.first, "${(100 * color2.second.floatValue()/767f).trunc(2)}%", color2.second.trunc(2))
                            }

                        }
                    }
                }
            }

        }

    }

    static void main (String[] arg) {

        ColorPaletteLoader colorLoader = new AutocadColorBookLoader()
        List<NamedColor> tikkurilaPalette = colorLoader.load(new File('src/main/resources/Tikkurila-Symphony.acb'))
        List<NamedColor> littleGreenyPalette = colorLoader.load(new File('src/main/resources/Little-Greeny.acb'))

        new PalettesMatcher(matcher: new SimpleColorMatcher(distance: new EnhancedWeightedDistanceMeasurer()) )
                .compareAndReport(littleGreenyPalette, tikkurilaPalette )
    }
}
