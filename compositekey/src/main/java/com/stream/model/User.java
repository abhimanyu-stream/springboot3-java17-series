package com.stream.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Setter
@Getter
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id","email"})
})
@IdClass(UserPrimarykey.class)
public class User {

    @Id
    private String id;
    private String userType;
    @Id
    private String email;
    private String firstName;
    private String lastName;

    private Boolean isActive;


    public User(){}
    public User(String userType, String email, String firstName, String lastName, Boolean isActive) {
        this.userType = userType;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (isActive != user.isActive) return false;
        if (! Objects.equals(id, user.id)) return false;
        if (! Objects.equals(userType, user.userType)) return false;
        if (! Objects.equals(email, user.email)) return false;
        if (! Objects.equals(firstName, user.firstName)) return false;
        return Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = (31 * result) + (isActive ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userType='" + userType + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
