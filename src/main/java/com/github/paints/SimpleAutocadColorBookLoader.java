package com.github.paints;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A {@link ColorPaletteLoader} for files containing AutoCAD Color Book in XML format:
 * https://download.autodesk.com/global/acb/index.html
 * <p>
 * It just finds all XML elements: 'colorEntry' and
 * uses sub-elements: 'colorName', 'RGB8 to construct a list of {@link NamedColor}.
 */
public class SimpleAutocadColorBookLoader implements ColorPaletteLoader {

    private final ObjectMapper mapper;

    public SimpleAutocadColorBookLoader(){
        mapper = new XmlMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public List<NamedColor> load(final InputStream palette) throws IOException {
        Objects.requireNonNull(palette);
        final ColorBook colorBook = mapper.readValue(palette, ColorBook.class);
        return colorBook.colorPage.stream()
                .map(colorPage -> colorPage.colorEntry)
                .flatMap(Collection::stream)
                .map(this::mapToNamedColor)
                .collect(Collectors.toList());
    }

    private NamedColor mapToNamedColor(final ColorEntry colorEntry) {
        return new NamedColor(colorEntry.colorName,
                new Color(colorEntry.rgb8.red,
                        colorEntry.rgb8.green,
                        colorEntry.rgb8.blue));
    }

    @JacksonXmlRootElement(localName = "colorBook")
    static class ColorBook {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "colorPage")
        private List<ColorPage> colorPage;
    }

    static class ColorPage {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "colorEntry")
        private List<ColorEntry> colorEntry;
    }

    static class ColorEntry {
        @JacksonXmlProperty(localName = "colorName")
        private String colorName;
        @JacksonXmlProperty(localName = "RGB8")
        private RGB8 rgb8;
    }

    static class RGB8 {
        @JacksonXmlProperty(localName = "red")
        private int red;
        @JacksonXmlProperty(localName = "green")
        private int green;
        @JacksonXmlProperty(localName = "blue")
        private int blue;
    }

}
