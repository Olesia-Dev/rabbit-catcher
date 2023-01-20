package com.zaiats.catcher.model;

import com.zaiats.catcher.repository.entity.Candidate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CandidateModel {

    private Long id;

    @NotBlank(message = "{candidate.firstName.notBlank}")
    @Size(max = 64, message = "{candidate.firstName.size}")
    private String firstName;

    @NotBlank(message = "{candidate.lastName.notBlank}")
    @Size(max = 64, message = "{candidate.lastName.size}")
    private String lastName;

    @NotBlank(message = "{candidate.email.notBlank}")
    @Email(message = "{candidate.email.invalid}")
    private String email;

    @Size(max = 256, message = "{candidate.currentPosition.size}")
    private String currentPosition;

    public CandidateModel() {
    }

    public CandidateModel(Long id, String firstName, String lastName, String email, String currentPosition) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.currentPosition = currentPosition;
    }

    public static CandidateModel fromEntity(Candidate candidate) {
        return new CandidateModel(candidate.getId(),
                candidate.getFirstName(),
                candidate.getLastName(),
                candidate.getEmail(),
                candidate.getCurrentPosition());
    }

    public static Candidate toEntity(CandidateModel candidateModel) {
        Candidate candidate = new Candidate();
        candidate.setFirstName(candidateModel.getFirstName());
        candidate.setLastName(candidateModel.getLastName());
        candidate.setEmail(candidateModel.getEmail());
        candidate.setCurrentPosition(candidateModel.getCurrentPosition());
        return candidate;
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
        if (!(o instanceof CandidateModel that)) return false;
        return Objects.equals(id, that.id)
                && Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName)
                && Objects.equals(email, that.email)
                && Objects.equals(currentPosition, that.currentPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, currentPosition);
    }

    @Override
    public String toString() {
        return "CandidateModel{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", currentPosition='" + currentPosition + '\'' +
                '}';
    }

}
