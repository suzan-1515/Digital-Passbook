package np.com.krishna.sahakaari.adapters;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.multiviewadapter.ItemViewHolder;

import np.com.krishna.R;
import np.com.krishna.sahakaari.network.LoanResponse;

public class LoanBinder extends ItemBinder<LoanResponse, LoanBinder.ViewHolder> {

    public LoanBinder() {
    }

    @Override
    public ViewHolder create(LayoutInflater inflater, ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_recent_loan, parent, false));
    }

    @Override
    public void bind(ViewHolder holder, LoanResponse item) {
        if (item != null) {
            holder.bindTo(item);
        }
    }

    @Override
    public boolean canBindData(Object item) {
        return item instanceof LoanResponse;
    }

    static class ViewHolder extends ItemViewHolder<LoanResponse> {

        TextView mLoanAmount;
        TextView mInterest;
        TextView mPaidAmount;
        TextView mRemainingAmount;
        TextView mDate;

        ViewHolder(View itemView) {
            super(itemView);

            mLoanAmount = itemView.findViewById(R.id.loan_amount);
            mInterest = itemView.findViewById(R.id.loan_interest);
            mPaidAmount = itemView.findViewById(R.id.loan_paid_amount);
            mRemainingAmount = itemView.findViewById(R.id.loan_remaining_amount);
            mDate = itemView.findViewById(R.id.date);

        }

        @SuppressLint("DefaultLocale")
        void bindTo(LoanResponse item) {
            mLoanAmount.setText(String.format("NPR. %.02f", item.getLoanAmount()));
            mInterest.setText(String.format("NPR. %.02f @ %.02f", item.getInterestAmount(), item.getInterestRate()));
            mPaidAmount.setText(String.format("NPR. %.02f", item.getPaidAmount()));
            mRemainingAmount.setText(String.format("NPR. %.02f", item.getRemainingAmount()));
            mDate.setText(TextUtils.isEmpty(item.getPaymentDate()) ? item.getLoanDate() : item.getPaymentDate());
        }
    }
}
