package np.com.krishna.sahakaari.adapters;

import android.annotation.SuppressLint;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.multiviewadapter.ItemViewHolder;

import np.com.krishna.R;
import np.com.krishna.sahakaari.network.TransactionResponse;

public class RecentTransactionBinder extends ItemBinder<TransactionResponse, RecentTransactionBinder.ViewHolder> {

    public RecentTransactionBinder() {
    }

    @Override
    public ViewHolder create(LayoutInflater inflater, ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_recent_transaction, parent, false));
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

        ViewHolder(View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.title);
            mDate = itemView.findViewById(R.id.date);
            mAmount = itemView.findViewById(R.id.amount);

        }

        @SuppressLint("DefaultLocale")
        void bindTo(TransactionResponse item) {
            mTitle.setText(item.getTitle());
            mDate.setText(item.getDate());
            mAmount.setText(String.format("NPR. %.02f", item.getAmount()));
            int color = item.getType().equals("deposit") ? R.color.primaryDarkColor : R.color.secondaryDarkColor;
            mAmount.setTextColor(ResourcesCompat.getColor(itemView.getContext().getResources(), color, itemView.getContext().getTheme()));
        }
    }
}
