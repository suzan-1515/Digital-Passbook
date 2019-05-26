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
import np.com.krishna.sahakaari.adapters.TransactionAdapter;
import np.com.krishna.sahakaari.adapters.TransactionBinder;
import np.com.krishna.sahakaari.helpers.Session;
import np.com.krishna.sahakaari.helpers.SlideUpItemAnimator;
import np.com.krishna.sahakaari.helpers.ToastHelper;
import np.com.krishna.sahakaari.network.RetrofitClient;
import np.com.krishna.sahakaari.network.TransactionResponse;
import np.com.krishna.sahakaari.network.UnauthorizedResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class TransactionFragment extends Fragment {

    private ArrayMap<String, String> userCredintials;
    private int type;

    private EasyRecyclerView mRecyclerView;


    private Call<List<TransactionResponse>> mTransactionCall;
    private TransactionAdapter mAdapter;

    public TransactionFragment() {
        // Required empty public constructor
    }

    public static TransactionFragment newInstance(ArrayMap<String, String> credintials, int code) {
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        args.putString("token", credintials.get("token"));
        args.putString("user_id", credintials.get("user_id"));
        args.putString("username", credintials.get("username"));
        args.putInt("code", code);
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
        type = getArguments().getInt("code");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaction, container, false);
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
        mAdapter = new TransactionAdapter(new TransactionBinder());
        mRecyclerView.setAdapterWithProgress(mAdapter);
        mRecyclerView.setItemAnimator(new SlideUpItemAnimator());

    }

    private void fetchData() {
        mTransactionCall = RetrofitClient.getInstance()
                .endpoint()
                .fetchTransactionData(userCredintials.get("token"), type);
        mTransactionCall.enqueue(new Callback<List<TransactionResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<TransactionResponse>> call, @NonNull Response<List<TransactionResponse>> response) {
                if (response.isSuccessful()) {
                    List<TransactionResponse> transactionResponses = response.body();
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
            public void onFailure(@NonNull Call<List<TransactionResponse>> call, @NonNull Throwable t) {
                Timber.e(t);
                mRecyclerView.showError();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        mTransactionCall.cancel();
    }


}
