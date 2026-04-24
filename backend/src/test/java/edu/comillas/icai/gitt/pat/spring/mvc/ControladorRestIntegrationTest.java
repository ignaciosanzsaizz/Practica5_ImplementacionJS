package edu.comillas.icai.gitt.pat.spring.mvc;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ControladorRest.class)
class ControladorRestIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void creaContadorOkTest() throws Exception {
        // Given ...
        String contador = "{\"nombre\":\"visitas\",\"valor\":0}";
        // When ...
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/contadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contador))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(contador));
    }


    @Test
    void creaContadorNuloTest() throws Exception {
        String contador = "{\"nombre\":\"visitas\",\"valor\":0}";

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/contadores")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(contador))

                //Then
        .andExpect(MockMvcResultMatchers.status().isBadRequest());;
    }
}
