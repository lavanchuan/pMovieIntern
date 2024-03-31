package com.example.movie.services;

import com.example.movie.context.DbContext;
import com.example.movie.models.BillStatus;
import com.example.movie.models.dto.*;
import com.example.movie.models.entities.BillDAO;
import com.example.movie.models.entities.PromotionDAO;
import com.example.movie.models.entities.UserDAO;
import com.example.movie.models.mapper.BillMapper;
import com.example.movie.models.mapper.MovieMapper;
import com.example.movie.models.mapper.UserMapper;
import com.example.movie.models.request.CreateBillRequest;
import com.example.movie.models.request.PayBillRequest;
import com.example.movie.models.response.CreateBillResponse;
import com.example.movie.models.response.PayBillResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    DbContext dbContext;

    @Autowired
    UserMapper userMapper;

    @Autowired
    MovieMapper movieMapper;

    @Autowired
    AdminService adminService;

    @Autowired
    JwtService jwtService;

    @Autowired
    BillMapper billMapper;

    public List<UserDTO> findAll(){
        return dbContext.userRepository
                .findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public UserDTO findById(int id){
        return dbContext.userRepository
                .findById(id)
                .map(userMapper::toDTO)
                .orElseThrow();
    }

    public void create(UserDTO userDTO){
        // Promotion
    }

    public List<MovieDTO> moviesByCinemaRoomSeatstatus(String nameCinema, String nameRoom, String seatStatus) {

        List<MovieDTO> movies = dbContext.movieRepository.movieListByCinemaRoomSeatstatus(nameCinema, nameRoom, seatStatus)
                .stream().map(movieMapper::toDTO).toList();

        return movies;
    }

    public List<MovieDTO> chooseMovie(){
        return adminService.movieService.findAll();
    }

    public List<CinemaDTO> chooseCinema(int movieId){
        return adminService.cinemaService.findAllByMovieId(movieId);
    }

    public List<RoomDTO> chooseRoom(int movieId, int cinemaId){
        return adminService.roomService.findAllByMovieIdCinemaId(movieId, cinemaId);
    }

//    public List<RoomDTO> chooseSchedule(int movieId, int roomId){ // roomId <= movieId and cinemaId
//        return adminService.scheduleService.findAllByMovieIdRoomId(movieId, roomId);
//    }

    public List<FoodDTO> chooseFood(){
        return adminService.foodService.findAll();
    }

    public void chooseScheduleFood(){

    }

    public List<ScheduleDTO> chooseSchedule(int movieId, int roomId) {
        return adminService.scheduleService.findAllByMovieIdRoomId(movieId, roomId);
    }

    public PayBillResponse payBill(PayBillRequest request){

        if(request.getBillId() <= 0 && !dbContext.billRepository.existsById(request.getBillId())){
            return new PayBillResponse(400);
        }

        BillDTO billDTO = billMapper.toDTO(dbContext.billRepository.findById(request.getBillId()).orElseThrow());

        if(request.getTotalMoney() - billDTO.getTotalMoney() < 0) return new PayBillResponse(400);

        String username = jwtService.extractUsername(request.getToken());
        if(username == null) return new PayBillResponse(400);

        List<Integer> listIdPromotion = dbContext.promotionRepository
                        .findIdAllByUsername(username);
        if(listIdPromotion.contains(request.getPromotionId())){
            PromotionDAO promotionDAO = dbContext.promotionRepository.findById(request.getPromotionId()).orElseThrow();
            if(promotionDAO.getQuantity() >= 1){
                billDTO.setTotalMoney(billDTO.getTotalMoney() - billDTO.getTotalMoney() * (promotionDAO.getPercent() * 0.01));
                promotionDAO.setQuantity(promotionDAO.getQuantity() - 1);
                dbContext.promotionRepository.save(promotionDAO);
            }
        }

        System.out.println("SO du: " + (request.getTotalMoney() - billDTO.getTotalMoney()));

        billDTO.setBillStatusId(dbContext.billStatusRepository
                .findByName(BillStatus.PAID).getId());
        billDTO = billMapper.toDTO(dbContext.billRepository.save(billMapper.toEntity(billDTO, dbContext)));

        return new PayBillResponse(billDTO);
    }
}
