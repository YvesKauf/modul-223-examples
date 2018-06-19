package ch.nyp.pendenzen_app.persistence;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ch.nyp.pendenzen_app.models.User;
import ch.nyp.pendenzen_app.util.SharedPrefUtil;

/**
 *
 */
public class DatabaseHelper {

	private static final String DEFAULT_PASSWORD = "12345";

	public static void createTestData(Context context) {
		if (SharedPrefUtil.getTestDataCreated(context) == false) {
			UserDao userDao = AppDatabase.getAppDb(context).getUserDao();
			userDao.deleteAll();

			createApprentice(userDao);
			createTeachers(userDao);

			SharedPrefUtil.setTestDataCreated(context, true);
		}
	}

	/**
	 * Erstellt beim Start der Applikation die Lernenden
	 *
	 * @param userDao Dao für die User-Tabelle
	 */
	private static void createApprentice(UserDao userDao) {
		List<User> newUsers = new ArrayList<>();
		newUsers.add(new User(1, "miriam.streit@nyp.ch", DEFAULT_PASSWORD , "Miriam", "Streit",
				true));
		newUsers.add(new User(2, "sven.baenninger@nyp.ch", DEFAULT_PASSWORD, "Sven", "Bänninger", true));
		newUsers.add(new User(3, "celine.spaeti@nyp.ch", DEFAULT_PASSWORD, "Céline", "Späti", true));
		newUsers.add(new User(4, "mathias.blaser@nyp.ch", DEFAULT_PASSWORD, "Mathias", "Blaser", true));
		newUsers.add(new User(5, "david.vonmuehlenen@nyp.ch", DEFAULT_PASSWORD, "David", "von Mühlenen", true));
		newUsers.add(new User(6, "kabilan.ketheeswaranathan@nyp.ch", DEFAULT_PASSWORD, "Kabilan", "Ketheeswaranathan", true));
		newUsers.add(new User(7, "mathias.mueller@nyp.ch", DEFAULT_PASSWORD, "Mathias", "Büller", true));

		userDao.insertAll(newUsers);
	}

	private static void createTeachers(UserDao userDao) {
		List<User> newUsers = new ArrayList<>();
		newUsers.add(new User(8, "joel.holzer@nyp.ch", DEFAULT_PASSWORD, "Joel", "Holzer",
				false));
		newUsers.add(new User(9, "adrian.krebs@nyp.ch", DEFAULT_PASSWORD, "Adrian", "Krebs", false));
		newUsers.add(new User(10, "robert.kolb@nyp.ch", DEFAULT_PASSWORD, "Robert", "Kolb", false));

		userDao.insertAll(newUsers);
	}
}
