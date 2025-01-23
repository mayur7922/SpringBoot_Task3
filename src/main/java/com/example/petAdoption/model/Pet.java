package com.example.petAdoption.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "pets") // Optional: Specify table name explicitly
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String breed;
    private Long age;

    // No-argument constructor
    public Pet() {
    }

    // All-argument constructor (optional, for convenience)
    public Pet(String type, String breed, Long age) {
        this.type = type;
        this.breed = breed;
        this.age = age;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

}