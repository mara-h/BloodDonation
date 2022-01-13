package com.blooddonation.model;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;

public class User {
    @Id
    private UUID id = UUID.randomUUID();
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<UUID> questionnairesIds;
    private List<UUID> appointmentIds;
    private boolean isMedic;

    @Enumerated(EnumType.STRING)
    private Enums.Sex sex; // if null -> it's general

    @Enumerated(EnumType.STRING)
    private Enums.BloodGroups bloodGroup;

    private int age;
    private String cnp;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, Enums.Sex sex, Enums.BloodGroups bloodGroup, int age, String cnp) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.bloodGroup = bloodGroup;
        this.age = age;
        this.cnp = cnp;
        this.isMedic = false;
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

    public void setId(UUID id) {
        this.id = id;
    }

    public Enums.BloodGroups getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(Enums.BloodGroups bloodGroup) {
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

    public List<UUID> getQuestionnairesIds() {
        return questionnairesIds;
    }

    public void setQuestionnairesIds(List<UUID> questionnairesIds) {
        this.questionnairesIds = questionnairesIds;
    }

    public void setSex(Enums.Sex sex) {
        this.sex = sex;
    }

    public Enums.Sex getSex() {
        return sex;
    }

    public List<UUID> getAppointmentIds() {
        return appointmentIds;
    }

    public void setAppointmentIds(List<UUID> appointmentIds) {
        this.appointmentIds = appointmentIds;
    }

    public boolean isMedic() {
        return isMedic;
    }

    public void setMedic(boolean medic) {
        isMedic = medic;
    }
}
