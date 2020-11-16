package com.example.lab.services.impl;

import com.example.lab.domain.dtos.UserLoginDto;
import com.example.lab.domain.dtos.seed.UserSeedDto;
import com.example.lab.domain.entities.User;
import com.example.lab.domain.repositories.UserRepository;
import com.example.lab.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(ModelMapper modelMapper,UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;

    }

    @Override
    public void save(UserSeedDto userSeedDto) {
        this.userRepository.save(this.modelMapper.map(userSeedDto, User.class));

    }

    @Override
    public boolean login(UserLoginDto userLoginDto) {
        User user = this.userRepository.findByUserNameAndPassword(userLoginDto.getUsername(),
                userLoginDto.getPassword()).orElse(null);

        return user != null;
    }
}
