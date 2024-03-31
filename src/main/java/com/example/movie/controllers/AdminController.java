package com.example.movie.controllers;

import com.example.movie.context.repositories.BillRepository;
import com.example.movie.models.Role;
import com.example.movie.models.dto.*;
import com.example.movie.models.response.BaseResponse;
import com.example.movie.models.response.UserResponse;
import com.example.movie.services.AdminService;
import com.example.movie.services.LocalDateTimeFormat;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    // CRUD Cinema
    @RequestMapping(value = "/cinema/add", method = RequestMethod.POST)
    public ResponseEntity<?> addCinema(@RequestBody CinemaDTO request){
        return ResponseEntity.ok(adminService.cinemaService.add(request));
    }

    @RequestMapping(value = "/cinema/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCinema(@RequestBody CinemaDTO request){
        return ResponseEntity.ok(adminService.cinemaService.update(request));
    }

    @RequestMapping(value = "/cinema/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCinema(@RequestBody CinemaDTO request){
        return ResponseEntity.ok(adminService.cinemaService.delete(request.getId()));
    }

    // CRUD Room
    @RequestMapping(value = "/room/add", method = RequestMethod.POST)
    public ResponseEntity<?> addRoom(@RequestBody RoomDTO request){
        return ResponseEntity.ok(adminService.roomService.add(request));
    }

    @RequestMapping(value = "/room/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateRoom(@RequestBody RoomDTO request){
        return ResponseEntity.ok(adminService.roomService.update(request));
    }

    @RequestMapping(value = "/room/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRoom(@RequestBody RoomDTO request){
        return ResponseEntity.ok(adminService.roomService.delete(request.getId()));
    }

    // CRUD Seat
    @RequestMapping(value = "/seat/add", method = RequestMethod.POST)
    public ResponseEntity<?> addSeat(@RequestBody SeatDTO request){
        return ResponseEntity.ok(adminService.seatService.add(request));
    }

    @RequestMapping(value = "/seat/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSeat(@RequestBody SeatDTO request){
        return ResponseEntity.ok(adminService.seatService.update(request));
    }

    @RequestMapping(value = "/seat/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSeat(@RequestBody SeatDTO request){
        return ResponseEntity.ok(adminService.seatService.delete(request.getId()));
    }

    // CRUD Food
    @RequestMapping(value = "/food/add", method = RequestMethod.POST)
    public ResponseEntity<?> addFood(@RequestBody FoodDTO request){
        return ResponseEntity.ok(adminService.foodService.add(request));
    }

    @RequestMapping(value = "/food/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateFood(@RequestBody FoodDTO request){
        return ResponseEntity.ok(adminService.foodService.update(request));
    }

    @RequestMapping(value = "/food/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFood(@RequestBody FoodDTO request){
        return ResponseEntity.ok(adminService.foodService.delete(request.getId()));
    }

    // CRUD Movie
    @RequestMapping(value = "/movie/add", method = RequestMethod.POST)
    public ResponseEntity<?> addMovie(@RequestBody MovieDTO request){
        return ResponseEntity.ok(adminService.movieService.add(request));
    }

    @RequestMapping(value = "/movie/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateMovie(@RequestBody MovieDTO request){
        return ResponseEntity.ok(adminService.movieService.update(request));
    }

    @RequestMapping(value = "/movie/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMovie(@RequestBody MovieDTO request){
        return ResponseEntity.ok(adminService.movieService.delete(request.getId()));
    }

    // CRUD Schedule

    // DEBUG
//    @GetMapping("/value")
//    public ResponseEntity<?> value(@RequestBody @Valid ScheduleDTO request){
//        return ResponseEntity.ok(request);
//    }
//
//    @GetMapping("/date")
//    public ResponseEntity<?> date(@RequestBody @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
//                                  LocalDateTime localDateTime){
//        return ResponseEntity.ok(localDateTime);
//    }

//    @GetMapping("/localdatetime")
//    public ResponseEntity<?> localDateTime(@RequestBody String localDateTime){
//        return ResponseEntity.ok(LocalDateTimeFormat.toLocalDateTime(localDateTime));
//    }

    @RequestMapping(value = "/schedule/add", method = RequestMethod.POST)
    public ResponseEntity<?> addSchedule(@RequestBody ScheduleDTO request){
//        System.out.println(request.getStartAt());
        return ResponseEntity.ok(adminService.scheduleService.add(request));
    }

//    @RequestMapping(value = "/movie/update", method = RequestMethod.PUT)
//    public ResponseEntity<?> updateMovie(@RequestBody MovieDTO request){
//        return ResponseEntity.ok(adminService.movieService.update(request));
//    }
//
//    @RequestMapping(value = "/movie/delete", method = RequestMethod.DELETE)
//    public ResponseEntity<?> deleteMovie(@RequestBody MovieDTO request){
//        return ResponseEntity.ok(adminService.movieService.delete(request.getId()));
//    }
//


    // REVENUE
    @GetMapping("/revenue")
    public ResponseEntity<?> revenue(@RequestBody IntervalDTO request){
        return ResponseEntity.ok(adminService.revenueByInterval(request));
    }

    //
    @GetMapping("/revenue-start")
    public ResponseEntity<?> revenue(
            @RequestParam(name = "startDay", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDay,
            @RequestParam(name = "endDay", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDay){
        System.out.println("//TODO AdminController REQUEST: " + startDay + "\t" + endDay);
        return ResponseEntity.ok(adminService.billService.revenueByStartDay(startDay, endDay));
    }

    @Autowired
    BillRepository billRepository;
    @GetMapping("/revenue-test")
    public ResponseEntity<?> revenueTest(){
        return ResponseEntity.ok(billRepository.revenueByInterval());
    }

    // REVENUE FEATURED FOOD IN A WEEK
    @GetMapping("/featured-food-in7d")
    public ResponseEntity<?> featuredFoodId7Day(){
        return ResponseEntity.ok(adminService.foodService.featuredFood7Day());
    }

    // MANAGER USER
        // CRUD USER
    @GetMapping("/find-user-all")
    public ResponseEntity<?> findUserAll(){
        return ResponseEntity.ok(adminService.userService.findAll());
    }

    @GetMapping("/find-user-by-id")
    public ResponseEntity<?> findUserById(@RequestParam(name = "userId", required = true) int userId){
        return ResponseEntity.ok(adminService.userService.findById(userId));
    }

    @PostMapping("/add-user")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(adminService.userService.add(userDTO));
    }

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(adminService.userService.update(userDTO));
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam(name = "userId", required = true) int userId){
        return ResponseEntity.ok(adminService.userService.delete(userId));
    }
        // CHANGE ROLE USER
    @PutMapping("/change-role-user")
    public ResponseEntity<?> changeRoleUser(@RequestParam(name = "userId", required = true) int userId,
                                            @RequestParam(name = "role", required = true) Role role){
        return ResponseEntity.ok(adminService.userService.changeRoleUser(userId, role));
    }
        // TURN OFF/OFF ACTIVE USER
    @PostMapping("/change-active-user")
    public ResponseEntity<?> changeActiveUser(@RequestParam(name = "userId", required = true) int userId,
                                              @RequestParam(name = "isActive", required = true) boolean isActive){
        UserResponse response = adminService.userService.changeActiveUser(userId, isActive);

        switch (response.getStatus()){
            case BaseResponse.SUCCESS_STATUS:
                return ResponseEntity.status(HttpStatus.OK).body(response);
            default:
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }
        // CHANGE RANK USER -> Promotion
    @PostMapping("/change-rank-user")
    public ResponseEntity<?> changeRankUser(@RequestParam(name = "userId", required = true) int userId,
                                            @RequestParam(name = "rankId", required = true) int rankId){
        UserResponse response = adminService.userService.changeRankUser(userId, rankId);

        switch (response.getStatus()){
            case BaseResponse.SUCCESS_STATUS:
                return ResponseEntity.status(HttpStatus.OK).body(response);
            default: return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }
}
