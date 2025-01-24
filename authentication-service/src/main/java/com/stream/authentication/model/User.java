package com.stream.authentication.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.experimental.Accessors;
import org.hibernate.action.internal.OrphanRemovalAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@Builder(toBuilder = true)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "user", uniqueConstraints = {
		@UniqueConstraint(columnNames = "username")}, schema = "authentication_service")
public class User extends AuditorEntity {


	private static final long serialVersionUID = 7364820073604547474L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Use UUID too and also implement in CustomUserDetails

	@Column(name = "username", nullable = false, unique = true, length = 500)
	private String username;

	@Column(name = "email", nullable = false, unique = true, length = 500)
	private String email;

	@Column(name = "password", nullable = false, length = 500)
	private String password;

	@Column(name = "first_name", nullable = true, length = 500)
	private String firstName;

	@Column(name = "last_name", nullable = true, length = 500)
	private String lastName;


	@Column(name = "phone", nullable = true, length = 15)
	private String phone;

	@Default
	//@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})

	@JoinTable(name = "USER_ROLES", joinColumns = {@JoinColumn(name = "USER_ID") }, inverseJoinColumns = {@JoinColumn(name = "ROLE_ID") })
    private Set<Role> roles  = new HashSet<>();


	public Set<Role> getRoles() {
		return roles;
	}



	@CreatedDate
	@Column(name = "created", nullable = true)
	private LocalDateTime created;

	@LastModifiedDate
	@Column(name = "modified", nullable = true)
	private LocalDateTime modified;
	
	public User() {}
	public User(String username, String email, String password, String firstName, String lastName) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		//this.roles = new HashSet<>(); // Initialize to avoid null
	}
	public User(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		
	}
}