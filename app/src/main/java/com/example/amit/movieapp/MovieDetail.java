package com.example.amit.movieapp;

public class MovieDetail {
    public final String overview;
    public final String backdrop_path;
    public final String homepage;
    public final String original_title;
    public final String poster_path;
    public final String release_date;
    public final String tagline;

    public MovieDetail(String overview, String backdrop_path, String homepage, String original_title, String poster_path, String release_date, String tagline) {
        this.overview = overview;
        this.backdrop_path = backdrop_path;
        this.homepage = homepage;
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.tagline = tagline;
    }
}
