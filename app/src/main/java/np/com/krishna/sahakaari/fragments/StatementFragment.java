package np.com.krishna.sahakaari.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import np.com.krishna.R;
import np.com.krishna.sahakaari.activities.LoginActivity;
import np.com.krishna.sahakaari.adapters.ViewPagerAdapter;
import np.com.krishna.sahakaari.helpers.Session;
import np.com.krishna.sahakaari.helpers.ToastHelper;
import np.com.krishna.sahakaari.helpers.Utils;
import np.com.krishna.sahakaari.network.AccountResponse;
import np.com.krishna.sahakaari.network.RetrofitClient;
import np.com.krishna.sahakaari.network.UnauthorizedResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class StatementFragment extends Fragment {

    private ArrayMap<String, String> userCredintials;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ProgressBar mProgressBar;
    private TextView mBalance;
    private TextView mDate;

    private Call<AccountResponse> accountInfoCall;

    public StatementFragment() {
        // Required empty public constructor
    }

    public static StatementFragment newInstance(ArrayMap<String, String> credintials) {
        StatementFragment fragment = new StatementFragment();
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
        return inflater.inflate(R.layout.fragment_statement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.d("View created");

        initViews(view);
        fetchData();

    }

    private void fetchData() {
        accountInfoCall = RetrofitClient.getInstance()
                .endpoint()
                .fetchAccountInfo(userCredintials.get("token"));
        accountInfoCall.enqueue(new Callback<AccountResponse>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(@NonNull Call<AccountResponse> call, @NonNull Response<AccountResponse> response) {
                mProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    AccountResponse profileResponse = response.body();
                    mBalance.setText(String.format("NPR. %.02f", profileResponse.getCurrentBalance()));
                    mDate.setText(Utils.getCurrentDate());
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
            public void onFailure(@NonNull Call<AccountResponse> call, @NonNull Throwable t) {
                Timber.e(t);
                ToastHelper.showToast(getContext(), "Unknown error. Try again later.");
            }
        });
    }

    private void initViews(View view) {
        mProgressBar = view.findViewById(R.id.progressbar);
        mTabLayout = view.findViewById(R.id.tablayout);
        mViewPager = view.findViewById(R.id.view_pager);
        mBalance = view.findViewById(R.id.balance);
        mDate = view.findViewById(R.id.date);

        mTabLayout.setupWithViewPager(mViewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(TransactionFragment.newInstance(userCredintials, 0), "All");
        viewPagerAdapter.addFragment(TransactionFragment.newInstance(userCredintials, 1), "Money In");
        viewPagerAdapter.addFragment(TransactionFragment.newInstance(userCredintials, 2), "Money Out");

        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void onStop() {
        super.onStop();
        accountInfoCall.cancel();
    }


}
