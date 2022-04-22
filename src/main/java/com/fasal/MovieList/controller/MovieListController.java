package com.fasal.MovieList.controller;


import com.fasal.MovieList.model.MovieDetailModel;
import com.fasal.MovieList.model.MovieListModel;
import com.fasal.MovieList.model.PlaylistModel;
import com.fasal.MovieList.model.UserModel;
import com.fasal.MovieList.service.MyUserDetailsService;
import com.fasal.MovieList.service.PlaylistService;
import com.fasal.MovieList.service.PlaylistServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MovieListController {

    @Autowired
    PlaylistService playlistService;

    @Autowired
    MyUserDetailsService myUserDetailsService;


    @GetMapping("/")
    public String home(){
        return "homePage";
    }

    @GetMapping("/movieList")
    public String movieList(@RequestParam(value = "title", required = false) String title, Model model){



        RestTemplate restTemplate = new RestTemplate();
        String ResourceUrl = "http://www.omdbapi.com/?s="+title+"&apikey=edb60183";
        ResponseEntity<String> response = null;
        try{
            response = restTemplate.getForEntity(ResourceUrl + "", String.class);
        }catch (Exception e){
            return "internalServerError";
        }
        JSONObject obj = new JSONObject(response.getBody());
        String error = obj.get("Response").toString();
        System.out.println(error);

        if(error.equals("False")){
//            System.out.println(obj.get("Response"));
            return "movieNotFound";
        }

        JSONArray search = obj.getJSONArray("Search");

        ArrayList<MovieListModel> movieListModels = new ArrayList<>();
        for(int i = 0; i<search.length(); i++){
            MovieListModel movieListModel = new MovieListModel();
            movieListModel.setTitle(search.getJSONObject(i).getString("Title"));
            movieListModel.setYear(search.getJSONObject(i).getString("Year"));
            movieListModel.setType(search.getJSONObject(i).getString("Type"));
            movieListModel.setImdbID(search.getJSONObject(i).getString("imdbID"));
            movieListModel.setPosterUrl(search.getJSONObject(i).getString("Poster"));
            movieListModels.add(movieListModel);
        }
//        System.out.println(movieListModels.toString());
        MovieListModel movieListModel = new MovieListModel();
        model.addAttribute("listMovies", movieListModels);
//        model.addAttribute("movieModel", movieListModel);



        return "movieList";
    }

    @GetMapping("/movieDetails/{id}")
    public String movieDetails(@PathVariable(value = "id") String id, Model model){
        RestTemplate restTemplate = new RestTemplate();
        String ResourceUrl = "http://www.omdbapi.com/?i="+id+"&apikey=edb60183";
        ResponseEntity<String> response
                = restTemplate.getForEntity(ResourceUrl + "", String.class);
        JSONObject obj = new JSONObject(response.getBody());
        MovieDetailModel movieDetailModel = new MovieDetailModel();


        movieDetailModel.setTitle(obj.get("Title").toString());
        movieDetailModel.setPoster(obj.get("Poster").toString());
        movieDetailModel.setDirector(obj.getString("Director"));
        movieDetailModel.setYearReleased(obj.getString("Released"));
        movieDetailModel.setRuntime(obj.getString("Runtime"));
        movieDetailModel.setPlot(obj.getString("Plot"));

        model.addAttribute("movie", movieDetailModel);



        return "movieDetails";
    }

    @PostMapping("/addPlayList")
    public String addMovieToPlaylist(@ModelAttribute("movieModel") MovieListModel movieListModel, Principal principal){
        PlaylistModel playlistModel = new PlaylistModel();
        playlistModel.setTitle(movieListModel.getTitle());
        playlistModel.setYear(movieListModel.getYear());
        playlistModel.setImdbId(movieListModel.getImdbID());
        playlistModel.setPoster(movieListModel.getPosterUrl());
        playlistModel.setType(movieListModel.getType());

        String userName = principal.getName();
        UserModel userModel = (UserModel) myUserDetailsService.loadUserByUsername(userName);
        userModel.getPlaylistModels().add(playlistModel);
        myUserDetailsService.save(userModel);

//        playlistService.saveMovie(playlistModel);
        return "redirect:/movieList?addedToPlaylist";

    }

    @GetMapping("/getPlaylist")
    public String getAllMoviesInPlaylist(Model model, Principal principal){

        String userName = principal.getName();
        UserModel userModel = (UserModel) myUserDetailsService.loadUserByUsername(userName);

        List<PlaylistModel> playlistModels = userModel.getPlaylistModels();
        model.addAttribute("allMovies", playlistModels);
        return "playlist";
    }

    @GetMapping("/deleteMovie/{id}")
    public String deleteMovie(@PathVariable(value = "id") long id){
        this.playlistService.deleteById(id);
        return "redirect:/getPlaylist";
    }


}
