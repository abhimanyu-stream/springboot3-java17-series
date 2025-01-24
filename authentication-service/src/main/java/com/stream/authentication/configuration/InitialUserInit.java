package com.stream.authentication.configuration;

import com.stream.authentication.model.Role;
import com.stream.authentication.model.RoleEnum;
import com.stream.authentication.model.User;
import com.stream.authentication.repository.RoleRepository;
import com.stream.authentication.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InitialUserInit {


        private UserRepository userRepository;
        private RoleRepository roleRepository;

        @Autowired
        public InitialUserInit(UserRepository userRepository, RoleRepository roleRepository) {
            this.userRepository = userRepository;
            this.roleRepository = roleRepository;
        }

        //@PostConstruct
        public void createDefaultUserAndAssignRoles() {
            if (!userRepository.existsByEmail("stream.abhimanyu@yahoo.com")) {


                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String hashedPassword = encoder.encode("securepassword123");

                // Create user
                User admin = new User()
                        .setUsername("Abhimanyu Kumar")
                        .setEmail("stream.abhimanyu@yahoo.com")
                        .setPassword(hashedPassword) // Remember to hash this password
                        .setFirstName("Abhimanyu")
                        .setLastName("Kumar");



                User superAdmin = new User()
                        .setUsername("Elizabeth")
                        .setEmail("elijabeth.uk@yahoo.com")
                        .setPassword(hashedPassword) // Remember to hash this password
                        .setFirstName("Elizabeth")
                        .setLastName("Forest");




                // Fetch roles
                Optional<Role> adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN);
                Optional<Role> moderatorRole = roleRepository.findByName(RoleEnum.ROLE_MODERATOR);

                if(adminRole.isPresent()){
                    // Assign roles
                    admin.getRoles().add(Role.builder().name(RoleEnum.valueOf(RoleEnum.ROLE_ADMIN.name())).build());
                }
                if(moderatorRole.isPresent()){
                    // Assign roles
                    superAdmin.getRoles().add(Role.builder().name(RoleEnum.valueOf(RoleEnum.ROLE_MODERATOR.name())).build());
                }

                // Save user
                //userRepository.save(admin);
                //userRepository.save(superAdmin);

                //System.out.println("User 'Abhimanyu Kumar' created and assigned roles: ROLE_ADMIN.");
                //System.out.println("User 'Elizabeth UK' created and assigned roles: ROLE_MODERATOR.");
            }
        }
}


