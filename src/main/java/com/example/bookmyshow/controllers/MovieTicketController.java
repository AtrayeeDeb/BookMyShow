package com.example.bookmyshow.controllers;

import com.example.bookmyshow.models.*;
import com.example.bookmyshow.repositories.MovieRepository;
import com.example.bookmyshow.repositories.MovieShowRepository;
import com.example.bookmyshow.repositories.MovieTicketRepository;
import com.example.bookmyshow.repositories.UserRepository;
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

    @PostMapping("/api/user/{userId}/movieShow/{movieShowId}")
    public MovieTicket createMovieTicket(@PathVariable("userId") int userId, @PathVariable("movieShowId") int movieShowId, @RequestBody Seat seat){
        Optional<MovieShow> movieShow = movieShowRepository.findById(movieShowId);
        //seat = movieShow.get().seats[seat.getRow()][seat.getCol()];
        Optional<User> user = userRepository.findById(userId);
        synchronized (seat.seatMutex){
            if(seat.isAvailable()) {
                try {
                    //payment
                    MovieTicket movieTicket = new MovieTicket(movieShow.get().getMovieName(), movieShow.get().getScreenNumber(), movieShow.get().getTheatreName(), movieShow.get().getTheatreAdress(), movieShow.get().getDate(), movieShow.get().getTime(), seat.getPrice(), seat.getRow(), seat.getCol());
                    movieTicketRepository.save(movieTicket);
                    movieShow.get().getMovieTickets().add(movieTicket);
                    user.get().getMovieTickets().add(movieTicket);
                    movieTicket.setMovieShow(movieShow.get());
                    movieTicket.setUser(user.get());
                    seat.setAvailable(false);
                    int filled = movieShow.get().getFilled();
                    movieShow.get().setFilled(filled+1);
                    return movieTicket;
                }
                catch (Exception e) {
                    System.out.println("[createMovieTicket]:Error occured in booking tickets");
                    return null;
                }
            }
            else{
                System.out.println("[createMovieTicket]:Seat not available");
                return null;
            }
        }
    }

//    @DeleteMapping("/api/movieTicket/{movieTicketId}/")
//    public void deleteMovieTicket(@PathVariable("movieTicketId") int movieTicketId){
//        Optional<MovieTicket> movieTicket = movieTicketRepository.findById(movieTicketId);
//        User user = movieTicket.get().getUser();
//        MovieShow movieShow = movieTicket.get().getMovieShow();
//        Seat seat = movieShow.getSeatsBooked()[movieTicket.get().getR()][movieTicket.get().getC()];
//
//        synchronized (seat.seatMutex){
//            if(!seat.isAvailable()) {
//                try {
//                    // reverse payment
//                    movieTicketRepository.deleteById(movieTicketId);
//                    List<MovieTicket> movieTickets = movieShow.getMovieTickets();
//                    for(MovieTicket m: movieTickets){
//                        if(m.equals(movieTicket.get())){
//                            movieTickets.remove(m);
//                        }
//                    }
//                    int filled = movieShow.getFilled();
//                    movieShow.setFilled(filled-1);
//                    movieTickets = user.getMovieTickets();
//                    for(MovieTicket m: movieTickets){
//                        if(m.equals(movieTicket.get())){
//                            movieTickets.remove(m);
//                        }
//                    }
//                    seat.setAvailable(true);
//                }
//                catch (Exception e) {
//                    System.out.println("[createMovieTicket]:Error occured in booking tickets");
//                }
//            }
//            else{
//                System.out.println("[createMovieTicket]:Seat not available");
//            }
//        }
//    }
//
//    @PutMapping("/api/movieTicket/{movieTicketId}")
//    public MovieTicket rescheduleMovieTicket(@PathVariable("movieTicketId") int movieTicketId, @RequestBody MovieShow newMovieShow, @RequestBody Seat newSeat ){
//        Optional<MovieTicket> movieTicket = movieTicketRepository.findById(movieTicketId);
//        User user = movieTicket.get().getUser();
//        MovieShow movieShow = movieTicket.get().getMovieShow();
//        Seat seat = movieShow.getSeatsBooked()[movieTicket.get().getR()][movieTicket.get().getC()];
//        synchronized (newSeat.seatMutex) {
//            synchronized (seat.seatMutex) {
//                if (!seat.isAvailable()) {
//                    List<MovieTicket> movieTickets = movieShow.getMovieTickets();
//                    for (MovieTicket m : movieTickets) {
//                        if (m.equals(movieTicket.get())) {
//                            movieTickets.remove(m);
//                        }
//                    }
//                    int filled = movieShow.getFilled();
//                    movieShow.setFilled(filled-1);
//                    movieTickets = user.getMovieTickets();
//                    for (MovieTicket m : movieTickets) {
//                        if (m.equals(movieTicket.get())) {
//                            movieTickets.remove(m);
//                        }
//                    }
//                    seat.setAvailable(true);
//                } else {
//
//                }
//            }
//            if(newSeat.isAvailable()) {
//                try {
//                    //payment
//                    MovieTicket newMovieTicket = new MovieTicket(newMovieShow.getMovieName(), newMovieShow.getScreenNumber(), newMovieShow.getTheatreName(), newMovieShow.getTheatreAdress(), newMovieShow.getDate(), newMovieShow.getTime(), newSeat.getPrice(), newSeat.getRow(), newSeat.getCol());
//                    movieTicket.get().set(newMovieTicket);
//                    movieTicketRepository.save(movieTicket.get());
//                    newMovieShow.getMovieTickets().add(movieTicket.get());
//                    int filled = newMovieShow.getFilled();
//                    movieShow.setFilled(filled+1);
//                    user.getMovieTickets().add(movieTicket.get());
//                    seat.setAvailable(false);
//                    return movieTicket.get();
//                }
//                catch (Exception e) {
//                    System.out.println("[createMovieTicket]:Error occured in booking tickets");
//                    return null;
//                }
//            }
//            else{
//                System.out.println("[createMovieTicket]:Seat not available");
//                return null;
//            }
//        }
//    }
    @GetMapping("/api/movieTicket/{movieTicketId}")
    public MovieTicket getTicket(@PathVariable("movieTicketId") int movieTicketId){
        return movieTicketRepository.findById(movieTicketId).get();
    }

}
