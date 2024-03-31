package com.example.movie.models.mapper;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.BillFoodDTO;
import com.example.movie.models.entities.BillFoodDAO;
import org.springframework.stereotype.Component;

@Component
public class BillFoodMapper implements BaseMapper<BillFoodDAO, BillFoodDTO, DbContext>{
    @Override
    public BillFoodDAO toEntity(BillFoodDTO billFoodDTO, DbContext dbContext) {

        BillFoodDAO billFoodDAO = new BillFoodDAO();

        if(billFoodDTO.getId() > 0 && dbContext.billFoodRepository.existsById(billFoodDTO.getId())){
            // update
            billFoodDAO = dbContext.billFoodRepository.findById(billFoodDTO.getId()).orElseThrow();
            if(billFoodDTO.getQuantity() > 0) billFoodDAO.setQuantity(billFoodDTO.getQuantity());
        } else {
            // add
            billFoodDAO.setQuantity(billFoodDTO.getQuantity());
        }

        if(billFoodDTO.getBillId() > 0 && dbContext.billRepository.existsById(billFoodDTO.getBillId())){
            billFoodDAO.setBillDAO(dbContext.billRepository.findById(billFoodDTO.getBillId()).orElseThrow());
        }

        if(billFoodDTO.getFoodId() > 0 && dbContext.foodRepository.existsById(billFoodDTO.getFoodId())){
            billFoodDAO.setFoodDAO(dbContext.foodRepository.findById(billFoodDTO.getFoodId()).orElseThrow());
        }

        return billFoodDAO;
    }

    @Override
    public BillFoodDTO toDTO(BillFoodDAO billFoodDAO) {
        BillFoodDTO billFoodDTO = new BillFoodDTO();

        billFoodDTO.setId(billFoodDAO.getId());
        billFoodDTO.setQuantity(billFoodDAO.getQuantity());
        if(billFoodDAO.getBillDAO() != null) billFoodDTO.setBillId(billFoodDAO.getBillDAO().getId());
        if(billFoodDAO.getFoodDAO() != null) billFoodDTO.setFoodId(billFoodDAO.getFoodDAO().getId());

        return billFoodDTO;
    }
}
