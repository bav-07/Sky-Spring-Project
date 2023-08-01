package com.qa.spring.games.rest;

import com.qa.spring.games.domain.Game;
import com.qa.spring.games.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping("/get/{id}")
    public HttpEntity<Game> getById(@PathVariable Integer id) {
        System.out.println("ID: " + id);
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public HttpEntity<List<Game>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public HttpEntity<Game> create(@RequestBody Game game) {
        System.out.println("RECEIVED: " + game);
        return new ResponseEntity<>(service.create(game), HttpStatus.CREATED);
    }

    // Behind the scenes, using Jackson library to convert JSON into whatever parameter type you would like
    // This works as long as the parameters you pass in match
    @PostMapping("/createMultiple")
    public HttpEntity<List<Game>> create(@RequestBody List<Game> newGames) {
        System.out.println("RECEIVED: " + newGames);
        return new ResponseEntity<>(service.create(newGames), HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    public HttpEntity<Game> update(@PathVariable Integer id,
                       @RequestParam(value="name", required = false) String name,
                       @RequestParam(value="genre", required = false) String genre,
                       @RequestParam(value="yearOfRelease", required = false) Integer yearOfRelease) {
        Game updated = service.update(id, name, genre, yearOfRelease);
        if (updated != null) {
            return new ResponseEntity<>(service.update(id, name, genre, yearOfRelease), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/remove/{id}")
    public HttpEntity<Game> remove(@PathVariable Integer id) {
        return new ResponseEntity<>(service.remove(id), HttpStatus.OK);
    }

}
