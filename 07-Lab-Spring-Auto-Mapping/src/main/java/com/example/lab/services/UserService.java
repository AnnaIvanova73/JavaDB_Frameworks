package com.example.lab.services;

import com.example.lab.domain.dtos.UserLoginDto;
import com.example.lab.domain.dtos.seed.UserSeedDto;

public interface UserService {

    void save(UserSeedDto userSeedDto);
    boolean login (UserLoginDto userLoginDto);

}
