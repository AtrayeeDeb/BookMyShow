package com.example.bookmyshow.repositories;

import com.example.bookmyshow.models.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Integer> {

    Iterable<Movie> findByReleaseDate(String releaseDate);

    @Query("SELECT m FROM Movie AS m WHERE m.title LIKE '%movieName%'")
    Iterable<Movie> findByMovieName(String movieName);



}
