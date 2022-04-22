package com.fasal.MovieList.service;

import com.fasal.MovieList.model.MovieListModel;
import com.fasal.MovieList.model.PlaylistModel;
//import jdk.dynalink.linker.LinkerServices;

import java.util.List;

public interface PlaylistService {

    List<PlaylistModel> getAllMovies();
    void saveMovie(PlaylistModel playlistModel);
    void deleteById(long id);

}
