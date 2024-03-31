package com.example.movie.services;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.UserDTO;
import com.example.movie.models.entities.RoleDAO;
import com.example.movie.models.entities.UserDAO;
import com.example.movie.models.impl.UserDetailsImpl;
import com.example.movie.models.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServicesImpl implements UserDetailsService {

    @Autowired
    DbContext dbContext;

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetailsImpl userDetails = new UserDetailsImpl();

        UserDAO dao = dbContext.userRepository.findByUsername(username);
//        UserDTO dto = userMapper.toDTO(dao);

        userDetails.setUsername(dao.getUsername());
        userDetails.setPassword(dao.getPassword());
        userDetails.setRole(dao.getRole());

        return userDetails;
    }
}
