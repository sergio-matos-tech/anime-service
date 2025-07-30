package org.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(value = "/hi") // -> another way of doing it @RequestMapping(method = RequestMethod.GET, value = "/hi")
    public String hi() {
        return "Howdy there!";
    }
}
