package com.example.amit.movieapp;

public class Result {
    public final int id;
    public final String title;
    public final String original_title;
    public final String poster_path;
    public final String overview;
    public final String release_date;
    public final String backdrop_path;

    public Result(int id, String title, String original_title, String poster_path, String overview, String release_date, String backdrop_path) {
        this.id = id;
        this.title = title;
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.backdrop_path = backdrop_path;
    }
}

