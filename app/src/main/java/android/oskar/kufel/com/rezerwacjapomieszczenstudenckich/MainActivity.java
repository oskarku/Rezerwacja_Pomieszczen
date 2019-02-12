package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.DateMe;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.LoginUser;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.RetroClient;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.api.ApiService;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.keep.CheckConnectedNetwork;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.keep.KeepKey;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CheckConnectedNetwork {




    private Button buttonLogin;
    private EditText loginEditText;
    private EditText passwortEditText, nickEditText, passwordEditText  ;
    private ProgressDialog pDialog;
    private TextView foreginTextView;
    private boolean isConnected;
    private Call<String> callGetStringToken;
    private Call<DateMe> callGetDate;
    private  ApiService api;
    private RequestBody body;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private AlertDialog.Builder alert;
    private  ProgressDialog progressDialog;


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        pref = getApplicationContext().getSharedPreferences(KeepKey.KEY_NAME_SHARED, MODE_PRIVATE); // 0 - for private mode
        editor = pref.edit();
        loginEditText = (EditText) findViewById(R.id.login_edit_text);
        passwortEditText =(EditText) findViewById(R.id.edit_text_password);
        buttonLogin = (Button) findViewById(R.id.button_login);
        foreginTextView = (TextView) findViewById(R.id.textViewForeginPasswort);





    }

    @Override
    protected void onResume() {
        super.onResume();


        foreginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = MainActivity.this;
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);


                nickEditText = new EditText(context);
                nickEditText.setHint("Podaj nick");
                nickEditText.setMaxLines(1);
                layout.addView(nickEditText); // Notice this is an add method


               passwordEditText = new EditText(context);
                passwordEditText.setHint("Podaj nowe haslo");
                passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                passwordEditText.setMaxLines(1);
                layout.addView(passwordEditText);

                alert = new AlertDialog.Builder(context);
                alert.setIcon(android.R.drawable.star_on);
                alert.setTitle("Odzyskaj haslo");
                alert.setView(layout);
                alert.setPositiveButton("Wyślij",
                        new DialogInterface.OnClickListener() {
                            public void onClick( DialogInterface dialog,
                                                int whichButton) {
                                ApiService api = RetroClient.getApiService();
                                String bodyPassword = "\"" + passwordEditText.getText().toString() + "\"";
                                Call<String>callSendPassword =(Call<String>) api.putSendPassword(nickEditText.getText().toString(), bodyPassword);
                                callSendPassword.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        Toast.makeText(getApplicationContext(),"code "+response.code()+" \n"+response.message(), Toast.LENGTH_SHORT).show();
                                        if(response.isSuccessful()){
                                            if(response.code()==200){
                                                Toast.makeText(getApplicationContext(),"wyslalem emaila", Toast.LENGTH_SHORT).show();
                                            }
                                            else if (response.code()==400){
                                                Toast.makeText(getApplicationContext(),"Przepraszam ale taki uzytkownik nie istnieje ", Toast.LENGTH_SHORT).show();


                                            }
                                            Toast.makeText(getApplicationContext(),"code "+response.code()+" \n"+response.message(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {

                                    }
                                });

                            }
                        }).setNegativeButton("Anuluj",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                                dialog.cancel();

                            }
                        });

                AlertDialog alertDialog = alert.create();
                // show it
                alertDialog.show();


            }
        });



        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginUser loginUser = new LoginUser();
                loginUser.setPasswort(passwortEditText.getText().toString());
                loginUser.setUsername(loginEditText.getText().toString());
                editor.putString(KeepKey.KEY_PASSWORD_USER,loginUser.getPasswort());
                editor.commit();
                api = RetroClient.getApiService();
                setDialogShow();
                isConnected = isConnected(MainActivity.this);

                if (isConnected) {


                    callGetStringToken = (Call<String>) api.loginByToken(loginUser.getUsername(), loginUser.getPasswort());
                    callGetStringToken.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.isSuccessful()){
                                pDialog.dismiss();
                                Toast.makeText(MainActivity.this, response.body().toString().substring(1,response.body().length()-1), Toast.LENGTH_SHORT).show();

                                String token = response.body().toString().substring(1,response.body().length()-1);
                                editor.putString(KeepKey.KEY_ID_TOKEN, token);
                                editor.commit();
                                callGetDate = (Call<DateMe>) api.getDate(RetroClient.getHeadersMap(getApplicationContext()));
                                callGetDate.enqueue(new Callback<DateMe>() {
                                    @Override
                                    public void onResponse(Call<DateMe> call, Response<DateMe> response) {
                                        if(response.isSuccessful()){
                                            pDialog.dismiss();

                                            editor.putString(KeepKey.KEY_ID_ACCOUNT, response.body().getId().toString());
                                            editor.putString(KeepKey.KEY_USERNAME, response.body().getNick());
                                            editor.putString(KeepKey.KEY_TYPE_ACCOUNT, response.body().getType());
                                            editor.putString(KeepKey.KEY_EMAIL_USER, response.body().getEmail());
                                            editor.putString(KeepKey.KEY_PHONE_USER, response.body().getPhone().toString());
                                            editor.putString(KeepKey.KEY_ROOM_USER, response.body().getRoom().toString());

                                            editor.commit();
                                            pDialog.dismiss();

                                            Toast.makeText(MainActivity.this, "Zalogowalem sie "
                                                            + pref.getString(KeepKey.KEY_TYPE_ACCOUNT, "null") + "\n  o id konta "
                                                            + pref.getString(KeepKey.KEY_ID_ACCOUNT, "null") + "\n  "
                                                            + pref.getString(KeepKey.KEY_USERNAME, "null") + "\n  ",
                                                    Toast.LENGTH_LONG
                                            ).show();

                                            Intent intent = new Intent(getBaseContext(), AccountActivity.class);
                                            startActivity(intent);
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<DateMe> call, Throwable t) {
                                        pDialog.dismiss();
                                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT);

                                    }
                                });


                            }
                            if(response.code()==403){
                                pDialog.dismiss();
                                Toast.makeText(MainActivity.this, "zle haslo lub login", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            pDialog.dismiss();
                            Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
                else if (isConnected==false){
                    pDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Brak poloczenia", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        ///super.onBackPressed();
    }

    @Override
    public Boolean isConnected(Context context) {
        NetworkInfo activeNetwork;
        Call<String> call2;
        ConnectivityManager connectivityManager;
        connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null &&  activeNetwork.isConnectedOrConnecting();

    }

    private void setDialogShow(){
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage(getString(R.string.dialog_loading_login));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }


    private void setProgrerssBar(String title, String message){
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage(title); // Setting Message
        progressDialog.setTitle(message); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
    }
}
