package com.zaiats.catcher.repository.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String currentPosition;

    public Candidate() {
    }

    public Candidate(Long id, String firstName, String lastName, String email, String currentPosition) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.currentPosition = currentPosition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Candidate candidate)) return false;
        return Objects.equals(id, candidate.id)
                && Objects.equals(firstName, candidate.firstName)
                && Objects.equals(lastName, candidate.lastName)
                && Objects.equals(email, candidate.email)
                && Objects.equals(currentPosition, candidate.currentPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, currentPosition);
    }

}
