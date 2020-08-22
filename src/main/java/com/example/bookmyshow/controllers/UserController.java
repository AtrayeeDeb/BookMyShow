package com.example.bookmyshow.controllers;

import com.example.bookmyshow.models.MovieTicket;
import com.example.bookmyshow.models.Review;
import com.example.bookmyshow.models.User;
import com.example.bookmyshow.repositories.AddressRepository;
import com.example.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {
    //findUserByCredential
    //findallusers
    //createuser
    //deleteuser
    //update an user
    //finduserbyuserid
    //find all reviews of a user
    //find all movie tickets of a user -> movie tickets
    //find all movie shows of a user
    //find all movies of a user
    //try creating get method with same url
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;

    @GetMapping("/api/user")
    User findUsersByCredentials(@RequestParam(name="username", required=false) String username, @RequestParam(name="password", required=false) String password){
        if(username!=null && password!=null){
            return userRepository.findByUsernameAndPassword(username, password);
        }
        else if(username!=null){
            return userRepository.findByUsername(username);
        }
        return null;
    }

    @GetMapping("/api/user/all")
    Iterable<User> findAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/api/user")
    User createUser(@RequestBody User user){
        return userRepository.save(user);
    }

    @DeleteMapping("/api/user/{userId}")
    void deleteUser(@PathVariable("userId") int userId){
        userRepository.deleteById(userId);
    }

    @PutMapping("/api/user/{userId}")//not working
    User updateUser(@PathVariable("userId") int userId, @RequestBody User newUser){
        Optional<User> user = userRepository.findById(userId);
        try{
            user.get().set(newUser);
            return userRepository.save(user.get());
        }
        catch(Exception e){
            System.out.println("[updateUser]:"+e);
        }
        return null;
    }

    @GetMapping("/api/user/{userId}")
    Optional<User> findUserByUserId(@PathVariable("userId") int userId){
        return userRepository.findById(userId);
    }

    @GetMapping("/api/user/{userId}/getReviews")
    List<Review> getAllReviews(@PathVariable("userId") int userId){
        Optional<User> user = userRepository.findById(userId);
        try{
            List<Review> reviews = user.get().getReviews();
            return reviews;
        }
        catch(Exception e){
            System.out.println("[getAllReviews]:"+e);
        }
        return null;
    }

    @GetMapping("/api/user/{userId}/getTickets")
    List<MovieTicket> getAllTickets(@PathVariable("userId") int userId){
        Optional<User> user = userRepository.findById(userId);
        try{
            List<MovieTicket> movieTickets = user.get().getMovieTickets();
            return movieTickets;
        }
        catch(Exception e){
            System.out.println("[getAllTickets]:"+e);
        }
        return null;
    }

}
