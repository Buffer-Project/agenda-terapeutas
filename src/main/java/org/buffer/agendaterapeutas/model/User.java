package org.buffer.agendaterapeutas.model;

import jakarta.persistence.*;

@Entity (name = "users")
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    private int birthDate;

    /*attribute for a soft delete*/
    private boolean active = true;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {this.username = username; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = firstName;
    }

    public String getLastName() { return lastName; }

    public void setLastName(String name) { this.lastName = name; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public int getBirthDate() { return birthDate; }

    public void setBirthDate(int birthDate) { this.birthDate = birthDate; }

    public boolean isActive() { return active; }

    public void setActive(boolean active) { this.active = active; }

}
