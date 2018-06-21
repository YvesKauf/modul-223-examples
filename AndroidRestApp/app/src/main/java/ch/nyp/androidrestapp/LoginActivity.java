package ch.nyp.androidrestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import ch.nyp.androidrestapp.helper.SharedPrefHelper;
import ch.nyp.androidrestapp.model.User;
import ch.nyp.androidrestapp.rest.RestApi;
import ch.nyp.androidrestapp.rest.RestClient;
import okhttp3.internal.http2.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText mEditTextEmail;
    private EditText mEditTextPasswort;
    private Button mButtonLogin;

    private View.OnClickListener mLoginOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View sendButton) {
            String email = mEditTextEmail.getText().toString();
            String password = mEditTextPasswort.getText().toString();

            //Login bei REST-API durchführen:
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);

            RestApi service = RestClient.getRestApi();
            Call<Void> loginCall = service.login(user);
            loginCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    String authorizationHeader = response.headers().get(RestClient
                            .AUTHORIZATION_HEADER);

                    //JWT abspeichern in Shared Preferences
                    SharedPrefHelper.setAuthorizationHeader(getApplicationContext(), authorizationHeader);

                    //Weiterleitung auf UsersAcitivty
                    Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    //Falls Fehler aufgetreten ist: Fehlermeldung anzeigen
                    Toast.makeText(getApplicationContext(), R.string.login_error, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditTextEmail = findViewById(R.id.edittext_login_email);
        mEditTextPasswort = findViewById(R.id.edittext_login_passwort);
        mButtonLogin = findViewById(R.id.button_login_login);
        mButtonLogin.setOnClickListener(mLoginOnClickListener);

    }
}