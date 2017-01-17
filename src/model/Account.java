package model;

import helpers.DateHelper;

import java.util.Date;

/**
 * Created by Giota on 31/12/2016.
 */
public class Account {
    private String phone;
    private String email;
    private String username;
    private String password;
    private Date registrationDate;
    private Subscriber owner;

    public Account() {
    }

    public Account(String phone, String email, String username, String password, Date registrationDate, Subscriber owner) {
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.password = password;
        this.registrationDate = registrationDate;
        this.owner = owner;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Subscriber getOwner() {
        return owner;
    }

    public void setOwner(Subscriber owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return phone + " " + email + " " + username + " " + password + " " + DateHelper.dateFormat(registrationDate);
    }
}
