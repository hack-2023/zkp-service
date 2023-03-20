package com.hack2023.zkp_service.adapter.outbound.user;

import com.hack2023.generated.jooq.model.Tables;
import com.hack2023.zkp_service.port.UserRepositoryPort;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.hack2023.generated.jooq.model.tables.pojos.User;

import java.util.List;
import java.util.Optional;

import static com.hack2023.generated.jooq.model.Tables.USER;

@Repository
public class UserRepository implements UserRepositoryPort {
    DSLContext context;

    public UserRepository(DSLContext context) {
        this.context = context;
    }

    public List<User> getUsers(){
        return context
                .selectFrom(USER)
                .fetchInto(User.class);
    }

    public Optional<User> findUserByEmail(String email){
        return context
                .selectFrom(USER)
                .where(USER.EMAIL.eq(email))
                .fetchOptionalInto(User.class);
    }

    public void insertUser(User user){
        context
                .insertInto(USER, USER.EMAIL, USER.SOMEHASH)
                .values(user.getEmail(), user.getSomehash())
                .execute();
    }
}
