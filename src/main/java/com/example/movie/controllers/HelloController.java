package com.example.movie.controllers;

import com.example.movie.models.entities.BillStatusDAO;
import com.example.movie.models.entities.FoodDAO;
import com.example.movie.services.GenerateOTP;
import org.eclipse.angus.mail.auth.MD4;
import org.eclipse.angus.mail.smtp.DigestMD5;
import org.eclipse.angus.mail.util.MailLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.PasswordAuthentication;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@RestController()
@RequestMapping(value = "/hello")
public class HelloController {

    @Autowired
    GenerateOTP generateOTP;

    @GetMapping("/demo")
    public String demo(){return "demo";}

    // food
    @RequestMapping(value = "/food", method = RequestMethod.GET)
    public ResponseEntity<?> food(){
        return ResponseEntity.ok(new FoodDAO());
    }

    // billStatus
    @RequestMapping(value = "/billStatus", method = RequestMethod.GET)
    public ResponseEntity<?> billStatus(){
        return ResponseEntity.ok(new BillStatusDAO());
    }

    // char
    @GetMapping("/char")
    public String character(){
        return String.format("0(%d), a(%d), A(%d)", (int)'0', (int)'a', (int)'A');
    }

    // otp
    @GetMapping("/otp")
    public String otpGenerate(){
        return generateOTP.createOTP();
    }

    // pas
    @GetMapping("/password")
    public String hassPassword(@RequestBody String password) throws Exception{
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] encodeHash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for(byte b : encodeHash){
            String hex = Integer.toHexString(0xff & b);
            if(hex.length() == 1) hexString.append("0");
            else hexString.append(hex);
        }
        return hexString.toString();
    }
}

