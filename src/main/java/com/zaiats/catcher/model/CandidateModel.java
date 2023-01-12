package com.zaiats.catcher.model;

import com.zaiats.catcher.repository.entity.Candidate;

import java.util.Objects;

public class CandidateModel {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    public CandidateModel() {
    }

    public CandidateModel(Integer id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public static CandidateModel fromEntity(Candidate candidate) {
        return new CandidateModel(candidate.getId(),
                candidate.getFirstName(),
                candidate.getLastName(),
                candidate.getEmail());
    }

    public static Candidate toEntity(CandidateModel candidateModel) {
        Candidate candidate = new Candidate();
        candidate.setFirstName(candidateModel.getFirstName());
        candidate.setLastName(candidateModel.getLastName());
        candidate.setEmail(candidateModel.getEmail());
        return candidate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CandidateModel candidateModel)) return false;
        return firstName.equals(candidateModel.firstName)
                && lastName.equals(candidateModel.lastName)
                && email.equals(candidateModel.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
