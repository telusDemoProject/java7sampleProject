
package com.example.demo.service;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Optional;

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
        return userRepository.findAll().stream()
                .filter(u -> Optional.ofNullable(u.getEmail())
                        .map(email -> email.endsWith("@" + domain)).orElse(false))
                .toList();
    }

    public List<Users> sortUsersBy(final String field) {
        List<Users> users = new ArrayList<>(userRepository.findAll());
        users.sort((u1, u2) -> {
            if ("name".equals(field)) {
                return u1.getName().compareTo(u2.getName());
            } else if ("email".equals(field)) {
                return u1.getEmail().compareTo(u2.getEmail());
            }
            return 0;
        });
        return users;
    }

    public Map<String, List<Users>> groupByEmailDomain() {
        return userRepository.findAll().stream()
                .filter(u -> Optional.ofNullable(u.getEmail())
                        .map(email -> email.contains("@")).orElse(false))
                .collect(Collectors.groupingBy(u -> u.getEmail().split("@")[1]));
    }

    public List<Users> searchUsersByName(String keyword) {
        return userRepository.findAll().stream()
                .filter(u -> Optional.ofNullable(u.getName())
                        .map(name -> name.toLowerCase().contains(keyword.toLowerCase())).orElse(false))
                .toList();
    }
}
