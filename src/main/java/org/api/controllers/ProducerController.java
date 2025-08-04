package org.api.controllers;


import lombok.extern.slf4j.Slf4j;
import org.api.mapper.ProducerMapper;
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

    private static final ProducerMapper PRODUCER_MAPPER = ProducerMapper.INSTANCE;

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
        var producer = PRODUCER_MAPPER.toProducer(producerPostRequest);
        var response = PRODUCER_MAPPER.toProducerGetResponse(producer);

        Producer.getProducers().add(producer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
