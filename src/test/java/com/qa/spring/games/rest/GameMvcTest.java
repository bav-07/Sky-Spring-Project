package com.qa.spring.games.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.spring.games.domain.Game;
import com.qa.spring.games.repos.GameRepo;
import com.qa.spring.games.services.GameService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.querydsl.binding.MultiValueBinding;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.print.attribute.standard.Media;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // loads the application context
@AutoConfigureMockMvc // create a MockMVC bean
@Sql(scripts = {"classpath:game-schema.sql", "classpath:game-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class GameMvcTest {

    @Autowired // tells spring to inject the MockMVC bean into this class
    private MockMvc mvc;

    //private static AtomicInteger createId = new AtomicInteger(1);

    // Spring uses ObjectMapper to convert Java to JSON and JSON to Java
    @Autowired // When you autowire it you call the exact same object mapper that Spring uses to convert from Java to JSON
    private ObjectMapper mapper;

    @Test
    void testCreate() throws Exception {
        // METHOD: POST
        // PATH: /game/create
        // BODY: JSON
        // HEADERS: CONTENT-TYPE: application/json
        Game ocarinaOfTime = new Game("The Legend of Zelda: Ocarina of Time", "Adventure", 1998);
        System.out.println("DATA: " + ocarinaOfTime);
        String ocarinaOfTimeJSON = this.mapper.writeValueAsString(ocarinaOfTime);
        System.out.println("JSON: " + ocarinaOfTimeJSON);
        RequestBuilder req = MockMvcRequestBuilders.post("/game/create")
                .content(ocarinaOfTimeJSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultMatcher checkStatus = MockMvcResultMatchers.status().isCreated();
        Game responseBody = new Game(3, "The Legend of Zelda: Ocarina of Time", "Adventure", 1998);
        String responseBodyJSON = this.mapper.writeValueAsString(responseBody);
        ResultMatcher checkBody = MockMvcResultMatchers.content().json(responseBodyJSON);

        this.mvc.perform(req).andExpect(checkStatus).andExpect(checkBody);

    }

    // This is if you were crazy and put it all in "one" line of code
    @Test
    void testCreate2() throws Exception {
        this.mvc.perform(
                MockMvcRequestBuilders
                        .post("/game/create")
                        .content(this.mapper.writeValueAsString(new Game("Portal 2", "Puzzle", 2011)))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(this.mapper.writeValueAsString(new Game(3, "Portal 2", "Puzzle", 2011))));
    }

    @Test
    void testCreateMultiple() throws Exception {

        List<Game> games = new ArrayList<>();
        games.add(new Game("The Legend of Zelda: Ocarina of Time", "Adventure", 1998));
        games.add(new Game("Portal 2", "Puzzle", 2011));
        games.add(new Game("Bloodborne","Action", 2015));

        List<Game> responseBody = new ArrayList<>();
        responseBody.add(new Game(3, "The Legend of Zelda: Ocarina of Time", "Adventure", 1998));
        responseBody.add(new Game(4, "Portal 2", "Puzzle", 2011));
        responseBody.add(new Game(5, "Bloodborne","Action", 2015));

        this.mvc.perform(
                MockMvcRequestBuilders
                        .post("/game/createMultiple")
                        .content(this.mapper.writeValueAsString(games))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(this.mapper.writeValueAsString(responseBody)));
    }

    @Test
    void testRead() throws Exception {
        final int id = 1;
        this.mvc.perform(MockMvcRequestBuilders.get("/game/get/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(this.mapper.writeValueAsString(new Game(id, "Super Mario Bros", "Platformer", 1985))));
    }

    @Test
    void testRead2() throws Exception {
        final int id = 2;
        this.mvc.perform(MockMvcRequestBuilders.get("/game/get/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(this.mapper.writeValueAsString(new Game(id, "DOOM", "Shooter", 1993))));
    }

    @Test
    void testReadAll() throws Exception {

        List<Game> games = new ArrayList<>();
        games.add(new Game(1, "Super Mario Bros", "Platformer", 1985));
        games.add(new Game(2, "DOOM", "Shooter", 1993));

        this.mvc.perform(MockMvcRequestBuilders.get("/game/getAll"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(this.mapper.writeValueAsString(games)));
    }

    @Test
    void testReadName() throws Exception {

        List<Game> games = new ArrayList();
        games.add(new Game(1, "Super Mario Bros", "Platformer", 1985));

        this.mvc.perform(MockMvcRequestBuilders.get("/game/findByName/Super Mario Bros"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(this.mapper.writeValueAsString(games)));
    }

    @Test
    void testUpdate() throws Exception {
        Game updated = new Game(1, "Super Mario Bros", "Adventure", 1985);
        this.mvc.perform(MockMvcRequestBuilders.patch("/game/update/1").queryParam("genre","Adventure"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(this.mapper.writeValueAsString(updated)));
    }

    @Test
    void testUpdateMultiple1() throws Exception {
        Game updated = new Game(1, "Super Mario 64", "Adventure", 1996);
        this.mvc.perform(MockMvcRequestBuilders.patch("/game/update/1")
                        .queryParam("genre","Adventure")
                        .queryParam("name","Super Mario 64")
                        .queryParam("yearOfRelease","1996")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(this.mapper.writeValueAsString(updated)));
    }

    @Test
    void testUpdateMultiple2() throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("name", Collections.singletonList("Super Mario 64"));
        params.put("genre", Collections.singletonList("Adventure"));
        params.put("yearOfRelease", Collections.singletonList("1996"));
        Game updated = new Game(1, "Super Mario 64", "Adventure", 1996);
        this.mvc.perform(MockMvcRequestBuilders.patch("/game/update/1")
                        .queryParams(params))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(this.mapper.writeValueAsString(updated)));
    }

    @Test
    void testDelete() throws Exception {

        this.mvc.perform(MockMvcRequestBuilders.delete("/game/remove/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(this.mapper.writeValueAsString(new Game(1, "Super Mario Bros", "Platformer", 1985))));
    }

    @Test
    void testNotFound() throws Exception {
        final int id = 99999;
        this.mvc.perform(MockMvcRequestBuilders.get("/game/get/" + id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testFailCreate() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                                .post("/game/create")
                                .content(this.mapper.writeValueAsString(new Game("Portal 2", "Puzzle", 2222)))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void testFailUpdate() throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("name", Collections.singletonList("Super Mario 64"));
        params.put("genre", Collections.singletonList("Mario"));
        params.put("yearOfRelease", Collections.singletonList("1996"));
        this.mvc.perform(MockMvcRequestBuilders.patch("/game/update/1")
                        .queryParams(params))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
