package com.example.movie.services;

import com.example.movie.context.DbContext;
import com.example.movie.models.Token;
import com.example.movie.models.UserStatus;
import com.example.movie.models.dto.MailDTO;
import com.example.movie.models.dto.UserDTO;
import com.example.movie.models.entities.ConfirmEmailDAO;
import com.example.movie.models.entities.RefreshTokenDAO;
import com.example.movie.models.entities.UserDAO;
import com.example.movie.models.impl.UserDetailsImpl;
import com.example.movie.models.mapper.UserMapper;
import com.example.movie.models.request.ChangePasswordRequest;
import com.example.movie.models.request.ConfirmEmailRequest;
import com.example.movie.models.request.ConfirmForgotPasswordRequest;
import com.example.movie.models.request.ForgotPasswordRequest;
import com.example.movie.models.response.AuthenResponse;
import com.example.movie.models.response.ConfirmEmailResponse;
import com.example.movie.models.response.RegisterResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenService {
    @Autowired
    GenerateOTP generateOTP;
    @Autowired
    MailServices mailServices;
    @Autowired
    DbContext dbContext;
    @Autowired
    UserMapper userMapper;
//    @Autowired
//    PasswordEncrypt passwordEncrypt;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserDetailsServicesImpl userDetailsServices;

    @Autowired
    ConfirmEmailService confirmEmailService;

//    public void register(@Valid UserDTO userDTO) throws Exception{
//        String otp = generateOTP.createOTP();
//
//        save(userDTO, otp);
//
//        MailDTO mailDTO = new MailDTO();
//        mailDTO.setFrom("no-reply@account.com");
//        mailDTO.setPersonal("Support");
//        mailDTO.setTo(userDTO.getEmail());
//        mailDTO.setContent("Password: " + otp);
//        mailDTO.setSubject("Verify account");
//        mailServices.sendMail(mailDTO);
//    }

    public RegisterResponse register(@Valid UserDTO userDTO){

        if(userDTO.getRankCustomerId() == 0 &&
        dbContext.rankCustomerRepository.count() > 0){
            userDTO.setRankCustomerId(dbContext.rankCustomerRepository.findAll().get(0).getId()); // default
        }

        UserDAO userDAO = dbContext.userRepository.findByUsername(userDTO.getUsername());

        if(userDAO != null) return null;

        userDAO = userMapper.toEntity(userDTO, dbContext);
        userDAO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userDAO = dbContext.userRepository.save(userDAO);

        // confirm email
        LocalDateTime requiredTime = LocalDateTime.now();
        LocalDateTime expiredTime = requiredTime.plusNanos(ConfirmEmailService.EXPIRATION);
        String confirmCode = confirmEmailService.generateConfirmCode();

        ConfirmEmailDAO confirmEmailDAO = new ConfirmEmailDAO();
        confirmEmailDAO.setUserDAO(userDAO);
        confirmEmailDAO.setRequiredTime(requiredTime);
        confirmEmailDAO.setExpiredTime(expiredTime);
        confirmEmailDAO.setConfirmCode(confirmCode);

        dbContext.confirmEmailRepository.save(confirmEmailDAO);

        // send mail
        MailDTO mailDTO = new MailDTO();
//        mailDTO.setFrom(MailServices.FROM);
//        mailDTO.setPersonal(MailServices.PERSONAL);
        mailDTO.setSubject("Confirm Code: " + confirmCode);
        mailDTO.setTo(userDAO.getEmail());
        mailDTO.setContent(String.format("CODE: '%s'\nactive username: '%s'",
                confirmCode, userDAO.getUsername()));

        mailServices.sendMail(mailDTO);

        return new RegisterResponse(200);
    }

    public ConfirmEmailResponse confirmEmail(ConfirmEmailRequest request){

        confirmEmailService.confirmEmail(request);

        UserDAO userDAO = dbContext.userRepository.findByUsername(request.getUsername());

        ConfirmEmailDAO confirmEmailDAO = dbContext.confirmEmailRepository.findByUserDAO(userDAO);

        if(!confirmEmailDAO.isConfirm()) return new ConfirmEmailResponse(400);

        userDAO.setActive(true);
        userDAO.setUserStatus(UserStatus.ACTIVE);
        userDAO.setUserStatusDAO(dbContext.userStatusRepository.findByName(userDAO.getUserStatus()));


        dbContext.userRepository.save(userDAO);

        return new ConfirmEmailResponse(200);
    }

//    public String login(UserDTO userDTO) {
//        int size = 0;
//        String pass = passwordEncoder.encode(userDTO.getPassword());
//        UserDAO userDAO = dbContext.userRepository.findAll().stream().filter(
//                user -> user.getEmail().equals(userDTO.getEmail())
//        ).collect(Collectors.toList()).get(0);
//        if(userDAO.getPassword().equals(pass)) return "SUCCESS";
//        return "FAILED";
//    }
    public AuthenResponse authentication(UserDTO userDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userDTO.getUsername(), userDTO.getPassword()
        ));

        UserDAO user = dbContext.userRepository.findByUsername(userDTO.getUsername());

        Token token;

        // check token old
        List<RefreshTokenDAO> refreshTokenDAOList = dbContext.refreshTokenRepository.findRefreshTokenDAOSByUserDAO(user);
        if(refreshTokenDAOList != null && refreshTokenDAOList.size() > 0){
            RefreshTokenDAO refreshTokenDAO = refreshTokenDAOList.stream().max(new Comparator<RefreshTokenDAO>() {
                @Override
                public int compare(RefreshTokenDAO o1, RefreshTokenDAO o2) {
                    return o1.getExpiredTime().compareTo(o2.getExpiredTime());
                }
            }).get();

            if(refreshTokenDAO.getExpiredTime().isBefore(LocalDateTime.now())){
                token = new Token();
                token.setSubject(user.getUsername());
                LocalDateTime expiredTime = refreshTokenDAO.getExpiredTime();
                token.setExpiredAt(new Date(expiredTime.getYear(),expiredTime.getMonthValue(),expiredTime.getDayOfMonth()));
                token.setValue(refreshTokenDAO.getToken());

                return new AuthenResponse(token);
            }
        }

        if(user == null) return null;
        if(!user.isActive()) return null;

        UserDetailsImpl userDetails = new UserDetailsImpl();

//        UserDTO dto = userMapper.toDTO(user);

//        userDetails.setData(dto);
        userDetails.setUsername(user.getUsername());
        userDetails.setPassword(user.getPassword());
        userDetails.setRole(user.getRole());

        token = jwtService.generateToken(userDetails);

        // save token
        RefreshTokenDAO newRefreshToken = new RefreshTokenDAO();
        newRefreshToken.setToken(token.getValue());
        newRefreshToken.setUserDAO(user);
        LocalDate expTime = jwtService.extractExpired(token.getValue());
//        newRefreshToken.setExpiredTime(LocalDateTime.of(expTime.getYear(), expTime.getMonth()+1, expTime.getDate(),0,0,0));
        newRefreshToken.setExpiredTime(LocalDateTime.of(
                expTime.getYear(), expTime.getMonthValue(), expTime.getDayOfMonth(), 0, 0, 0));


        dbContext.refreshTokenRepository.save(newRefreshToken);

        return new AuthenResponse(token);
    }

    public UserDAO save(UserDTO userDTO, String password) throws Exception{
        UserDAO newUser;
        newUser = userMapper.toEntity(userDTO, dbContext);
        newUser.setPassword(passwordEncoder.encode(password));

        return dbContext.userRepository.save(newUser);
    }

    public boolean changePassword(ChangePasswordRequest request) {

        String username = jwtService.extractUsername(request.getToken());
        UserDAO user;
        if(username != null){
            user = dbContext.userRepository.findByUsername(username);
            if(user != null && passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
                user.setPassword(passwordEncoder.encode(request.getNewPassword()));

                dbContext.userRepository.save(user);
                return true;
            }
        }

        return false;
    }

    public Object forgotPassword(ForgotPasswordRequest request) {
        String otp;
        UserDAO userDAO;
        long expiration = 5 * 60 * 1000; // 5 minute
        if((request.getEmail() != null && dbContext.userRepository.existsByEmail(request.getEmail())) ||
        request.getUsername() != null && dbContext.userRepository.existsByUsername(request.getUsername())){
            otp = generateOTP.createOTP();

            if(request.getEmail() != null) {
                userDAO = dbContext.userRepository.findByEmail(request.getEmail());
            } else {
                userDAO = dbContext.userRepository.findByUsername(request.getUsername());
            }

            ConfirmEmailDAO confirmEmail = new ConfirmEmailDAO();
            confirmEmail.setConfirmCode(otp);
            confirmEmail.setId(userDAO.getId());
            confirmEmail.setRequiredTime(LocalDateTime.now());
            confirmEmail.setExpiredTime(LocalDateTime.now().plusMinutes(5));
            confirmEmail.setUserDAO(userDAO);

            dbContext.confirmEmailRepository.save(confirmEmail);

            MailDTO mailDTO = new MailDTO();
            mailDTO.setTo(userDAO.getEmail());
            mailDTO.setSubject(String.format("OTP: '%s'", otp));
            mailDTO.setContent(String.format("OTP: '%s', for create new password of username '%s'", otp, userDAO.getUsername()));

            mailServices.sendMail(mailDTO);

            return "Link confirm forgot password"; // link confirm forgot pass
        }
        return null;
    }

    public Object confirmForgotPassword(ConfirmForgotPasswordRequest request) {
        if(dbContext.userRepository.existsByEmail(request.getEmail())){

            UserDAO userDAO = dbContext.userRepository.findByEmail(request.getEmail());

            ConfirmEmailDAO confirmEmailDAO = dbContext.confirmEmailRepository.findByUserDAO(userDAO);

            if(request.getOtp().equals(confirmEmailDAO.getConfirmCode())){

                LocalDateTime requestTime = LocalDateTime.now();

                if(requestTime.isBefore(confirmEmailDAO.getExpiredTime()) &&
                requestTime.isAfter(confirmEmailDAO.getRequiredTime())){

                    confirmEmailDAO.setConfirm(true);
                    dbContext.confirmEmailRepository.save(confirmEmailDAO);

                    userDAO.setPassword(passwordEncoder.encode(request.getPassword()));
                    dbContext.userRepository.save(userDAO);

                    return "Link login api";

                }
            }

        }

        return null;
    }
}
