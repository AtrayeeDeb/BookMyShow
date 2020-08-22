package com.example.bookmyshow.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
public class MovieTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String movieName;
    private int screenNumber;
    private String theatreName;
    private String theatreAdress;
    private String date;
    private String time;
    private int price;
    private int r;
    private int c;
    //private String[] seatsBooked;

    public MovieTicket(String movieName, int screenNumber, String theatreName, String theatreAdress, String date,
                       String time, int price, int r, int c) {
        super();
        this.movieName = movieName;
        this.screenNumber = screenNumber;
        this.theatreName = theatreName;
        this.theatreAdress = theatreAdress;
        this.date = date;
        this.time = time;
        this.price = price;
        this.r = r;
        this.c = c;
    }

    @ManyToOne
    @JsonIgnore
    User user;

    @ManyToOne
    @JsonIgnore
    MovieShow movieShow;

    public MovieTicket() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getScreenNumber() {
        return screenNumber;
    }

    public void setScreenNumber(int screenNumber) {
        this.screenNumber = screenNumber;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public String getTheatreAdress() {
        return theatreAdress;
    }

    public void setTheatreAdress(String theatreAdress) {
        this.theatreAdress = theatreAdress;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }



//    public String[] getSeatsBooked() {
//        return seatsBooked;
//    }
//
//    public void setSeatsBooked(String[] seatsBooked) {
//        this.seatsBooked = seatsBooked;
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if(!user.getMovieTickets().contains(this)) {
            user.getMovieTickets().add(this);
        }
    }

    public MovieShow getMovieShow() {
        return movieShow;
    }

    public void setMovieShow(MovieShow movieShow) {
        this.movieShow = movieShow;
        if(!movieShow.getMovieTickets().contains(this)) {
            movieShow.getMovieTickets().add(this);
        }
    }
    public void set(MovieTicket newMovieTicket) {
        setId(newMovieTicket.getId());
        setMovieName(newMovieTicket.getMovieName());
        setScreenNumber(newMovieTicket.getScreenNumber());
        setTheatreName(newMovieTicket.getTheatreName());
        setTheatreAdress(newMovieTicket.getTheatreAdress());
        setDate(newMovieTicket.getDate());
        setTime(newMovieTicket.getTime());
        setR(newMovieTicket.getR());
        setC(newMovieTicket.getC());
        setPrice(newMovieTicket.getPrice());
    }


}
