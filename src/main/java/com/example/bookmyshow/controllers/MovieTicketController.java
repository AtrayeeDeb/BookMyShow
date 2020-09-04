package com.example.bookmyshow.controllers;

import com.example.bookmyshow.models.*;
import com.example.bookmyshow.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MovieTicketController {
    @Autowired
    MovieTicketRepository movieTicketRepository;
    @Autowired
    MovieShowRepository movieShowRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SeatRepository seatRepository;

    @PostMapping("/api/user/{userId}/movieShow/{movieShowId}")
    public void createMovieTicket(@PathVariable("userId") int userId, @PathVariable("movieShowId") int movieShowId, @RequestBody Seat seat){
        try{
            Optional<MovieShow> movieShow = movieShowRepository.findById(movieShowId);
            Optional<User> user = userRepository.findById(userId);
            synchronized (seat.seatMutex){
                if(seat.isAvailable()) {
                    try {
                        //payment
                        MovieTicket movieTicket = new MovieTicket(movieShow.get().getMovieName(), movieShow.get().getScreenNumber(), movieShow.get().getTheatreName(), movieShow.get().getTheatreAddress(), movieShow.get().getDate(), movieShow.get().getTime(), seat.getPrice(), seat.getR(), seat.getC());
                        movieShow.get().getMovieTickets().add(movieTicket);
                        user.get().getMovieTickets().add(movieTicket);
                        movieTicket.setMovieShow(movieShow.get());
                        movieTicket.setUser(user.get());
                        movieTicket.setSeat(seat);
                        seat.setAvailable(false);
                        movieShow.get().getSeats().add(seat);
                        seat.setMovieShow(movieShow.get());
                        seat.setMovieTicket(movieTicket);
                        seatRepository.save(seat);
                        int filled = movieShow.get().getFilled();
                        movieShow.get().setFilled(filled+1);
                        movieTicketRepository.save(movieTicket);
                    }
                    catch (Exception e) {
                        System.out.println("[createMovieTicket]:Error occured in booking tickets");
                    }
                }
                else{
                    System.out.println("[createMovieTicket]:Seat not available");
                }
            }
        }
        catch(Exception e){
            System.out.println("[createMovieTicket]:error occured->"+e);
            //return null;
        }

    }

    @DeleteMapping("/api/movieTicket/{movieTicketId}")
    public void deleteMovieTicket(@PathVariable("movieTicketId") int movieTicketId){
        try{
            Optional<MovieTicket> movieTicket = movieTicketRepository.findById(movieTicketId);
            User user = movieTicket.get().getUser();
            MovieShow movieShow = movieTicket.get().getMovieShow();
            Seat seat = movieTicket.get().getSeat();

            synchronized (seat.seatMutex){
                if(!seat.isAvailable()) {
                    try {
                        // reverse payment
                        List<Seat> seats = movieShow.getSeats();
                        List<MovieTicket> movieTickets = movieShow.getMovieTickets();
                        movieTickets.remove(movieTicket.get());
                        seats.remove(seat);
                        int filled = movieShow.getFilled();
                        movieShow.setFilled(filled-1);
                        movieTickets = user.getMovieTickets();
                        movieTickets.remove(movieTicket.get());
                        seatRepository.deleteById(seat.getId());
                        movieTicketRepository.deleteById(movieTicketId);
                        seat.setAvailable(true);
                    }
                    catch (Exception e) {
                        System.out.println("[deleteMovieTicket]:Error occured in cancelling ticket");
                    }
                }
                else{
                    System.out.println("[deleteMovieTicket]:Seat not available");
                }
            }

        }
        catch(Exception e){
            System.out.println("[deleteMovieTicket]:error occured->"+e);
        }
    }

    @PutMapping("/api/movieTicket/{movieTicketId}")
    public void rescheduleMovieTicket(@PathVariable("movieTicketId") int movieTicketId, @RequestBody RescheduleMovieTicketRequest rescheduleRequest ){
        Optional<MovieTicket> movieTicket = movieTicketRepository.findById(movieTicketId);
        User user = movieTicket.get().getUser();
        MovieShow movieShow = movieTicket.get().getMovieShow();
        Seat seat = movieTicket.get().getSeat();
        MovieShow newMovieShow = movieShowRepository.findById(rescheduleRequest.movieShowId).get();
        Seat newSeat = rescheduleRequest.seat;
        synchronized (newSeat.seatMutex) {
            synchronized (seat.seatMutex) {
                if (!seat.isAvailable()) {
                    List<Seat> seats = movieShow.getSeats();
                    List<MovieTicket> movieTickets = movieShow.getMovieTickets();
                    movieTickets.remove(movieTicket.get());
                    seats.remove(seat);
                    int filled = movieShow.getFilled();
                    movieShow.setFilled(filled-1);
                    movieTickets = user.getMovieTickets();
                    movieTickets.remove(movieTicket.get());
                    seatRepository.deleteById(seat.getId());
                    movieTicketRepository.deleteById(movieTicketId);
                    seat.setAvailable(true);
                }
            }
            if(newSeat.isAvailable()) {
                try {
                    //payment
                    MovieTicket newMovieTicket = new MovieTicket(newMovieShow.getMovieName(), newMovieShow.getScreenNumber(), newMovieShow.getTheatreName(),newMovieShow.getTheatreAddress(), newMovieShow.getDate(), newMovieShow.getTime(), newSeat.getPrice(), newSeat.getR(), newSeat.getC());
                    newMovieShow.getMovieTickets().add(newMovieTicket);
                    user.getMovieTickets().add(newMovieTicket);
                    newMovieTicket.setMovieShow(newMovieShow);
                    newMovieTicket.setUser(user);
                    newMovieTicket.setSeat(newSeat);
                    newSeat.setAvailable(false);
                    newMovieShow.getSeats().add(newSeat);
                    newSeat.setMovieShow(newMovieShow);
                    newSeat.setMovieTicket(newMovieTicket);
                    seatRepository.save(newSeat);
                    int filled = newMovieShow.getFilled();
                    newMovieShow.setFilled(filled+1);
                    movieTicketRepository.save(newMovieTicket);
                }
                catch (Exception e) {
                    System.out.println("[rescheduleMovieTicket]:Error occured in booking tickets");
                }
            }
            else{
                System.out.println("[rescheduleMovieTicket]:Seat not available");
            }
        }
    }
    @GetMapping("/api/movieTicket/{movieTicketId}")
    public MovieTicket getTicket(@PathVariable("movieTicketId") int movieTicketId){
        Optional<MovieTicket> movieTicket = movieTicketRepository.findById(movieTicketId);
        return movieTicket.get();
    }

}
