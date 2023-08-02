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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.print.attribute.standard.Media;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // loads the application context
@AutoConfigureMockMvc // create a MockMVC bean
public class GameMvcTest {

    @Autowired // tells spring to inject the MockMVC bean into this class
    private MockMvc mvc;

    @Autowired
    static
    GameService gameService;

    private static AtomicInteger createId = new AtomicInteger(1);

    // Spring uses ObjectMapper to convert Java to JSON and JSON to Java
    @Autowired // When you autowire it you call the exact same object mapper that Spring uses to convert from Java to JSON
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
    }

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
        Game responseBody = new Game(createId.getAndIncrement(), "The Legend of Zelda: Ocarina of Time", "Adventure", 1998);
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
                .andExpect(MockMvcResultMatchers.content().json(this.mapper.writeValueAsString(new Game(createId.getAndIncrement(), "Portal 2", "Puzzle", 2011))));

    }


}
