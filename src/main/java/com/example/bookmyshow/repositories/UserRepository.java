package com.example.bookmyshow.repositories;

import com.example.bookmyshow.models.Review;
import com.example.bookmyshow.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
}
