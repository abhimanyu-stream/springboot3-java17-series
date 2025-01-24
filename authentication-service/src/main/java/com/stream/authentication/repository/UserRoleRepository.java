package com.stream.authentication.repository;

import com.stream.authentication.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    void deleteByRoleId(Long roleId); // Method to delete user-role associations by role ID

}
