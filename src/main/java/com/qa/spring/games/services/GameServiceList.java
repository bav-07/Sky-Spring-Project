package com.qa.spring.games.services;

import com.qa.spring.games.domain.Game;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceList implements GameService {

    private List<Game> games = new ArrayList<>();

    @Override
    public Game create(Game game) {
        this.games.add(game);
        return this.games.get(this.games.size() - 1);
    }

    @Override
    public List<Game> create(List<Game> newGames) {
        this.games.addAll(newGames);
        return this.games.subList(this.games.size() - newGames.size(), this.games.size());
    }

    @Override
    public Game get(int id) {
        return this.games.get(id);
    }

    @Override
    public List<Game> getAll() {
        return this.games;
    }

    @Override
    public Game update(int id, String name, String genre, Integer yearOfRelease) {
        if (this.games.get(id) != null) {
            Game game = this.games.get(id);
            if (name != null) {
                game.setName(name);
            }
            if (genre != null) {
                game.setGenre(genre);
            }
            if (yearOfRelease != null) {
                game.setYearOfRelease(yearOfRelease);
            }
            return this.games.get(id);
        }
        return null;
    }

    @Override
    public Game remove(int id) {
        if (this.games.get(id) != null) {
            return this.games.remove(id);
        }
        return null;
    }

}
