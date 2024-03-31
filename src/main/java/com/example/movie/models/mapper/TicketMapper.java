package com.example.movie.models.mapper;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.TicketDTO;
import com.example.movie.models.entities.TicketDAO;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper implements BaseMapper<TicketDAO, TicketDTO, DbContext> {
    @Override
    public TicketDAO toEntity(TicketDTO ticketDTO, DbContext dbContext) {

        TicketDAO ticketDAO = new TicketDAO();

        if(ticketDTO.getId() > 0 && dbContext.ticketRepository.existsById(ticketDTO.getId())){
            // update
            ticketDAO = dbContext.ticketRepository.findById(ticketDTO.getId()).orElseThrow();

            if(ticketDTO.getCode() != null && !ticketDTO.getCode().isEmpty()) {
                ticketDAO.setCode(ticketDTO.getCode());
            }
            if(ticketDTO.getScheduleId() > 0) {
                ticketDAO.setScheduleDAO(dbContext.scheduleRepository.findById(ticketDTO.getScheduleId()).orElseThrow());
            }
            if(ticketDTO.getSeatId() > 0) {
                ticketDAO.setSeatDAO(dbContext.seatRepository.findById(ticketDTO.getSeatId()).orElseThrow());
            }
            if(ticketDTO.getPriceTicket() > 0){
                ticketDAO.setPriceTicket(ticketDTO.getPriceTicket());
            }
        } else {
            // add
            ticketDAO.setCode(ticketDTO.getCode());
            if(ticketDTO.getScheduleId() > 0) {
                ticketDAO.setScheduleDAO(dbContext.scheduleRepository.findById(ticketDTO.getScheduleId()).orElseThrow());
            }
            if(ticketDTO.getSeatId() > 0) {
                ticketDAO.setSeatDAO(dbContext.seatRepository.findById(ticketDTO.getSeatId()).orElseThrow());
            }
            ticketDAO.setPriceTicket(ticketDTO.getPriceTicket());
        }

        ticketDAO.setActive(ticketDTO.isActive());

        return ticketDAO;
    }

    @Override
    public TicketDTO toDTO(TicketDAO ticketDAO) {

        TicketDTO ticketDTO = new TicketDTO();

        ticketDTO.setId(ticketDAO.getId());
        ticketDTO.setCode(ticketDAO.getCode());
        if(ticketDAO.getScheduleDAO() != null) ticketDTO.setScheduleId(ticketDAO.getScheduleDAO().getId());
        if(ticketDAO.getSeatDAO() != null) ticketDTO.setSeatId(ticketDAO.getSeatDAO().getId());
        ticketDTO.setPriceTicket(ticketDAO.getPriceTicket());
        ticketDTO.setActive(ticketDAO.isActive());

        return ticketDTO;
    }
}
