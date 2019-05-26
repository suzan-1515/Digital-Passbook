package np.com.krishna.sahakaari.helpers;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

public class Session {

    public static Response isUserAuthenticated(Bundle bundle) {
        if (bundle == null) return new Response(false);
        String token = bundle.getString("token");
        String userId = bundle.getString("user_id");
        String username = bundle.getString("username");

        if (TextUtils.isEmpty(token) || TextUtils.isEmpty(userId) || TextUtils.isEmpty(username))
            return new Response(false);

        Response response = new Response();
        response.status = true;
        response.credintals = new ArrayMap<>();
        response.credintals.put("token", token);
        response.credintals.put("user_id", userId);
        response.credintals.put("username", username);

        return response;
    }

    public static class Response {
        public boolean status;
        public ArrayMap<String, String> credintals;

        public Response(boolean status) {
            this.status = status;
        }

        public Response() {
        }
    }

}
