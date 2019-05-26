package np.com.krishna.sahakaari.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import np.com.krishna.R;
import np.com.krishna.sahakaari.helpers.ToastHelper;
import np.com.krishna.sahakaari.network.InvalidLoginResponse;
import np.com.krishna.sahakaari.network.LoginResponse;
import np.com.krishna.sahakaari.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar mProgress;
    private TextInputLayout mEmailInputLayout;
    private TextInputEditText mEmailEditText;
    private TextInputLayout mPasswordInputLayout;
    private TextInputEditText mPasswordEditText;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        mEmailInputLayout = findViewById(R.id.txtEmailLayout);
        mEmailEditText = findViewById(R.id.txtEmail);
        mPasswordInputLayout = findViewById(R.id.txtPasswordLayout);
        mPasswordEditText = findViewById(R.id.txtPassword);
        mLoginButton = findViewById(R.id.btnLogin);
        mProgress = findViewById(R.id.progress);
        mLoginButton.setOnClickListener(view -> loginUser());

    }

    private void loginUser() {
        String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();

        boolean isValid = true;
        if (email.isEmpty()) {
            mEmailInputLayout.setError("Email is required!");
            isValid = false;
        }

        if (password.isEmpty()) {
            mPasswordInputLayout.setError("Password is required!");
            isValid = false;
        }

        if (isValid) {
            mLoginButton.setEnabled(false);
            mProgress.setVisibility(View.VISIBLE);

            RetrofitClient.getInstance()
                    .endpoint()
                    .login(email, password)
                    .enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                            if (isDestroyed()) return;
                            if (response.isSuccessful()) {
                                LoginResponse loginResponse = response.body();
                                if (loginResponse == null) {
                                    ToastHelper.showToast(LoginActivity.this, "Unknown error. Try again later.");
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                    intent.putExtra("token", "JWT " + loginResponse.getToken());
                                    intent.putExtra("user_id", loginResponse.getUser().getId());
                                    intent.putExtra("username", loginResponse.getUser().getUsername());
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                if (response.code() == 400) {
                                    InvalidLoginResponse invalidLoginResponse = InvalidLoginResponse.fromJson(response.errorBody().charStream());
                                    if (invalidLoginResponse != null) {
                                        if (invalidLoginResponse.getInvalidUsername() != null || invalidLoginResponse.getInvalidPassword() != null) {
                                            ToastHelper.showToast(LoginActivity.this, "Invalid request. Username or password is missing!");
                                        } else if (invalidLoginResponse.getInvalidLogin() != null) {
                                            ToastHelper.showToast(LoginActivity.this, invalidLoginResponse.getInvalidLogin().get(0));
                                        } else
                                            ToastHelper.showToast(LoginActivity.this, "Unknown error. Try again later.");
                                    } else
                                        ToastHelper.showToast(LoginActivity.this, "Unknown error. Try again later.");
                                }
                                mLoginButton.setEnabled(true);
                                mProgress.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                            Timber.e(t);
                            if (!isDestroyed()) {
                                mLoginButton.setEnabled(true);
                                mProgress.setVisibility(View.GONE);
                                ToastHelper.showToast(LoginActivity.this, "Error connecting. Make sure internet connection is available and try again.");
                            }
                        }
                    });

        }
    }


}
