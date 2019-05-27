package np.com.krishna.sahakaari.network;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Type;

public class InvalidPasswordResponse implements Serializable {

    @SerializedName("current_password")
    private String currentPasswordErrorMessage;

    @SerializedName("new_password")
    private String newPasswordErrorMessage;

    public InvalidPasswordResponse(String currentPasswordErrorMessage, String newPasswordErrorMessage) {
        this.currentPasswordErrorMessage = currentPasswordErrorMessage;
        this.newPasswordErrorMessage = newPasswordErrorMessage;
    }

    public InvalidPasswordResponse() {
    }

    public static InvalidPasswordResponse fromJson(Reader jsonReader) {
        Gson gson = new Gson();
        Type type = new TypeToken<InvalidPasswordResponse>() {
        }.getType();
        return gson.fromJson(jsonReader, type);
    }

    public String getCurrentPasswordErrorMessage() {
        return currentPasswordErrorMessage;
    }

    public void setCurrentPasswordErrorMessage(String currentPasswordErrorMessage) {
        this.currentPasswordErrorMessage = currentPasswordErrorMessage;
    }

    public String getNewPasswordErrorMessage() {
        return newPasswordErrorMessage;
    }

    public void setNewPasswordErrorMessage(String newPasswordErrorMessage) {
        this.newPasswordErrorMessage = newPasswordErrorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvalidPasswordResponse)) return false;

        InvalidPasswordResponse that = (InvalidPasswordResponse) o;

        if (getCurrentPasswordErrorMessage() != null ? !getCurrentPasswordErrorMessage().equals(that.getCurrentPasswordErrorMessage()) : that.getCurrentPasswordErrorMessage() != null)
            return false;
        return getNewPasswordErrorMessage() != null ? getNewPasswordErrorMessage().equals(that.getNewPasswordErrorMessage()) : that.getNewPasswordErrorMessage() == null;

    }

    @Override
    public int hashCode() {
        int result = getCurrentPasswordErrorMessage() != null ? getCurrentPasswordErrorMessage().hashCode() : 0;
        result = 31 * result + (getNewPasswordErrorMessage() != null ? getNewPasswordErrorMessage().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "InvalidPasswordResponse{" +
                "currentPasswordErrorMessage='" + currentPasswordErrorMessage + '\'' +
                ", newPasswordErrorMessage='" + newPasswordErrorMessage + '\'' +
                '}';
    }
}
