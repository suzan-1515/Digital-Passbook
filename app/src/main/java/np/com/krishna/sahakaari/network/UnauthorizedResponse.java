package np.com.krishna.sahakaari.network;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Type;

public class UnauthorizedResponse implements Serializable {

    @SerializedName("detail")
    private String detail;

    public UnauthorizedResponse() {

    }

    public UnauthorizedResponse(String detail) {
        this.detail = detail;
    }

    public static UnauthorizedResponse fromJson(Reader jsonReader) {
        Gson gson = new Gson();
        Type type = new TypeToken<UnauthorizedResponse>() {
        }.getType();

        return gson.fromJson(jsonReader, type);
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @NonNull
    @Override
    public String toString() {
        return "UnauthorizedResponse{" +
                "detail='" + detail + '\'' +
                '}';
    }
}
