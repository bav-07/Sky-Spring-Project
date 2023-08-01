package com.qa.spring.games.repos;

import com.qa.spring.games.domain.Game;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepo extends JpaRepository<Game, Integer> {

    // Derived Query
    // In SQL this would be: SELECT * FROM GAME WHERE 'name' = ?;
    List<Game> findByNameContainingIgnoreCase(String name);

    // This is written in JPQL -> Java Persistence Query Language
    @Query(value = "SELECT genre FROM game WHERE name = ?1", nativeQuery = true)
//    @Query("SELECT g.genre FROM Game g WHERE g.name CONTAINS ?1")
    String findGenreByName(String name);

    List<Game> findByGenreContainingIgnoreCase(String genre);

    // This is not necessary because the Repo already seemingly implements a findAll method overloaded with a Sort parameter
    @Query(value = "SELECT g FROM Game g")
    List<Game> findAllGames(Sort sort);

}
