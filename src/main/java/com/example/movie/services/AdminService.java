package com.example.movie.services;

import com.example.movie.models.Role;
import com.example.movie.models.dto.IntervalDTO;
import com.example.movie.models.response.RevenueCinema;
import com.example.movie.services.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    public FoodServiceImpl foodService;

    @Autowired
    public SeatServiceImpl seatService;

    @Autowired
    public CinemaServiceImpl cinemaService;

    @Autowired
    public RoomServiceImpl roomService;

    @Autowired
    public MovieServiceImpl movieService;

    @Autowired
    public UserServiceImpl userService;

    @Autowired
    public ScheduleServiceImpl scheduleService;

    @Autowired
    public TicketServiceImpl ticketService;

    @Autowired
    public BillTicketServiceImpl billTicketService;

    @Autowired
    public PromotionServiceImpl promotionService;

    @Autowired
    public RankCustomerServiceImpl rankCustomerService;

    @Autowired
    public BillServiceImpl billService;

    public List<RevenueCinema> revenueByInterval(IntervalDTO request) {

        return billService.revenueByInterval(request);
    }

}
