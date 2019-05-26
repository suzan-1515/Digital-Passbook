package np.com.krishna.sahakaari.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import np.com.krishna.R;
import np.com.krishna.sahakaari.activities.LoginActivity;
import np.com.krishna.sahakaari.adapters.LoanAdapter;
import np.com.krishna.sahakaari.adapters.LoanBinder;
import np.com.krishna.sahakaari.adapters.RecentTransactionAdapter;
import np.com.krishna.sahakaari.adapters.RecentTransactionBinder;
import np.com.krishna.sahakaari.helpers.Session;
import np.com.krishna.sahakaari.helpers.SlideUpItemAnimator;
import np.com.krishna.sahakaari.helpers.ToastHelper;
import np.com.krishna.sahakaari.network.AccountResponse;
import np.com.krishna.sahakaari.network.LoanResponse;
import np.com.krishna.sahakaari.network.RetrofitClient;
import np.com.krishna.sahakaari.network.TransactionResponse;
import np.com.krishna.sahakaari.network.UnauthorizedResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class HomeFragment extends Fragment {

    private ArrayMap<String, String> userCredintials;
    private RecyclerView mTransactionRecyclerView;
    private RecyclerView mLoanRecyclerView;
    private View mTransactionEmptyView;
    private View mLoanEmptyView;
    private View mTransactionProgressView;
    private View mLoanProgressView;

    private TextView mWelcomeText;
    private AppCompatImageView mAvatar;
    private TextView mAccountType;
    private TextView mAccountNumber;
    private TextView mBalance;

    private Call<AccountResponse> accountInfoCall;
    private Call<List<TransactionResponse>> transactionCall;
    private Call<List<LoanResponse>> loanCall;

    private RecentTransactionAdapter mRecentTransactionAdapter;
    private LoanAdapter mLoanAdapter;

    public HomeFragment() {
        // Required empty public constructor
        setRetainInstance(true);
    }

    public static HomeFragment newInstance(ArrayMap<String, String> credintials) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
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
                if (response.isSuccessful()) {
                    AccountResponse profileResponse = response.body();
                    mWelcomeText.setText(
                            String.format(
                                    getContext().getResources().getString(R.string.welcome_text), profileResponse.getFullName()
                            )
                    );
                    mAccountType.setText("Sahakari - " + profileResponse.getPlan().toUpperCase());
                    mAccountNumber.setText(profileResponse.getAccountNumber());
                    mBalance.setText(String.format("NPR. %.02f", profileResponse.getCurrentBalance()));
                    if (!TextUtils.isEmpty(profileResponse.getPhoto())) {
                        Glide.with(getContext())
                                .load(profileResponse.getPhoto())
                                .apply(new RequestOptions()
                                        .circleCrop()
                                        .override(60, 60)
                                        .error(R.drawable.ic_person_black)
                                        .placeholder(R.drawable.ic_person_black))
                                .into(mAvatar);
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
            public void onFailure(@NonNull Call<AccountResponse> call, @NonNull Throwable t) {
                Timber.e(t);
                ToastHelper.showToast(getContext(), "Unknown error. Try again later.");
            }
        });
        transactionCall = RetrofitClient.getInstance()
                .endpoint()
                .fetchRecentTransactionData(userCredintials.get("token"));
        transactionCall.enqueue(new Callback<List<TransactionResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<TransactionResponse>> call, @NonNull Response<List<TransactionResponse>> response) {
                mTransactionProgressView.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    List<TransactionResponse> transactionResponses = response.body();
                    if (transactionResponses == null || transactionResponses.isEmpty()) {
                        mTransactionEmptyView.setVisibility(View.VISIBLE);
                    } else {

                        mRecentTransactionAdapter.setData(transactionResponses);
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
            public void onFailure(@NonNull Call<List<TransactionResponse>> call, @NonNull Throwable t) {
                Timber.e(t);
            }
        });
        loanCall = RetrofitClient.getInstance()
                .endpoint()
                .fetchRecentLoanData(userCredintials.get("token"));
        loanCall.enqueue(new Callback<List<LoanResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<LoanResponse>> call, @NonNull Response<List<LoanResponse>> response) {
                mLoanProgressView.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    List<LoanResponse> loanResponse = response.body();
                    if (loanResponse == null || loanResponse.isEmpty()) {
                        mLoanEmptyView.setVisibility(View.VISIBLE);
                    } else {
                        mLoanAdapter.setData(loanResponse);
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
            public void onFailure(@NonNull Call<List<LoanResponse>> call, @NonNull Throwable t) {
                Timber.e(t);
            }
        });
    }

    private void initViews(@NonNull View view) {
        mWelcomeText = view.findViewById(R.id.welcome_text);
        mAvatar = view.findViewById(R.id.avatar);
        mAccountType = view.findViewById(R.id.account_type);
        mAccountNumber = view.findViewById(R.id.account_number);
        mBalance = view.findViewById(R.id.account_balance);
        mTransactionEmptyView = view.findViewById(R.id.transaction_empty_view);
        mLoanEmptyView = view.findViewById(R.id.loan_empty_view);
        mTransactionProgressView = view.findViewById(R.id.transaction_progress_view);
        mLoanProgressView = view.findViewById(R.id.loan_progress_view);

        mTransactionRecyclerView = view.findViewById(R.id.transaction_recyclerview);
        mTransactionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecentTransactionAdapter = new RecentTransactionAdapter(new RecentTransactionBinder());
        mTransactionRecyclerView.setAdapter(mRecentTransactionAdapter);
        mTransactionRecyclerView.setItemAnimator(new SlideUpItemAnimator());

        mLoanRecyclerView = view.findViewById(R.id.loan_recyclerview);
        mLoanRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mLoanAdapter = new LoanAdapter(new LoanBinder());
        mLoanRecyclerView.setAdapter(mLoanAdapter);
        mLoanRecyclerView.setItemAnimator(new SlideUpItemAnimator());
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d("On resume");
    }

    @Override
    public void onStop() {
        super.onStop();
        accountInfoCall.cancel();
        transactionCall.cancel();
        loanCall.cancel();
    }
}
