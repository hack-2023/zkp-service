package com.hack2023.zkp_service.adapter.inbound.register;

import com.hack2023.generated.jooq.model.tables.pojos.User;
import com.hack2023.zkp_service.domain.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserEndpoint {
    private UserService userService;

    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public List<User> getUsers(){
        return this.userService.getUsers();
    }

    @PostMapping("/user")
    public void register(@RequestBody User user){
        this.userService.addUser(user);
    }
}
