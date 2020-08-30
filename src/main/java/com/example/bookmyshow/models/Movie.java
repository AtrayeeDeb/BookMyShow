package com.example.bookmyshow.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.*;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String trailerUrl;
    private double stars;
    private String releaseDate;
    private String plot;
    private String poster;
    private int voteCount;

    @OneToMany(mappedBy="movie", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MovieShow> movieShows = new ArrayList<>();

    @OneToMany(mappedBy="movie", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();

    public Movie() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public List<MovieShow> getMovieShows() {
        return movieShows;
    }

    public void setMovieShows(List<MovieShow> movieShows) {
        this.movieShows = movieShows;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void set(Movie newMovie) {
        if(newMovie.getPlot()!=null){
            setPlot(newMovie.getPlot());
        }
        if(newMovie.getPoster()!=null){
            setPoster(newMovie.getPoster());
        }
        if(newMovie.getReleaseDate()!=null){
            setReleaseDate(newMovie.getReleaseDate());
        }
        if(newMovie.getTitle()!=null){
            setTitle(newMovie.getTitle());
        }
        if(newMovie.getTrailerUrl()!=null){
            setTrailerUrl(newMovie.getTrailerUrl());
        }
        if(newMovie.getStars()!=this.getStars()){
            setStars(newMovie.getStars());
        }
        if(newMovie.getVoteCount()!=this.getVoteCount()){
            setVoteCount(newMovie.getVoteCount());
        }

    }
}
