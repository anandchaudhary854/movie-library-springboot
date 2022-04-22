package com.fasal.MovieList.service;

import com.fasal.MovieList.model.MovieListModel;
import com.fasal.MovieList.model.PlaylistModel;
import com.fasal.MovieList.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistServiceImpl implements PlaylistService{

    @Autowired
    PlaylistRepository playlistRepository;

    @Override
    public List<PlaylistModel> getAllMovies() {
        return playlistRepository.findAll();
    }

    @Override
    public void saveMovie(PlaylistModel playlistModel) {
        playlistRepository.save(playlistModel);
    }

    @Override
    public void deleteById(long id) {
        this.playlistRepository.deleteById(id);
    }


}
