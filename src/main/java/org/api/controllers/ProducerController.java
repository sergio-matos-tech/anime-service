package org.api.controllers;


import lombok.extern.slf4j.Slf4j;
import org.api.domain.Anime;
import org.api.mapper.ProducerMapper;
import org.api.request.ProducerPostRequest;
import org.api.domain.Producer;
import org.api.request.ProducerPutRequest;
import org.api.response.AnimeGetResponse;
import org.api.response.ProducerGetResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController()
@RequestMapping(value = "v1/producers")
@Slf4j
public class ProducerController {

    private static final ProducerMapper PRODUCER_MAPPER = ProducerMapper.INSTANCE;

    // idempotent HTTP method
    @GetMapping
    public ResponseEntity<List<ProducerGetResponse>> findAll() {
        log.info("Request all producers");
        var producers = Producer.getProducers();
        List<ProducerGetResponse> producerGetResponses = PRODUCER_MAPPER.toProducerGetResponseList(producers);

        return ResponseEntity.ok(producerGetResponses.stream().toList());
    }

    // idempotent HTTP method
    @GetMapping("/search")
    public ResponseEntity<List<ProducerGetResponse>> findByName(@RequestParam(required = false) String name) {
        log.debug("Request to find by name: {}", name);

        var producers = Producer.getProducers();
        List<ProducerGetResponse> producerGetResponse = PRODUCER_MAPPER.toProducerGetResponseList(producers);

        return ResponseEntity.ok(producerGetResponse.stream().filter(p -> p.getName().equalsIgnoreCase(name)).toList());
    }

    // idempotent HTTP method
    @GetMapping("/{id}")
    public ResponseEntity<ProducerGetResponse> findById(@PathVariable Long id) {

        log.debug("Request to find producer by id: {}", id);
        var producerGetResponse = Producer.getProducers()
                .stream()
                .filter(producer -> producer.getId().equals(id))
                .findFirst()
                .map(PRODUCER_MAPPER::toProducerGetResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(producerGetResponse);
    }

    @PostMapping
    public ResponseEntity<ProducerGetResponse> save(@RequestBody ProducerPostRequest producerPostRequest) {
        var producer = PRODUCER_MAPPER.toProducer(producerPostRequest);
        var response = PRODUCER_MAPPER.toProducerGetResponse(producer);

        Producer.getProducers().add(producer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // idempotent HTTP method
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.debug("Request to delete producer by id: {}", id);

        var producerToDelete = Producer.getProducers()
                .stream()
                .filter(producer -> producer.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Producer.getProducers().remove(producerToDelete);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest request) {
        log.debug("Request to delete producer: {}", request);

        var producerToDelete = Producer.getProducers()
                .stream()
                .filter(producer -> producer.getId().equals(request.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var producerUpdated = PRODUCER_MAPPER.toProducer(request, LocalDateTime.now());
        Producer.getProducers().remove(producerToDelete);
        Producer.getProducers().add(producerUpdated);

        return ResponseEntity.noContent().build();
    }
}
