package ch.nyp.pendenzen_app.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ch.nyp.pendenzen_app.models.Pendenz;

/** Alle SQL-Abfragen an die Datenbank siend hier in dem Data Access Object eingespeichert.
 *
 * @author Jens Scheidmann
 */

@Dao
public interface PendenzDao {

    //Die Abfrage um alle Daten aus der Tabelle zu bekommen
    @Query("SELECT * FROM pendenz WHERE idUser =:userId")
	List<Pendenz> getAllByUserId(int userId);

    //Die Abfrage um eine bestimmte Anzahl Daten in die DB einzuspeichern
    @Insert
    void insertAll(List<Pendenz> pendenzen);

    //Die Abfrage um einen Eintrag in die DB einzuspeichern
    @Insert
    void insert(Pendenz pendenz);
}
