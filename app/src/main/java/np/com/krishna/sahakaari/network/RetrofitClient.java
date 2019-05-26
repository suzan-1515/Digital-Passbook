package np.com.krishna.sahakaari.network;

import np.com.krishna.sahakaari.config.AppConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final RetrofitClient ourInstance = new RetrofitClient();
    private Retrofit retrofit;

    private RetrofitClient() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitClient getInstance() {
        return ourInstance;
    }

    public APIService endpoint() {
        return retrofit.create(APIService.class);
    }

}
