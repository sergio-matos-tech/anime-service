package org.api.controllers;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/heroes")
public class HeroController {

    private static final List<String> HEROES = List.of("Guts", "Zoro", "Kakashi", "Goku");

    @GetMapping("{index}")
    public String findHeroByIndex(@PathVariable Integer index) {
        return HEROES.stream()
                .skip(index)
                .findFirst()
                .orElseThrow(() -> new ArrayIndexOutOfBoundsException("Index incorrect."));
    }

    @GetMapping
    public List<String> findAll() {
        return HEROES;
    }

    @GetMapping("filter")
    public List<String> findAllParam(@RequestParam String name) {
        return HEROES.stream().filter(hero -> hero.equalsIgnoreCase(name)).toList();
    }
}
