package ch.nyp.pendenzen_app.util;

import android.content.Context;
import android.content.SharedPreferences;

import ch.nyp.pendenzen_app.models.User;

/**
 *
 */
public class SharedPrefUtil {

	private static final String NAME_USER_SHARED_PREFS = "user_preferences";
	private static final String NAME_USER_ID_SHARED_PREFS = "loggedin_userid";
	private static final String NAME_IS_APPRENTICE_SHARED_PREFS = "is_apprentice";
	private static final String EMAIL_SHARED_PREFS = "loggedin_email";
	private static final String PW_SHARED_PREFS = "loggedin_password";
	private static final String NAME_TESTDATA_SHARED_PREFS = "testdata_preferences";
	private static final String NAME_CREATED_SHARED_PREFS = "testdata_created";

	public static void setLoggedInUser(Context applicationContext, User loggedInUser) {
		SharedPreferences sharedPref = applicationContext.getSharedPreferences(
				NAME_USER_SHARED_PREFS, Context.MODE_PRIVATE);
		SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();

		sharedPrefEditor.putInt(NAME_USER_ID_SHARED_PREFS, loggedInUser.getUserId());
		sharedPrefEditor.putBoolean(NAME_IS_APPRENTICE_SHARED_PREFS, loggedInUser.isApprentice());
		sharedPrefEditor.putString(EMAIL_SHARED_PREFS, loggedInUser.getEmail());
		sharedPrefEditor.putString(PW_SHARED_PREFS, loggedInUser.getPassword());

		sharedPrefEditor.commit();
	}

	public static User getLoggedInUser(Context applicationContext) {
		SharedPreferences sharedPref = applicationContext.getSharedPreferences(
				NAME_USER_SHARED_PREFS, Context.MODE_PRIVATE);

		User loggedInUser = new User();
		loggedInUser.setUserId(sharedPref.getInt(NAME_USER_ID_SHARED_PREFS, 0));
		loggedInUser.setApprentice(sharedPref.getBoolean(NAME_IS_APPRENTICE_SHARED_PREFS, true));
		loggedInUser.setEmail(sharedPref.getString(EMAIL_SHARED_PREFS, null));
		loggedInUser.setPassword(sharedPref.getString(PW_SHARED_PREFS, null));
		return loggedInUser;
	}

	public static void setTestDataCreated(Context applicationContext, boolean areCreated) {
		SharedPreferences sharedPref = applicationContext.getSharedPreferences(
				NAME_TESTDATA_SHARED_PREFS, Context.MODE_PRIVATE);
		SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();
		sharedPrefEditor.putBoolean(NAME_CREATED_SHARED_PREFS, areCreated);
		sharedPrefEditor.commit();
	}

	public static boolean getTestDataCreated(Context applicationContext) {
		SharedPreferences sharedPref = applicationContext.getSharedPreferences(
				NAME_TESTDATA_SHARED_PREFS, Context.MODE_PRIVATE);
		return sharedPref.getBoolean(NAME_CREATED_SHARED_PREFS, false);
	}
}
