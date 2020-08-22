package com.example.bookmyshow.controllers;

import com.example.bookmyshow.models.Movie;
import com.example.bookmyshow.models.MovieShow;
import com.example.bookmyshow.models.Theatre;
import com.example.bookmyshow.repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TheatreController {
    //CRUD
    //get all movie shows in a theatre
    //get all movies in a theatre
    @Autowired
    TheatreRepository theatreRepository;

    @GetMapping("/api/theatre/{theatreId}")
    public Optional<Theatre> getMovie(@PathVariable("theatreId") int theatreId) {
        return theatreRepository.findById(theatreId);
    }

    @PostMapping("/api/theatre")
    public Theatre createTheatre(@RequestBody Theatre theatre) {
        return theatreRepository.save(theatre);
    }

    @PutMapping("/api/theatre/{theatreId}")
    public Theatre updateTheatre(@PathVariable("theatreId") int theatreId, @RequestBody Theatre newTheatre) {
        Optional<Theatre> theatre = theatreRepository.findById(theatreId);
        try {
            theatre.get().set(newTheatre);
            return theatreRepository.save(theatre.get());
        } catch (Exception e) {
            System.out.println("[updateTheatre]:" + e);
        }
        return null;
    }

    @DeleteMapping("/api/theatre/{theatreId}")
    public void deleteTheatre(@PathVariable("theatreId") int theatreId) {
        theatreRepository.deleteById(theatreId);
    }
    @GetMapping("/api/theatre/{theatreId}/getMovieShows")
    public List<MovieShow> getMovieShowsByTheatreId(@PathVariable("theatreId") int theatreId){
        Optional<Theatre> theatre = theatreRepository.findById(theatreId);
        try{
            return theatre.get().getMovieShows();
        }
        catch(Exception e){
            System.out.println("[getMovieShowsByTheatreId]:" + e);
            return null;
        }
    }
    @GetMapping("/api/theatre/{theatreId}/getMovies")
    public List<Movie> getMoviesByTheatreId(@PathVariable("theatreId") int theatreId){
        Optional<Theatre> theatre = theatreRepository.findById(theatreId);
        try{
            List<MovieShow> movieShows = theatre.get().getMovieShows();
            List<Movie> movies = new ArrayList<>();
            for(MovieShow m: movieShows){
                movies.add(m.getMovie());
            }
            return movies;
        }
        catch(Exception e){
            System.out.println("[getMoviesByTheatreId]:" + e);
            return null;
        }
    }
}
