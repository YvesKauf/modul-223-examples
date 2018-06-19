package ch.nyp.aemtli_app.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ch.nyp.aemtli_app.model.User;

/**
 * Data-Access-Objekt
 *
 * @author Miriam Streit <miriam.streit@nyp.ch>
 */
@Dao
public interface UserDao {
    /**
     * holt alle Benutzer
     *
     * @author Miriam Streit <miriam.streit@nyp.ch>
     * @return List<Benutzer> Liste aller Benutzer
     */
    @Query("SELECT * FROM User")
	List<User> getAll();

    /**
     * löscht alle Benutzer
     *
     * @author Miriam Streit <miriam.streit@nyp.ch>
     */
    @Query("DELETE FROM User")
    void deleteAll();

    /**
     * holt alle Lernenden
     *
     * @author Miriam Streit <miriam.streit@nyp.ch>
     * @return List<Benutzer> Liste aller Lernenden
     */
    @Query("SELECT * FROM User WHERE isApprentice = 1")
    List<User> getAllApprentices();

    /**
     * holt eine Person anhand ihrer E-Mail-Adresse
     *
     * @author Miriam Streit <miriam.streit@nyp.ch>
     * @param email E-Mail-Adresse des gewünschten Accounts
     * @return Benutzer Benutzerobjekt
     */
    @Query("SELECT * FROM User WHERE email = :email LIMIT 1")
    User getByEmail(String email);

    /**
     * holt eine Person anhand ihrer ID
     *
     * @author Miriam Streit <miriam.streit@nyp.ch>
     * @param userId ID des gewünschten Accounts
     * @return Benutzer Benutzerobjekt
     */
    @Query("SELECT * FROM User WHERE userId = :userId LIMIT 1")
    User getById(int userId);

    /**
     * fügt eine Liste in die Tabelle ien
     *
     * @author Miriam Streit <miriam.streit@nyp.ch>
     * @param users
     */
    @Insert
    void insertAll(List<User> users);

    /**
     * Updates the fields of an existing entry in the database. If no entry
     * with the same primary key as the object passed is found, no changes are
     * applied.
     *
     * @param users  One or more user objects with a set primary key, as varargs
     */
    @Update
    void update(User... users);
}
