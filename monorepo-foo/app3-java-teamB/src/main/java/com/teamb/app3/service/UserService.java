package com.teamb.app3.service;

import com.teamb.app3.model.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public UserService() {
        users.put(1L, new User(1L, "admin", "admin@example.com", "Admin User"));
        users.put(2L, new User(2L, "user1", "user1@example.com", "User One"));
        idCounter.set(3L);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public User createUser(User user) {
        Long id = idCounter.getAndIncrement();
        user.setId(id);
        users.put(id, user);
        return user;
    }

    public Optional<User> updateUser(Long id, User updatedUser) {
        if (!users.containsKey(id)) {
            return Optional.empty();
        }
        updatedUser.setId(id);
        users.put(id, updatedUser);
        return Optional.of(updatedUser);
    }

    public boolean deleteUser(Long id) {
        return users.remove(id) != null;
    }
}
