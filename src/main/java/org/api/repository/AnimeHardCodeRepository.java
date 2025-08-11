package org.api.repository;

import org.api.domain.Anime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class AnimeHardCodeRepository {

    private final static List<Anime> ANIMES = new ArrayList<>();
    static {
        Anime deathNote = new Anime(1L, "Death Note");
        Anime naruto = new Anime(2L, "Naruto");
        Anime attackOnTitan = new Anime(3L, "Attack on Titan");
        Anime fullmetalAlchemist = new Anime(4L, "Fullmetal Alchemist");
        Anime dragonBallSuper = new Anime(5L, "Dragon Ball Super");
        Collections.addAll(ANIMES, deathNote, naruto, attackOnTitan, fullmetalAlchemist, dragonBallSuper);
    }

    public List<Anime> findAll() { return ANIMES; }

    public Optional<Anime> findByName(String name) {
        return ANIMES.stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).findFirst();
    }

    public Optional<Anime> findById(Long id) {
        return ANIMES.stream().filter(anime -> anime.getId().equals(id)).findFirst();
    }

    public Anime save(Anime animeToSave) {
        ANIMES.add(animeToSave);
        return animeToSave;
    }

    public void deleteById(Long id) {
        ANIMES.removeIf(anime -> anime.getId().equals(id));
    }

    public void update(Anime animeToUpdate) {
        deleteById(animeToUpdate.getId());
        ANIMES.add(animeToUpdate);
    }
}
