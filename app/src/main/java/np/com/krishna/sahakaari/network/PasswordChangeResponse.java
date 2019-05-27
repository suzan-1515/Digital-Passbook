package np.com.krishna.sahakaari.network;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PasswordChangeResponse implements Serializable {

    @SerializedName("success")
    private String successMessage;

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PasswordChangeResponse)) return false;

        PasswordChangeResponse that = (PasswordChangeResponse) o;

        return getSuccessMessage() != null ? getSuccessMessage().equals(that.getSuccessMessage()) : that.getSuccessMessage() == null;

    }

    @Override
    public int hashCode() {
        return getSuccessMessage() != null ? getSuccessMessage().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PasswordChangeResponse{" +
                "successMessage='" + successMessage + '\'' +
                '}';
    }
}
