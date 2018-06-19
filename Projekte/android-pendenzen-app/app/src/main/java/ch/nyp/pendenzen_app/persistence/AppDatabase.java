package ch.nyp.pendenzen_app.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ch.nyp.pendenzen_app.models.Pendenz;
import ch.nyp.pendenzen_app.models.User;

/**
 * Created by Joel - NYP
 *
 * this is the Java Class which create the connection to
 * the SQLite Database
 * Queries and Inserts are in UserDao
 * DB connection has to be created only once
 * so the Class is Singleton
 *
 * needs the library Room
 *
 */
@Database(entities = {User.class, Pendenz.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

	/**
	 * this is the database name
	 */
	private static final String DB_NAME = "db_nyp_pendenzen";
	private static AppDatabase appDb;

	/**
	 * @param context -> comes from an activity
	 * @return AppDatabase -> used in connection.Database
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
	public abstract UserDao getUserDao();

	public abstract PendenzDao getPendenzDao();
}


