package com.qa.spring.games.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.qa.spring.games.domain.Game;
import com.qa.spring.games.repos.GameRepo;
import com.qa.spring.games.services.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // boots the context (loads all the beans)
public class GameServiceDBUnitTest {

    @Autowired
    private GameService service;

    @MockBean // instructs Spring to create a mock version of this class.
    private GameRepo repo;

    @Test
    void testUpdate() {
        // GIVEN
        int id = 1;
        Game existing = new Game(id, "Super Mario Bros", "Platformer", 1985);
        String name = "Super Mario 64";
        String genre = "Adventure";
        int yearOfRelease = 1996;
        Game updated = new Game(id, name, genre, yearOfRelease);

        // WHEN
        Mockito.when(this.repo.findById(id)).thenReturn(Optional.of(existing));
        Mockito.when(this.repo.save(updated)).thenReturn(updated);

        // THEN
        Assertions.assertEquals(updated, this.service.update(id, name, genre, yearOfRelease));

        // Verifying that the mock was called the expected number of times
        Mockito.verify(this.repo, Mockito.times(2)).findById(id); // used twice
        Mockito.verify(this.repo, Mockito.times(1)).save(updated);
    }

    @Test
    void testCreate() {
        // GIVEN
        Game toCreate = new Game("Fortnite: Save the World", "Shooter", 2018);

        Mockito.when(this.repo.save(toCreate)).thenReturn(toCreate);

        Assertions.assertEquals(toCreate, this.service.create(toCreate));

        Mockito.verify(this.repo, Mockito.times(1)).save(toCreate);

    }

    @Test
    void testCreateMultiple() {
        Game toCreate1 = new Game("Kingdom Hearts", "Action", 2002);
        Game toCreate2 = new Game("Final Fantasy VII", "RPG", 1997);
        List<Game> games = Arrays.asList(toCreate1, toCreate2);
        Mockito.when(this.repo.saveAll(games)).thenReturn(games);
        Assertions.assertEquals(games, this.service.create(games));
    }

    @Test
    void testGet() {
        Game game = new Game(1, "Super Mario Bros", "Platformer", 1985);
        Mockito.when(this.repo.findById(1)).thenReturn(Optional.of(game));
        Assertions.assertEquals(game, this.service.get(1));
    }

    @Test
    void testGetAll() {
        Game game1 = new Game(1, "Super Mario Bros", "Platformer", 1985);
        Game game2 = new Game(2, "DOOM", "Shooter", 1993);
        List<Game> games = Arrays.asList(game1, game2);
        Mockito.when(this.repo.findAll()).thenReturn(games);
        Assertions.assertEquals(games, this.service.getAll());
    }

    @Test
    void testDelete() {
        Game game = new Game(1, "Super Mario Bros", "Platformer", 1985);
        Mockito.when(this.repo.findById(1)).thenReturn(Optional.of(game));
        Assertions.assertEquals(game, this.service.remove(1));
    }


}
