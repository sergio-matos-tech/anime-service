package org.api.repository;

import org.api.domain.Anime;
import org.api.domain.Producer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@ExtendWith(MockitoExtension.class)
class AnimeHardCodedRepositoryTest {

    @InjectMocks
    private AnimeHardCodeRepository repository;

    @Mock
    private AnimeData animeData;
    private List<Anime> animes;

    @BeforeEach
    void init() {
        Anime deathNote = new Anime(1L, "Death Note");
        Anime naruto = new Anime(2L, "Naruto");
        Anime attackOnTitan = new Anime(3L, "Attack on Titan");
        Anime fullmetalAlchemist = new Anime(4L, "Fullmetal Alchemist");
        Anime dragonBallSuper = new Anime(5L, "Dragon Ball Super");
        animes = new ArrayList<>();
        Collections.addAll(animes, deathNote, naruto, attackOnTitan, fullmetalAlchemist, dragonBallSuper);
    }

    @Test
    @DisplayName("findAll must return a list with all animes" +
            "")
    void findAll_ReturnsAllAnimes_whenSuccesful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);

        var animes = repository.findAll();
        Assertions.assertThat(animes).isNotNull().hasSize(5);
    }

    @Test
    @DisplayName("findById must return an anime with given id")
    void findById_ReturnsAnime_whenSuccesful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);

        var expectedAnime = animes.getFirst();
        var anime = repository.findById(expectedAnime.getId());
        Assertions.assertThat(anime).isPresent().contains(expectedAnime);
    }

    @Test
    @DisplayName("findByName must return an anime with given name")
    void findByName_ReturnsAnime_WhenSuccesful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);

        var expectedAnime = animes.getFirst();
        var anime = repository.findByName(expectedAnime.getName());
        Assertions.assertThat(anime).isPresent().contains(expectedAnime);
    }

    @Test
    @DisplayName("save must create an anime")
    void save_CreatesAnime_WhenSuccesful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animes
        );

        var animeToSave = new Anime(ThreadLocalRandom.current().nextLong(), "Pokemon");
        var anime = repository.save(animeToSave);

        Assertions.assertThat(anime).isEqualTo(animeToSave).hasNoNullFieldsOrProperties();

        var animeSavedOptional = repository.findById(animeToSave.getId());
        Assertions.assertThat(animeSavedOptional).isPresent().contains(animeToSave);
    }

    @Test
    @DisplayName("delete removes an anime")
    void delete_RemovesAnime_WhenSuccesful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);

        var animeToDelete = animes.getFirst();
        repository.deleteById(animeToDelete.getId());

        Assertions.assertThat(this.animes).isNotEmpty().doesNotContain(animeToDelete);
    }

    @Test
    @DisplayName("update must update an anime")
    void update_UpdatesProducer_WhenSuccesful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);

        var animeToUpdate = this.animes.getFirst();
        animeToUpdate.setName("Hellsing");
        repository.update(animeToUpdate);

        Assertions.assertThat(this.animes).contains(animeToUpdate);

        var animeUpdatedOptional = repository.findById(animeToUpdate.getId());

        Assertions.assertThat(animeUpdatedOptional).isPresent();
        Assertions.assertThat(animeUpdatedOptional.get().getName()).isEqualTo("Hellsing");
    }
}
