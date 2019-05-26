package np.com.krishna.sahakaari.network;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoanResponse implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("user")
    private User user;
    @SerializedName("loan_date")
    private String loanDate;
    @SerializedName("payment_date")
    private String paymentDate;
    @SerializedName("loan_amount")
    private float loanAmount;
    @SerializedName("interest_amount")
    private float interestAmount;
    @SerializedName("interest_rate")
    private float interestRate;
    @SerializedName("paid_amount")
    private float paidAmount;
    @SerializedName("total_amount")
    private float remainingAmount;

    public LoanResponse(String id, User user, String loanDate, String paymentDate, float interestRate) {
        this.id = id;
        this.user = user;
        this.loanDate = loanDate;
        this.paymentDate = paymentDate;
        this.interestRate = interestRate;
    }

    public LoanResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public float getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(float loanAmount) {
        this.loanAmount = loanAmount;
    }

    public float getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(float interestAmount) {
        this.interestAmount = interestAmount;
    }

    public float getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(float paidAmount) {
        this.paidAmount = paidAmount;
    }

    public float getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(float remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanResponse)) return false;

        LoanResponse that = (LoanResponse) o;

        if (Float.compare(that.getLoanAmount(), getLoanAmount()) != 0) return false;
        if (Float.compare(that.getInterestAmount(), getInterestAmount()) != 0) return false;
        if (Float.compare(that.getInterestRate(), getInterestRate()) != 0) return false;
        if (Float.compare(that.getPaidAmount(), getPaidAmount()) != 0) return false;
        if (Float.compare(that.getRemainingAmount(), getRemainingAmount()) != 0) return false;
        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getUser() != null ? !getUser().equals(that.getUser()) : that.getUser() != null)
            return false;
        if (getLoanDate() != null ? !getLoanDate().equals(that.getLoanDate()) : that.getLoanDate() != null)
            return false;
        return getPaymentDate() != null ? getPaymentDate().equals(that.getPaymentDate()) : that.getPaymentDate() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        result = 31 * result + (getLoanDate() != null ? getLoanDate().hashCode() : 0);
        result = 31 * result + (getPaymentDate() != null ? getPaymentDate().hashCode() : 0);
        result = 31 * result + (getLoanAmount() != +0.0f ? Float.floatToIntBits(getLoanAmount()) : 0);
        result = 31 * result + (getInterestAmount() != +0.0f ? Float.floatToIntBits(getInterestAmount()) : 0);
        result = 31 * result + (getInterestRate() != +0.0f ? Float.floatToIntBits(getInterestRate()) : 0);
        result = 31 * result + (getPaidAmount() != +0.0f ? Float.floatToIntBits(getPaidAmount()) : 0);
        result = 31 * result + (getRemainingAmount() != +0.0f ? Float.floatToIntBits(getRemainingAmount()) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LoanResponse{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", loanDate='" + loanDate + '\'' +
                ", loanAmount='" + loanAmount + '\'' +
                ", interestAmount='" + interestAmount + '\'' +
                ", paidAmount='" + paidAmount + '\'' +
                ", remainingAmount='" + remainingAmount + '\'' +
                '}';
    }

}
