package vn.phongandfriends.motorwashing.persistence;

import java.io.Serializable;

public class Account implements Serializable {
    private String phone;
    private String password;
    private String fullname;
    private String email;
    private String birhtDate;
    private String carType;

    public Account() {
    }

    public Account(String phone, String password, String fullname, String email, String birhtDate, String carType) {
        this.phone = phone;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.birhtDate = birhtDate;
        this.carType = carType;
    }

    public String getPhone() {
        return phone;
    }

    public Account setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Account setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFullname() {
        return fullname;
    }

    public Account setFullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Account setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getBirhtDate() {
        return birhtDate;
    }

    public Account setBirhtDate(String birhtDate) {
        this.birhtDate = birhtDate;
        return this;
    }

    public String getCarType() {
        return carType;
    }

    public Account setCarType(String carType) {
        this.carType = carType;
        return this;
    }
}
