package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotNull(message = "Name should not be null")
    @Size(min = 3, max = 20, message = "Provide a real name")
    @Pattern(regexp = "[a-z-A-Z]*", message = "Name has invalid characters")
    private String name;

    @NotNull(message = "Age should not be null")
    private Integer age;
}
