import com.github.paints.ClosestMatchColors
import org.apache.commons.lang3.tuple.Pair

Closure<String> calcSimilarityPercentage = { Float distance ->
    "${100 - (100 * distance.floatValue() / 767f).trunc(2)}%"
}

Closure<String> calcSimilarityDistance = { Float distance ->
    "${distance.trunc(2)}"
}

html() {
    head() {
        link(rel: 'stylesheet', href: '/main.css')
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
                                span(class: 'swatch-text') {
                                    yield "${calcSimilarityPercentage.call(closestColor.right)} similar"
                                }
                                br()
                                span(class: 'swatch-text') {
                                    yield "${calcSimilarityDistance.call(closestColor.right)} distance"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
