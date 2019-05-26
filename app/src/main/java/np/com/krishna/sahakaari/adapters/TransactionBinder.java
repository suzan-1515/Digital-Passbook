package np.com.krishna.sahakaari.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.multiviewadapter.ItemViewHolder;

import np.com.krishna.R;
import np.com.krishna.sahakaari.network.TransactionResponse;

public class TransactionBinder extends ItemBinder<TransactionResponse, TransactionBinder.ViewHolder> {

    public TransactionBinder() {
    }

    @Override
    public ViewHolder create(LayoutInflater inflater, ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_transaction, parent, false));
    }

    @Override
    public void bind(ViewHolder holder, TransactionResponse item) {
        if (item != null) {
            holder.bindTo(item);
        }
    }

    @Override
    public boolean canBindData(Object item) {
        return item instanceof TransactionResponse;
    }

    static class ViewHolder extends ItemViewHolder<TransactionResponse> {

        TextView mTitle;
        TextView mDate;
        TextView mAmount;
        TextView mBalance;

        ViewHolder(View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.title);
            mDate = itemView.findViewById(R.id.date);
            mAmount = itemView.findViewById(R.id.amount);
            mBalance = itemView.findViewById(R.id.balance);

        }

        @SuppressLint("DefaultLocale")
        void bindTo(TransactionResponse item) {
            mTitle.setText(item.getTitle());
            mDate.setText(item.getDate());
            boolean isDeposit = item.getType().equals("deposit");
            mAmount.setText(String.format(isDeposit ? "+ NPR. %.02f" : "- NPR. %.02f", item.getAmount()));
            mBalance.setText(String.format("NPR. %.02f", item.getTotalBalance()));
        }
    }
}
