package ch.nyp.aemtli_app.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Model class for users / apprentices.
 *
 * History:
 * 14.05.2018 1.0 Severin Zahler create class
 * 15.05.2018 1.1 Severin Zahler moved class and converted to Room Entity
 * 16.05.2018 1.2 Severin Zahler comments
 *
 * @author Severin Zahler
 * @version 1.2
 */
@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int userId;

    @NonNull
    private String email;     //The globally unique email that is used to log in (E-mail)

    private String lastName;

    private String firstName;

    private String password;

    private String profilePicturePath;

    private boolean isApprentice;

    public User(int userId, String email, String password, String
            firstName, String lastName, boolean isApprentice) {
        this.userId = userId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.isApprentice = isApprentice;
    }

    //Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setMLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public boolean getIsApprentice() {
        return isApprentice;
    }

    public void setIsApprentice(boolean isApprentice) {
        this.isApprentice = isApprentice;
    }

    public String getName() {
        return firstName + " " + lastName;
    }
}
