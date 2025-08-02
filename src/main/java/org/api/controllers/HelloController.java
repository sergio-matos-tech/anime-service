package org.api.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RequestMapping("v1/greetings")
@RestController()
@Slf4j
public class HelloController {

    @GetMapping(value = "/hi") // -> another way of doing it @RequestMapping(method = RequestMethod.GET, value = "/hi")
    public String hi() {
        return "Howdy there!";
    }

    @PostMapping
    public Long save(@RequestBody String name) {
        log.info("save '{}'", name);
        return ThreadLocalRandom.current().nextLong(1, 1000);
    }
}
