package com.stream.sortbydob;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {


    private String name;
    private LocalDate dob;
}