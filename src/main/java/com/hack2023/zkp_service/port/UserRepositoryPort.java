package com.hack2023.zkp_service.port;

import com.hack2023.generated.jooq.model.tables.pojos.User;

import java.util.List;

public interface UserRepositoryPort {
    List<User> getUsers();
    void insertUser(User user);
}
