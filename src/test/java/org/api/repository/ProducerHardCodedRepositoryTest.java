package org.api.repository;

import org.api.domain.Producer;
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
import java.util.concurrent.ThreadLocalRandom;

@ExtendWith(MockitoExtension.class)
class ProducerHardCodedRepositoryTest {

    @InjectMocks
    private ProducerHardCodedRepository repository;

    @Mock
    private ProducerData producerData;
    private List<Producer> producers;

    @BeforeEach
    void init() {
        Producer madhouse = new Producer(1L, "Madhouse", LocalDateTime.now());
        Producer pierrot = new Producer(2L, "Pierrot", LocalDateTime.now());
        Producer witStudio = new Producer(3L, "Wit Studio", LocalDateTime.now());
        Producer bones = new Producer(4L, "Bones", LocalDateTime.now());
        Producer toeiAnimation = new Producer(5L, "ToeiAnimation", LocalDateTime.now());
        producers = new ArrayList<>();
        Collections.addAll(producers, madhouse, pierrot, witStudio, bones, toeiAnimation);
    }

    @Test
    @DisplayName("findAll must return a list with all producers")
    void findAll_ReturnsAllProducers_whenSuccesful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producers);

        var producers = repository.findAll();
        Assertions.assertThat(producers).isNotNull().hasSize(5);
    }

    @Test
    @DisplayName("findById must return a producer with given id")
    void findById_ReturnsProducer_whenSuccesful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producers);

        var expectedProducer = producers.getFirst();
        var producer = repository.findById(expectedProducer.getId());
        Assertions.assertThat(producer).isPresent().contains(expectedProducer);
    }

    @Test
    @DisplayName("findByName must return a producer with given name")
    void findByName_ReturnsProducer_WhenSuccesful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producers);

        var expectedProducer = producers.getFirst();
        var producer = repository.findByName(expectedProducer.getName());
        Assertions.assertThat(producer).isPresent().contains(expectedProducer);
    }

    @Test
    @DisplayName("save must create a producer")
    void save_CreatesProducer_WhenSuccesful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producers);

        var producerToSave = new Producer(ThreadLocalRandom.current().nextLong(), "MAPPA", LocalDateTime.now());
        var producer = repository.save(producerToSave);

        Assertions.assertThat(producer).isEqualTo(producerToSave).hasNoNullFieldsOrProperties();

        var producerSavedOptional = repository.findById(producerToSave.getId());
        Assertions.assertThat(producerSavedOptional).isPresent().contains(producerToSave);
    }

    @Test
    @DisplayName("delete removes a producer")
    void delete_RemovesProducer_WhenSuccesful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producers);

        var producerToDelete = producers.getFirst();
        repository.deleteById(producerToDelete.getId());

        Assertions.assertThat(this.producers).isNotEmpty().doesNotContain(producerToDelete);
    }

    @Test
    @DisplayName("update must update a producer")
    void update_UpdatesProducer_WhenSuccesful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producers);

        var producerToUpdate = this.producers.getFirst();
        producerToUpdate.setName("Aniplex");
        repository.update(producerToUpdate);

        Assertions.assertThat(this.producers).contains(producerToUpdate);

        var producerUpdatedOptional = repository.findById(producerToUpdate.getId());

        Assertions.assertThat(producerUpdatedOptional).isPresent();
        Assertions.assertThat(producerUpdatedOptional.get().getName()).isEqualTo("Aniplex");
    }
}
