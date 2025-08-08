package org.api.controllers;


import lombok.extern.slf4j.Slf4j;
import org.api.domain.Anime;
import org.api.mapper.AnimeMapper;
import org.api.request.AnimePostRequest;
import org.api.request.AnimePutRequest;
import org.api.response.AnimeGetResponse;
import org.api.response.AnimePostResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController()
@RequestMapping(value = "v1/animes")
@Slf4j
public class AnimeController {

    private static final AnimeMapper ANIME_MAPPER = AnimeMapper.INSTANCE;

    // idempotent HTTP method
    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> findAll() {
        log.debug("Request all animes");
        var animes = Anime.getAnimes();
        List<AnimeGetResponse> animeGetResponseList = ANIME_MAPPER.toAnimeGetResponseList(animes);

        return ResponseEntity.ok(animeGetResponseList.stream().toList());
    }

    // idempotent HTTP method
    @GetMapping("/search")
    public ResponseEntity<List<AnimeGetResponse>> findByName(@RequestParam(required = false) String name) {
        log.debug("Request to find by name: {}", name);
        var animes = Anime.getAnimes();
        List<AnimeGetResponse> animeGetResponseList = ANIME_MAPPER.toAnimeGetResponseList(animes);

        var response = animeGetResponseList.stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(name))
                .toList();

        return ResponseEntity.ok(response);
    }

    // idempotent HTTP method
    @GetMapping("/{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {

        log.debug("Request to find anime by id: {}", id);
        var animeGetResponse = Anime.getAnimes()
                .stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .map(ANIME_MAPPER::toAnimeGetResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(animeGetResponse);
    }


    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest request) {
        log.debug("Request to save anime : {}", request);
        var anime = ANIME_MAPPER.toAnime(request);

        Anime.getAnimes().add(anime);

        var response = ANIME_MAPPER.toAnimePostResponse(anime);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // idempotent HTTP method
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.debug("Request to delete anime by id: {}", id);

        var animeToDelete = Anime.getAnimes()
                .stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Anime.getAnimes().remove(animeToDelete);

        return ResponseEntity.noContent().build();
    }


    // idempotent HTTP method
    @PutMapping
    public ResponseEntity<AnimeGetResponse> update(@RequestBody AnimePutRequest request) {
        log.debug("Request to update anime {}", request);

        var animeToRemove = Anime.getAnimes()
                .stream()
                .filter(a -> a.getId().equals(request.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var animeUpdated = ANIME_MAPPER.toAnime(request);
        Anime.getAnimes().remove(animeToRemove);
        Anime.getAnimes().add(animeUpdated);

        return ResponseEntity.noContent().build();
    }
}
