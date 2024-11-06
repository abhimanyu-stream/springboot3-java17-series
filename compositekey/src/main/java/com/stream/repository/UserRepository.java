package com.stream.repository;

import com.stream.model.User;
import com.stream.model.UserPrimarykey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, UserPrimarykey>{



    Optional<User> findByIdAndEmail(String id,String email);
}