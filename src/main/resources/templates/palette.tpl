import com.github.paints.NamedColor

html() {
    head() {
        link(rel: 'stylesheet', href: '/main.css')
    }
    body() {
        div() {
            colorsFromThePalette.each { NamedColor color ->
                div(class: 'row') {
                    String hex = Integer.toHexString(color.color.getRGB()).substring(2)
                    div(class: 'swatch') {
                        div(class: 'swatch', style: "background-color: #${hex};", ' ')
                        div {
                            yield "${color.name}"
                            if (info) {
                                br()
                                yield "${info.join(' ')}"
                            }
                        }
                    }
                }
            }
        }
    }
}
