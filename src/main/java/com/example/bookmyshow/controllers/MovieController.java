package com.example.bookmyshow.controllers;

import com.example.bookmyshow.models.Movie;
import com.example.bookmyshow.models.MovieShow;
import com.example.bookmyshow.models.Review;
import com.example.bookmyshow.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class MovieController {
    //create a movie
    //delete a movie
    //update a movie
    //find a movie by id
    //find movies by movie name/all movies
    //find movies by release date
    //find all reviews of a movie
    //find all shows of a movie
    @Autowired
    MovieRepository movieRepository;

    @GetMapping("/api/movie/{movieId}")
    public Optional<Movie> getMovie(@PathVariable("movieId") int movieId){
        return movieRepository.findById(movieId);
    }

    @PostMapping("/api/movie") //votecount not getting updated
    public Movie createMovie(@RequestBody Movie movie){
        return movieRepository.save(movie);
    }

    @PutMapping("/api/movie/{movieId}")
    public Movie updateMovie(@PathVariable("movieId") int movieId, @RequestBody Movie newMovie){
        Optional<Movie> movie = movieRepository.findById(movieId);
        try{
            movie.get().set(newMovie);
            return movieRepository.save(movie.get());
        }
        catch(Exception e){
            System.out.println("[updateMovie]:"+e);
        }
        return null;
    }
    @DeleteMapping("/api/movie/{movieId}")
    public void deleteMovie(@PathVariable("movieId") int movieId){
        movieRepository.deleteById(movieId);
    }
    @GetMapping("/api/movie")
    public Iterable<Movie> findMovies(@RequestParam(name = "movieName", required = false) String movieName){
        if(movieName!=null){
            return movieRepository.findByMovieName(movieName);
        }
        return movieRepository.findAll();
    }
    @GetMapping("/api/movie/release")
    public Iterable<Movie> findMoviesByReleaseDate(@RequestParam("releaseDate") String releaseDate){
        return movieRepository.findByReleaseDate(releaseDate);
    }
    @GetMapping("/api/movie/{movieId}/getReviews")
    public Iterable<Review> findAllReviews(@PathVariable("movieId") int movieId){
        Optional<Movie> movie = movieRepository.findById(movieId);
        try{
            return movie.get().getReviews();
        }
        catch(Exception e){
            System.out.println("[findAllReviews]:"+e);
        }
        return null;
    }
    @GetMapping("/api/movie/{movieId}/getMovieShows")
    public Iterable<MovieShow> findAllMovieShows(@PathVariable("movieId") int movieId){
        Optional<Movie> movie = movieRepository.findById(movieId);
        try{
            return movie.get().getMovieShows();
        }
        catch(Exception e){
            System.out.println("[findAllMovieShows]:"+e);
        }
        return null;
    }

}
