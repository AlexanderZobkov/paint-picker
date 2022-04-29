package com.github.paints;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.awt.*;

/**
 * Converts from String to a {@link NamedColor}.
 *
 * A string is a pair of a color visual-human name and a hex value representing that color separated with a ':'.
 */
public class StringToNamedColorConverter implements Converter<String, NamedColor> {

    @Override
    public NamedColor convert(final String source) {
        String[] strings = StringUtils.split(source, ":");
        String name = strings[0];
        String hex = strings[1];
        return new NamedColor(name, Color.decode("#" + hex));
    }
}
