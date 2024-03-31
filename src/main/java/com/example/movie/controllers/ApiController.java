package com.example.movie.controllers;

import com.example.movie.assembler.ChooseAssembler;
import com.example.movie.assembler.ChooseCiemaAssembler;
import com.example.movie.models.mapper.ScheduleMapper;
import com.example.movie.services.AdminService;
import com.example.movie.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    @Autowired
    ChooseAssembler adminAssembler;


    @RequestMapping(value = "/featured-movies", method = RequestMethod.GET)
    public ResponseEntity<?> featuredMovies(){
        return ResponseEntity.ok(adminService.movieService.featuredMovies());
    }

    // CHOOSE MOVIE -> PAY
//    @RequestMapping(value = "/chooseMovie", method = RequestMethod.GET)
//    public ResponseEntity<?> chooseMovie(){
//
//    }

//    @RequestMapping(value = "/choose-movie", method = RequestMethod.GET)
//    public ResponseEntity<?> chooseMovie(){
//        return ResponseEntity.ok(userService.chooseMovie());
//    }



    @RequestMapping(value = "/choose-movie", method = RequestMethod.GET)
    public ResponseEntity<?> chooseMovie(){
        return ResponseEntity.ok(adminAssembler.chooseCinemaAssembler.toCollectionModel(
                userService.chooseMovie()
        ));
    }



    @RequestMapping(value = "/choose-cinema", method = RequestMethod.GET)
    public ResponseEntity<?> chooseCinema(@RequestParam(name = "movieId", required = true) int movieId){

        adminAssembler.chooseRoomAssembler.setMovieId(movieId);

        return ResponseEntity.ok(adminAssembler.chooseRoomAssembler.toCollectionModel(
                userService.chooseCinema(movieId)));
    }

    @RequestMapping(value = "/choose-room", method = RequestMethod.GET)
    public ResponseEntity<?> chooseRoom(@RequestParam(name = "movieId", required = true) int movieId,
                                        @RequestParam(name = "cinemaId", required = true) int cinemaId){

        adminAssembler.chooseScheduleAssembler.setMovieId(movieId);

        return ResponseEntity.ok(adminAssembler.chooseScheduleAssembler.toCollectionModel(
                userService.chooseRoom(movieId, cinemaId)
        ));
    }


    @RequestMapping(value = "/choose-schedule", method = RequestMethod.GET)
    public ResponseEntity<?> chooseSchedule(@RequestParam(name = "movieId", required = true) int movieId,
                                            @RequestParam(name = "roomId", required = true) int roomId){

        return ResponseEntity.ok(adminAssembler.chooseFoodAssembler.toCollectionModel(
                userService.chooseSchedule(movieId, roomId)
        ));
    }

    @RequestMapping(value = "/choose-food", method = RequestMethod.GET)
    public ResponseEntity<?> chooseFood(@RequestParam(name = "scheduleId", required = true) int scheduleId){
        return ResponseEntity.ok(userService.chooseFood());
    }



}
