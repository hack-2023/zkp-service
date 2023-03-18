package com.hack2023.zkp_service.domain;

import com.hack2023.generated.jooq.model.tables.pojos.User;
import com.hack2023.zkp_service.port.UserRepositoryPort;

import java.util.List;

public class UserService {
    private UserRepositoryPort userRepository;

    public UserService(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public void addUser(User user) {
        userRepository.insertUser(user);
    }
}