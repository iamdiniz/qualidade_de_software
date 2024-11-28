package com.project.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.project.project.domain.model.User;
import com.project.project.domain.model.service.UserService;

public class UserServiceTest {
    private UserService userService = new UserService();

    @Test
    public void createUser() {
        User user = new User("Diniz", "diniz@gmail.com", "xazb9182");

        List<User> users = userService.create(user);

        assertEquals(1, users.size());
        assertEquals(user, users.get(0));
    }
}
