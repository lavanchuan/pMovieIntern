package com.example.movie.controllers;

import com.example.movie.assembler.AppAssembler;
import com.example.movie.models.app.AppApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @Autowired
    AppAssembler appAssembler;

    @Value("${appName}")
    String appName;

    @GetMapping("")
    public ResponseEntity<?> appApiList(){
        return ResponseEntity.ok(appAssembler.toModel(new AppApi(appName)));
    }
}
