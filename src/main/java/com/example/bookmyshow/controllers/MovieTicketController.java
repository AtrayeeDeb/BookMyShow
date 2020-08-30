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
            //seat = movieShow.get().seats[seat.getRow()][seat.getCol()];
            Optional<User> user = userRepository.findById(userId);
            synchronized (seat.seatMutex){
                if(seat.isAvailable()) {
                    try {
                        //payment
                        MovieTicket movieTicket = new MovieTicket(movieShow.get().getMovieName(), movieShow.get().getScreenNumber(), movieShow.get().getTheatreName(), movieShow.get().getTheatreAdress(), movieShow.get().getDate(), movieShow.get().getTime(), seat.getPrice(), seat.getR(), seat.getR());
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
                        movieShowRepository.save(movieShow.get());
                        movieTicketRepository.save(movieTicket);
                    }
                    catch (Exception e) {
                        System.out.println("[createMovieTicket]:Error occured in booking tickets");
                        //return null;
                    }
                }
                else{
                    System.out.println("[createMovieTicket]:Seat not available");
                    //return null;
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
                        System.out.println("heree-->"+seats.size());
                        List<MovieTicket> movieTickets = movieShow.getMovieTickets();
                        movieTickets.remove(movieTicket.get());
                        seats.remove(seat);
                        int filled = movieShow.getFilled();
                        movieShow.setFilled(filled-1);
                        movieShowRepository.save(movieShow);
                        movieTickets = user.getMovieTickets();
                        movieTickets.remove(movieTicket.get());
                        seatRepository.deleteById(seat.getId());
                        movieTicketRepository.deleteById(movieTicketId);
                        seat.setAvailable(true);
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
            System.out.println("[deleteMovieTicket]:error occured->"+e);
        }
    }

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
        Optional<MovieTicket> movieTicket = movieTicketRepository.findById(movieTicketId);
        return movieTicket.get();
    }

}
