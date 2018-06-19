package ch.nyp.aemtli_app.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import ch.nyp.aemtli_app.model.User;
import ch.nyp.aemtli_app.persistence.AppDatabase;
import ch.nyp.aemtli_app.persistence.UserDao;

/**
 * Helper-Klasse, in der sich praktische Funktionen befinden
 *
 * @author Miriam Streit <miriam.streit@nyp.ch>
 */
public class Helper {

	private static final String NAME_USER_SHARED_PREFS = "user_preferences";
	private static final String NAME_USER_ID_SHARED_PREFS = "loggedin_userid";
	private static final String NAME_IS_APPRENTICE_SHARED_PREFS = "is_apprentice";
	private static final String NAME_TESTDATA_SHARED_PREFS = "testdata_preferences";
	private static final String NAME_CREATED_SHARED_PREFS = "testdata_created";

	public static void setLoggedInUserId(Context applicationContext, int userId) {
		SharedPreferences sharedPref = applicationContext.getSharedPreferences(
				NAME_USER_SHARED_PREFS, Context.MODE_PRIVATE);
		SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();
		sharedPrefEditor.putInt(NAME_USER_ID_SHARED_PREFS, userId);
		sharedPrefEditor.commit();
	}

	public static boolean isApprenticeLoggedIn(Context applicationContext) {
		SharedPreferences sharedPref = applicationContext.getSharedPreferences(
				NAME_USER_SHARED_PREFS, Context.MODE_PRIVATE);
		return sharedPref.getBoolean(NAME_IS_APPRENTICE_SHARED_PREFS, true);
	}

	public static void setIsApprenticeLoggedIn(Context applicationContext, boolean
			isApprentice) {
		SharedPreferences sharedPref = applicationContext.getSharedPreferences(
				NAME_USER_SHARED_PREFS, Context.MODE_PRIVATE);
		SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();
		sharedPrefEditor.putBoolean(NAME_IS_APPRENTICE_SHARED_PREFS, isApprentice);
		sharedPrefEditor.commit();
	}

	public static int getLoggedInUserId(Context applicationContext) {
		SharedPreferences sharedPref = applicationContext.getSharedPreferences(
				NAME_USER_SHARED_PREFS, Context.MODE_PRIVATE);
		return sharedPref.getInt(NAME_USER_ID_SHARED_PREFS, 0);
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

	/**
	 * kontrolliert die Berechtigungen
	 * Sind sie gesetzt, wird true returned
	 * Sind sie nicht gesetzt, werden sie angefordert
	 *
	 * @param context
	 * @param activity
	 * @return
	 */
	public static Boolean canReadExtStorage(Context context, Activity activity) {
		if (Build.VERSION.SDK_INT >= 23) {
			int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission
					.READ_EXTERNAL_STORAGE);
			if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission
						.READ_EXTERNAL_STORAGE}, 3);
				return false;
			}
		}
		return true;
	}
}
