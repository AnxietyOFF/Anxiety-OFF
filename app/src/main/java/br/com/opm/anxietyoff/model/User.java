package br.com.opm.anxietyoff.model;

import android.net.Uri;

public class User {

    private String name, email, password, phone;
    private Uri photoUri;

    public User() {
    }

    public User(String name, String email, String password, String phone, Uri photoUri) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.photoUri=photoUri;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
