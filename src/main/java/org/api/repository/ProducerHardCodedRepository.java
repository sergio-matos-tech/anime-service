package org.api.repository;

import org.api.domain.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class ProducerHardCodedRepository {

    private static final List<Producer> PRODUCERS = new ArrayList<>();
    static {
        Producer madhouse = new Producer(1L, "Madhouse", LocalDateTime.now());
        Producer pierrot = new Producer(2L, "Pierrot", LocalDateTime.now());
        Producer witStudio = new Producer(3L, "Wit Studio", LocalDateTime.now());
        Producer bones = new Producer(4L, "Bones", LocalDateTime.now());
        Producer toeiAnimation = new Producer(5L, "ToeiAnimation", LocalDateTime.now());
        Collections.addAll(PRODUCERS, madhouse, pierrot, witStudio, bones, toeiAnimation);
    }

    public List<Producer> findAll() { return PRODUCERS; }

    public Optional<Producer> findById(Long id) {
        return PRODUCERS.stream().filter(producer -> producer.getId().equals(id)).findFirst();
    }

    public Optional<Producer> findByName(String name) {
        return PRODUCERS.stream().filter(producer -> producer.getName().equalsIgnoreCase(name)).findFirst();
    }

    public Producer save(Producer producer) {
        PRODUCERS.add(producer);
        return producer;
    }

    public void deleteById(Long id) {
        PRODUCERS.removeIf(producer -> producer.getId().equals(id));
    }

    public void update(Producer producer) {
        deleteById(producer.getId());
        PRODUCERS.add(producer);
    }
}
