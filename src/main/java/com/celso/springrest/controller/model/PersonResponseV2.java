package com.celso.springrest.controller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "address", "firstName", "lastName", "gender", "enabled"})
public class PersonResponseV2 {

    private Long id;
    @JsonProperty("Nome")
    private String firstName;
    @JsonProperty("Sobrenome")
    private String lastName;
    @JsonProperty("Endereço")
    private String address;
    private String gender;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-3")
    private String birthDay;

    public PersonResponseV2() {
    }

    public PersonResponseV2(Long id, String firstName, String lastName, String address, String gender, String birthDay) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.gender = gender;
        this.birthDay = birthDay;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }
}