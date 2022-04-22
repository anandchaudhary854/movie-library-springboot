package com.fasal.MovieList.repository;


import com.fasal.MovieList.model.PlaylistModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistModel, Long> {

}
