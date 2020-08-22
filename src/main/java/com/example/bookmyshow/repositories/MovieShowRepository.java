package com.example.bookmyshow.repositories;

import com.example.bookmyshow.models.Movie;
import com.example.bookmyshow.models.MovieShow;
import com.example.bookmyshow.models.Theatre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieShowRepository extends CrudRepository<MovieShow,Integer> {
    //get movie shows by theatre name
    //get movie shows by movie name and theatre name
    //get movie shows by movie name and date
    //get movie shows by movie name, date and time
    Iterable<MovieShow> findByTheatreId(int theatreId);
    Iterable<MovieShow> findByMovieAndTheatre(Movie movie, Theatre theatre);
    Iterable<MovieShow> findByMovieIdAndDate(int movieId, String date);
    Iterable<MovieShow> findByMovieIdAndDateAndTime(int movieId, String date, String time);
    Iterable<MovieShow> findByMovieIdAndTheatreIdAndDateAndTime(int movieId, int theatreId, String date, String time);
    Iterable<MovieShow> findByMovieIdAndTheatreIdAndDate(int movieId, int theatreId, String date);
}
