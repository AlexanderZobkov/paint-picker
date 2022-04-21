import com.github.paints.ClosestMatchColors
import org.apache.commons.lang3.tuple.Pair

Closure<List<Object>> reportSimilarityInfo = { Float distance ->
    [
            "${(100 * distance.floatValue() / 767f).trunc(2)}%",
            distance.trunc(2),
    ]
}

html() {
    head() {
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
    }
    body() {
        div() {
            closestMatchColors.each { ClosestMatchColors matchColors ->
                div(class: 'row') {
                    String baseColorHex = Integer.toHexString(matchColors.baseColor.color.getRGB()).substring(2)
                    div(class: 'swatch') {
                        div(class: 'swatch', style: "background-color: #${baseColorHex};", ' ')
                        div {
                            yield "${matchColors.baseColor.name}"
                        }
                    }
                    div(class: 'spacer', ' ')
                    matchColors.similarColorsWithDistance.each { Pair closestColor ->
                        String similarColorHex = Integer.toHexString(closestColor.left.color.getRGB()).substring(2)
                        div(class: 'swatch') {
                            div(class: 'swatch', style: "background-color: #${similarColorHex};", ' ')
                            div {
                                yield "${closestColor.left.name}"
                                br()
                                yield "${reportSimilarityInfo.call(closestColor.right).join(' ')}"
                            }
                        }
                    }
                }
            }
        }
    }
}
