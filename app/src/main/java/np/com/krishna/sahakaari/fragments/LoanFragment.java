package np.com.krishna.sahakaari.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

import np.com.krishna.R;
import np.com.krishna.sahakaari.activities.LoginActivity;
import np.com.krishna.sahakaari.adapters.LoanAdapter;
import np.com.krishna.sahakaari.adapters.LoanBinder;
import np.com.krishna.sahakaari.helpers.Session;
import np.com.krishna.sahakaari.helpers.SlideUpItemAnimator;
import np.com.krishna.sahakaari.helpers.ToastHelper;
import np.com.krishna.sahakaari.network.LoanResponse;
import np.com.krishna.sahakaari.network.RetrofitClient;
import np.com.krishna.sahakaari.network.UnauthorizedResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class LoanFragment extends Fragment {

    private ArrayMap<String, String> userCredintials;

    private EasyRecyclerView mRecyclerView;


    private Call<List<LoanResponse>> mLoanCall;
    private LoanAdapter mAdapter;


    public LoanFragment() {
        // Required empty public constructor
    }

    public static LoanFragment newInstance(ArrayMap<String, String> credintials) {
        LoanFragment fragment = new LoanFragment();
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
        return inflater.inflate(R.layout.fragment_loan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.d("View created");

        initViews(view);
        fetchData();
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new LoanAdapter(new LoanBinder());
        mRecyclerView.setAdapterWithProgress(mAdapter);
        mRecyclerView.setItemAnimator(new SlideUpItemAnimator());

    }

    private void fetchData() {
        mLoanCall = RetrofitClient.getInstance()
                .endpoint()
                .fetchLoanData(userCredintials.get("token"));
        mLoanCall.enqueue(new Callback<List<LoanResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<LoanResponse>> call, @NonNull Response<List<LoanResponse>> response) {
                if (response.isSuccessful()) {
                    List<LoanResponse> transactionResponses = response.body();
                    if (transactionResponses == null || transactionResponses.isEmpty()) {
                        mRecyclerView.showEmpty();
                    } else {
                        mAdapter.setData(transactionResponses);
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
                mRecyclerView.showError();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        mLoanCall.cancel();
    }

}
