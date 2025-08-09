package org.api.service;

import org.api.domain.Anime;
import org.api.repository.AnimeHardCodeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class AnimeService {

    private final AnimeHardCodeRepository repository;


    public AnimeService() {
        this.repository = new AnimeHardCodeRepository();
    }

    public List<Anime> findAll() {
        return repository.findAll();
    }

    public Anime findByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Anime findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Anime save(Anime animeToSave) {
        return repository.save(animeToSave);
    }

    public void deleteById(Long id) {
        var animeToDelete = findById(id);
        repository.deleteById(animeToDelete.getId());
    }

    public void update(Anime animeToUpdate) {
        findById(animeToUpdate.getId());
        repository.update(animeToUpdate);
    }
}
