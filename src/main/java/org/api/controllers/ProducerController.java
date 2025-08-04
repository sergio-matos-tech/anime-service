package org.api.controllers;


import lombok.extern.slf4j.Slf4j;
import org.api.domain.Producer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController()
@RequestMapping(value = "v1/producers")
@Slf4j
public class ProducerController {

    @GetMapping
    public List<Producer> findAll() {
        log.info(Thread.currentThread().getName());
        return Producer.getProducers();
    }

    @GetMapping("/search")
    public Producer findByName(@RequestParam(required = false) String name) {
        return Producer.getProducers()
                .stream()
                .filter(producer -> producer.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public Producer findById(@PathVariable Long id) {
        return Producer.getProducers()
                .stream()
                .filter(producer -> producer.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // Idempotent
    @PostMapping
    public Producer save(@RequestBody Producer producer) {
        Producer obj = new Producer(ThreadLocalRandom.current().nextLong(100_000), producer.getName());
        Producer.getProducers().addLast(obj);
        return obj;
    }
}
