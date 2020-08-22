package com.example.bookmyshow.repositories;

import com.example.bookmyshow.models.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review,Integer> {
}
