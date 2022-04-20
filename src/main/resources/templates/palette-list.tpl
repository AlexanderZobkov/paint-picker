html() {
    body() {
            palettes.each { String p ->
                div {
                    a(href: "/palette?name=${p}") {
                        yield p
                    }
                }
            }
    }
}
