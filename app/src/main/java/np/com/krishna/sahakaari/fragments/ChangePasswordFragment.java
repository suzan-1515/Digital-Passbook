package np.com.krishna.sahakaari.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import np.com.krishna.R;
import np.com.krishna.sahakaari.helpers.ToastHelper;
import np.com.krishna.sahakaari.network.InvalidPasswordResponse;
import np.com.krishna.sahakaari.network.PasswordChangeResponse;
import np.com.krishna.sahakaari.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ChangePasswordFragment extends BottomSheetDialogFragment {


    private TextInputLayout mCurrentPasswordLayout;
    private TextInputEditText mCurrentPassword;
    private TextInputLayout mNewPasswordLayout;
    private TextInputEditText mNewPassword;
    private TextInputLayout mConfirmPasswordLayout;
    private TextInputEditText mConfirmPassword;
    private MaterialButton mChangePasswordButton;
    private ProgressBar mProgressbar;

    private String currentPassword = "";
    private String newPassword = "";
    private String confirmPassword = "";

    private Call<PasswordChangeResponse> mPasswordChangeCall;


    public ChangePasswordFragment() {

    }

    public static ChangePasswordFragment newInstance(String token) {
        ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
        Bundle bundle = new Bundle();
        bundle.putString("token", token);
        changePasswordFragment.setArguments(bundle);
        return changePasswordFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_password_change, container, false);

        String token = getArguments().getString("token");

        mCurrentPasswordLayout = view.findViewById(R.id.current_password_layout);
        mCurrentPassword = view.findViewById(R.id.current_password);
        mNewPasswordLayout = view.findViewById(R.id.new_password_layout);
        mNewPassword = view.findViewById(R.id.new_password);
        mConfirmPasswordLayout = view.findViewById(R.id.confirm_password_layout);
        mConfirmPassword = view.findViewById(R.id.confirm_password);
        mChangePasswordButton = view.findViewById(R.id.change_password_button);
        mProgressbar = view.findViewById(R.id.progress);

        mChangePasswordButton.setOnClickListener(v -> {
            boolean isValid = true;
            if (currentPassword.isEmpty()) {
                mCurrentPasswordLayout.setError("Current password cannot be empty.");
                isValid = false;
            }

            if (newPassword.isEmpty()) {
                mNewPasswordLayout.setError("New password cannot be empty.");
                isValid = false;
            }
            if (confirmPassword.isEmpty()) {
                mConfirmPasswordLayout.setError("Confirm password cannot be empty.");
                isValid = false;
            }

            if (!isValid) return;

            mProgressbar.setVisibility(View.VISIBLE);
            mChangePasswordButton.setEnabled(false);
            mPasswordChangeCall = RetrofitClient.getInstance()
                    .endpoint()
                    .changePassword(token, currentPassword, newPassword, confirmPassword);
            mPasswordChangeCall.enqueue(new Callback<PasswordChangeResponse>() {
                @Override
                public void onResponse(@NonNull Call<PasswordChangeResponse> call, @NonNull Response<PasswordChangeResponse> response) {
                    if (response.isSuccessful()) {
                        mProgressbar.setVisibility(View.GONE);
                        mChangePasswordButton.setEnabled(true);
                        PasswordChangeResponse changeResponse = response.body();
                        if (changeResponse != null) {
                            ToastHelper.showToast(getContext(), changeResponse.getSuccessMessage());
                        } else
                            ToastHelper.showToast(getContext(), "Unknown error. Try again later.");
                    } else {
                        if (response.code() == 400) {
                            InvalidPasswordResponse unauthorizedResponse = InvalidPasswordResponse.fromJson(response.errorBody().charStream());
                            if (unauthorizedResponse.getCurrentPasswordErrorMessage() != null || unauthorizedResponse.getNewPasswordErrorMessage() != null) {
                                ToastHelper.showToast(getContext(), "Invalid password request.");
                            } else
                                ToastHelper.showToast(getContext(), "Unknown error. Try again later.");
                        } else {
                            ToastHelper.showToast(getContext(), "Unknown error. Try again later.");
                        }
                    }
                    dismiss();
                }

                @Override
                public void onFailure(@NonNull Call<PasswordChangeResponse> call, @NonNull Throwable t) {
                    Timber.e(t);
                    ToastHelper.showToast(getContext(), "Unknown error. Try again later.");
                    mProgressbar.setVisibility(View.GONE);
                    mChangePasswordButton.setEnabled(true);
                }
            });


        });

        addTextWatchers();

        return view;
    }

    private void addTextWatchers() {
        mCurrentPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                currentPassword = s.toString().trim();
            }
        });
        mNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                newPassword = s.toString().trim();
            }
        });
        mConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                confirmPassword = s.toString().trim();
                if (!newPassword.equals(confirmPassword)) {
                    mConfirmPasswordLayout.setError("Password do not match.");
                } else {
                    mConfirmPasswordLayout.setError(null);
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPasswordChangeCall != null)
            mPasswordChangeCall.cancel();
    }
}
