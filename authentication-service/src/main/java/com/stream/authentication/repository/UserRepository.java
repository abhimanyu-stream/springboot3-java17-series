package com.stream.authentication.repository;


import com.stream.authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);
    User findByUsername(String username);
    //
    //Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	User findByUsernameAndPassword(String username, String password);

}
