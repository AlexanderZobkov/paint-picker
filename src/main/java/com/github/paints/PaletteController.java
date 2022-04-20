package com.github.paints;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PaletteController {

    @GetMapping("/palette")
    public String palette(@RequestParam(name = "name", required = false) String name, Model model) throws IOException {
        if (name == null) {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            List<String> foundPalettes = Arrays.stream(resolver.getResources("classpath*:**/*.acb"))
                    .map(Resource::getFilename)
                    .collect(Collectors.toList());
            model.addAttribute("palettes", foundPalettes);
            return "palette-list";
        } else {
            InputStream paletteStream = SimpleAutocadColorBookLoader.class.getClassLoader().getResourceAsStream(name);
            List<NamedColor> colorsFromPalette = new SimpleAutocadColorBookLoader().load(paletteStream);
            model.addAttribute("colorsFromPalette", colorsFromPalette);
            return "palette";
        }
    }

}
