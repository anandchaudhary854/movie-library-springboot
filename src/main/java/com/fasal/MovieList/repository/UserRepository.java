package com.fasal.MovieList.repository;

import com.fasal.MovieList.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    public UserModel findByUserName(String name);
}
