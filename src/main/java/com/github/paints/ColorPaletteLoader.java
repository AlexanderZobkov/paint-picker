package com.github.paints;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * A loader of a color palette.
 */
public interface ColorPaletteLoader {

    /**
     * Loads a color palette from a specified file.
     *
     * @param palette an input stream containing color palette. Must not be null.
     * @return a list of colors loaded from the palette. Never null.
     * @throws IOException if an error occurred when loading a palette.
     */
    List<NamedColor> load(InputStream palette) throws IOException;
}
