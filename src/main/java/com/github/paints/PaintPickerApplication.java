package com.github.paints;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PaintPickerApplication {

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

}