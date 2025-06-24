
package com.example.demo.controller;

import com.example.demo.service.UserService;
import com.example.demo.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<String> addUser(@Valid @RequestBody Users users) {
        return Optional.ofNullable(users)
                .map(u -> {
                    userService.addUser(u);
                    return ResponseEntity.ok("User added successfully");
                })
                .orElse(ResponseEntity.badRequest().body("Invalid user data"));
    }

    @GetMapping("/by-domain")
    public List<Users> getUsersByDomain(@RequestParam String domain) {
        return userService.getUsersByDomain(domain);
    }

    @GetMapping("/sorted")
    public List<Users> getSortedUsers(@RequestParam(defaultValue = "name") String field) {
        return userService.sortUsersBy(field);
    }

    @GetMapping("/grouped-by-domain")
    public Map<String, List<Users>> getGroupedByDomain() {
        return userService.groupByEmailDomain();
    }

    @GetMapping("/search")
    public List<Users> searchByName(@RequestParam String keyword) {
        return userService.searchUsersByName(keyword);
    }
}
