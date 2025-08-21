package org.api.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.api.domain.Producer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProducerHardCodedRepository {

    private final ProducerData producerData;

    public List<Producer> findAll() { return producerData.getProducers(); }

    public Optional<Producer> findById(Long id) {
        return producerData.getProducers().stream().filter(producer -> producer.getId().equals(id)).findFirst();
    }

    public Optional<Producer> findByName(String name) {
        return producerData.getProducers().stream().filter(producer -> producer.getName().equalsIgnoreCase(name)).findFirst();
    }

    public Producer save(Producer producer) {
        producerData.getProducers().add(producer);
        return producer;
    }

    public void deleteById(Long id) {
        producerData.getProducers().removeIf(producer -> producer.getId().equals(id));
    }

    public void update(Producer producer) {
        deleteById(producer.getId());
        producerData.getProducers().add(producer);
    }
}
