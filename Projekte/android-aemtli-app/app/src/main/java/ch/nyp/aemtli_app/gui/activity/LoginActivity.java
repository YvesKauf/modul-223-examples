package ch.nyp.aemtli_app.gui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ch.nyp.aemtli_app.R;
import ch.nyp.aemtli_app.helper.Helper;
import ch.nyp.aemtli_app.model.User;
import ch.nyp.aemtli_app.persistence.AppDatabase;
import ch.nyp.aemtli_app.persistence.DatabaseHelper;
import ch.nyp.aemtli_app.persistence.UserDao;

/**
 * Klasse, die das GUI des Logins handelt
 *
 * @author Miriam Streit <miriam.streit@nyp.ch>
 */
public class LoginActivity extends AppCompatActivity {

	private EditText mEmailField;
	private EditText mPasswordField;

	private View.OnClickListener mLoginOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast errorToast = Toast.makeText(
					getApplicationContext(),
					getResources().getString(R.string.loginactivity_falsche_logindaten_meldung),
					Toast.LENGTH_LONG);

			String emailInput = mEmailField.getText().toString();
			String passwordInput = mPasswordField.getText().toString();

			if (emailInput.isEmpty() || passwordInput.isEmpty()) {
				if (emailInput.isEmpty()) {
					mEmailField.setError(getResources().getString(R.string.loginactivity_feld_ausfuellen_meldung));
				}
				if (passwordInput.isEmpty()) {
					mPasswordField.setError(getResources().getString(R.string.loginactivity_feld_ausfuellen_meldung));
				}
				errorToast.show();
			} else {
				User loggedInUser = getUser(emailInput, passwordInput);
				if (loggedInUser == null) {
					errorToast.show();
				} else {
					Intent intent;
					if (loggedInUser.getIsApprentice()) {
						intent = new Intent(getApplicationContext(), MyDutiesActivity.class);
					} else {
						intent = new Intent(getApplicationContext(), ApprenticesActivity.class);
					}
					Helper.setLoggedInUserId(getApplicationContext(), loggedInUser.getUserId());
					Helper.setIsApprenticeLoggedIn(getApplicationContext(), loggedInUser
							.getIsApprentice());
					startActivity(intent);
					finish();
				}
			}
		}
	};

	/**
	 * Methode, die beim Erstellen der Activity als Erstes aufgerufen wird
	 *
	 * @param savedInstanceState letzter gespeicherter Zustand
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setTitle(R.string.loginactivity_label_login);

		//Users in der DB erstellen
		DatabaseHelper.createTestData(getApplicationContext());

		mEmailField = findViewById(R.id.loginactivity_input_email);
		mPasswordField = findViewById(R.id.loginactivity_input_passwort);

		Button loginButton = findViewById(R.id.loginactivity_button_login);
		loginButton.setOnClickListener(mLoginOnClickListener);
	}

	/**
	 * Gibt den User mit den übergebenen Logindaten zurück. Falls der User nicht existiert (z.B.
	 * E-Mail und/oder PW falsch), wird null zurückgegeben.
	 *
	 * @param email eingegebene E-Mail-Adresse
	 * @param password eingegebenes Passwort
	 * @return loginValid true, wenn Logindaten stimmen und false wenn nicht
	 */
	private User getUser(String email, String password) {
		UserDao mUserDao = AppDatabase.getAppDb(getApplicationContext()).getUserDao();
		User user = mUserDao.getByEmail(email);
		if (user != null) {
			if (password.equals(user.getPassword())) {
				return user;
			}
		}
		return null;
	}
}
