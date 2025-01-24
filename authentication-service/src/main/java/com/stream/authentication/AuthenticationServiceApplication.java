package com.stream.authentication;

import com.stream.authentication.configuration.RoleRepositorySpringJDBC;
import com.stream.authentication.service.UserAndAssignRolesToDefaultUserSpringJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import org.springframework.context.event.EventListener;

@SpringBootApplication
public class AuthenticationServiceApplication {

	Logger logger = LoggerFactory.getLogger(AuthenticationServiceApplication.class);

	@Autowired
	private RoleRepositorySpringJDBC roleRepositorySpringJDBC;
	@Autowired
	private UserAndAssignRolesToDefaultUserSpringJPA userAndAssignRolesToDefaultUserSpringJPA;


	public static void main(String[] args) {

		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}

	//ApplicationStartedEvent
	//ApplicationFailedEvent
	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady(ApplicationReadyEvent event) {

		logger.info("onApplicationReady");
		// Custom setup when the application is ready

		// Step 1: Delete existing user-role associations
		userAndAssignRolesToDefaultUserSpringJPA.deleteRolesAndAssociations();

		// Step 2: Delete all roles from the database (if necessary)
		roleRepositorySpringJDBC.deleteAllRecords();

		// Step 3: Insert new roles into the database
		insertRolesIfNotExist();

		// Step 4: Create default users and assign roles
		userAndAssignRolesToDefaultUserSpringJPA.createDefaultUserAndAssignRoles();

		System.out.println("Application is ready! Let's do something awesome.");
	}

	// Helper method to insert roles if they don't exist
	private void insertRolesIfNotExist() {
		// Insert roles only if they do not already exist
		try {
			if (!roleRepositorySpringJDBC.roleExists("ROLE_ADMIN")) {
				roleRepositorySpringJDBC.insertIntoRoleAdmin();
			}
			if (!roleRepositorySpringJDBC.roleExists("ROLE_USER")) {
				roleRepositorySpringJDBC.insertIntoRoleUser();
			}
			if (!roleRepositorySpringJDBC.roleExists("ROLE_MODERATOR")) {
				roleRepositorySpringJDBC.insertIntoRoleModerator();
			}
		} catch (Exception e) {
			System.err.println("Error inserting roles: " + e.getMessage());
		}
	}



}
