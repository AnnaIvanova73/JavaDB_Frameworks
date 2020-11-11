package softuni.springintrousers.services.impl;

import org.springframework.stereotype.Service;
import softuni.springintrousers.repo.UserRepository;
import softuni.springintrousers.services.UserService;


import java.io.IOException;

import static softuni.springintrousers.constants.Path.PATH_USERS;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void seedUser() throws IOException {
    }
}
