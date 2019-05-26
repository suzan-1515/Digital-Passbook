package np.com.krishna.sahakaari.network;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AccountResponse implements Serializable {

    @SerializedName("full_name")
    private String fullName;
    @SerializedName("photo")
    private String photo;
    @SerializedName("type_of_plan")
    private String plan;
    @SerializedName("account_number")
    private String accountNumber;
    @SerializedName("total_balance")
    private float currentBalance;

    public AccountResponse(String fullName, String photo, String plan, String accountNumber, float currentBalance) {
        this.fullName = fullName;
        this.photo = photo;
        this.plan = plan;
        this.accountNumber = accountNumber;
        this.currentBalance = currentBalance;
    }

    public AccountResponse() {
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public float getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(float currentBalance) {
        this.currentBalance = currentBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountResponse)) return false;

        AccountResponse that = (AccountResponse) o;

        if (Float.compare(that.getCurrentBalance(), getCurrentBalance()) != 0) return false;
        if (getPlan() != null ? !getPlan().equals(that.getPlan()) : that.getPlan() != null)
            return false;
        return getAccountNumber() != null ? getAccountNumber().equals(that.getAccountNumber()) : that.getAccountNumber() == null;

    }

    @Override
    public int hashCode() {
        int result = getPlan() != null ? getPlan().hashCode() : 0;
        result = 31 * result + (getAccountNumber() != null ? getAccountNumber().hashCode() : 0);
        result = 31 * result + (getCurrentBalance() != +0.0f ? Float.floatToIntBits(getCurrentBalance()) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AccountResponse{" +
                "plan='" + plan + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", currentBalance=" + currentBalance +
                '}';
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
