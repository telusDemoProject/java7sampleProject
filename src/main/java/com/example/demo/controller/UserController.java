
package com.example.demo.controller;

import com.example.demo.service.UserService;
import com.example.demo.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public String addUser(@Valid @RequestBody Users users) {
        return Optional.ofNullable(users)
                .map(u -> {
                    userService.addUser(u);
                    return "Users added";
                })
                .orElse("Invalid user data");
    }

    @GetMapping("/by-domain")
    public List<Users> getUsersByDomain(@RequestParam String domain) {
        return Optional.ofNullable(domain)
                .filter(d -> !d.isBlank())
                .map(userService::getUsersByDomain)
                .orElse(List.of());
    }

    @GetMapping("/sorted")
    public List<Users> getSortedUsers(@RequestParam(defaultValue = "name") String field) {
        return Optional.ofNullable(field)
                .filter(f -> !f.isBlank())
                .map(userService::sortUsersBy)
                .orElse(List.of());
    }

    @GetMapping("/grouped-by-domain")
    public Map<String, List<Users>> getGroupedByDomain() {
        return userService.groupByEmailDomain();
    }

    @GetMapping("/search")
    public List<Users> searchByName(@RequestParam String keyword) {
        return Optional.ofNullable(keyword)
                .filter(k -> !k.isBlank())
                .map(userService::searchUsersByName)
                .orElse(List.of());
    }
}
