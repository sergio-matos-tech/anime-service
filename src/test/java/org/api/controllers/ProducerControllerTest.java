package org.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.api.domain.Producer;
import org.api.request.ProducerPostRequest;
import org.api.service.ProducerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.mockito.BDDMockito;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProducerController.class)
class ProducerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProducerService producerService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("findAll returns a list of producers when successful")
    void findAll_ReturnAllProducers_WhenSuccessful() throws Exception {
        // Arrange
        List<Producer> mockProducers = List.of(
                new Producer(1L, "Madhouse", LocalDateTime.now()),
                new Producer(2L, "Pierrot", LocalDateTime.now())
        );
        BDDMockito.given(producerService.findAll()).willReturn(mockProducers);

        // Act & Assert
        mockMvc.perform(get("/v1/producers"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Madhouse"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Pierrot"));
    }

    @Test
    @DisplayName("findById returns a single producer when successful")
    void findById_ReturnsSingleProducer_WhenSuccessful() throws Exception {
        // Arrange
        Producer mockProducer = new Producer(1L, "Madhouse", LocalDateTime.now());
        BDDMockito.given(producerService.findById(1L)).willReturn(mockProducer);

        // Act & Assert
        mockMvc.perform(get("/v1/producers/{id}", 1L))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Madhouse"));
    }

    @Test
    @DisplayName("findById returns 404 when producer is not found")
    void findById_Returns404_WhenProducerNotFound() throws Exception {
        // Arrange
        BDDMockito.given(producerService.findById(99L))
                .willThrow(new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND));

        // Act & Assert
        mockMvc.perform(get("/v1/producers/{id}", 99L))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("findByName returns a single producer when successful")
    void findByName_ReturnsSingleProducer_WhenSuccessful() throws Exception {
        // Arrange
        Producer mockProducer = new Producer(1L, "Madhouse", LocalDateTime.now());
        BDDMockito.given(producerService.findByName("Madhouse")).willReturn(mockProducer);

        // Act & Assert
        mockMvc.perform(get("/v1/producers/search").param("name", "Madhouse"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Madhouse"));
    }

    @Test
    @DisplayName("findByName returns 404 when producer is not found")
    void findByName_Returns404_WhenProducerNotFound() throws Exception {
        // Arrange
        BDDMockito.given(producerService.findByName("NonExistent"))
                .willThrow(new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND));

        // Act & Assert
        mockMvc.perform(get("/v1/producers/search").param("name", "NonExistent"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("save creates and returns a producer when successful")
    void save_CreatesAndReturnsProducer_WhenSuccessful() throws Exception {
        // Arrange
        ProducerPostRequest postRequest = new ProducerPostRequest();
        postRequest.setName("MAPPA");

        // The ID is generated by the mapper, so we mock the service to return a Producer with a new ID.
        Producer producerToSave = new Producer(ThreadLocalRandom.current().nextLong(), postRequest.getName(), LocalDateTime.now());

        // BDDMockito.any() is used because we don't know the generated ID the controller will send to the service.
        BDDMockito.given(producerService.save(BDDMockito.any(Producer.class))).willReturn(producerToSave);

        // Act & Assert
        mockMvc.perform(post("/v1/producers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated()) // Verify the HTTP status is 201 CREATED
                .andExpect(jsonPath("$.id").value(producerToSave.getId())) // Verify the ID
                .andExpect(jsonPath("$.name").value(producerToSave.getName())); // Verify the name
    }
}