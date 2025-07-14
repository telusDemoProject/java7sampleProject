
package com.example.demo.service;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(Users users) {
        userRepository.save(users);
    }

    public List<Users> getUsersByDomain(String domain) {
        return userRepository.findAll().stream()
                .filter(user -> Optional.ofNullable(user.getEmail())
                        .map(email -> email.endsWith("@" + domain))
                        .orElse(false))
                .collect(Collectors.toList());
    }

    public List<Users> sortUsersBy(String field) {
        return userRepository.findAll().stream()
                .sorted(switch (field) {
                    case "name" -> Comparator.comparing(Users::getName);
                    case "email" -> Comparator.comparing(Users::getEmail);
                    default -> Comparator.comparing(Users::getId);
                })
                .collect(Collectors.toList());
    }

    public Map<String, List<Users>> groupByEmailDomain() {
        return userRepository.findAll().stream()
                .filter(user -> user.getEmail() != null && user.getEmail().contains("@"))
                .collect(Collectors.groupingBy(
                        user -> user.getEmail().split("@")[1]
                ));
    }

    public List<Users> searchUsersByName(String keyword) {
        return userRepository.findByNameContainingIgnoreCase(keyword);
    }
}
