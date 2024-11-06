package com.stream.controller;

import com.stream.model.User;
import com.stream.model.UserPrimarykey;
import com.stream.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }



    @PostMapping("create")
    public ResponseEntity<User> createUser(@RequestBody User user) {

        //Note:- here we can generate id value manually

        user.setId(UUID.randomUUID().toString());
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUser());
    }



    @GetMapping("/{id}/email/{email}")
    public ResponseEntity<User> findByIdAndEmail(@PathVariable String id, @PathVariable String email){
        log.info("Finding user with id: {} and email: {}",id, email);

        Optional<User> userOptional = userService.findByIdAndEmail(id,email);
        return userOptional.map(ResponseEntity :: ok).orElse(null);

    }

}
