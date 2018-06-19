package ch.nyp.aemtli_app.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Model class for duties
 *
 * History:
 * 14.05.2018 1.0 Céline Späti add class
 * 15.05.2018 1.1 Severin Zahler moved class and converted to Room Entity
 * 16.05.2018 1.2 Severin Zahler comments
 * 22.05.2018 1.3 Severin Zahler date field as Date
 *
 * @author Céline Späti, Severin Zahler
 * @version 1.2
 */
@Entity
public class Duty {
    @PrimaryKey(autoGenerate = true)
    private int dutyId;

    private String name;

    @Ignore
    private List<User> users = new ArrayList<>();

    public Duty() {}

    @Ignore
    public Duty(String name) {
        this.name = name;
    }

    @Ignore
    public Duty(int dutyId, String name){
        this.dutyId = dutyId;
        this.name = name;
    }

    //Getters and Setters
    public int getDutyId() {
        return dutyId;
    }

    public void setDutyId(int dutyId) {
        this.dutyId = dutyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }
}
