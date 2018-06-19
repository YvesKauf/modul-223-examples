package ch.nyp.aemtli_app.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ch.nyp.aemtli_app.model.User;
import ch.nyp.aemtli_app.model.UserDuty;
import ch.nyp.aemtli_app.model.UserDutyJoin;
import ch.nyp.aemtli_app.model.Duty;

/**
 * Data access object class for transformation table "user_has_duty"
 *
 * History:
 * 15.05.2018 1.0 Severin Zahler create class
 * 16.05.2018 1.1 Severin Zahler comments
 *
 * @author Severin Zahler
 * @version 1.1
 */
@Dao
public interface UserDutyJoinDao {

    /**
     * Inserts a new connection of a user having a duty. Pass the data as UserDutyJoin
     * object, which requires the Database-ID's of the user and duty to connect.
     *
     * @param userDutyJoin  The UserDutyJoin object containing the id's of the user and duty.
     */
    @Insert
    void insert(UserDutyJoin userDutyJoin);

    @Insert
    void insertAll(List<UserDutyJoin> userDutyJoins);

    /**
     * Returns all users that belong to a given duty.
     *
     * @param dutyId  The ID of the duty.
     * @return  All users for that duty as List.
     */
    @Query(
        "SELECT * FROM User " +
        "INNER JOIN UserDutyJoin ON User.userId = UserDutyJoin.idUser " +
        "WHERE UserDutyJoin.idDuty = :dutyId")
    List<User> getUsersForDuty(final int dutyId);

    @Query(
            "SELECT User.*, Duty.* FROM UserDutyJoin INNER JOIN User ON UserDutyJoin.idUser =" +
                    " User.userId INNER JOIN Duty ON UserDutyJoin.idDuty = Duty.dutyId WHERE " +
                    "UserDutyJoin.date = :date ORDER BY UserDutyJoin.idDuty ASC")
    List<UserDuty> getDutiesByDate(String date);

    /**
     * Deletes all entries in the transformation table.
     */
    @Query("DELETE FROM UserDutyJoin")
    void deleteAll();
}
