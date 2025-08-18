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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProducerHardCodedRepositoryTest {

    @InjectMocks
    private ProducerHardCodedRepository repository;

    @Mock
    private ProducerData producerData;
    private List<Producer> producers = new ArrayList<>();

    @BeforeEach
    void init() {
        Producer madhouse = new Producer(1L, "Madhouse", LocalDateTime.now());
        Producer pierrot = new Producer(2L, "Pierrot", LocalDateTime.now());
        Producer witStudio = new Producer(3L, "Wit Studio", LocalDateTime.now());
        Producer bones = new Producer(4L, "Bones", LocalDateTime.now());
        Producer toeiAnimation = new Producer(5L, "ToeiAnimation", LocalDateTime.now());
        Collections.addAll(producers, madhouse, pierrot, witStudio, bones, toeiAnimation);
    }

    @Test
    @DisplayName("findAll must returns a list with all producers")
    void findAll_ReturnsAllProducers_whenSuccesful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producers);
        var producers = repository.findAll();
        Assertions.assertThat(producers).isNotNull().hasSize(5);
    }
}
