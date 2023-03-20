package com.hack2023.zkp_service.port;

import com.hack2023.generated.jooq.model.tables.pojos.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    List<User> getUsers();

    Optional<User> findUserByEmail(String email);
    void insertUser(User user);
}
