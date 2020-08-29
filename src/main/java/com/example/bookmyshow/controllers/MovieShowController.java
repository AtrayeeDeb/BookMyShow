package com.example.bookmyshow.controllers;

import com.example.bookmyshow.models.*;
import com.example.bookmyshow.repositories.MovieRepository;
import com.example.bookmyshow.repositories.MovieShowRepository;
import com.example.bookmyshow.repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@RestController
public class MovieShowController {
    @Autowired
    MovieShowRepository movieShowRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    TheatreRepository theatreRepository;

    //CRUD
    //get all theatre for a movie and a date
    //get a movie show for a movie and a theatre and a date and time
    //get all movie shows for a movie and a theatre and a date
    //CRUD
    //book a ticket for a movieshow
    @GetMapping("/api/movie/{movieId}/movieshow/{date}")
    public Set<Theatre> findAllTheatresByMovieAndDate(@PathVariable("movieId") int movieId, @PathVariable("date") String date){
        Set<Theatre> theatres = new HashSet<>();
        try{
            List<MovieShow> movieShows = (List<MovieShow>)movieShowRepository.findByMovieIdAndDate(movieId, date);
            for(MovieShow m : movieShows){
                theatres.add(m.getTheatre());
            }
            return theatres;
        }
        catch(Exception e){
            return null;
        }
    }
    @GetMapping("/api/movie/{movieId}/theatre/{theatreId}/date/{date}/time/{time}")
    public MovieShow findAllMovieShowsByMovieDateTimeAndTheatre(@PathVariable("movieId") int movieId,@PathVariable("theatreId") int theatreId,@PathVariable("date") String date, @PathVariable("time") String time){
        try{
            List<MovieShow> movieShows = (List<MovieShow>)movieShowRepository.findByMovieIdAndTheatreIdAndDateAndTime(movieId, theatreId, date, time);
            if(!movieShows.isEmpty()){
                return movieShows.get(0);
            }
            return null;
        }
        catch(Exception e){
            return null;
        }
    }
    @GetMapping("/api/movie/{movieId}/theatre/{theatreId}/date/{date}")
    public List<MovieShow> findAllMovieShowsByMovieDateAndTheatre(@PathVariable("movieId") int movieId,@PathVariable("theatreId") int theatreId,@PathVariable("date") String date){
        try{
            List<MovieShow> movieShows = (List<MovieShow>)movieShowRepository.findByMovieIdAndTheatreIdAndDate(movieId, theatreId, date);
            return movieShows;
        }
        catch(Exception e){
            return null;
        }
    }
    @PostMapping("/api/movie/{movieId}/theatre/{theatreId}/createMovieShow")
    public MovieShow createMovieShow(@PathVariable("movieId") int movieId, @PathVariable("theatreId") int theatreId, @RequestBody MovieShowRequest movieShowRequest){
        System.out.println("createMovieShow called");
        Optional<Movie> movie = movieRepository.findById(movieId);
        Optional<Theatre> theatre = theatreRepository.findById(theatreId);
        MovieShow movieShow = new MovieShow(movie.get().getTitle(), movieShowRequest.screenNumber, theatre.get().getName(), theatre.get().getLocation(), movieShowRequest.date, movieShowRequest.time, movieShowRequest.rows, movieShowRequest.cols, movie.get(), theatre.get());
        movie.get().getMovieShows().add(movieShow);
        theatre.get().getMovieShows().add(movieShow);
        movieShow.setMovie(movie.get());
        movieShow.setTheatre(theatre.get());
        movieShow = movieShowRepository.save(movieShow);
        return movieShow;
    }

    @PutMapping("/api/movieshow/{movieShowId}")
    public MovieShow updateMovieShow(@PathVariable("movieShowId") int movieShowId,@RequestBody MovieShow newMovieShow){
        Optional<MovieShow> movieShow = movieShowRepository.findById(movieShowId);
        try{

            List<MovieShow> movieShows= movieShow.get().getMovie().getMovieShows();
            for(MovieShow m: movieShows){
                if(m.equals(movieShow.get())){
                    movieShows.remove(m);
                }
            }
            movieShows.add(newMovieShow);
            List<MovieShow> theatreMovieShows= movieShow.get().getMovie().getMovieShows();
            for(MovieShow m: theatreMovieShows){
                if(m.equals(movieShow.get())){
                    theatreMovieShows.remove(m);
                }
            }
            theatreMovieShows.add(newMovieShow);
            movieShow.get().set(newMovieShow);
            return movieShowRepository.save(movieShow.get());
        }
        catch(Exception e){
            System.out.println("[updateMovieShow]:"+e);
        }
        return null;
    }
    @GetMapping("/api/movieShow/{movieShowId}")
    public MovieShow getMovieShow(@PathVariable("movieShowId") int movieShowId){
        return movieShowRepository.findById(movieShowId).get();
    }
    @DeleteMapping("/api/movieShow/{movieShowId}")
    public void deleteMovieShow(@PathVariable("movieShowId") int movieShowId){
        Optional<MovieShow> movieShow = movieShowRepository.findById(movieShowId);
        try{
            List<MovieShow> movieShows= movieShow.get().getMovie().getMovieShows();
            movieShows.remove(movieShow.get());
//            for(MovieShow m: movieShows){
//                if(m.equals(movieShow.get())){
//                    movieShows.remove(m);
//                }
//            }
            List<MovieShow> theatreMovieShows= movieShow.get().getMovie().getMovieShows();
            theatreMovieShows.remove(movieShow.get());
//            for(MovieShow m: theatreMovieShows){
//                if(m.equals(movieShow.get())){
//                    theatreMovieShows.remove(m);
//                }
//            }
            movieShowRepository.deleteById(movieShowId);
        }
        catch(Exception e){
            System.out.println("[deleteMovieShow]:"+e);
        }
    }
    @GetMapping("/api/movieShow/{movieShowId}/isAvailable")
    public boolean isAvailable(@PathVariable("movieShowId") int movieShowId){
        Optional<MovieShow> movieShow = movieShowRepository.findById(movieShowId);
        int rows = movieShow.get().getRows();
        int cols = movieShow.get().getCols();
        int filled = movieShow.get().getFilled();
        if(((rows*cols)-filled)>0){
            return true;
        }
        return false;
    }



}
