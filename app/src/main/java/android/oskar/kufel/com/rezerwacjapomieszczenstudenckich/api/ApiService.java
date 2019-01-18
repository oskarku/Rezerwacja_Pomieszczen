package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.api;

import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.LoginUser;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.MSG;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.RegisterUser;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/tasks")
    Call<MSG> createUser( @Body RegisterUser user);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("member/login.php")
    Call<MSG> loginUsers (@Body RequestBody requestBody);


}
