package com.qa.spring.games.services;

import com.qa.spring.games.domain.Game;
import com.qa.spring.games.repos.GameRepo;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
public class GameServiceDB implements GameService{

    public GameRepo repo;

    public GameServiceDB(GameRepo repo) {
        this.repo = repo;
    }

    @Override
    public Game create(Game game) {
        return this.repo.save(game);
    }

    @Override
    public List<Game> create(List<Game> newGames) {
        return this.repo.saveAll(newGames);
    }

    @Override
    public Game get(int id) {
        return this.repo.findById(id).get();
    }

    @Override
    public List<Game> getAll() {
        return this.repo.findAll();
    }

    @Override
    public Game update(int id, String name, String genre, Integer yearOfRelease) {
        Game toUpdate = this.get(id);
        if (name != null) {
            toUpdate.setName(name);
        }
        if (genre != null) {
            toUpdate.setGenre(genre);
        }
        if (yearOfRelease != null) {
            toUpdate.setYearOfRelease(yearOfRelease);
        }
        return this.repo.save(toUpdate);
    }

    @Override
    public Game remove(int id) {
        Game game = this.repo.findById(id).get();
        this.repo.deleteById(id);
        return game;
    }

    @Override
    public List<Game> findByName(String name) {
        return this.repo.findByNameContainingIgnoreCase(name);
    }

    @Override
    public String findGenreByName(String name) {
        return this.repo.findGenreByName(name);
    }

    @Override
    public List<Game> findByGenre(String genre) {
        return this.repo.findByGenreContainingIgnoreCase(genre);
    }

    @Override
    public List<Game> getAllSorted(String parameter) {
        return this.repo.findAll(Sort.by(parameter));
    }
}
