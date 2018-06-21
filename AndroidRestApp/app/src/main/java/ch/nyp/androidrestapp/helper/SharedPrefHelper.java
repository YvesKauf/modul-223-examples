package ch.nyp.androidrestapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper {
	private static final String SHARED_PREF = "shared_prefs";
	private static final String AUTHORIZATION_HEADER_PREF = "authorization_header";

	public static void setAuthorizationHeader(Context applicationContext, String jwtToken) {
		SharedPreferences sharedPref = applicationContext.getSharedPreferences(
				SHARED_PREF, Context.MODE_PRIVATE);
		SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();
		sharedPrefEditor.putString(AUTHORIZATION_HEADER_PREF, jwtToken);
		sharedPrefEditor.commit();
	}

	public static String getAuthorizationHeader(Context applicationContext) {
		SharedPreferences sharedPref = applicationContext.getSharedPreferences(
				SHARED_PREF, Context.MODE_PRIVATE);
		return sharedPref.getString(AUTHORIZATION_HEADER_PREF, null);
	}
}
