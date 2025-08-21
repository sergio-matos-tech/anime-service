package org.api.service;

import org.api.domain.Producer;
import org.api.repository.ProducerHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@ExtendWith(MockitoExtension.class)
class ProducerServiceTest {

    @InjectMocks
    private ProducerService service;

    @Mock
    private ProducerHardCodedRepository repository;

    private List<Producer> producersList;

    @BeforeEach
    void init() {
        Producer madhouse = new Producer(1L, "Madhouse", LocalDateTime.now());
        Producer pierrot = new Producer(2L, "Pierrot", LocalDateTime.now());
        Producer witStudio = new Producer(3L, "Wit Studio", LocalDateTime.now());
        Producer bones = new Producer(4L, "Bones", LocalDateTime.now());
        Producer toeiAnimation = new Producer(5L, "ToeiAnimation", LocalDateTime.now());
        producersList = new ArrayList<>();
        Collections.addAll(producersList, madhouse, pierrot, witStudio, bones, toeiAnimation);
    }

    @Test
    @DisplayName("findAll returns a list with all producers")
    void findAll_ReturnsAllProducers_WhenSuccessful() {
        BDDMockito.when(repository.findAll()).thenReturn(producersList);

        var producers = service.findAll();

        Assertions.assertThat(producers)
                .isNotNull()
                .isNotEmpty()
                .hasSameElementsAs(producersList);
    }

    @Test
    @DisplayName("findById returns a producer when successful")
    void findById_ReturnsProducer_WhenSuccessful() {
        Producer expectedProducer = producersList.getFirst();
        BDDMockito.when(repository.findById(expectedProducer.getId()))
                .thenReturn(Optional.of(expectedProducer));

        Producer producerFound = service.findById(expectedProducer.getId());

        Assertions.assertThat(producerFound).isNotNull().isEqualTo(expectedProducer);
    }

    @Test
    @DisplayName("findByName throws exception when producer is not found")
    void findByName_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        Producer expectedProducer = producersList.getFirst();
        BDDMockito.when(repository.findByName(expectedProducer.getName()))
                .thenReturn(Optional.of(expectedProducer));

        var producerFound = service.findByName(expectedProducer.getName());
        Assertions.assertThat(producerFound)
                .isNotNull()
                .isEqualTo(expectedProducer);
    }

    @Test
    @DisplayName("save creates and returns a producer when successful")
    void save_CreatesProducer_WhenSuccessful() {
        var producerToSave = new Producer(ThreadLocalRandom.current().nextLong(), "MAPPA", LocalDateTime.now());
        BDDMockito.when(repository.save(producerToSave)).thenReturn(producerToSave);

        var producerSaved = service.save(producerToSave);

        Assertions.assertThat(producerSaved)
                .isNotNull()
                .isEqualTo(producerToSave);
    }

    @Test
    @DisplayName("deleteById removes a producer when successful")
    void deleteById_RemovesProducer_WhenSuccessful() {
        var producerToDelete = producersList.getFirst();

        BDDMockito.when(repository.findById(producerToDelete.getId()))
                .thenReturn(Optional.of(producerToDelete));

        BDDMockito.willDoNothing().given(repository).deleteById(producerToDelete.getId());

        Assertions.assertThatCode(() -> service.deleteById(producerToDelete.getId()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("update saves a producer when successful")
    void update_SavesProducer_WhenSuccessful() {
        var producerToUpdate = producersList.getFirst();

        BDDMockito.when(repository.findById(producerToUpdate.getId()))
                .thenReturn(Optional.of(producerToUpdate));

        Assertions.assertThatCode(() -> service.update(producerToUpdate))
                .doesNotThrowAnyException();
    }
}