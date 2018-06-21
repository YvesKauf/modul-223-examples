package ch.nyp.androidrestapp.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

	public static final String AUTHORIZATION_HEADER = "Authorization";

	private static final String REST_API_URL = "https://rest-app-login.herokuapp.com";

	private static RestApi restApiInstance;

	public static RestApi getRestApi() {
		if (restApiInstance == null) {
			Retrofit retrofit = new Retrofit.Builder()
					.baseUrl(REST_API_URL)
					.addConverterFactory(GsonConverterFactory.create())
					.build();
			restApiInstance = retrofit.create(RestApi.class);
		}
		return restApiInstance;
	}
}
