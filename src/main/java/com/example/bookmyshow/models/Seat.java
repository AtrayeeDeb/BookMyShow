package com.example.bookmyshow.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int r;
    private int c;
    private int price;
    private boolean available = true;

    @JsonIgnore
    public transient Object seatMutex = new Object();

    @ManyToOne
    @JsonIgnore
    MovieShow movieShow;

    @OneToOne(mappedBy = "seat")
    @JsonIgnore
    MovieTicket movieTicket;

    public Seat() {
        super();
    }

    Seat(int row, int col, int price, boolean available){
        this.r = row;
        this.c=col;
        this.price = price;
        this.available = available;
    }

    public int getR() {
        return r;
    }

    public void setR(int row) {
        this.r = row;
    }

    public int getC() {
        return c;
    }

    public void setC(int col) {
        this.c = col;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public MovieShow getMovieShow() {
        return movieShow;
    }

    public void setMovieShow(MovieShow movieShow) {
        this.movieShow = movieShow;
    }
    public MovieTicket getMovieTicket() {
        return movieTicket;
    }

    public void setMovieTicket(MovieTicket movieTicket) {
        this.movieTicket = movieTicket;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
