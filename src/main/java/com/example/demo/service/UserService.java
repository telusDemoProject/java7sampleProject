package com.example.demo.service;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(Users users) {
        userRepository.save(users);
    }

    public List<Users> getUsersByDomain(String domain) {
        List<Users> all = userRepository.findAll();
        return all.stream()
                .filter(u -> u.getEmail() != null && u.getEmail().endsWith("@" + domain))
                .collect(Collectors.toList());
    }

    public List<Users> sortUsersBy(final String field) {
        List<Users> users = userRepository.findAll();
        Comparator<Users> comparator;
        if ("name".equals(field)) {
            comparator = Comparator.comparing(Users::getName);
        } else if ("email".equals(field)) {
            comparator = Comparator.comparing(Users::getEmail);
        } else {
            comparator = (u1, u2) -> 0;
        }
        users.sort(comparator);
        return users;
    }

    public Map<String, List<Users>> groupByEmailDomain() {
        List<Users> users = userRepository.findAll();
        return users.stream()
                .filter(u -> u.getEmail() != null && u.getEmail().contains("@"))
                .collect(Collectors.groupingBy(u -> u.getEmail().split("@")[1]));
    }

    public List<Users> searchUsersByName(String keyword) {
        List<Users> users = userRepository.findAll();
        return users.stream()
                .filter(u -> u.getName() != null && u.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
