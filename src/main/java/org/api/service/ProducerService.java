package org.api.service;

import org.api.domain.Producer;
import org.api.repository.ProducerHardCodedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProducerService {

    private final ProducerHardCodedRepository repository;

    @Autowired
    public ProducerService(ProducerHardCodedRepository repository) {
        this.repository = repository;
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

    public void update(Producer producerToUpdate) {
        var producer = findById(producerToUpdate.getId());
        producerToUpdate.setCreatedAt(producer.getCreatedAt());
        repository.update(producerToUpdate);
    }
}
