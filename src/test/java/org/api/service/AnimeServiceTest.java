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
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;


// This annotation integrates Mockito with JUnit 5, enabling mock creation and injection.
@ExtendWith(MockitoExtension.class)
class AnimeServiceTest {

    // @InjectMocks creates an instance of the class you want to test (AnimeService)
    // and automatically injects any fields annotated with @Mock into it.
    @InjectMocks
    private AnimeService service;


    // @Mock creates a "dummy" or "fake" version of a dependency
    // without needing a real repository implementation.
    @Mock
    private AnimeHardCodeRepository repository;

    private List<Anime> animeList;

     // @BeforeEach is a JUnit 5 annotation that runs this 'init' method before
     // every single @Test in this class. This ensures that each test starts with
     // a fresh, consistent set of data.
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
    @DisplayName("findAll returns a list of animes when successful")
    void findAll_ReturnsListOfAnimes_WhenSuccessful() {
        // --- Arrange (Given) ---
        // Here, we define the behavior of our mock. We're saying: "When the findAll()
        // method is called on the repository, then return our predefined animeList."
        BDDMockito.when(repository.findAll()).thenReturn(animeList);

        // --- Act (When) ---
        // We execute the actual method we want to test.
        var animesFound = service.findAll();

        // --- Assert (Then) ---
        // We verify the outcome. AssertJ provides a fluent API for clear assertions.
        Assertions.assertThat(animesFound)
                .isNotNull() // The list should not be null
                .isNotEmpty() // The list should not be empty
                .hasSameElementsAs(animeList); // The list should contain the exact same elements as our source list
    }

    @Test
    @DisplayName("findAll returns an empty list when no animes exist")
    void findAll_ReturnsEmptyList_WhenNoAnimesExist() {
        // --- Arrange (Given) ---
        // We tell our mock to return an empty list for this specific test case.
        BDDMockito.when(repository.findAll()).thenReturn(Collections.emptyList());

        // --- Act (When) ---
        var animesFound = service.findAll();

        // --- Assert (Then) ---
        Assertions.assertThat(animesFound)
                .isNotNull() // It should still return a list, not null
                .isEmpty();  // We expect the list to be empty
    }


    @Test
    @DisplayName("findById returns an anime when successful")
    void findById_ReturnsAnime_WhenSuccessful() {
        // --- Arrange (Given) ---
        var expectedAnime = animeList.getFirst();
        // The repository returns an Optional, so we wrap our expected object in Optional.of().
        BDDMockito.when(repository.findById(expectedAnime.getId()))
                .thenReturn(Optional.of(expectedAnime));

        // --- Act (When) ---
        var animeFound = service.findById(expectedAnime.getId());

        // --- Assert (Then) ---
        Assertions.assertThat(animeFound)
                .isNotNull()
                .isEqualTo(expectedAnime); // The found anime should be identical to the expected one
    }

    @Test
    @DisplayName("findById throws exception when anime is not found")
    void findById_ThrowsException_WhenNotFound() {
        // --- Arrange (Given) ---
        // We configure the mock to return an empty Optional, simulating a "not found" scenario.
        // BDDMockito.anyLong() is a matcher that means "any long value will trigger this mock."
        BDDMockito.when(repository.findById(BDDMockito.anyLong()))
                .thenReturn(Optional.empty());

        // --- Act & Assert (When & Then) ---
        // We use AssertJ's exception testing features to verify that calling the service method
        // with a non-existent ID correctly throws a ResponseStatusException.
        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> service.findById(99L));
    }

    @Test
    @DisplayName("findByName returns an anime when successful")
    void findByName_ReturnsAnime_WhenSuccessful() {
        // --- Arrange (Given) ---
        var expectedAnime = animeList.getFirst();
        // We configure the mock to return our expected anime when its name is used.
        BDDMockito.when(repository.findByName(expectedAnime.getName()))
                .thenReturn(Optional.of(expectedAnime));

        // --- Act (When) ---
        var animeFound = service.findByName(expectedAnime.getName());

        // --- Assert (Then) ---
        Assertions.assertThat(animeFound)
                .isNotNull()
                .isEqualTo(expectedAnime);
    }

    @Test
    @DisplayName("findByName throws exception when anime is not found")
    void findByName_ThrowsException_WhenNotFound() {
        // --- Arrange (Given) ---
        // We tell the mock that for any string passed to findByName, it should return an empty Optional.
        BDDMockito.when(repository.findByName(BDDMockito.anyString()))
                .thenReturn(Optional.empty());

        // --- Act & Assert (When & Then) ---
        // We verify that the service correctly throws an exception.
        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> service.findByName("NonExistentName"));
    }

    @Test
    @DisplayName("save creates and returns an anime when successful")
    void save_CreatesAnime_WhenSuccessful() {
        // --- Arrange (Given) ---
        var animeToSave = new Anime(ThreadLocalRandom.current().nextLong(), "Aniplex");
        // We mock the repository's save method to return the same object it receives.
        BDDMockito.when(repository.save(animeToSave))
                .thenReturn(animeToSave);

        // --- Act (When) ---
        var animeSaved = service.save(animeToSave);

        // --- Assert (Then) ---
        Assertions.assertThat(animeSaved)
                .isNotNull()
                .isEqualTo(animeToSave); // The saved object should be the same as the one we passed in
    }

    @Test
    @DisplayName("deleteById removes an anime when successful")
    void deleteById_RemovesAnime_WhenSuccessful() {
        // --- Arrange (Given) ---
        var animeToDelete = animeList.getFirst();
        // The service's deleteById first calls findById to ensure the anime exists.
        // We must mock this initial call.
        BDDMockito.when(repository.findById(animeToDelete.getId()))
                .thenReturn(Optional.of(animeToDelete));
        // For methods that return 'void', we use willDoNothing().given(...) to mock them.
        BDDMockito.willDoNothing().given(repository).deleteById(animeToDelete.getId());

        // --- Act & Assert (When & Then) ---
        // We assert that calling the delete method does not throw any exceptions,
        // which implies the operation was successful.
        Assertions.assertThatCode(() -> service.deleteById(animeToDelete.getId()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("update saves an anime when successful")
    void update_SavesAnime_WhenSuccessful() {
        // --- Arrange (Given) ---
        var animeToUpdate = animeList.getFirst();
        // Similar to delete, the update method also checks if the anime exists first.
        BDDMockito.when(repository.findById(animeToUpdate.getId()))
                .thenReturn(Optional.of(animeToUpdate));

        // The repository's update method is void, so we don't need to mock a return value.

        // --- Act & Assert (When & Then) ---
        // We verify that the service's update method completes without throwing errors.
        Assertions.assertThatCode(() -> service.update(animeToUpdate))
                .doesNotThrowAnyException();
    }
}