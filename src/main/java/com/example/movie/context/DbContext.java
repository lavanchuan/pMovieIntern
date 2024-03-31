package com.example.movie.context;

import com.example.movie.context.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbContext {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public UserStatusRepository userStatusRepository;
    @Autowired
    public RoleRepository roleRepository;
    @Autowired
    public RankCustomerRepository rankCustomerRepository;

    @Autowired
    public ConfirmEmailRepository confirmEmailRepository;

    @Autowired
    public RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public MovieRepository movieRepository;

    @Autowired
    public CinemaRepository cinemaRepository;

    @Autowired
    public RoomRepository roomRepository;

    @Autowired
    public SeatRepository seatRepository;

    @Autowired
    public SeatStatusRepository seatStatusRepository;

    @Autowired
    public SeatTypeRepository seatTypeRepository;

    @Autowired
    public TicketRepository ticketRepository;

    @Autowired
    public BillTicketRepository billTicketRepository;

    @Autowired
    public ScheduleRepository scheduleRepository;

    @Autowired
    public FoodRepository foodRepository;

    @Autowired
    public BillFoodRepository billFoodRepository;

    @Autowired
    public BillRepository billRepository;

    @Autowired
    public MovieTypeRepository movieTypeRepository;

    @Autowired
    public RateRepository rateRepository;

    @Autowired
    public BillStatusRepository billStatusRepository;

    @Autowired
    public PromotionRepository promotionRepository;
}
