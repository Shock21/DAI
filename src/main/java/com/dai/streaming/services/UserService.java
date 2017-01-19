package com.dai.streaming.services;

import com.dai.streaming.entity.User;

/**
 * Created by Tiberiu on 1/19/2017.
 */
public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
