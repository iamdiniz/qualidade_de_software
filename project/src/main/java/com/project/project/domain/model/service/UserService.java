package com.project.project.domain.model.service;
import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.project.project.domain.model.User;

@Service
public class UserService {
    List<User> users = new ArrayList<>();

    public List<User> create(User user) {
        if (users.contains(user))
            throw new RuntimeException();
        users.add(user);
        return users;
    }
    
}
