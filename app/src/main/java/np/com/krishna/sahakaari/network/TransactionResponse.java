package np.com.krishna.sahakaari.network;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TransactionResponse implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("user")
    private User user;
    @SerializedName("date")
    private String date;
    @SerializedName("title")
    private String title;
    @SerializedName("amount_status")
    private String type;
    @SerializedName("amount")
    private float amount;
    @SerializedName("total_amount")
    private float totalBalance;

    public TransactionResponse(String id, User user, String date, String title, String type,
                               float amount, float totalBalance) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.title = title;
        this.type = type;
        this.amount = amount;
        this.totalBalance = totalBalance;
    }

    public TransactionResponse() {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(float totalBalance) {
        this.totalBalance = totalBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionResponse)) return false;

        TransactionResponse that = (TransactionResponse) o;

        if (Float.compare(that.getAmount(), getAmount()) != 0) return false;
        if (Float.compare(that.getTotalBalance(), getTotalBalance()) != 0) return false;
        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getUser() != null ? !getUser().equals(that.getUser()) : that.getUser() != null)
            return false;
        if (getDate() != null ? !getDate().equals(that.getDate()) : that.getDate() != null)
            return false;
        if (getTitle() != null ? !getTitle().equals(that.getTitle()) : that.getTitle() != null)
            return false;
        return getType() != null ? getType().equals(that.getType()) : that.getType() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getAmount() != +0.0f ? Float.floatToIntBits(getAmount()) : 0);
        result = 31 * result + (getTotalBalance() != +0.0f ? Float.floatToIntBits(getTotalBalance()) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TransactionResponse{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", amount='" + amount + '\'' +
                ", totalBalance='" + totalBalance + '\'' +
                '}';
    }

}
