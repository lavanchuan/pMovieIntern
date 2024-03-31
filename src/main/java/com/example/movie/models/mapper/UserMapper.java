package com.example.movie.models.mapper;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.UserDTO;
import com.example.movie.models.entities.RankCustomerDAO;
import com.example.movie.models.entities.RoleDAO;
import com.example.movie.models.entities.UserDAO;
import com.example.movie.models.entities.UserStatusDAO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements BaseMapper<UserDAO, UserDTO, DbContext>{
    @Override
    public UserDAO toEntity(UserDTO userDTO, DbContext dbContext) {
        UserDAO user = new UserDAO();

        if(dbContext.userRepository.existsByUsername(userDTO.getUsername())
        || dbContext.userRepository.existsById(userDTO.getId())){
            // update
            user = dbContext.userRepository.findById(userDTO.getId()).orElseThrow();

            if(userDTO.getPoint() > 0) user.setPoint(userDTO.getPoint());
            if(userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()) user.setEmail(userDTO.getEmail());
            if(userDTO.getName() != null && !userDTO.getName().isEmpty()) user.setName(userDTO.getName());
            if(userDTO.getPhoneNumber() != null && !userDTO.getPhoneNumber().isEmpty()) user.setPhoneNumber(userDTO.getPhoneNumber());
            if(userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) user.setPassword(userDTO.getPassword());

            if(userDTO.getRoleId() > 0 && dbContext.roleRepository.existsById(userDTO.getRoleId())){
                user.setRoleDAO(dbContext.roleRepository.findById(userDTO.getRoleId()).orElseThrow());
                user.setRole(user.getRoleDAO().getRoleName());
            }


            if(userDTO.getUserStatusId() > 0 && dbContext.userStatusRepository.existsById(userDTO.getUserStatusId())) {
                user.setUserStatusDAO(dbContext.userStatusRepository.findById(userDTO.getUserStatusId()).orElseThrow());
                user.setUserStatus(user.getUserStatusDAO().getName());
            }

            user.setActive(userDTO.getIsActive());

            if(userDTO.getRankCustomerId() > 0 && dbContext.rankCustomerRepository.existsById(userDTO.getRankCustomerId())) {
                user.setRankCustomerDAO(dbContext.rankCustomerRepository.findById(userDTO.getRankCustomerId()).orElseThrow());
            }

        } else {
            // add new user
            user.setEmail(userDTO.getEmail());
            user.setUsername(userDTO.getUsername());
            user.setName(userDTO.getName());
            user.setPassword(userDTO.getPassword());
            user.setPhoneNumber(userDTO.getPhoneNumber());

            user.setPoint(userDTO.getPoint());
            user.setActive(userDTO.getIsActive());

            if(userDTO.getRoleId() > 0){
                user.setRoleDAO(dbContext.roleRepository.findById(userDTO.getRoleId()).orElseThrow());
                user.setRole(user.getRoleDAO().getRoleName());
            } else {
                user.setRole(userDTO.getRole());
                user.setRoleDAO(dbContext.roleRepository.findByRoleName(userDTO.getRole()));
            }


            if(userDTO.getUserStatusId() > 0) {
                user.setUserStatusDAO(dbContext.userStatusRepository.findById(userDTO.getUserStatusId()).orElseThrow());
                user.setUserStatus(user.getUserStatusDAO().getName());
            } else {
                user.setUserStatus(userDTO.getUserStatus());
                user.setUserStatusDAO(dbContext.userStatusRepository.findByName(userDTO.getUserStatus()));
            }

            if(userDTO.getRankCustomerId() > 0) {
                user.setRankCustomerDAO(dbContext.rankCustomerRepository.findById(userDTO.getRankCustomerId()).orElseThrow());
            }
        }

        return user;
    }

    @Override
    public UserDTO toDTO(UserDAO userDAO) {

        UserDTO user = new UserDTO();

        user.setId(userDAO.getId());
        user.setIsActive(userDAO.isActive());
        user.setUsername(userDAO.getUsername());
        user.setEmail(userDAO.getEmail());
        user.setName(userDAO.getName());
        user.setPassword(userDAO.getPassword());
        user.setPhoneNumber(userDAO.getPhoneNumber());

        user.setRole(null);
        user.setUserStatus(null);

        if(userDAO.getRoleDAO()!=null){
            user.setRole(userDAO.getRoleDAO().getRoleName());
            user.setRoleId(userDAO.getRoleDAO().getId());
        }

        if(userDAO.getUserStatusDAO()!=null){
            user.setUserStatus(userDAO.getUserStatusDAO().getName());
            user.setUserStatusId(userDAO.getUserStatusDAO().getId());
        }

        if(userDAO.getRankCustomerDAO()!=null){
            user.setRankCustomerId(userDAO.getRankCustomerDAO().getId());
        }

        return user;
    }
}
