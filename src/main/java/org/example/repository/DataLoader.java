package org.example.repository;

import org.example.contract.requests.RegisterRequest;
import org.example.model.Role;
import org.example.services.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
    private final UserService userService;

    public DataLoader(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(userService.getUser("admin@example.com").isEmpty()){
            userService.register(new RegisterRequest("admin@example.com", "password", Role.SUPER_ADMIN));
        }
    }

}
