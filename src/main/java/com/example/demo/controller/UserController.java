
package com.example.demo.controller;

import com.example.demo.service.UserService;
import com.example.demo.model.Users;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<String> addUser(@Valid @RequestBody Users users) {
        userService.addUser(users);
        return ResponseEntity.ok("Users added");
    }

    @GetMapping("/by-domain")
    public ResponseEntity<List<Users>> getUsersByDomain(@RequestParam String domain) {
        return ResponseEntity.ok(userService.getUsersByDomain(domain));
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Users>> getSortedUsers(@RequestParam(defaultValue = "name") String field) {
        return ResponseEntity.ok(userService.sortUsersBy(field));
    }

    @GetMapping("/grouped-by-domain")
    public ResponseEntity<Map<String, List<Users>>> getGroupedByDomain() {
        return ResponseEntity.ok(userService.groupByEmailDomain());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Users>> searchByName(@RequestParam String keyword) {
        return ResponseEntity.ok(userService.searchUsersByName(keyword));
    }
}
