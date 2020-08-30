package com.example.bookmyshow.controllers;

import com.example.bookmyshow.models.Movie;
import com.example.bookmyshow.models.Review;
import com.example.bookmyshow.models.User;
import com.example.bookmyshow.repositories.MovieRepository;
import com.example.bookmyshow.repositories.ReviewRepository;
import com.example.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ReviewController {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MovieRepository movieRepository;
    //CRUD
    //get reviews
    @GetMapping("/api/movie/{reviewId}")
    public Optional<Review> getReview(@PathVariable("reviewId") int reviewId){
        return reviewRepository.findById(reviewId);
    }

    @PostMapping("/api/user/{userId}/movie/{movieId}/createReview")
    public Review createReview(@PathVariable("movieId") int movieId, @PathVariable("userId") int userId, @RequestBody Review review){
        Optional<Movie> movie = movieRepository.findById(movieId);
        Optional<User> user = userRepository.findById(userId);
        movie.get().getReviews().add(review);
        user.get().getReviews().add(review);
        review.setMovie(movie.get());
        review.setUser(user.get());
        return reviewRepository.save(review);
    }

    @PutMapping("/api/review/{reviewId}")
    public Review updateReview(@PathVariable("reviewId") int reviewId, @RequestBody Review newReview){
        Optional<Review> review = reviewRepository.findById(reviewId);
        try{
//            List<Review> movieReviews= review.get().getMovie().getReviews();
//            for(Review r: movieReviews){
//                if(r.equals(review.get())){
//                    movieReviews.remove(r);
//                }
//            }
//            movieReviews.add(newReview);
//            List<Review> userReviews= review.get().getUser().getReviews();
//            for(Review r: userReviews){
//                if(r.equals(review.get())){
//                    userReviews.remove(r);
//                }
//            }
//            userReviews.add(newReview);
            review.get().set(newReview);
            return reviewRepository.save(review.get());
        }
        catch(Exception e){
            System.out.println("[updateReview]:"+e);
        }
        return null;
    }
    @DeleteMapping("/api/review/{reviewId}")
    public void deleteReview(@PathVariable("reviewId") int reviewId){
        Optional<Review> review = reviewRepository.findById(reviewId);
        try{
            List<Review> movieReviews= review.get().getMovie().getReviews();
            movieReviews.remove(review.get());
            List<Review> userReviews= review.get().getUser().getReviews();
            userReviews.remove(review.get());
            reviewRepository.deleteById(reviewId);
        }
        catch(Exception e){
            System.out.println("[updateReview]:"+e);
        }
    }
}
