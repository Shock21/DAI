package com.dai.streaming.repository;

import com.dai.streaming.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Tiberiu on 1/19/2017.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
