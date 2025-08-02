package org.api.domain;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class Anime {

    private Long id;
    private String name;
    @Getter
    private static List<Anime> animes = new ArrayList<>();
    static {
        Anime deathNote = new Anime(1L, "Death Note");
        Anime naruto = new Anime(2L, "Naruto");
        Anime attackOnTitan = new Anime(3L, "Attack on Titan");
        Anime fullmetalAlchemist = new Anime(4L, "Fullmetal Alchemist");
        Anime dragonBallSuper = new Anime(5L, "Dragon Ball Super");
        Collections.addAll(animes, deathNote, naruto, attackOnTitan, fullmetalAlchemist, dragonBallSuper);
    }

    public Anime() {}

    public Anime(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
