package ch.nyp.pendenzen_app.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ch.nyp.pendenzen_app.R;
import ch.nyp.pendenzen_app.connection.Database;
import ch.nyp.pendenzen_app.models.User;
import ch.nyp.pendenzen_app.persistence.DatabaseHelper;
import ch.nyp.pendenzen_app.util.SharedPrefUtil;

/**
 *
 * Created by meier1 on 25.01.2018.
 *
 * Class for Activity Login
 */

public class LoginActivity extends AppCompatActivity {

    private EditText mEditTextEmail;
    private EditText mEditTextPasswort;
    private Button mButtonSave;
    private TextView mRegistration;
    private Database database;

    public static final String INTENT_KEY_EMAIL = "EMAIL";
    public static final String INTENT_KEY_FIRSTNAME = "FIRSTNAME";
    public static final String INTENT_KEY_LASTNAME = "LASTNAME";
    public static final String INTENT_KEY_PICTURE = "PICTURE";

    /**
     *
     * @author Mike Meier
     *
     * OnClickListener for Button "LOGIN"
     */

    private View.OnClickListener mSaveOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View sendButton) {
            String email = mEditTextEmail.getText().toString();
            String password = mEditTextPasswort.getText().toString();
            User user = new User(email, password);
            User userresult = database.loginUser(user);
            if (userresult != null) {
                SharedPrefUtil.setLoggedInUser(getApplicationContext(), userresult);
                Intent intent = new Intent(getApplicationContext(), PendenzenActivity.class);
                startActivity(intent);
				finish();
            }else {
                if(database.checkEmail(email)){
                    mEditTextEmail.setError("Falsche E-Mail");
                    mEditTextPasswort.setError("Falsches Passwort");
                } else {
                    mEditTextPasswort.setError("Falsches Passwort");
                }
            }
        }
    };

    /**
     * OnClickListener for Button "Registrieren"
     * User is being redirected to RegisterActivity
     */
    private View.OnClickListener mRegistrateOnClickListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        }
    };

    /**
     * Method onCreate creates Database, onClickListeners
     * are set, if email doesn't equal null, user gets
     * redirected to WelcomeActivity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = new Database(this.getApplicationContext());

        mEditTextEmail = findViewById(R.id.edittext_login_email);
        mEditTextPasswort = findViewById(R.id.edittext_login_passwort);
        mButtonSave = findViewById(R.id.button_login_login);
        mRegistration = findViewById(R.id.textview_login_registration);
        mButtonSave.setOnClickListener(mSaveOnClickListener);
        mRegistration.setMovementMethod(LinkMovementMethod.getInstance());
        mRegistration.setOnClickListener(mRegistrateOnClickListener);

        //Testdaten erzeugen
        DatabaseHelper.createTestData(getApplicationContext());
    }
}