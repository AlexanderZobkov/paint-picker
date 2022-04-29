package com.github.paints;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PaintPickerApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(PaintPickerApplication.class, args);
    }

    @Bean
    ColorPaletteLoader paletteLoader(){
        return new SimpleAutocadColorBookLoader();
    }

    @Bean
    ColorMatcher colorMatcher(){
        return new SimpleColorMatcher(new EnhancedWeightedDistanceMeasurer());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToNamedColorConverter());
    }

}