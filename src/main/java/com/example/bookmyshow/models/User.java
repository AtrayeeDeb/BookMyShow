package com.example.bookmyshow.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
public class User extends Person{
    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MovieTicket> movieTickets = new ArrayList<>();

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public User() {
        super();
    }

    public List<MovieTicket> getMovieTickets() {
        return movieTickets;
    }

    public void setMovieTicket(MovieTicket movieTicket) {
        this.movieTickets.add(movieTicket);
        if(movieTicket.getUser() != this) {
            movieTicket.setUser(this);
        }
    }


    public void set(User newUser) {
        this.setAddress(newUser.getAddress());
        this.setDob(newUser.getDob());
        this.setEmail(newUser.getEmail());
        this.setFirstName(newUser.getFirstName());
        this.setLastName(newUser.getLastName());
        this.setPassword(newUser.getPassword());
        this.setPhone(newUser.getPhone());
    }

}
