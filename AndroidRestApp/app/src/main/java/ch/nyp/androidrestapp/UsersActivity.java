package ch.nyp.androidrestapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import ch.nyp.androidrestapp.helper.SharedPrefHelper;
import ch.nyp.androidrestapp.model.User;
import ch.nyp.androidrestapp.rest.RestApi;
import ch.nyp.androidrestapp.rest.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_users);

		final ListView listView = findViewById(R.id.listview_main_users);

		//Authorization Header der eingeloggten Person (JWT-TOken) aus Shared Preferences lesen
		String authorizationHeader = SharedPrefHelper.getAuthorizationHeader
				(getApplicationContext());

		//Alle vorhandenen Users von der REST-API ermitteln
		RestApi service = RestClient.getRestApi();
		Call<List<User>> userCall = service.getUsers(authorizationHeader);
		userCall.enqueue(new Callback<List<User>>() {
			@Override
			public void onResponse(Call<List<User>> call, Response<List<User>> response) {
				List<User> usersFromRestApi = response.body();
				listView.setAdapter(new UserAdapter(getApplicationContext(), usersFromRestApi));
			}

			@Override
			public void onFailure(Call<List<User>> call, Throwable t) {
				//Falls Fehler aufgetreten ist: Fehlermeldung anzeigen
				Toast.makeText(getApplicationContext(), R.string.load_user_error, Toast.LENGTH_LONG).show();
			}
		});
	}


}
