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
    public String getById(@PathVariable Integer id) {
        System.out.println("ID: " + id);
        return null;
    }

    @GetMapping("/getAll")
    public List<Game> getAll() {
        return null;
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
    public List<Game> create(@RequestBody List<Game> newGames) {
        System.out.println("RECEIVED: " + newGames);
        return null;
    }

    @PatchMapping("/update/{id}")
    public Game update(@PathVariable Integer id,
                       @PathParam("name") String name,
                       @PathParam("genre") String genre,
                       @PathParam("yearOfRelease") Integer yearOfRelease) {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Genre: " + genre);
        System.out.println("Year of Release: " + yearOfRelease);
        return null;
    }

    @DeleteMapping("/remove/{id}")
    public Game remove(@PathVariable Integer id) {
        return null;
    }

}
