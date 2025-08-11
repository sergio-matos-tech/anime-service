package org.api.controllers;


import lombok.extern.slf4j.Slf4j;
import org.api.mapper.AnimeMapper;
import org.api.request.AnimePostRequest;
import org.api.request.AnimePutRequest;
import org.api.response.AnimeGetResponse;
import org.api.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController()
@RequestMapping(value = "v1/animes")
@Slf4j
public class AnimeController {

    private static final AnimeMapper ANIME_MAPPER = AnimeMapper.INSTANCE;

    private final AnimeService service;

    @Autowired
    public AnimeController(AnimeService service) {
        this.service = service;
    }

    // idempotent HTTP method
    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> findAll() {
        log.debug("Request all animes");

        var animes = service.findAll();

        var responses = ANIME_MAPPER.toAnimeGetResponseList(animes);

        return ResponseEntity.ok(responses.stream().toList());
    }

    // idempotent HTTP method
    @GetMapping("/search")
    public ResponseEntity<AnimeGetResponse> findByName(@RequestParam(required = false) String name) {
        log.debug("Request to find by name: {}", name);

        var anime = service.findByName(name);

        var animeGetResponse = ANIME_MAPPER.toAnimeGetResponse(anime);

        return ResponseEntity.ok(animeGetResponse);
    }

    // idempotent HTTP method
    @GetMapping("/{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find anime by id: {}", id);

        var anime = service.findById(id);

        var animeGetResponse = ANIME_MAPPER.toAnimeGetResponse(anime);

        return ResponseEntity.ok(animeGetResponse);
    }


    @PostMapping
    public ResponseEntity<AnimeGetResponse> save(@RequestBody AnimePostRequest request) {
        log.debug("Request to save anime : {}", request);

        var anime = ANIME_MAPPER.toAnime(request);

        var animeSaved = service.save(anime);

        var response = ANIME_MAPPER.toAnimeGetResponse(animeSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // idempotent HTTP method
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.debug("Request to delete anime by id: {}", id);

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }


    // idempotent HTTP method
    @PutMapping
    public ResponseEntity<AnimeGetResponse> update(@RequestBody AnimePutRequest request) {
        log.debug("Request to update anime {}", request);

        var animeToUpdate = ANIME_MAPPER.toAnime(request);

        service.update(animeToUpdate);

        return ResponseEntity.noContent().build();
    }
}
