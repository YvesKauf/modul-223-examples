package ch.nyp.aemtli_app.persistence;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ch.nyp.aemtli_app.helper.Helper;
import ch.nyp.aemtli_app.model.Duty;
import ch.nyp.aemtli_app.model.User;
import ch.nyp.aemtli_app.model.UserDutyJoin;

/**
 *
 */
public class DatabaseHelper {

	private static final String DEFAULT_PASSWORD = "12345";

	public static void createTestData(Context context) {
		if (Helper.getTestDataCreated(context) == false) {
			UserDao userDao = AppDatabase.getAppDb(context).getUserDao();
			userDao.deleteAll();

			DutyDao dutyDao = AppDatabase.getAppDb(context).getDutyDao();
			dutyDao.deleteAll();

			UserDutyJoinDao userDutyJoinDao = AppDatabase.getAppDb(context).getUserDutyJoinDao();
			userDutyJoinDao.deleteAll();

			createApprentice(userDao);
			createTeachers(userDao);
			createDuties(dutyDao);
			createUserDuties(userDutyJoinDao);

			Helper.setTestDataCreated(context, true);
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

	private static void createDuties(DutyDao dutyDao) {
		List<Duty> newDuties = new ArrayList<>();
		newDuties.add(new Duty(1,"Post"));
		newDuties.add(new Duty(2,"Facility"));
		newDuties.add(new Duty(3,"Pflanze giessen"));
		newDuties.add(new Duty(4,"Backup-Tape wechseln"));
		newDuties.add(new Duty(5,"Telefondienst"));
		newDuties.add(new Duty(6,"Kaffee-Ämtli"));
		newDuties.add(new Duty(7,"Arbeitsjournale kontrollieren"));
		newDuties.add(new Duty(8,"Begrüssung"));
		dutyDao.insertAll(newDuties);
	}

	private static void createUserDuties(UserDutyJoinDao userDutyJoinDao) {
		DateFormat dateFormat = new SimpleDateFormat("dd'.'MM'.'yyyy");
		String currentDateString = dateFormat.format(new Date()).toString();

		Calendar calendar = Calendar.getInstance();
		List<UserDutyJoin> newUserDutyJoins = new ArrayList<>();
		//Miriam
		newUserDutyJoins.add(new UserDutyJoin(1 ,1, currentDateString));
		newUserDutyJoins.add(new UserDutyJoin(1 ,2, currentDateString));
		newUserDutyJoins.add(new UserDutyJoin(1 ,3, currentDateString));
		newUserDutyJoins.add(new UserDutyJoin(1 ,4, currentDateString));

		//Sven
		newUserDutyJoins.add(new UserDutyJoin(2 ,1, currentDateString));
		newUserDutyJoins.add(new UserDutyJoin(2 ,3, currentDateString));
		newUserDutyJoins.add(new UserDutyJoin(2 ,5, currentDateString));
		newUserDutyJoins.add(new UserDutyJoin(2 ,6, currentDateString));

		//Céline
		newUserDutyJoins.add(new UserDutyJoin(3 ,2, currentDateString));
		newUserDutyJoins.add(new UserDutyJoin(3 ,8, currentDateString));

		userDutyJoinDao.insertAll(newUserDutyJoins);
	}
}
