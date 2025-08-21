package org.api.repository;

import lombok.RequiredArgsConstructor;
import org.api.domain.Anime;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AnimeHardCodeRepository {

    private final AnimeData animeData;

    public List<Anime> findAll() { return animeData.getAnimes(); }

    public Optional<Anime> findByName(String name) {
        return animeData.getAnimes().stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).findFirst();
    }

    public Optional<Anime> findById(Long id) {
        return animeData.getAnimes().stream().filter(anime -> anime.getId().equals(id)).findFirst();
    }

    public Anime save(Anime animeToSave) {
        animeData.getAnimes().add(animeToSave);
        return animeToSave;
    }

    public void deleteById(Long id) {
        animeData.getAnimes().removeIf(anime -> anime.getId().equals(id));
    }

    public void update(Anime animeToUpdate) {
        deleteById(animeToUpdate.getId());
        animeData.getAnimes().add(animeToUpdate);
    }
}
