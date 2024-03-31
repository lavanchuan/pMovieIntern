package com.example.movie.models.mapper;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.BillDTO;
import com.example.movie.models.entities.BillDAO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BillMapper implements BaseMapper<BillDAO, BillDTO, DbContext>{
    @Override
    public BillDAO toEntity(BillDTO billDTO, DbContext dbContext) {

        BillDAO billDAO = new BillDAO();

        if(billDTO.getId() > 0 && dbContext.billRepository.existsById(billDTO.getId())){
            // update
            billDAO = dbContext.billRepository.findById(billDTO.getId()).orElseThrow();

            if(billDTO.getTotalMoney() > 0) billDAO.setTotalMoney(billDTO.getTotalMoney());
            if(billDTO.getTradingCode() != null && !billDTO.getTradingCode().isEmpty()) billDAO.setTradingCode(billDTO.getTradingCode());

            if(billDTO.getCreateTime() != null){
                billDAO.setCreateTime(billDTO.getCreateTime());
            }

            if(billDTO.getUpdateTime() != null){
                billDAO.setUpdateTime(billDTO.getUpdateTime());
            } else {
                billDAO.setUpdateTime(LocalDateTime.now());
            }
            if(billDTO.getName() != null && !billDTO.getName().isEmpty()){
                billDAO.setName(billDTO.getName());
            }
        } else {
            // add
            billDAO.setTotalMoney(billDTO.getTotalMoney());
            billDAO.setTradingCode(billDTO.getTradingCode());
            if(billDTO.getCreateTime() != null){
                billDAO.setCreateTime(billDTO.getCreateTime());
            } else {
                billDAO.setCreateTime(LocalDateTime.now());
            }

            if(billDTO.getUpdateTime() != null){
                billDAO.setUpdateTime(billDTO.getUpdateTime());
            } else {
                billDAO.setUpdateTime(billDTO.getCreateTime());
            }

            billDAO.setName(billDTO.getName());
        }

        billDAO.setActive(billDTO.isActive());

        if(billDTO.getCustomerId() > 0 && dbContext.userRepository.existsById(billDTO.getCustomerId())){
            billDAO.setCustomer(dbContext.userRepository.findById(billDTO.getCustomerId()).orElseThrow());
        }

        if(billDTO.getPromotionId() > 0 && dbContext.promotionRepository.existsById(billDTO.getPromotionId())){
            billDAO.setPromotionDAO(dbContext.promotionRepository.findById(billDTO.getPromotionId()).orElseThrow());
        }

        if(billDTO.getBillStatusId() > 0 && dbContext.billStatusRepository.existsById(billDTO.getBillStatusId())){
            billDAO.setBillStatusDAO(dbContext.billStatusRepository.findById(billDTO.getBillStatusId()).orElseThrow());
        }



        return billDAO;
    }

    @Override
    public BillDTO toDTO(BillDAO billDAO) {

        BillDTO billDTO = new BillDTO();

        billDTO.setId(billDAO.getId());
        billDTO.setTotalMoney(billDAO.getTotalMoney());
        billDTO.setTradingCode(billDAO.getTradingCode());
        billDTO.setCreateTime(billDAO.getCreateTime());
        billDTO.setName(billDAO.getName());
        billDTO.setUpdateTime(billDAO.getUpdateTime());
        billDTO.setActive(billDAO.isActive());

        if(billDAO.getCustomer() != null) {
            billDTO.setCustomerId(billDAO.getCustomer().getId());
        }

        if(billDAO.getPromotionDAO() != null) {
            billDTO.setPromotionId(billDAO.getPromotionDAO().getId());
        }

        if(billDAO.getBillStatusDAO() != null) {
            billDTO.setBillStatusId(billDAO.getBillStatusDAO().getId());
        }

        return billDTO;
    }
}
