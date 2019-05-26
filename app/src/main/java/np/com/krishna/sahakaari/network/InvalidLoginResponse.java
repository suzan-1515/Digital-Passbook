package np.com.krishna.sahakaari.network;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class InvalidLoginResponse implements Serializable {

    @SerializedName("non_field_errors")
    private List<String> invalidLogin;
    @SerializedName("username")
    private List<String> invalidUsername;
    @SerializedName("password")
    private List<String> invalidPassword;

    public InvalidLoginResponse() {

    }

    public static InvalidLoginResponse fromJson(Reader jsonReader) {
        Gson gson = new Gson();
        Type type = new TypeToken<InvalidLoginResponse>() {
        }.getType();
        return gson.fromJson(jsonReader, type);
    }

    public List<String> getInvalidLogin() {
        return invalidLogin;
    }

    public void setInvalidLogin(List<String> invalidLogin) {
        this.invalidLogin = invalidLogin;
    }

    public List<String> getInvalidUsername() {
        return invalidUsername;
    }

    public void setInvalidUsername(List<String> invalidUsername) {
        this.invalidUsername = invalidUsername;
    }

    public List<String> getInvalidPassword() {
        return invalidPassword;
    }

    public void setInvalidPassword(List<String> invalidPassword) {
        this.invalidPassword = invalidPassword;
    }

    @Override
    public String toString() {
        return "InvalidLoginResponse{" +
                "invalidLogin=" + invalidLogin +
                ", invalidUsername=" + invalidUsername +
                ", invalidPassword=" + invalidPassword +
                '}';
    }
}
