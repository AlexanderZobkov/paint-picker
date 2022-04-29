import com.github.paints.NamedColor

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
            colorsFromThePalette.each { NamedColor color ->
                div( class: 'row') {
                    String hex = Integer.toHexString(color.color.getRGB()).substring(2)
                    div (class:'swatch') {
                        div(class:'swatch', style:"background-color: #${hex};", ' ')
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
