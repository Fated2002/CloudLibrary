package com.demo.service;

import com.demo.domain.User;
import org.springframework.stereotype.Service;


public interface UserService {
    public User login(User user);
}
