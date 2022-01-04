package com.blooddonation.model;


import javax.persistence.*;
import java.util.UUID;

public class User {
    @Id
    private UUID id = UUID.randomUUID();
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String sex; //TODO change this to enum
    //TODO change this to enum
    private String bloodGroup;
    private int age;
    private String cnp;

    public User() {}

    public User(String firstName, String lastName, String email, String password, String sex, String bloodGroup, int age, String cnp) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.bloodGroup = bloodGroup;
        this.age = age;
        this.cnp = cnp;
    }

    public UUID getId() {
        return id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }
}
