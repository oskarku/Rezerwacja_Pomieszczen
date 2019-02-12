package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.api;

import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.DateMe;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.SingelMoreDetalRezervation;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.SingelMoreDetalRoomApi;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.SingelRezervationPozitionApi;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.WaschingMachine;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.WaschingMachineMode;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {





    @GET("token")
    Call<String> loginByToken(@Query("nick") String nick, @Query("password") String password);


    @GET("users/me")
    Call<DateMe>getDate(@HeaderMap Map<String, String> headers);
    @GET("users/{id}")
    Call<DateMe>getDataUser(@HeaderMap Map<String, String> headers, @Path("id") Integer id);

    @GET("reservations/{date}")
    Call<List<SingelRezervationPozitionApi>> getListRezervationOnData (@HeaderMap Map<String, String> headers, @Path("date") String date);


    @GET ("users/me/reservations")
    Call<List<SingelRezervationPozitionApi>> getMyRezervationList(@HeaderMap Map<String, String> headers);


    @GET("public-rooms/{id}")
    Call<SingelMoreDetalRoomApi> getInformationAboutOneRoom (@HeaderMap Map<String, String> headers, @Path("id") String id);

    //Lista pokoiu
    @GET("public-rooms")
    Call<List<SingelMoreDetalRoomApi>> getAllListRoom (@HeaderMap Map<String, String> headers);

    @GET("public-rooms")
    Call<List<SingelMoreDetalRoomApi>> getAllListRoomMy (@HeaderMap Map<String, String> headers, @Query("room") String room);


    //dostawanie info na temat konkretnej
    // zobaczyc czy  int przejdzie
    @GET("reservations/{id}")
    Call<SingelMoreDetalRezervation> getMoreDetalRezervation(@HeaderMap Map<String, String> headers, @Path("id") Integer id);

    @GET("washing-machines/{id}/modes")
    Call<List<WaschingMachineMode>> getWaschingMode(@HeaderMap Map<String, String> headers,@Path("id") String idWasch);

    @GET("washing-machines")
    Call<List<WaschingMachine>> getListWaschingMachines(@HeaderMap Map<String, String> headers);


    /***
     * TO jest  metoda ktora dodaje rezerwacje
     * @param headers  HasMap w ktorym miscie sie w tym przypadku token oraz content type
     * @param body to body typu RequestBody
     * @return Zwraca obiekt typu Call String
     */
    @POST("reservations")
    Call<String> postRezerwation(@HeaderMap Map<String, String> headers, @Body RequestBody body);

    @POST("users")
    Call<String> postCreateUser(@HeaderMap Map<String, String> headers, @Body RequestBody body);

    @PUT("users/{nick}/password")
    Call<String> putSendPassword(@Path("nick") String nick, @Body String body);

    @DELETE("users/me/reservations/{reservation_id}")
    Call<String>delateMyrezerwation(@HeaderMap Map<String, String> headers, @Path("reservation_id")Integer rezervID);

    @DELETE("users/{id}")
    Call<String>delateUsers(@HeaderMap Map<String, String> headers, @Path("id") Integer idUser);











}
