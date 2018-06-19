package ch.nyp.aemtli_app.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ch.nyp.aemtli_app.model.Duty;

/**
 * Data Access Object class for duties.
 *
 * History:
 * 15.05.2018 1.0 Severin Zahler create class
 * 16.05.2018 1.1 Severin Zahler comments
 *
 * @author Severin Zahler
 * @version 1.1
 */
@Dao
public interface DutyDao {

    /**
     * Inserts one or more new duties into the database.
     *
     * @param duties  One or more Duty objects as Varargs
     */
    @Insert
    void insert(Duty... duties);

    /**
     * Inserts a collection of duties into the database.
     *
     * @param duties  A List of duties.
     */
    @Insert
    void insertAll(List<Duty> duties);

    /**
     * Updates the fields of an existing entry in the database. If no entry
     * with the same primary key as the object passed is found, no changes are
     * applied.
     *
     * @param duties  One or more duty objects with a set primary key, as varargs
     */
    @Update
    void update(Duty... duties);

    /**
     * Fetches all duties currently saved in the database.
     *
     * @return  All duties as List.
     */
    @Query("SELECT * FROM Duty")
    List<Duty> getAllDuties();

    /**
     * Deletes all duties from the database. Entries in the transformation table are deleted
     * as well.
     */
    @Query("DELETE FROM Duty")
    void deleteAll();
}
