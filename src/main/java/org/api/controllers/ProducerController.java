package org.api.controllers;


import lombok.extern.slf4j.Slf4j;
import org.api.mapper.ProducerMapper;
import org.api.request.ProducerPostRequest;
import org.api.request.ProducerPutRequest;
import org.api.response.ProducerGetResponse;
import org.api.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "v1/producers")
@Slf4j
public class ProducerController {

    private static final ProducerMapper PRODUCER_MAPPER = ProducerMapper.INSTANCE;

    private final ProducerService service;

    @Autowired
    public ProducerController(ProducerService service) {
        this.service = service;
    }

    // idempotent HTTP method
    @GetMapping
    public ResponseEntity<List<ProducerGetResponse>> findAll() {
        log.info("Request all producers");

        var producers = service.findAll();
        var responses = PRODUCER_MAPPER.toProducerGetResponseList(producers);

        return ResponseEntity.ok(responses.stream().toList());
    }

    // idempotent HTTP method
    @GetMapping("/search")
    public ResponseEntity<ProducerGetResponse> findByName(@RequestParam(required = false) String name) {
        log.debug("Request to find by name: {}", name);

        var producer = service.findByName(name);

        var producerGetResponse = PRODUCER_MAPPER.toProducerGetResponse(producer);

        return ResponseEntity.ok(producerGetResponse);
    }

    // idempotent HTTP method
    @GetMapping("/{id}")
    public ResponseEntity<ProducerGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find producer by id: {}", id);

        var producer = service.findById(id);

        var producerGetResponse = PRODUCER_MAPPER.toProducerGetResponse(producer);

        return ResponseEntity.ok(producerGetResponse);
    }

    @PostMapping
    public ResponseEntity<ProducerGetResponse> save(@RequestBody ProducerPostRequest producerPostRequest) {
        log.debug("Request to save producer {}", producerPostRequest);

        var producer = PRODUCER_MAPPER.toProducer(producerPostRequest);

        var producerSaved = service.save(producer);

        var response = PRODUCER_MAPPER.toProducerGetResponse(producerSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // idempotent HTTP method
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.debug("Request to delete producer by id: {}", id);

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest request) {
        log.debug("Request to delete producer: {}", request);

        var producerToUpdate = PRODUCER_MAPPER.toProducer(request);

        service.update(producerToUpdate);

        return ResponseEntity.noContent().build();
    }
}
