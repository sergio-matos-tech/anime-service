package org.api.controllers;


import lombok.extern.slf4j.Slf4j;
import org.api.domain.Anime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController()
@RequestMapping(value = "v1/animes")
@Slf4j
public class AnimeController {

    @GetMapping
    public List<Anime> findAll() {
        log.info(Thread.currentThread().getName());
        return Anime.getHardCodedAnimes();
    }

    @GetMapping("/search")
    public Anime findByName(@RequestParam String name) {
        return Anime.getHardCodedAnimes()
                .stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public Anime findById(@PathVariable Long id) {
        return Anime.getHardCodedAnimes()
                .stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
