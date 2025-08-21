package org.api.repository;

import org.api.domain.Anime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class AnimeData {

    private final List<Anime> ANIMES = new ArrayList<>();

    {
        Anime deathNote = new Anime(1L, "Death Note");
        Anime naruto = new Anime(2L, "Naruto");
        Anime attackOnTitan = new Anime(3L, "Attack on Titan");
        Anime fullmetalAlchemist = new Anime(4L, "Fullmetal Alchemist");
        Anime dragonBallSuper = new Anime(5L, "Dragon Ball Super");
        Collections.addAll(ANIMES, deathNote, naruto, attackOnTitan, fullmetalAlchemist, dragonBallSuper);
    }

    public List<Anime> getAnimes() {
        return ANIMES;
    }
}
