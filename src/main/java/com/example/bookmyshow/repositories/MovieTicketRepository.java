package com.example.bookmyshow.repositories;

import com.example.bookmyshow.models.MovieTicket;
import org.springframework.data.repository.CrudRepository;

public interface MovieTicketRepository extends CrudRepository<MovieTicket,Integer> {
}
