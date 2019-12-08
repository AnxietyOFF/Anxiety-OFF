package br.com.opm.anxietyoff.model;

import android.net.Uri;

public class User {

    private String name, email, password;
    private Uri photoUri;

    public User() {
    }

    public User(String name, String email, String password, Uri photoUri) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.photoUri=photoUri;
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

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }
}
