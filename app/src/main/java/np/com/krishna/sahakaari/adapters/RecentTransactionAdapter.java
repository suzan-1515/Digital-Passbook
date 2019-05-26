package np.com.krishna.sahakaari.adapters;


import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import com.ahamed.multiviewadapter.util.PayloadProvider;

import java.util.List;

import np.com.krishna.sahakaari.network.TransactionResponse;


public class RecentTransactionAdapter extends RecyclerAdapter {

    private DataListManager<TransactionResponse> transactionDataListManager;

    public RecentTransactionAdapter(RecentTransactionBinder binder) {
        transactionDataListManager = new DataListManager<>(this, new PayloadProvider<TransactionResponse>() {
            @Override
            public boolean areContentsTheSame(TransactionResponse oldItem, TransactionResponse newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public Object getChangePayload(TransactionResponse oldItem, TransactionResponse newItem) {
                return null;
            }
        });

        addDataManager(transactionDataListManager);

        registerBinder(binder);
    }

    public void addData(List<TransactionResponse> transactionResponses) {
        if (transactionResponses == null || transactionResponses.isEmpty())
            return;
        transactionDataListManager.addAll(transactionResponses);
    }

    public void setData(List<TransactionResponse> transactionResponses) {
        if (transactionResponses == null || transactionResponses.isEmpty())
            return;
        transactionDataListManager.set(transactionResponses);
    }
}
