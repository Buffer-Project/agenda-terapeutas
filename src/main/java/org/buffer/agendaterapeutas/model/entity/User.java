package org.buffer.agendaterapeutas.model.entity;

import jakarta.persistence.*;
import org.buffer.agendaterapeutas.model.bo.UserBO;
import org.buffer.agendaterapeutas.model.vo.UserVO;

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    private int birthDate;
    private boolean active;

    public User() {

    }

    public User(String username, String password, String firstName, String lastName, String email, String phone, String gender, int birthDate, boolean active) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.birthDate = birthDate;
        this.active = active;
    }

    public User(UserBO userBO) {
        this.id = userBO.getId();
        this.username = userBO.getUsername();
        this.password = userBO.getPassword();
        this.firstName = userBO.getFirstName();
        this.lastName = userBO.getLastName();
        this.email = userBO.getEmail();
        this.phone = userBO.getPhone();
        this.gender = userBO.getGender();
        this.birthDate = userBO.getBirthDate();
        this.active = userBO.isActive();
    }

    public User(UserVO userVO) {
        this.id = userVO.getId();
        this.username = userVO.getUsername();
        this.password = userVO.getPassword();
        this.firstName = userVO.getFirstName();
        this.lastName = userVO.getLastName();
        this.email = userVO.getEmail();
        this.phone = userVO.getPhone();
        this.gender = userVO.getGender();
        this.birthDate = userVO.getBirthDate();
        this.active = userVO.isActive();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(int birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
