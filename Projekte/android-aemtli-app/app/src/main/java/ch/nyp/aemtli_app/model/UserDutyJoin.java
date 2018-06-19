package ch.nyp.aemtli_app.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Model class for transformation table "user_has_duty".
 *
 * History:
 * 15.05.2018 1.0 Severin Zahler create class
 * 16.05.2018 1.1 Severin Zahler comments
 *
 * @author Severin Zahler
 * @version 1.1
 */
@Entity(
    primaryKeys = { "idUser", "idDuty" },
    foreignKeys = {
        @ForeignKey(
            onDelete = CASCADE,
            entity = User.class,
            parentColumns = "userId",
            childColumns = "idUser"),
        @ForeignKey(
            onDelete = CASCADE,
            entity = Duty.class,
            parentColumns = "dutyId",
            childColumns = "idDuty")
    })
public class UserDutyJoin {

    private int idUser;

    private int idDuty;

    private String date;

    public UserDutyJoin(int idUser, int idDuty, String date) {
        this.idUser = idUser;
        this.idDuty = idDuty;
        this.date = date;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdDuty() {
        return idDuty;
    }

    public void setIdDuty(int idDuty) {
        this.idDuty = idDuty;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
