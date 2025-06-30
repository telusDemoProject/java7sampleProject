
package com.example.demo.service;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

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
                .filter(user -> user.getEmail() != null && user.getEmail().endsWith("@" + domain))
                .collect(Collectors.toList());
    }

    public List<Users> sortUsersBy(String field) {
        var comparator = "email".equalsIgnoreCase(field) 
            ? Comparator.comparing(Users::getEmail, Comparator.nullsLast(String::compareTo))
            : Comparator.comparing(Users::getName, Comparator.nullsLast(String::compareTo));
        
        return userRepository.findAll().stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public Map<String, List<Users>> groupByEmailDomain() {
        return userRepository.findAll().stream()
                .filter(user -> user.getEmail() != null && user.getEmail().contains("@"))
                .collect(Collectors.groupingBy(user -> {
                    var parts = user.getEmail().split("@");
                    return parts.length == 2 ? parts[1] : "unknown";
                }));
    }

    public List<Users> searchUsersByName(String keyword) {
        var lowerKeyword = keyword.toLowerCase();
        return userRepository.findAll().stream()
                .filter(user -> user.getName() != null && 
                               user.getName().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }
}
