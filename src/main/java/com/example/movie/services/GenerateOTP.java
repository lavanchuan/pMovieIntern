package com.example.movie.services;

import org.springframework.stereotype.Service;

@Service
public class GenerateOTP {
    static int OTP_LENGTH = 6;
    static int LIMIT_RANDOM = 1000000;

    public String createOTP(){
        String otp_code = "";
        for(int i = 0; i < OTP_LENGTH; i++){
            otp_code += generateCharacter();
        }
        return otp_code;
    }

    public String createOTP(int length){
        String otp_code = "";
        for(int i = 0; i < length; i++){
            otp_code += generateCharacter();
        }
        return otp_code;
    }

    private char generateCharacter(){
        int randomCode = ((int)(Math.random()*LIMIT_RANDOM));
        int totalChar = ('9'-'0')+('z'-'a')+('Z'-'A')+3;
        int code = randomCode % totalChar;
//        System.out.println("Code: " + code);
        if(code < '9'-'0' + 1) return (char)('0' + code);
        else if(code < ('9' - '0') + ('Z' - 'A') + 2) return (char)('A' + (code-('9'-'0' + 1)));
        return (char)('a' + (code - (('9'-'0') + ('Z'-'A') + 2)));
    }

}
