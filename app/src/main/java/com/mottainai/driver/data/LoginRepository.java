package com.mottainai.driver.data;

import androidx.lifecycle.MutableLiveData;

import com.mottainai.driver.R;
import com.mottainai.driver.data.model.login.LoginRequest;
import com.mottainai.driver.data.model.login.LoginResponse;
import com.mottainai.driver.networking.APIInterface;
import com.mottainai.driver.networking.RetrofitService;
import com.mottainai.driver.ui.login.LoginResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    APIInterface apiInterface;

    // private constructor : singleton access
    private LoginRepository() {
        this.apiInterface = RetrofitService.createService(APIInterface.class);
    }

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }

    public void login(final MutableLiveData<LoginResult> loginResult, String username, String password, String deviceToken) {
        // handle login
        apiInterface.makeLoginRequest(new LoginRequest(username, password, deviceToken)).
                enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()) {
                    loginResult.setValue(new LoginResult(response.body()));
                } else {
                    loginResult.setValue(new LoginResult(R.string.login_failed));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginResult.setValue(new LoginResult(R.string.login_failed));
            }
        });
    }
}
