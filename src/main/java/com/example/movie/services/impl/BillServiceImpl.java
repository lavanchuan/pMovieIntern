package com.example.movie.services.impl;

import com.example.movie.context.DbContext;
import com.example.movie.models.BillStatus;
import com.example.movie.models.dto.BillDTO;
import com.example.movie.models.dto.BillFoodDTO;
import com.example.movie.models.dto.BillTicketDTO;
import com.example.movie.models.dto.IntervalDTO;
import com.example.movie.models.entities.*;
import com.example.movie.models.mapper.*;
import com.example.movie.models.request.CreateBillRequest;
import com.example.movie.models.response.CreateBillResponse;
import com.example.movie.models.response.RevenueCinema;
import com.example.movie.services.JwtService;
import com.example.movie.services.UserDetailsServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BillServiceImpl {

    @Autowired
    DbContext dbContext;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserDetailsServicesImpl userDetailsServices;

    @Autowired
    PromotionMapper promotionMapper;

    @Autowired
    BillMapper billMapper;

    @Autowired
    BillFoodMapper billFoodMapper;

    @Autowired
    BillticketMapper billTicketMapper;

    public CreateBillResponse createBill(CreateBillRequest request){

        // Bill
        BillDAO billDAO = null;
        boolean flag = false;

        String username = jwtService.extractUsername(request.getToken());
        int userId = 0;
        if(username != null && dbContext.userRepository.existsByUsername(username)){
            userId = dbContext.userRepository.findByUsername(username)
                    .getId();

//            if(dbContext.billRepository.countByUserIdAndunPaid(userId) > 0) {
//                billDAO = dbContext.billRepository.findByUserIdAndNotPaid(userId);
//            }

//            System.out.println("??");

            if(userId == 0) return new CreateBillResponse(400);
        }

//        if(billDAO != null){
//            // ?? promotionId
//        } else {
            BillDTO billDTO = new BillDTO();

            billDTO.setActive(true);
            billDTO.setCustomerId(userId);
            billDTO.setBillStatusId(
                    dbContext.billStatusRepository.findByName(BillStatus.UNPAID).getId()
            );

            billDAO = billMapper.toEntity(billDTO, dbContext);
//        }

        // Bill Food
        AtomicReference<Float> totalMoney = new AtomicReference<>((float) 0);
        List<TicketDAO> ticketDAOS = null;
        if(dbContext.foodRepository.existsById(request.getFoodId())){
            flag = true;
            totalMoney.set((float)dbContext.foodRepository.findById(request.getFoodId()).orElseThrow().getPrice() * request.getFoodQuantity());
        }

        // Bill Ticket [Ticket.isActive => true == chua ban, false == da ban]
        if(request.getScheduleId() > 0 &&
        dbContext.scheduleRepository.existsById(request.getScheduleId())){
            flag = true;
            ticketDAOS = dbContext.ticketRepository
                    .findAllByScheduleIdAndTrueIsActive(request.getScheduleId())
                    .subList(0, request.getTicketQuantity());

            if(ticketDAOS.size() < request.getTicketQuantity()) return new CreateBillResponse(400);

            for(TicketDAO ticket : ticketDAOS){
                ticket.setActive(false);
                totalMoney.updateAndGet(v -> v + (float) ticket.getPriceTicket());
                dbContext.ticketRepository.save(ticket);
            }
        }

        if(!flag) return new CreateBillResponse(400);

        // save ->

        billDAO.setTotalMoney((double)totalMoney.get());
        billDAO = dbContext.billRepository.save(billDAO);

        for(TicketDAO ticket : ticketDAOS){
            BillTicketDTO billTicketDTO = new BillTicketDTO();
            billTicketDTO.setQuantity(1);
            billTicketDTO.setTicketId(ticket.getId());
            billTicketDTO.setBillId(billDAO.getId());
            dbContext.billTicketRepository.save(billTicketMapper.toEntity(billTicketDTO, dbContext));
        }

        if(request.getFoodId() > 0 && dbContext.foodRepository.existsById(request.getFoodId())){
            BillFoodDTO billFoodDTO = new BillFoodDTO();
            billFoodDTO.setQuantity(request.getFoodQuantity());
            billFoodDTO.setFoodId(request.getFoodId());
            billFoodDTO.setBillId(billDAO.getId());
            dbContext.billFoodRepository.save(billFoodMapper.toEntity(billFoodDTO, dbContext));
        }

        return new CreateBillResponse(billMapper.toDTO(billDAO));
    }

    public List<RevenueCinema> revenueByInterval(IntervalDTO request) {
        List<String> revenuesString = dbContext.billRepository.revenueByInterval(request.getStartDay(),
                request.getEndDay());

        System.out.println("//TODO SIZE: " + revenuesString.size());

        List<RevenueCinema> revenueCinemas = new ArrayList<>();
        for(String revenue : revenuesString){
            revenueCinemas.add(new RevenueCinema(revenue));
        }

        return revenueCinemas;
    }
//
    public List<RevenueCinema> revenueByInterval() {
        List<String> revenuesString = dbContext.billRepository.revenueByInterval();

        List<RevenueCinema> revenueCinemas = new ArrayList<>();
        for(String revenue : revenuesString){
            revenueCinemas.add(new RevenueCinema(revenue));
        }

        return revenueCinemas;
    }

    public List<String> revenueByStartDay(LocalDate startDay, LocalDate endDay) {
//        System.out.println("//TODO REQUEST: " + request.toString());
        return dbContext.billRepository.revenueByInterval(startDay, endDay);
    }
}
