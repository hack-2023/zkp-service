package com.hack2023.zkp_service.adapter.outbound.user;

import com.hack2023.generated.jooq.model.Tables;
import com.hack2023.zkp_service.port.UserRepositoryPort;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.hack2023.generated.jooq.model.tables.pojos.User;

import java.util.List;

@Repository
public class UserRepository implements UserRepositoryPort {
    DSLContext context;

    public UserRepository(DSLContext context) {
        this.context = context;
    }

    public List<User> getUsers(){
        return context
                .selectFrom(Tables.USER)
                .fetchInto(User.class);
    }

    public void insertUser(User user){
        context
                .insertInto(Tables.USER, Tables.USER.EMAIL, Tables.USER.SOMEHASH)
                .values(user.getEmail(), user.getSomehash())
                .execute();
    }
}
