package com.example.movie.services;

import com.example.movie.context.DbContext;
import com.example.movie.models.entities.ConfirmEmailDAO;
import com.example.movie.models.entities.UserDAO;
import com.example.movie.models.request.ConfirmEmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmEmailService {
    final int LENGTH = 8;
    public static long EXPIRATION = 10 * 60 * 1000;// 10 minutes

    @Autowired
    GenerateOTP generateOTP;

    @Autowired
    DbContext dbContext;

    public String generateConfirmCode(){
        return generateOTP.createOTP(LENGTH);
    }

    public void confirmEmail(ConfirmEmailRequest request){

        UserDAO userDAO = dbContext.userRepository.findByUsername(request.getUsername());
        if(userDAO == null) return;

        ConfirmEmailDAO confirmEmailDAO = dbContext.confirmEmailRepository.findByUserDAO(userDAO);
        if(confirmEmailDAO == null) return;


        if(!request.getConfirmCode().equals(confirmEmailDAO.getConfirmCode())
        && !request.getRequestTime().isBefore(confirmEmailDAO.getRequiredTime())) return;

        confirmEmailDAO.setConfirm(true);
    }
}
