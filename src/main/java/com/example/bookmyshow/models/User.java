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
        if(newUser.getAddress()!=null) {
            this.setAddress(newUser.getAddress());
        }
        if(newUser.getDob()!=null){
            this.setDob(newUser.getDob());
        }
        if(newUser.getEmail()!=null){
            this.setEmail(newUser.getEmail());
        }
        if(newUser.getFirstName()!=null){
            this.setFirstName(newUser.getFirstName());
        }
        if(newUser.getLastName()!=null){
            this.setLastName(newUser.getLastName());
        }
        if(newUser.getPassword()!=null){
            this.setPassword(newUser.getPassword());
        }
        if(newUser.getPhone()!=null){
            this.setPhone(newUser.getPhone());
        }
    }

}
