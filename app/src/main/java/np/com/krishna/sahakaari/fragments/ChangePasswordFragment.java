package np.com.krishna.sahakaari.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChangePasswordFragment extends DialogFragment {


    private EditText oldPassword;
    private EditText newPassword;
    private EditText confirmPassword;

    private String old;
    private String pass;
    private String confirm;

    private View rootView;
    private Context activity;

    public ChangePasswordFragment() {

    }

    public static ChangePasswordFragment newInstance(Context activity) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        fragment.activity = activity;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initViews();
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(rootView)
                .setTitle("Change password")
                .setCancelable(false)
                .setPositiveButton("Change", null)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.setOnShowListener(dialog -> {
            onDialogShow(alertDialog);
        });
        return alertDialog;
    }

    private void initViews() {
//        rootView = LayoutInflater.from(getContext())
//                .inflate(R.layout.password_change_layout, null, false);
//
//        oldPassword = rootView.findViewById(R.id.oldp);
//        newPassword = rootView.findViewById(R.id.newp);
//        confirmPassword = rootView.findViewById(R.id.confirmp);

        addTextWatchers();
    }

    private void onDialogShow(AlertDialog dialog) {
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(v -> {
            onDoneClicked();
        });
    }

    private void onDoneClicked() {
        if (old.trim().isEmpty() || !pass.trim().equals(confirm.trim())) {
            return;
        }

    }

    private void addTextWatchers() {
        oldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                old = s.toString();
            }
        });
        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                pass = s.toString();
            }
        });
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                confirm = s.toString();
            }
        });
    }

}
