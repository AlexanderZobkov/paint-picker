package com.github.paints

import groovy.xml.XmlParser
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult

import static org.junit.jupiter.api.Assertions.assertTrue
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest()
@AutoConfigureMockMvc
class PaletteControllerTest {

    @Autowired
    MockMvc mockMvc

    @Test
    void listAvailablePalettes() {
        MvcResult result = mockMvc.perform(get("/palettes"))
                .andExpect(status().isOk())
                .andReturn()
        String responseBody = result.response.getContentAsString()
        Node rootNode = new XmlParser().parseText(responseBody)
        NodeList availablePalettes = rootNode.body.div.a
        assertTrue(availablePalettes.size() > 0)

    }

    @Test
    void viewColorsInThePalette() {
        MvcResult result = mockMvc.perform(get("/palettes/Dulux.acb"))
                .andExpect(status().isOk())
                .andReturn()
        String responseBody = result.response.getContentAsString()
        Node rootNode = new XmlParser().parseText(responseBody)
        NodeList colors = rootNode.body.div.div
        assertTrue(colors.size() > 0)
    }

    @Test
    @Disabled('Error handling is not property implemented')
    void unknownPalette() {
        mockMvc.perform(get("/palettes/unknown.acb"))
                .andExpect(status().isBadRequest())
                .andReturn()
    }

    @Test
    void searchColors() {
        MvcResult result = mockMvc.perform(get("/palettes/Dulux.acb/search/colors?" +
                "named-color=color 1:AABBCC&named-color=color 2:BBCCAA&named-color=color 3:CCBBAA"))
                .andExpect(status().isOk())
                .andReturn()
        String responseBody = result.response.getContentAsString()
        Node rootNode = new XmlParser().parseText(responseBody)
        NodeList colors = rootNode.body.div.div
        assertTrue(colors.size() > 0)
    }

    @Test
    void searchPalette() {
        MvcResult result = mockMvc.perform(get("/palettes/Dulux.acb/search/palette/Little-Greeny.acb"))
                .andExpect(status().isOk())
                .andReturn()
        String responseBody = result.response.getContentAsString()
        Node rootNode = new XmlParser().parseText(responseBody)
        NodeList colors = rootNode.body.div.div
        assertTrue(colors.size() > 0)
    }

}