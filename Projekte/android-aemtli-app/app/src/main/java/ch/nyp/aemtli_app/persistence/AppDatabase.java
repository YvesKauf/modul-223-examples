package ch.nyp.aemtli_app.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ch.nyp.aemtli_app.model.Duty;
import ch.nyp.aemtli_app.model.User;
import ch.nyp.aemtli_app.model.UserDutyJoin;

/**
 * Klasse, die die Datenbankanbindung handelt
 *
 * @author Miriam Streit <miriam.streit@nyp.ch>
 * @version 1: Benutzerklasse erstellt
 */
@Database(entities = {User.class, Duty.class, UserDutyJoin.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "aemtli-app";
    private static AppDatabase appDb;

    /**
     * Singleton. Erstellt neue Instanz des Datenbankzugriffs
     * oder gibt bestehende Instanz zurück
     *
     * @author Miriam Streit <miriam.streit@nyp.ch>
     * @param context Kontext aus View
     * @return appDb AppDatabase Datenbankzugriff-Instanz
     */
    public static AppDatabase getAppDb(Context context) {
        if (appDb == null) {
            appDb = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return appDb;
    }

    /**
     * abstrakte Methode, das Data-Access-Object für Benutzer holt
     *
     * @author Miriam Streit <miriam.streit@nyp.ch>
     * @return BenutzerDao-Objekt
     */
    public abstract UserDao getUserDao();

    public abstract DutyDao getDutyDao();
    public abstract UserDutyJoinDao getUserDutyJoinDao();
}
