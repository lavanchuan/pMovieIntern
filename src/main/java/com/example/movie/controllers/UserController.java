package com.example.movie.controllers;

import com.example.movie.models.SeatStatus;
import com.example.movie.models.dto.BillDTO;
import com.example.movie.models.request.*;
import com.example.movie.models.response.CreateBillResponse;
import com.example.movie.services.AdminService;
import com.example.movie.services.AuthenService;
import com.example.movie.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    AuthenService authenService;

    @Autowired
    UserService userService;

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request){
        return ResponseEntity.ok(authenService.changePassword(request));
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request){
        return ResponseEntity.ok(authenService.forgotPassword(request));
    }

    @RequestMapping(value = "/confirm-forgot-password", method = RequestMethod.POST)
    public ResponseEntity<?> confirmForgotPassword(@Valid @RequestBody ConfirmForgotPasswordRequest request){
        return ResponseEntity.ok(authenService.confirmForgotPassword(request));
    }

    @RequestMapping(value = "/find-all", method = RequestMethod.GET)
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @RequestMapping(value = "/find-by-id", method = RequestMethod.GET)
    public ResponseEntity<?> findById(@RequestParam(name = "id", required = true) int id){
        return ResponseEntity.ok(userService.findById(id));
    }

    // Display Movies by nameCinema, nameRoom, seatStatus
    @RequestMapping(value = "/movies-by-cinema-room-seatstatus", method = RequestMethod.GET)
    public ResponseEntity<?> moviesByCinemaRoomSeatstatus(@RequestParam(name = "nameCinema", defaultValue = "") String nameCinema,
                                                          @RequestParam(name = "nameRoom", defaultValue = "") String nameRoom,
                                                          @RequestParam(name = "seatStatus", defaultValue = "EMPTY") String seatStatus){

        return ResponseEntity.ok(userService.moviesByCinemaRoomSeatstatus(nameCinema, nameRoom, seatStatus));
    }

    // CREATE BILL
    @RequestMapping(value = "/create-bill", method = RequestMethod.POST)
    public ResponseEntity<?> createBill(@RequestBody CreateBillRequest createBillRequest){

        return ResponseEntity.ok(adminService.billService.createBill(createBillRequest));
    }

    // PAY
    @RequestMapping(value = "/pay-bill", method = RequestMethod.PUT)
    public ResponseEntity<?> payBill(@RequestBody PayBillRequest request){

        return ResponseEntity.ok(userService.payBill(request));
    }
}
