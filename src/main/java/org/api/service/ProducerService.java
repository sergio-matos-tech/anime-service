package org.api.service;

import org.api.domain.Producer;
import org.api.repository.ProducerHardCodedRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

public class ProducerService {

    private final ProducerHardCodedRepository repository;

    public ProducerService() {
        this.repository = new ProducerHardCodedRepository();
    }

    public List<Producer> findAll() {
        return repository.findAll();
    }

    public Producer findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Producer findByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Producer save(Producer producer) {
        return repository.save(producer);
    }

    public void deleteById(Long id) {
        var producerToDelete = findById(id);
        repository.deleteById(producerToDelete.getId());
    }

    public void update(Producer producer) {
        var producerToUpdate = findById(producer.getId());
        producerToUpdate.setCreatedAt(producerToUpdate.getCreatedAt());
        repository.update(producerToUpdate);
    }
}
