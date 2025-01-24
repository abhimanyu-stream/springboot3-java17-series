package com.stream.authentication.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@AllArgsConstructor
@Builder(toBuilder = true)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "role", schema = "authentication_service")
public class Role implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7092795090689266661L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "rolename", unique = true, length = 100)
	private RoleEnum name;

	@ManyToMany(mappedBy = "roles")
	private Set<User> users = new HashSet<>();



	public Role() {
		//this.users = new HashSet<>();
	}
	public Role(RoleEnum name) {
		super();
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public RoleEnum getName() {
		return name;
	}
	public void setName(RoleEnum name) {
		this.name = name;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

}