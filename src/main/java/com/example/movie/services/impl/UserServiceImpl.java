package com.example.movie.services.impl;

import com.example.movie.context.DbContext;
import com.example.movie.models.Role;
import com.example.movie.models.dto.UserDTO;
import com.example.movie.models.entities.UserDAO;
import com.example.movie.models.mapper.UserMapper;
import com.example.movie.models.response.BaseResponse;
import com.example.movie.models.response.UserResponse;
import com.example.movie.services.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements ICRUDService<UserDTO, Integer> {

    @Autowired
    DbContext dbContext;

    @Autowired
    UserMapper userMapper;

    public List<UserDTO> findAll(){
        List<UserDTO> userDTOS = dbContext.userRepository.findAll()
                .stream().map(userMapper::toDTO).toList();

        return userDTOS;
    }

    public UserDTO findById(int id) {
        return userMapper.toDTO(dbContext.userRepository.findById(id).orElseThrow());
    }

    public int countByRankCustomerId(int rankCustomerId) {
        return dbContext.userRepository.countByRankCustomerId(rankCustomerId);
    }

    @Override
    public boolean add(UserDTO userDTO) {
        if(userDTO.getId() > 0 && dbContext.userRepository.existsById(userDTO.getId()))
            return false;

        userDTO.setPassword(cryPassword(userDTO.getPassword()));
        dbContext.userRepository.save(userMapper.toEntity(userDTO, dbContext));

        return true;
    }

    @Override
    public boolean update(UserDTO userDTO) {
        if(userDTO.getId() == 0 || !dbContext.userRepository.existsById(userDTO.getId()))
            return false;

        userDTO.setPassword(cryPassword(userDTO.getPassword()));
        dbContext.userRepository.save(userMapper.toEntity(userDTO, dbContext));

        return true;
    }

    @Override
    public boolean delete(Integer id) {

        if(id == 0 || !dbContext.userRepository.existsById(id)) return false;

        if(dbContext.billRepository.countByUserId(id) > 0 ||
        dbContext.refreshTokenRepository.countByUserId(id) > 0 ||
        dbContext.confirmEmailRepository.countByUserId(id) > 0)
            return false;

        dbContext.userRepository.deleteById(id);

        return true;
    }

    @Autowired
    PasswordEncoder passwordEncoder;
    private String cryPassword(String password){
        return passwordEncoder.encode(password);
    }

    public UserResponse changeRoleUser(int userId, Role role) {

        if(dbContext.userRepository.existsById(userId) &&
        dbContext.roleRepository.existsByRoleName(role)){
            dbContext.userRepository.findAll()
                    .stream().map(user -> {
                        user.setRole(role);
                        user.setRoleDAO(dbContext.roleRepository.findByRoleName(role));
                        return dbContext.userRepository.save(user);
                    }).collect(Collectors.toList());

            return new UserResponse(findById(userId));
        }

        return new UserResponse(BaseResponse.ERROR_STATUS);
    }

    public UserResponse changeActiveUser(int userId, boolean isActive) {
        if(!dbContext.userRepository.existsById(userId)){
            return new UserResponse(BaseResponse.ERROR_STATUS);
        }

        dbContext.userRepository.findAll()
                .stream().map(user ->{
                    user.setActive(isActive);

                    return dbContext.userRepository.save(user);
                }).collect(Collectors.toList());

        return new UserResponse(findById(userId));
    }

    public UserResponse changeRankUser(int userId, int rankId) {

        if(!dbContext.userRepository.existsById(userId) ||
        !dbContext.rankCustomerRepository.existsById(rankId))
            return new UserResponse(BaseResponse.ERROR_STATUS);

        dbContext.userRepository.findAll()
                .stream().map(user -> {
                    UserDTO userDTO = userMapper.toDTO(user);
                    userDTO.setRankCustomerId(rankId);

                    return dbContext.userRepository.save(userMapper.toEntity(userDTO, dbContext));
                }).collect(Collectors.toList());

        return new UserResponse(findById(userId));
    }
}
