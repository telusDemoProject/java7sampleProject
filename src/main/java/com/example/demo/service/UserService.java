
package com.example.demo.service;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;

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
                .filter(user -> user.getEmail() != null && user.getEmail().endsWith("@" + domain))
                .collect(toList());
    }

    public List<Users> sortUsersBy(String field) {
        Comparator<Users> comparator = "name".equals(field) 
                ? Comparator.comparing(Users::getName)
                : "email".equals(field) 
                    ? Comparator.comparing(Users::getEmail)
                    : (u1, u2) -> 0;
        
        return userRepository.findAll().stream()
                .sorted(comparator)
                .collect(toList());
    }

    public Map<String, List<Users>> groupByEmailDomain() {
        return userRepository.findAll().stream()
                .filter(user -> user.getEmail() != null && user.getEmail().contains("@"))
                .collect(groupingBy(user -> user.getEmail().split("@")[1]));
    }

    public List<Users> searchUsersByName(String keyword) {
        return userRepository.findAll().stream()
                .filter(user -> user.getName() != null && 
                        user.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(toList());
    }
}
