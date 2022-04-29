html() {
    body() {
        palettes.each { String p ->
            div {
                a(href: "/palettes/${p}") {
                    yield p
                }
            }
        }
    }
}
