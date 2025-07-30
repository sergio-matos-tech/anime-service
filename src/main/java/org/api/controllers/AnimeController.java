package org.api.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController()
@RequestMapping(value = "/v1/animes")
public class AnimeController {

    @GetMapping
    public List<String> findAll() {
        return List.of("Dragon Ball Z", "Naruto", "Bleach");
    }
}
