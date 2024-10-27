package com.example.prm392_homebuddy_app.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterService {
    private static final String BASE_URL = "http://10.0.2.2:5053/api/";

    private static Retrofit retrofit = null;

    public static RegisterAPI getRegisterAPI() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(RegisterAPI.class);
    }
}
