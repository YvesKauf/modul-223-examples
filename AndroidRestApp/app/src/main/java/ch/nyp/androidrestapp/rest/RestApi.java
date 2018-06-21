package ch.nyp.androidrestapp.rest;

import java.util.List;

import ch.nyp.androidrestapp.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RestApi {

	@POST("login")
	Call<Void> login(@Body User user);

	@GET("loginrestapi/users/all/")
	Call<List<User>> getUsers(@Header(RestClient.AUTHORIZATION_HEADER) String authorization);
}
