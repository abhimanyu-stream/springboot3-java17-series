package com.stream.authentication.service;

import com.stream.authentication.model.Role;
import com.stream.authentication.model.RoleEnum;
import com.stream.authentication.model.User;
import com.stream.authentication.repository.RoleRepository;
import com.stream.authentication.repository.UserRepository;
import com.stream.authentication.repository.UserRoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserAndAssignRolesToDefaultUserSpringJPA {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserAndAssignRolesToDefaultUserSpringJPA(UserRepository userRepository, RoleRepository roleRepository,
                                                    UserRoleRepository userRoleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Method to delete user-role associations and roles from the database
    @Transactional
    public void deleteRolesAndAssociations() {
        String[] roleNames = {"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"};
        for (String roleName : roleNames) {
            Role role = roleRepository.findByName(RoleEnum.valueOf(roleName)).orElse(null);
            if (role != null) {
                // Delete user-role associations
                userRoleRepository.deleteByRoleId(role.getId());
                roleRepository.deleteById(role.getId());
                System.out.println("Deleted associations for role: " + roleName);
            } else {
                System.out.println("Role " + roleName + " not found.");
            }
        }
    }

    // Method to create default users and assign roles
    @Transactional
    public void createDefaultUserAndAssignRoles() {
        String hashedPassword = passwordEncoder.encode("securepassword123");

        // User details
        User user1 = getOrCreateUser("stream.abhimanyu@yahoo.com", "8789929244", "Abhimanyu Kumar", hashedPassword);
        User user2 = getOrCreateUser("elijabeth.forest@yahoo.com", "7992306009", "Elizabeth Forest", hashedPassword);

        // Get roles
        Role roleAdmin = roleRepository.findByName(RoleEnum.ROLE_ADMIN).orElse(null);
        Role roleModerator = roleRepository.findByName(RoleEnum.ROLE_MODERATOR).orElse(null);

        // Assign roles to users
        assignRoleToUser(user1, roleAdmin, "ROLE_ADMIN");
        assignRoleToUser(user2, roleModerator, "ROLE_MODERATOR");
    }

    // Helper method to create or fetch user based on email
    @Transactional
    public User getOrCreateUser(String email, String phone, String username, String hashedPassword) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            User newUser = new User(username, email, hashedPassword);
            newUser.setPhone(phone);
            return userRepository.save(newUser);
        } else {
            System.out.println("User with email " + email + " already exists.");
            return existingUser;
        }
    }

    // Helper method to assign role to user
    @Transactional
    public void assignRoleToUser(User user, Role role, String roleName) {
        if (role != null && user.getRoles() != null && !user.getRoles().contains(role)) {
            user.getRoles().add(role);
            userRepository.save(user);
            System.out.println("Role '" + roleName + "' assigned to user '" + user.getUsername() + "'.");
        }
    }
}

