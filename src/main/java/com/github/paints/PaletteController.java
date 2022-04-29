package com.github.paints;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Controller
public class PaletteController {

    @Autowired
    private ColorPaletteLoader loader;

    @Autowired
    private ColorMatcher matcher;

    /**
     * Lists known palettes.
     * <p>
     * - GET /palettes
     */
    @GetMapping("/palettes")
    public String listPalettes(Model model) throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<String> foundPalettes = Arrays.stream(resolver.getResources("classpath*:**/*.acb"))
                .map(Resource::getFilename)
                .collect(Collectors.toList());
        model.addAttribute("palettes", foundPalettes);
        return "palette-list";

    }

    /**
     * Shows colors from the specified palette.
     * <p>
     * - GET /palettes/{name}
     */
    @GetMapping("/palettes/{name}")
    public String showColors(@PathVariable(name = "name") String palette, Model model) throws IOException {
        List<NamedColor> colorsFromPalette = loadPaletteFromClasspath(palette);
        model.addAttribute("colorsFromThePalette", colorsFromPalette);
        return "palette";
    }

    /**
     * Search colors in the specified palette.
     * <p>
     * - GET /palettes/{name}/search/palette/{anotherPalette} - search colors of {anotherPalette} in the palette {name}
     */
    @GetMapping("/palettes/{name}/search/palette/{anotherPalette}")
    public String search(@PathVariable(name = "name") String paletteName,
                         @PathVariable(name = "anotherPalette") String anotherPaletteName,
                         Model model) throws IOException {

        List<NamedColor> palette1 = loadPaletteFromClasspath(paletteName);
        List<NamedColor> palette2 = loadPaletteFromClasspath(anotherPaletteName);

        List<ClosestMatchColors> closestMatchColors = palette1.stream()
                .map(namedColor -> findClosestMatch(namedColor, palette2))
                .collect(Collectors.toList());

        model.addAttribute("closestMatchColors", closestMatchColors);
        return "palette-match";
    }

    /**
     * Search colors in the specified palette.
     * <p>
     * - GET /palettes/{name}/search/colors?named-color={namedColor}&... - search the specified colors in the palette {name}
     * Where {namedColor} is a pair of a color visual-human name and a hex value representing that color separated with a ':'.
     */
    @GetMapping("/palettes/{name}/search/colors")
    public String search(@PathVariable(name = "name") String paletteName,
                         @RequestParam(name = "named-color") List<NamedColor> namedColors,
                         Model model) throws IOException {

        List<NamedColor> palette = loadPaletteFromClasspath(paletteName);

        List<ClosestMatchColors> closestMatchColors = namedColors.stream()
                .map(namedColor -> findClosestMatch(namedColor, palette))
                .collect(Collectors.toList());

        model.addAttribute("closestMatchColors", closestMatchColors);
        return "palette-match";
    }

    private List<NamedColor> loadPaletteFromClasspath(String palette) throws IOException {
        InputStream paletteStream = loader.getClass().getClassLoader().getResourceAsStream(palette);
        if (paletteStream == null) {
          throw new ResponseStatusException(BAD_REQUEST, "The specified palette '" + palette + "' not found");
        }
        return loader.load(paletteStream);
    }

    private ClosestMatchColors findClosestMatch(NamedColor baseColor, List<NamedColor> palette) {
        List<Pair<NamedColor, Float>> closestColors = matcher.findClosestMatch(baseColor, palette);
        return new ClosestMatchColors(baseColor, closestColors);
    }

}
