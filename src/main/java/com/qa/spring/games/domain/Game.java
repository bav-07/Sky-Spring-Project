package com.qa.spring.games.domain;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = false, nullable = false)
    @NotBlank
    @Size(min = 1, max = 200)
    private String name;

    private String genre;

    @NotNull
    @Range(min = 1900, max = 2100) //Uses validation library
    private Integer yearOfRelease;

    public Game(String name, String genre, Integer yearOfRelease) {
        this.name = name;
        this.genre = genre;
        this.yearOfRelease = yearOfRelease;
    }

    // REQUIRED TO CREATE A DEFAULT CONSTRUCTOR
    public Game() {
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
}
