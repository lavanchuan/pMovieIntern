package com.example.movie.controllers;

import com.example.movie.models.dto.UserDTO;
import com.example.movie.models.request.ChangePasswordRequest;
import com.example.movie.models.request.ConfirmEmailRequest;
import com.example.movie.models.request.ForgotPasswordRequest;
import com.example.movie.services.AuthenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/authen")
public class AuthenController {

    @Autowired
    AuthenService authenService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) throws Exception{
        ConfirmEmailRequest emailRequest = new ConfirmEmailRequest();
        emailRequest.setUsername(userDTO.getUsername());

        return ResponseEntity.ok(
                EntityModel.of(authenService.register(userDTO),
                        linkTo(methodOn(AuthenController.class).confirmEmail(emailRequest)).withRel("Confirm Email"))
        );
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(authenService.authentication(userDTO));
    }

    @PostMapping("/confirm-email")
    public ResponseEntity<?> confirmEmail(@RequestBody ConfirmEmailRequest request){

        return ResponseEntity.ok(EntityModel.of(authenService.confirmEmail(request),
                linkTo(methodOn(AuthenController.class).login(null)).withRel("Login")));
    }


}
