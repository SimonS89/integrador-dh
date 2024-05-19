package com.example.workoutWonderland.configuration.security.service;


import com.example.workoutWonderland.configuration.security.entiy.UserEntityUserDetails;
import com.example.workoutWonderland.entity.UserEntity;
import com.example.workoutWonderland.repository.IUserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserEntityUserDetailsService implements UserDetailsService {
    @Autowired
    private IUserEntityRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userEntityRepository.findByUsername(username);
        return userEntity.map(UserEntityUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
}
