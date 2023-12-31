package com.qa.spring.games.domain;

import com.qa.spring.games.annotations.Genre;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = false, nullable = false)
    @NotBlank
    @Size(min = 1, max = 200)
    private String name;

    @Genre
    private String genre;

    @NotNull
    @Range(min = 1900, max = 2100, message = "Year of release must be between 1900 and 2100") //Uses validation library
    private Integer yearOfRelease;

    public Game(String name, String genre, Integer yearOfRelease) {
        this.name = name;
        this.genre = genre;
        this.yearOfRelease = yearOfRelease;
    }

    // REQUIRED TO CREATE A DEFAULT CONSTRUCTOR
    public Game() {
    }

    public Game(Integer id, String name, String genre, Integer yearOfRelease) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.yearOfRelease = yearOfRelease;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(Integer yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    @Override
    public String toString() {
        return "Game{" +
                "name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(id, game.id) && Objects.equals(name, game.name) && Objects.equals(genre, game.genre) && Objects.equals(yearOfRelease, game.yearOfRelease);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, genre, yearOfRelease);
    }
}
