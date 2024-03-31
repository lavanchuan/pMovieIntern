package com.example.movie.models.mapper;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.BillTicketDTO;
import com.example.movie.models.entities.BillTicketDAO;
import org.springframework.stereotype.Component;

@Component
public class BillticketMapper implements BaseMapper<BillTicketDAO, BillTicketDTO, DbContext>{
    @Override
    public BillTicketDAO toEntity(BillTicketDTO billTicketDTO, DbContext dbContext) {

        BillTicketDAO billTicketDAO = new BillTicketDAO();

        if(billTicketDTO.getId() > 0 && dbContext.billTicketRepository.existsById(billTicketDTO.getId())){
            // update
            billTicketDAO = dbContext.billTicketRepository.findById(billTicketDTO.getId()).orElseThrow();

            if(billTicketDTO.getQuantity() > 0) {
                billTicketDAO.setQuantity(billTicketDTO.getQuantity());
            }
            if(billTicketDTO.getBillId() > 0){
                billTicketDAO.setBillDAO(dbContext.billRepository.findById(billTicketDTO.getBillId()).orElseThrow());
            }
            if(billTicketDTO.getTicketId() > 0){
                billTicketDAO.setTicketDAO(dbContext.ticketRepository.findById(billTicketDTO.getTicketId()).orElseThrow());
            }
        } else {
            // add
            billTicketDAO.setQuantity(billTicketDTO.getQuantity());
            if(billTicketDTO.getBillId() > 0){
                billTicketDAO.setBillDAO(dbContext.billRepository.findById(billTicketDTO.getBillId()).orElseThrow());
            }
            if(billTicketDTO.getTicketId() > 0){
                billTicketDAO.setTicketDAO(dbContext.ticketRepository.findById(billTicketDTO.getTicketId()).orElseThrow());
            }
        }

        return billTicketDAO;
    }

    @Override
    public BillTicketDTO toDTO(BillTicketDAO billTicketDAO) {

        BillTicketDTO billTicketDTO = new BillTicketDTO();

        billTicketDTO.setId(billTicketDAO.getId());
        billTicketDTO.setQuantity(billTicketDAO.getQuantity());
        if(billTicketDAO.getBillDAO() != null) billTicketDTO.setBillId(billTicketDAO.getBillDAO().getId());
        if(billTicketDAO.getTicketDAO() != null) billTicketDTO.setTicketId(billTicketDAO.getTicketDAO().getId());

        return billTicketDTO;
    }
}
