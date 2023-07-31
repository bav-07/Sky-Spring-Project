package com.qa.spring.games.domain;

public class Game {

    private String name;
    private String genre;
    private int yearOfRelease;

    public Game(String name, String genre, int yearOfRelease) {
        this.name = name;
        this.genre = genre;
        this.yearOfRelease = yearOfRelease;
    }

    // REQUIRED TO CREATE A DEFAULT CONSTRUCTOR
    public Game() {
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

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
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
