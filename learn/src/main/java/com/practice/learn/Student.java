package com.practice.learn;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentName;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE })
    @JoinTable(
            name = "student_course", // join table name
            joinColumns = @JoinColumn(name = "student_id"),// a column with name student_id will be created in student table
            inverseJoinColumns = @JoinColumn(name = "course_id")// a column with name course_id will be created in student table
    )

    private Set<Course> courses = new HashSet<>();// this variable is mentioned in Course class with mappedBy

    // Getters and setters omitted for brevity


}
