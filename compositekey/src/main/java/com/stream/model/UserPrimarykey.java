package com.stream.model;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
public class UserPrimarykey implements Serializable {

    private String id;
    private String email;

    public UserPrimarykey() {}

    public UserPrimarykey(String id, String email) {
        this.id = id;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPrimarykey that = (UserPrimarykey) o;

        if (! Objects.equals(id, that.id)) return false;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserPrimarykey{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
