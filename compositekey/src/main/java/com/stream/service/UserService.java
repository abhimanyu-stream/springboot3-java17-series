package com.stream.service;

import com.stream.model.User;
import com.stream.model.UserPrimarykey;
import com.stream.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }




    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }


    public Optional<User> findByIdAndEmail(String id,String email) {
        return userRepository.findByIdAndEmail(id,email);
    }


}
