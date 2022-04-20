package com.github.paints;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class PaletteController {

    @GetMapping("/palette")
    public String palette(@RequestParam(name = "name", required = true, defaultValue = "Milassa.acb") String name, Model model) throws IOException {
        InputStream paletteStream = SimpleAutocadColorBookLoader.class.getClassLoader().getResourceAsStream(name);

        List<NamedColor> colorsFromPalette = new SimpleAutocadColorBookLoader().load(paletteStream);
        model.addAttribute("colorsFromPalette", colorsFromPalette);
        return "palette";
    }

}