package ch.nyp.pendenzen_app.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ch.nyp.pendenzen_app.util.PasswordUtil;

/**
 * Created by robin on 25.01.2018
 *
 * Database Class, needed for login or
 * register
 *
 * 1. constructor for email & password -> login
 * 2. constructor for email, password, firstname & lastname -> register (profilpicture is optional) -> setter
 *
 */
@Entity
public class User {

    @PrimaryKey (autoGenerate = true)
    private int userId;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private String profilpicture;

    private boolean isApprentice;

    public User() {}

    @Ignore
    public User(String email, String password){
        this.email = email;
        this.password = PasswordUtil.hashPassword(password);
    }

    @Ignore
    public User(String email, String password, String firstname, String lastname) {
        this.email = email;
        this.password = PasswordUtil.hashPassword(password);
        this.firstname = firstname;
        this.lastname = lastname;
    }

    @Ignore
    public User(int userId, String email, String password, String firstname, String lastname, boolean
            isApprentice) {
        this(email, password, firstname, lastname);
        this.userId = userId;
        this.isApprentice = isApprentice;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getProfilpicture() {
        return profilpicture;
    }

    public void setProfilpicture(String profilpicture) {
        this.profilpicture = profilpicture;
    }

    public boolean isApprentice() {
        return isApprentice;
    }

    public void setApprentice(boolean apprentice) {
        isApprentice = apprentice;
    }
}
