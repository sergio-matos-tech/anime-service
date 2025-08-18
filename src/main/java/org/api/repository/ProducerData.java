package org.api.repository;

import org.api.domain.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ProducerData {
    private final List<Producer> producers = new ArrayList<>();
    {
        Producer madhouse = new Producer(1L, "Madhouse", LocalDateTime.now());
        Producer pierrot = new Producer(2L, "Pierrot", LocalDateTime.now());
        Producer witStudio = new Producer(3L, "Wit Studio", LocalDateTime.now());
        Producer bones = new Producer(4L, "Bones", LocalDateTime.now());
        Producer toeiAnimation = new Producer(5L, "ToeiAnimation", LocalDateTime.now());
        Collections.addAll(producers, madhouse, pierrot, witStudio, bones, toeiAnimation);
    }

    public List<Producer> getProducers() { return producers; }
}
