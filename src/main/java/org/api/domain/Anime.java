package org.api.domain;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Anime {

    private Long id;
    private String name;

    public Anime() {}

    public Anime(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Anime> getHardCodedAnimes() {
        return List.of(
                new Anime(1L, "Death Note"),
                new Anime(2L, "Naruto"),
                new Anime(3L, "Attack on Titan"),
                new Anime(4L, "Fullmetal Alchemist"),
                new Anime(5L, "Dragon Ball Super")
        );
    }
}
