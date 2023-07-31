package com.qa.spring.games.rest;

import com.qa.spring.games.domain.Game;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    private List<Game> games = new ArrayList<>();

//    @RequestMapping(method = HttpMethod.GET, value="/hello")
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/get/{id}")
    public HttpEntity<Game> getById(@PathVariable Integer id) {
        System.out.println("ID: " + id);
        Game game = this.games.get(id);
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public HttpEntity<List<Game>> getAll() {
        List<Game> gamesList = this.games;
        return new ResponseEntity<>(gamesList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public HttpEntity<Game> create(@RequestBody Game game) {
        System.out.println("RECEIVED: " + game);
        this.games.add(game);
        Game created = this.games.get(this.games.size() - 1);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Behind the scenes, using Jackson library to convert JSON into whatever parameter type you would like
    // This works as long as the parameters you pass in match
    @PostMapping("/createMultiple")
    public HttpEntity<List<Game>> create(@RequestBody List<Game> newGames) {
        System.out.println("RECEIVED: " + newGames);
        this.games.addAll(newGames);
        List<Game> createdAll = this.games.subList(this.games.size() - newGames.size(), this.games.size());
        return new ResponseEntity<>(createdAll, HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    public HttpEntity<Game> update(@PathVariable Integer id,
                       @PathParam("name") String name,
                       @PathParam("genre") String genre,
                       @PathParam("yearOfRelease") Integer yearOfRelease) {
        Game game = this.games.get(id);
        game.setName(name);
        game.setGenre(genre);
        game.setYearOfRelease(yearOfRelease);
        return new ResponseEntity<>(this.games.get(id), HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    public HttpEntity<Game> remove(@PathVariable Integer id) {
        Game toDelete = this.games.get(id);
        this.games.remove(toDelete);
        return new ResponseEntity<>(toDelete, HttpStatus.OK);
    }

}
