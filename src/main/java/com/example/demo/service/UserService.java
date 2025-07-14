
package com.example.demo.service;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
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
        return userRepository.findAll()
                .stream()
                .filter(user -> Optional.ofNullable(user.getEmail())
                        .map(email -> email.endsWith("@" + domain))
                        .orElse(false))
                .collect(Collectors.toList());
    }

    public List<Users> sortUsersBy(String field) {
        return userRepository.findAll()
                .stream()
                .sorted(getComparator(field))
                .collect(Collectors.toList());
    }

    private Comparator<Users> getComparator(String field) {
        return switch (field) {
            case "name" -> Comparator.comparing(Users::getName, Comparator.nullsLast(String::compareTo));
            case "email" -> Comparator.comparing(Users::getEmail, Comparator.nullsLast(String::compareTo));
            default -> Comparator.comparing(Users::getId, Comparator.nullsLast(Long::compareTo));
        };
    }

    public Map<String, List<Users>> groupByEmailDomain() {
        return userRepository.findAll()
                .stream()
                .filter(user -> Optional.ofNullable(user.getEmail()).isPresent())
                .collect(Collectors.groupingBy(
                        user -> extractDomain(user.getEmail()),
                        Collectors.toList()
                ));
    }

    private String extractDomain(String email) {
        return Optional.ofNullable(email)
                .map(e -> e.split("@"))
                .filter(parts -> parts.length == 2)
                .map(parts -> parts[1])
                .orElse("unknown");
    }

    public List<Users> searchUsersByName(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return userRepository.findAll()
                .stream()
                .filter(user -> Optional.ofNullable(user.getName())
                        .map(name -> name.toLowerCase().contains(lowerKeyword))
                        .orElse(false))
                .collect(Collectors.toList());
    }
}
