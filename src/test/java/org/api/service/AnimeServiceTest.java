package org.api.service;

import org.api.domain.Anime;
import org.api.repository.AnimeHardCodeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AnimeServiceTest {

    @InjectMocks
    private AnimeService service;

    @Mock
    private AnimeHardCodeRepository repository;

    private List<Anime> animeList;

    @BeforeEach
    void init() {
        Anime deathNote = new Anime(1L, "Death Note");
        Anime naruto = new Anime(2L, "Naruto");
        Anime attackOnTitan = new Anime(3L, "Attack on Titan");
        Anime fullmetalAlchemist = new Anime(4L, "Fullmetal Alchemist");
        Anime dragonBallSuper = new Anime(5L, "Dragon Ball Super");
        animeList = new ArrayList<>();
        Collections.addAll(animeList, deathNote, naruto, attackOnTitan, fullmetalAlchemist, dragonBallSuper);
    }

    @Test
    @DisplayName("findAll should return a list with all animes")
    void findAll_ReturnsAllAnimes_WhenSuccessful() {
        BDDMockito.when(repository.findAll()).thenReturn(animeList);

        var animes = service.findAll();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSameElementsAs(animeList);
    }

    @Test
    @DisplayName("findAll should return an Anime with given name")
    void findByName_ReturnsAnime_WhenSuccessful() {
        var expectedAnime = animeList.getFirst();

        BDDMockito.when(repository.findByName(expectedAnime.getName()))
                .thenReturn(Optional.of(expectedAnime));

        var animeFound = service.findByName(expectedAnime.getName());

        Assertions.assertThat(animeFound)
                .isNotNull()
                .isEqualTo(expectedAnime);
    }

    @Test
    @DisplayName("findById should return an Anime with given ID")
    void findById_ReturnsAnime_WhenSuccessful() {
        var expectedAnime = animeList.getFirst();

        BDDMockito.when(repository.findById(expectedAnime.getId()))
                .thenReturn(Optional.of(expectedAnime));

        var animeFound = service.findById(expectedAnime.getId());

        Assertions.assertThat(animeFound)
                .isNotNull()
                .isEqualTo(expectedAnime);
    }

    @Test
    @DisplayName("save should save an Anime")
    void save_Anime_WhenSuccessful() {
        var animeToSave = new Anime(ThreadLocalRandom.current().nextLong(), "Aniplex");

        BDDMockito.when(repository.save(animeToSave))
                .thenReturn(animeToSave);

        var animeSaved = service.save(animeToSave);

        Assertions.assertThat(animeSaved)
                .isNotNull()
                .isEqualTo(animeToSave);
    }

    @Test
    @DisplayName("deleteById should delete a given Anime by a given ID")
    void deleteById_RemovesAnime_WhenSuccessful() {
        var animeToDelete = animeList.getFirst();

        BDDMockito.when(repository.findById(animeToDelete.getId()))
                .thenReturn(Optional.of(animeToDelete));

        Assertions.assertThatCode(() -> service.deleteById(animeToDelete.getId()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("update saves a producer when successful")
    void update() {
        var animeToUpdate = animeList.getFirst();

        BDDMockito.when(repository.findById(animeToUpdate.getId()))
                .thenReturn(Optional.of(animeToUpdate));

        Assertions.assertThatCode(() -> service.update(animeToUpdate))
                .doesNotThrowAnyException();
    }
}