
package com.example.demo.service;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        return userRepository.findByEmailContaining("@" + domain);
    }

    public List<Users> sortUsersBy(String field) {
        return userRepository.findAll().stream()
                .sorted(getComparator(field))
                .collect(java.util.stream.Collectors.toList());
    }

    private java.util.Comparator<Users> getComparator(String field) {
        switch (field) {
            case "name": return java.util.Comparator.comparing(Users::getName);
            case "email": return java.util.Comparator.comparing(Users::getEmail);
            default: return (u1, u2) -> 0;
        }
    }

    public Map<String, List<Users>> groupByEmailDomain() {
        return userRepository.findAll().stream()
                .filter(u -> u.getEmail() != null && u.getEmail().contains("@"))
                .collect(java.util.stream.Collectors.groupingBy(
                        u -> u.getEmail().split("@")[1]
                ));
    }

    public List<Users> searchUsersByName(String keyword) {
        return userRepository.findByNameContainingIgnoreCase(keyword);
    }
}
