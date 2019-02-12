package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork;

import android.content.Context;
import android.content.SharedPreferences;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.api.ApiService;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.keep.KeepKey;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetroClient {


    private static final String ROOT_URL = "http://oskarkufel.hekko24.pl/api/index.php/";
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;


    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }


    public static void getNewToken(final Context contextAplication, final String info){
        final Context conTO = contextAplication;

        pref = contextAplication.getSharedPreferences(KeepKey.KEY_NAME_SHARED, contextAplication.MODE_PRIVATE); // 0 - for private mode
        editor = pref.edit();

        final ApiService api = getApiService();
        final Call<String> callGetStringToken = (Call<String>) api.loginByToken(pref.getString(KeepKey.KEY_USERNAME, "null"), pref.getString(KeepKey.KEY_PASSWORD_USER, "null"));
        callGetStringToken.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("down_new_token", "pobralem nowy token "+response.body().toString().substring(1,response.body().length()-1));
                Log.d("dow_new_token", "old key "+pref.getString(KeepKey.KEY_ID_TOKEN, "nie ma ")
                        + "\n nowy :"+response.body().toString().substring(1,response.body().length()-1));

                Toast.makeText(conTO.getApplicationContext(), info+"\n"+"old key "+pref.getString(KeepKey.KEY_ID_TOKEN, "nie ma ")
                        + "\n nowy : \n"+response.body().toString().substring(1,response.body().length()-1),
                        Toast.LENGTH_SHORT).show();
                editor.remove(KeepKey.KEY_ID_TOKEN);
                editor.putString(KeepKey.KEY_ID_TOKEN, response.body().toString().substring(1,response.body().length()-1));
                editor.commit();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(conTO.getApplicationContext(), info+"\n"+"ERROR GET TOKEN \n"+t.getMessage(),Toast.LENGTH_LONG).show();
                Log.e("dow_new_token", t.getMessage());

            }
        });

    }
    public static Map<String,String> getHeadersMap(Context context){
        pref = context.getSharedPreferences(KeepKey.KEY_NAME_SHARED, context.MODE_PRIVATE);

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", pref.getString(KeepKey.KEY_ID_TOKEN, ""));
        return  map;

    }
}
