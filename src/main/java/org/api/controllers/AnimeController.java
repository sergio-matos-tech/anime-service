package org.api.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController()
@RequestMapping(value = "v1/animes")
@Slf4j
public class AnimeController {

    @GetMapping
    public List<String> findAll() {
        log.info(Thread.currentThread().getName());
        return List.of("Dragon Ball Z", "Naruto", "Bleach");
    }
}
