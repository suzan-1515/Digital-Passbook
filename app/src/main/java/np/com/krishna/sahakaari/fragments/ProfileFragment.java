package np.com.krishna.sahakaari.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import np.com.krishna.R;
import np.com.krishna.sahakaari.activities.LoginActivity;
import np.com.krishna.sahakaari.helpers.Session;
import np.com.krishna.sahakaari.helpers.ToastHelper;
import np.com.krishna.sahakaari.network.ProfileResponse;
import np.com.krishna.sahakaari.network.RetrofitClient;
import np.com.krishna.sahakaari.network.UnauthorizedResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ProfileFragment extends Fragment {

    private ArrayMap<String, String> userCredintials;

    private TextView mName;
    private ImageView mAvatar;
    private TextView mFatherName;
    private TextView mAddressName;
    private TextView mUsername;
    private TextView mMembershipNumber;
    private TextView mAccountNumber;
    private TextView mAccountPlan;
    private TextView mDepositAmount;
    private TextView mDuration;
    private TextView mAssignedStaff;
    private ProgressBar mProgressBar;

    private Call<ProfileResponse> mProfileCall;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(ArrayMap<String, String> credintials) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("token", credintials.get("token"));
        args.putString("user_id", credintials.get("user_id"));
        args.putString("username", credintials.get("username"));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session.Response authResponse = Session.isUserAuthenticated(getArguments());
        if (!authResponse.status) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }
        userCredintials = authResponse.credintals;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.d("View created");

        initViews(view);
        fetchData();
    }

    private void fetchData() {
        mProfileCall = RetrofitClient.getInstance()
                .endpoint()
                .fetchProfile(userCredintials.get("token"));
        mProfileCall.enqueue(new Callback<ProfileResponse>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull Response<ProfileResponse> response) {
                mProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    ProfileResponse profileResponse = response.body();
                    if (profileResponse == null) {
                        ToastHelper.showToast(getContext(), "Unknown error. Try again later.");
                    } else {
                        mName.setText(profileResponse.getFullName());
                        if (!TextUtils.isEmpty(profileResponse.getPhoto())) {
                            Glide.with(getContext())
                                    .load(profileResponse.getPhoto())
                                    .apply(new RequestOptions()
                                            .circleCrop()
                                            .override(200, 200)
                                            .error(R.drawable.ic_person_black)
                                            .placeholder(R.drawable.ic_person_black))
                                    .into(mAvatar);
                        }
                        mFatherName.setText(profileResponse.getFatherName());
                        mAddressName.setText(profileResponse.getAddress());
                        mUsername.setText(profileResponse.getUser().getUsername());
                        mMembershipNumber.setText(profileResponse.getMembershipNumber());
                        mAccountNumber.setText(profileResponse.getAccountNumber());
                        mAccountPlan.setText(profileResponse.getType().toUpperCase());
                        mDepositAmount.setText(String.format("NPR. %.02f", profileResponse.getAmount()));
                        mDuration.setText(profileResponse.getDuration().toUpperCase());
                        mAssignedStaff.setText(profileResponse.getFieldAgent().getUsername());
                    }
                } else {
                    if (response.code() == 401) {
                        UnauthorizedResponse unauthorizedResponse = UnauthorizedResponse.fromJson(response.errorBody().charStream());
                        ToastHelper.showToast(getContext(), unauthorizedResponse.getDetail());
                    } else {
                        ToastHelper.showToast(getContext(), "Unknown error. Try again later.");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProfileResponse> call, @NonNull Throwable t) {
                Timber.e(t);
                mProgressBar.setVisibility(View.GONE);
                ToastHelper.showToast(getContext(), "Unknown error. Try again later.");
            }
        });
    }

    private void initViews(View view) {
        mProgressBar = view.findViewById(R.id.progressbar);
        mName = view.findViewById(R.id.name);
        mAvatar = view.findViewById(R.id.avatar);
        mFatherName = view.findViewById(R.id.father_name);
        mAddressName = view.findViewById(R.id.address);
        mUsername = view.findViewById(R.id.username);
        mMembershipNumber = view.findViewById(R.id.membership_number);
        mAccountNumber = view.findViewById(R.id.account_number);
        mAccountPlan = view.findViewById(R.id.plan);
        mDepositAmount = view.findViewById(R.id.deposit_amount);
        mDuration = view.findViewById(R.id.duration);
        mAssignedStaff = view.findViewById(R.id.assigned_staff);
    }

    @Override
    public void onStop() {
        super.onStop();
        mProfileCall.cancel();
    }

}
