package com.example.movie.controllers;

import com.example.movie.context.DbContext;
import com.example.movie.models.*;
import com.example.movie.models.dto.*;
import com.example.movie.models.entities.*;
import com.example.movie.models.mapper.*;
import com.example.movie.services.AdminService;
import com.example.movie.services.GenerateOTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    DbContext dbContext;

    @Autowired
    RoomMapper roomMapper;

    @Autowired
    AdminService adminService;

    @Autowired
    RankCustomerMapper rankCustomerMapper;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }


    @PostMapping("/load")
    public String load(){
        dbContext.roleRepository.save(new RoleDAO(Role.USER));
        dbContext.roleRepository.save(new RoleDAO(Role.ADMIN));

        dbContext.userStatusRepository.save(new UserStatusDAO(UserStatus.ACTIVE));
        dbContext.userStatusRepository.save(new UserStatusDAO(UserStatus.NOT_ACTIVE));

        dbContext.seatStatusRepository.save(new SeatStatusDAO(SeatStatus.EMPTY));
        dbContext.seatStatusRepository.save(new SeatStatusDAO(SeatStatus.NOT_EMPTY));

        dbContext.seatTypeRepository.save(new SeatTypeDAO(SeatType.NORMAL));
        dbContext.seatTypeRepository.save(new SeatTypeDAO(SeatType.VIP));

        dbContext.movieTypeRepository.save(new MovieTypeDAO(MovieType.FILM));
        dbContext.movieTypeRepository.save(new MovieTypeDAO(MovieType.HH3D));
        dbContext.movieTypeRepository.save(new MovieTypeDAO(MovieType.ANIME));

        dbContext.billStatusRepository.save(new BillStatusDAO(BillStatus.UNPAID));
        dbContext.billStatusRepository.save(new BillStatusDAO(BillStatus.PAID));

        // CINEMA
        dbContext.cinemaRepository.save(new CinemaDAO("Bắc Từ Liêm - Hà Nội", "RCP", "", "BTL Cinema", true));
        dbContext.cinemaRepository.save(new CinemaDAO("Nam Từ Liêm - Hà Nội", "RCP", "", "NTL Cinema", true));
        dbContext.cinemaRepository.save(new CinemaDAO("Tây Từ Liêm - Hà Nội", "RCP", "", "TTL Cinema", true));
        dbContext.cinemaRepository.save(new CinemaDAO("Đông Từ Liêm - Hà Nội", "RCP", "", "DTL Cinema", true));


        // RATE
        dbContext.rateRepository.save(new RateDAO("10 sao", ""));
        dbContext.rateRepository.save(new RateDAO("9 sao", ""));

        // ROOM
        int roomQuantity = 10;
        for(int i = 1; i <= roomQuantity; i++){
            dbContext.roomRepository.save(roomMapper.toEntity(generateRoomDTO(i), dbContext));
        }

        // MOVIE
        generateMovie();

        // SCHEDULE
        generateSchedule();

        // SEAT
        generateSeat();

        // TICKET
        generateTicket();

        // BILL


        // FOOD
        dbContext.foodRepository.save(new FoodDAO(20000, "Đồ ăn nhẹ", "banh_my", "Bánh Mỳ", true));
        dbContext.foodRepository.save(new FoodDAO(10000, "Nước có gas", "pepsi", "Pepsi", true));
        dbContext.foodRepository.save(new FoodDAO(15000, "Nước ép", "nuoc_cam", "Nước ép cam", true));
        dbContext.foodRepository.save(new FoodDAO(35000, "Bim bim", "bimbim", "Kẹo dẻo chip chip", true));


        // BILL FOOD
        generateBillFood();

        // BILL TICKET
//        generateBillTicket();

        // RANK CUSTOMER
        generateRankCustomer();

        // PROMOTION
        generatePromotion();


        return String.format("Role(%d)\nUserStatus(%d)",
                dbContext.roleRepository.count(),
                dbContext.userStatusRepository.count());
    }

    private void generatePromotion() {
        int percents[] = {10, 20, 30};
        for(int i = 1; i <= dbContext.rankCustomerRepository.count(); i++){
            for(int percent : percents){
                PromotionDTO dto = new PromotionDTO();
                dto.setPercent(percent);
                dto.setQuantity(generateInt() % 3 + 1);
                dto.setType("Type " + percent + "%");
                dto.setStartTime(LocalDateTime.now());
                dto.setEndTime(LocalDateTime.now().plusHours(24));
                dto.setDescription("Descriptin " + dto.getType());
                dto.setName("Promotion " +percent+"%");
                dto.setActive(true);
                dto.setRankCustomerId(i);

                adminService.promotionService.add(dto);
            }
        }
    }

    private void generateRankCustomer() {
        int n = 3;
        for(int i = 1; i <= n; i++){
            RankCustomerDTO dto = new RankCustomerDTO();
            dto.setName("RANK " + i);
            dto.setPoint(n - i + 1);
            dto.setDescription(String.format("%s - Point[%d]", dto.getName(), dto.getPoint()));
            dto.setActive(true);

            adminService.rankCustomerService.add(dto);
        }
    }

    private void generateBillFood() {

    }

    private void generateBillTicket() {

        boolean isBooking;
        for(TicketDAO ticketDAO : dbContext.ticketRepository.findAll()){
            isBooking = (generateInt() % 10 >= 7) ? false : true;
            if(!isBooking) continue;

            // DEBUG BillId ====>>> Mỗi ticket chỉ có 1 billTicket và quantity = 1
            // (vì ticket gắn liền với 1 ghế của 1 phòng và 1 schedule tại phòng đó.
            // mà ghế mặc định chỉ ngồi được 1 người tại 1 schedule nên quantity = 1[BOOKING = 1 USER])
//            int billQuantity = generateInt() % 20; // tam thoi co toi da 19 bill
//            for(int i = 0; i < billQuantity; i++){
//                int ticketQuantity = generateInt() % 5 + 1;
//
//                BillTicketDTO billTicketDTO = new BillTicketDTO();
//                billTicketDTO.setQuantity(ticketQuantity);
////                billTicketDTO.setBillId(0);
//                billTicketDTO.setTicketId(ticketDAO.getId());
//
//                adminService.billTicketService.add(billTicketDTO);
//            }

            // EDIT
            BillTicketDTO billTicketDTO = new BillTicketDTO();
            billTicketDTO.setQuantity(1);
            billTicketDTO.setTicketId(ticketDAO.getId());
            adminService.billTicketService.add(billTicketDTO);
        }
    }

    private void generateTicket() {
        for(SeatDAO seatDAO : dbContext.seatRepository.findAll()){
            generateTicket(seatDAO);
        }
    }

    private void generateTicket(SeatDAO seatDAO) {
        int roomId = dbContext.seatRepository.roomIdFromSeatId(seatDAO.getId());
        List<Integer> idScheduleList = dbContext.scheduleRepository.idListFromRoomId(roomId);
        int scheduleId = idScheduleList.get(generateInt() % idScheduleList.size());

        TicketDTO ticketDTO = new TicketDTO();

        ticketDTO.setCode(generateOTP.createOTP(10));
        ticketDTO.setScheduleId(scheduleId);
        ticketDTO.setSeatId(seatDAO.getId());

        final int PRICE_MIN = 30;
        final int PRICE_MAX = 100;
        double priceTicket = generateInt() % (PRICE_MAX - PRICE_MIN + 1) + PRICE_MIN;
        ticketDTO.setPriceTicket(priceTicket * 1000);
        ticketDTO.setActive(true);

        adminService.ticketService.add(ticketDTO);
    }

    private void generateSeat() {
        final int quantityMin = 10;
        final int quantityMax = 50;
        int seatQuantity;

        for (RoomDAO roomDAO : dbContext.roomRepository.findAll()){
            seatQuantity = generateInt() % (quantityMax - quantityMin + 1) + quantityMin;
            generateSeat(roomDAO, seatQuantity);
        }
    }

    private void generateSeat(RoomDAO roomDAO, int seatQuantity) {
        final int QUANTITY_PER_LINE = 10;
        for(int i = 0; i < seatQuantity; i++){
            SeatDTO seatDTO = new SeatDTO();

            seatDTO.setNumber(i+1);
            seatDTO.setLine(String.format("LINE: %d\n", seatDTO.getNumber() / QUANTITY_PER_LINE + 1));
            seatDTO.setSeatStatusId(dbContext.seatStatusRepository.findByNameStatus(SeatStatus.EMPTY).getId());
            seatDTO.setRoomId(roomDAO.getId());
            seatDTO.setActive(true);
            seatDTO.setSeatTypeId(generateInt() % (int)dbContext.seatTypeRepository.count() + 1);

            adminService.seatService.add(seatDTO);
        }

        System.out.printf("Added room %s : '%d' seat\n\n", roomDAO.getName(), seatQuantity);
    }


    private void generateSchedule() {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime defaultStartAt = LocalDateTime.of(now.getYear(),
                now.getMonthValue(),
                now.getDayOfMonth(),
                7,
                0,
                0);

        final int allowHour = 22;// > 22h startAt is false

        LocalDateTime startAt;
        final int timeSleep = 15;// minutes
        int duration;
        int counter = 0;
        for(int roomId = 1; roomId <= dbContext.roomRepository.count(); roomId++){
            System.out.println("ROOM SIZE: " + dbContext.roomRepository.findAll().size());
            System.out.printf("Adding schedule for room: %d\n", roomId);
            startAt = defaultStartAt;
            System.out.printf("\nStartAt: %s\n\n", startAt.toString());

            while(startAt.getHour() < allowHour){
                MovieDAO movie = getRandomMovie();
                duration = movie.getMovieDuration();

                ScheduleDTO scheduleDTO = new ScheduleDTO();
                scheduleDTO.setPrice(generatePriceShcedule());
                scheduleDTO.setStartAt(startAt);
                scheduleDTO.setEndAt(startAt.plusMinutes(duration));
                scheduleDTO.setCode(generateOTP.createOTP(10));
                scheduleDTO.setMovieId(movie.getId());
                scheduleDTO.setName(String.format("%s %d:%d\n",
                        movie.getName(), startAt.getHour(), startAt.getMinute()));
                scheduleDTO.setRoomId(roomId);
                scheduleDTO.setActive(true);

                startAt = scheduleDTO.getEndAt().plusMinutes(timeSleep);

                if(!adminService.scheduleService.add(scheduleDTO)){
                    break;
                }
                counter++;
            }
        }
        System.out.printf("Added %d schedule\n", counter);
    }

    private MovieDAO getRandomMovie() {
        return dbContext.movieRepository.findAll().get(
                generateInt() % (int)dbContext.movieRepository.count());
    }

    private double generatePriceShcedule() {
        int minPrice = 10; // 10M
        int maxPrice = 50; // 50M
        return (double)(generateInt() % (maxPrice-minPrice+1) + minPrice) * 1000000;
    }

    private void generateMovie() {
        String[] moviesName = {
                "Tây Du Ký",
                "Đấu Phá Thương Khung",
                "Thần Điêu Đại Hiệp",
                "Tam Quốc Diễn Nghĩa",
                "One Piece",
                "Naruto",
                "Đại Chúa Tể",
                "Võ Thần Chúa Tể"
        };

        int[] moviesDuration = {
                20,
                45,
                90,
                120
        };

        String[] languages = {"Japan", "VietNam", "China"};

        for(int i = 0; i < moviesName.length; i++){
            dbContext.movieRepository.save(movieMapper.toEntity(
                    generateMovieDTO(i, moviesName, moviesDuration, languages), dbContext));
        }

    }

    private MovieDTO generateMovieDTO(int index, String[] moviesName, int[] moviesDuration, String[] languages) {
        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setActive(true);
        int languageIndex = generateInt() % languages.length;
        movieDTO.setLanguage(languages[languageIndex]);

        movieDTO.setPremiereDate(generateDate(LocalDateTime.now().getYear() - 1));
        movieDTO.setEndTime(generateDate(LocalDateTime.now().getYear() + 1));

        movieDTO.setMovieDuration(moviesDuration[generateInt() % moviesDuration.length]);
        movieDTO.setMovieTypeId(generateInt() % (int)dbContext.movieTypeRepository.count() + 1);

        movieDTO.setName(moviesName[index]);
        movieDTO.setImage(movieDTO.getName().trim()+" Image");
        movieDTO.setTrailer(movieDTO.getName() + " trailer");
        movieDTO.setHeroImage("Hero Image");
        movieDTO.setDirector(movieDTO.getName() + " Director");
        movieDTO.setDescription(movieDTO.getName() + " Description");

        movieDTO.setRateId(generateInt() % (int)dbContext.rateRepository.count() + 1);


        return movieDTO;
    }

    private LocalDateTime generateDate(int year) {
        int month = generateInt() % 12 + 1;
        int DAY_MAX = 31;

        switch (month){
            case 2: DAY_MAX = (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0) ? 29 : 28); break;
            case 4, 6, 9, 11: DAY_MAX = 30; break;
        }

        int day = generateInt() % DAY_MAX + 1;

        return LocalDateTime.of(
                year,
                month,
                day,
                generateInt() % 24,
                generateInt() % 60
        );
    }

    private int generateInt() {
        return (int)(Math.random() * 1000000);
    }

    @Autowired
    MovieMapper movieMapper;

    private RoomDTO generateRoomDTO(int i) {
        RoomDTO roomDTO = new RoomDTO();

        roomDTO.setActive(true);
        roomDTO.setCapacity(((int)(Math.random() * 1000000) % 20 + 30));
        int typeRate = ((int)(Math.random() * 1000000)) % 2;
        roomDTO.setType((typeRate == 0 ? RoomType.PUBLIC : RoomType.PRIVATE));
        int cinemaId = ((int)(Math.random() * 1000000)) % (int)dbContext.cinemaRepository.count() + 1;
        roomDTO.setCinemaId(cinemaId);

        int roomQuantity = dbContext.roomRepository.countByCinemaDAO(dbContext.cinemaRepository
                .findById(cinemaId).orElseThrow());
        int idx = roomQuantity + 1;
        roomDTO.setName("R"+(idx < 10 ? "0"+idx : idx));

        roomDTO.setDescription(roomDTO.getName() + " " + dbContext.cinemaRepository
                .findById(cinemaId).orElseThrow().getNameOfCinema());

        roomDTO.setCode(generateOTP.createOTP(10));

        return roomDTO;
    }

    @Autowired
    GenerateOTP generateOTP;

    @GetMapping("/movies")
    ResponseEntity<?> search(@RequestParam(name = "nameCinema", defaultValue = "") String nameCinema,
                             @RequestParam(name = "nameRoom", defaultValue = "") String nameRoom,
                             @RequestParam(name = "seatStatus", defaultValue = "EMPTY") String seatStatus){
        return ResponseEntity.ok(dbContext.movieRepository.moviesByCinemaRoomSeatstatus(nameCinema, nameRoom, seatStatus));
    }

    @GetMapping("/movieList")
    ResponseEntity<?> search2(@RequestParam(name = "nameCinema", defaultValue = "") String nameCinema,
                             @RequestParam(name = "nameRoom", defaultValue = "") String nameRoom,
                             @RequestParam(name = "seatStatus", defaultValue = "EMPTY") String seatStatus){

        List<MovieDTO> movieDTOList = dbContext.movieRepository.movieListByCinemaRoomSeatstatus(
                nameCinema, nameRoom, seatStatus
        ).stream().map(movieMapper::toDTO).toList();

        return ResponseEntity.ok(movieDTOList);
    }




    @GetMapping("/times")
    public ResponseEntity<?> spaceTimeCompares(){

        LocalDateTime a1 = LocalDateTime.now();
        LocalDateTime b1 = a1.plusMinutes(60);

        LocalDateTime a2 = a1.plusMinutes(-60);
        LocalDateTime b2 = a2.plusMinutes(40);

        return ResponseEntity.ok(timeSpaceCompare(a1, b1, a2, b2));
    }

    boolean timeSpaceCompare(LocalDateTime a1, LocalDateTime b1,
                             LocalDateTime a2, LocalDateTime b2){
        return (a1.isBefore(a2) && b1.isBefore(a2)) || (a2.isBefore(a1) && b2.isBefore(a1));
    }

    // FIND ALL SCHEDULE BY DATE
//    @GetMapping("/schedule")
//    public ResponseEntity<?> scheduleByStartAt(){
//        LocalDate startAt = LocalDate.of(2024, 3, 7);
//
//        List<ScheduleDAO> scheduleDAOList = dbContext.scheduleRepository.findAllByStartDate(startAt);
//
//        List<ScheduleDTO> scheduleDTOList = scheduleDAOList.stream()
//                .map(scheduleMapper::toDTO).toList();
//
//        return ResponseEntity.ok(scheduleDTOList);
//    }

    @Autowired
    ScheduleMapper scheduleMapper;


}
