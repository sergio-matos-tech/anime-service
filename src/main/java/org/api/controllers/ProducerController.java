package org.api.controllers;


import lombok.extern.slf4j.Slf4j;
import org.api.request.ProducerPostRequest;
import org.api.domain.Producer;
import org.api.response.ProducerGetResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
    public ResponseEntity<ProducerGetResponse> save(@RequestBody ProducerPostRequest producerPostRequest) {
        Producer obj = new Producer(ThreadLocalRandom.current().nextLong(100_000), producerPostRequest.getName(), LocalDateTime.now());
        Producer.getProducers().add(obj);
        var response = ProducerGetResponse.builder()
                .id(obj.getId())
                .name(obj.getName())
                .createdAt(obj.getCreatedAt())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
