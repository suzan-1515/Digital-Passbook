package np.com.krishna.sahakaari.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    @FormUrlEncoded
    @POST("login/")
    Call<LoginResponse> login(@Field("username") String username, @Field("password") String password);

    @GET("loans/")
    Call<List<LoanResponse>> fetchLoanData(@Header("Authorization") String authorization);

    @GET("loans/recent/")
    Call<List<LoanResponse>> fetchRecentLoanData(@Header("Authorization") String authorization);

    @GET("transactions/{type}/")
    Call<List<TransactionResponse>> fetchTransactionData(@Header("Authorization") String authorization, @Path("type") int type);

    @GET("transactions/recent/")
    Call<List<TransactionResponse>> fetchRecentTransactionData(@Header("Authorization") String authorization);

    @GET("user/profile/")
    Call<ProfileResponse> fetchProfile(@Header("Authorization") String authorization);

    @GET("user/account-info/")
    Call<AccountResponse> fetchAccountInfo(@Header("Authorization") String authorization);

}
