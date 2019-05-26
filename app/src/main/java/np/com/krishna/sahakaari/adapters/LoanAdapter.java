package np.com.krishna.sahakaari.adapters;


import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import com.ahamed.multiviewadapter.util.PayloadProvider;

import java.util.List;

import np.com.krishna.sahakaari.network.LoanResponse;


public class LoanAdapter extends RecyclerAdapter {

    private DataListManager<LoanResponse> loanDataListManager;

    public LoanAdapter(LoanBinder binder) {
        loanDataListManager = new DataListManager<>(this, new PayloadProvider<LoanResponse>() {
            @Override
            public boolean areContentsTheSame(LoanResponse oldItem, LoanResponse newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public Object getChangePayload(LoanResponse oldItem, LoanResponse newItem) {
                return null;
            }
        });

        addDataManager(loanDataListManager);

        registerBinder(binder);
    }

    public void addData(List<LoanResponse> loanResponses) {
        if (loanResponses == null || loanResponses.isEmpty())
            return;
        loanDataListManager.addAll(loanResponses);
    }

    public void setData(List<LoanResponse> loanResponses) {
        if (loanResponses == null || loanResponses.isEmpty())
            return;
        loanDataListManager.set(loanResponses);
    }
}
