package com.github.paints;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PaletteMatcherController {

    private final ColorMatcher matcher = new SimpleColorMatcher(new EnhancedWeightedDistanceMeasurer());
    private final ColorPaletteLoader loader = new SimpleAutocadColorBookLoader();

    @GetMapping("/palette-match")
    public String palette(@RequestParam(name = "name1", defaultValue = "Dulux.acb") String name1,
                          @RequestParam(name = "name2", required = false) String name2,
                          @RequestParam(name = "named-color", required = false) List<String> namedColors,
                          Model model) throws IOException {

        List<NamedColor> palette1;
        List<NamedColor> palette2;
        if (name2 == null) {
            palette1 = buildPaletteFromParameter(namedColors);
            palette2 = loadPaletteFromClasspath(name1);
        } else {
            palette1 = loadPaletteFromClasspath(name1);
            palette2 = loadPaletteFromClasspath(name2);
        }

        List<ClosestMatchColors> closestMatchColors = palette1.stream()
                .map(namedColor -> findClosestMatch(namedColor, palette2))
                .collect(Collectors.toList());

        model.addAttribute("closestMatchColors", closestMatchColors);
        return "palette-match";
    }

    private List<NamedColor> loadPaletteFromClasspath(String resource) throws IOException {
        InputStream paletteStream = loader.getClass().getClassLoader().getResourceAsStream(resource);
        return loader.load(paletteStream);
    }

    private ClosestMatchColors findClosestMatch(NamedColor baseColor, List<NamedColor> palette) {
        List<Pair<NamedColor, Float>> closestColors = matcher.findClosestMatch(baseColor, palette);
        return new ClosestMatchColors(baseColor, closestColors);
    }

    // [name1:hex1,name2:hex2,...]
    private List<NamedColor> buildPaletteFromParameter(List<String> namedColors){
        return namedColors.stream()
                .map(pair -> {
                    String[] strings = StringUtils.split(pair,":");
                    String name = strings[0];
                    String hex = strings[1];
                    return new NamedColor(name, Color.decode("#" + hex));
                })
                .collect(Collectors.toList());
    }

}
