package org.api.controllers;

import org.api.domain.Producer;
import org.api.service.ProducerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.BDDMockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProducerController.class)
class ProducerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProducerService producerService;

    @Test
    @DisplayName("findAll returns a list of producers when successful")
    void findAll_ReturnAllProducers_WhenSuccessful() throws Exception {
        List<Producer> mockProducers = List.of(
                new Producer(1L, "Madhouse", LocalDateTime.now()),
                new Producer(2L, "Pierrot", LocalDateTime.now())
        );
        BDDMockito.given(producerService.findAll()).willReturn(mockProducers);

        mockMvc.perform(get("/v1/producers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Madhouse"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Pierrot"));
    }
}