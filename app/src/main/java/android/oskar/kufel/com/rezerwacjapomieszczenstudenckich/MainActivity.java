package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.LoginUser;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.MSG;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.RetroClient;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.api.ApiService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {




    private Button buttonLogin;
    private EditText loginEditText;
    private EditText passwortEditText;
    private ProgressDialog pDialog;
    private TextView foreginTextView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginEditText = (EditText) findViewById(R.id.login_edit_text);
        passwortEditText =(EditText) findViewById(R.id.edit_text_password);

        buttonLogin = (Button) findViewById(R.id.button_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage(getString(R.string.dialog_loading_login));
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();

                LoginUser loginUser = new LoginUser();
                loginUser.setPasswort(passwortEditText.getText().toString());
                loginUser.setUsername(loginEditText.getText().toString());

                JSONObject paramJ = new JSONObject();
                try {
                    paramJ.put( KeepKey.KEY_USERNAME, loginUser.getUsername());
                    paramJ.put(KeepKey.KEY_PASSWORD, loginUser.getPasswort());


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                ApiService api = RetroClient.getApiService();

                /**
                 * Calling JSON
                 */

                RequestBody body =  RequestBody.create(MediaType.parse("application/json"), paramJ.toString() );

                Call<MSG> call = (Call<MSG>) api.loginUsers(body);

                /**
                 * Enqueue Callback will be call when get response...
                 */
                call.enqueue(new Callback<MSG>() {
                    @Override
                    public void onResponse(Call<MSG> call, Response<MSG> response) {
                        pDialog.dismiss();
                        //Dismiss Dialog

                        Context context = getApplicationContext();
                        CharSequence text = response.body().getStatus()+"  "+response.body().getMsg();
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();




                        Log.d("loadingData", "caly czas sie locze");

                        if (response.isSuccessful()) {



                            Toast toast2 = Toast.makeText(context, response.body().getUsername()+"  id :"+response.body().getIdUser(), duration);
                            toast2.show();

                            if(response.body().getStatus()==0){


                                SharedPreferences pref = getApplicationContext().getSharedPreferences(KeepKey.KEY_NAME_SHARED, MODE_PRIVATE); // 0 - for private mode
                                SharedPreferences.Editor editor = pref.edit();

                                editor.putString(KeepKey.KEY_TYPE_ACCOUNT,response.body().getTypeAC());
                                editor.putInt(KeepKey.KEY_ID_ACCOUNT,response.body().getIdUser());
                                editor.commit();

                                //TODO zedytowac baze danych tak zeby uzyskac numer pokoju


//                                response.body().getTypeAC()
                                Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                                startActivity(intent);
                            }







                        }
                    }

                    @Override
                    public void onFailure(Call<MSG> call, Throwable t) {
                        pDialog.dismiss();

                        Context context = getApplicationContext();
                        CharSequence text = t.getMessage();
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        Log.d("requestLoczeniu", t.getMessage());
                    }
                });

            }
        });




        foreginTextView = (TextView) findViewById(R.id.textViewForeginPasswort);
        foreginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///TODO zaimplementowac rozwiazanie z odzyskaniem hasla albo web view albo inny fragment z danymi i retofit

                Toast toast = Toast.makeText(getApplicationContext(), "Zaraz cie przeniesie w panel odzyskiwania hasla", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }





}
