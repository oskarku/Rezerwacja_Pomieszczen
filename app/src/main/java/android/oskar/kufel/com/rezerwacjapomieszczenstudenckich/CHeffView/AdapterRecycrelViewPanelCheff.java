package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.CHeffView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.AccountActivity;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.RetroClient;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.MainActivity;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.api.ApiService;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterRecycrelViewPanelCheff extends RecyclerView.Adapter<AdapterRecycrelViewPanelCheff.MyViewHolder> {
    private Context context;
    private List<SingelPositionCardUser> listUser;
    private Activity activity;
    private LayoutInflater layoutinflater;
    private View customizedUserView;
    private EditText  editTextGetNewPassword, editTextGetReepetNewPassword, editTextEmail, editTextPhoneNumber, editTextNumberRoom;
    private Button saveData;
    private Spinner typeChangeSpiner;
    private ConstraintLayout layoutOtherDate, layoutSetPassword ;


    private ImageView cancelImageView;
    private AlertDialog alertDialog;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username, numberRoom, typAccount;
        public Button setingUser, delateUser, callUsero;
        public ImageView avatarUser;

        public MyViewHolder(@NonNull View view) {
            super(view);
            username = view.findViewById(R.id.textViewNameUserCardCheff);
            numberRoom = view.findViewById(R.id.textViewNumberRoomCardCheff);
            typAccount = view.findViewById(R.id.textViewTypeAccountCardCheff);

            setingUser = view.findViewById(R.id.buttonSetingCardCheff);
            delateUser = view.findViewById(R.id.buttonDelateUserCardCheff);
            avatarUser = view.findViewById(R.id.imageViewAvatarAccount);
            callUsero = view.findViewById(R.id.buttonCallToUser);


        }
    }


    public AdapterRecycrelViewPanelCheff(Context context, List<SingelPositionCardUser> list, Activity activity) {
        this.context = context;
        this.listUser = list;
        this.activity = activity;


    }


    @NonNull
    @Override
    public AdapterRecycrelViewPanelCheff.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.one_position_list_account_cheef, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterRecycrelViewPanelCheff.MyViewHolder myViewHolder, int i) {

        final int position = i;

        final SingelPositionCardUser singelPosition = listUser.get(i);
        myViewHolder.username.setText(singelPosition.getUserName());
        myViewHolder.avatarUser.setImageResource(singelPosition.getAvatarAcoount());
        myViewHolder.typAccount.setText(singelPosition.getTypeAccount());

        myViewHolder.callUsero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity.getApplicationContext(), "klik tel ", Toast.LENGTH_SHORT).show();

                PhoneCallListener phoneListener = new PhoneCallListener();
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);


                SingelPositionCardUser card = listUser.get(position);

                String dial = "tel:" + "+48"+card.getPhoneNumber();

                activity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));



            }
        });



        ///TODO zrobic fragment do zmiany uzytkownika

        myViewHolder.setingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layoutinflater = LayoutInflater.from(activity);
                customizedUserView = layoutinflater.inflate(R.layout.edit_date_alert, null);
                cancelImageView = (customizedUserView.findViewById(R.id.imageViewCancelEdidAlertDialog));
                editTextGetNewPassword = (customizedUserView.findViewById(R.id.editTextGetNewPassword));

                editTextGetReepetNewPassword = (customizedUserView.findViewById(R.id.editTextRepeatNewPassword));

                typeChangeSpiner = (customizedUserView.findViewById(R.id.spinner_type_change_data_edit_data));
                layoutOtherDate = (ConstraintLayout)(customizedUserView.findViewById(R.id.constrain_layout_other_date));
                layoutSetPassword = (ConstraintLayout)(customizedUserView.findViewById(R.id.constrain_Layout_set_password));
                editTextEmail =(customizedUserView.findViewById(R.id.edit_text_email_edit_date_alert));
                editTextNumberRoom=(customizedUserView.findViewById(R.id.edit_text_room_edit_alert_data));
                editTextPhoneNumber = (customizedUserView.findViewById(R.id.edit_text_number_phone_edit_alert_data));
                saveData= (customizedUserView.findViewById(R.id.button_save_date_edit_date_alert));

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                        R.array.type_change, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                typeChangeSpiner.setAdapter(adapter);


                JSONObject objectBody = new JSONObject();

                typeChangeSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0){
                            //jaak jest zmiana daty
                            layoutOtherDate.setVisibility(View.VISIBLE);
                            layoutSetPassword.setVisibility(View.INVISIBLE);


                            saveData.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if()
                                }
                            });

                        }
                        else if (position ==1){
                            layoutOtherDate.setVisibility(View.INVISIBLE);
                            layoutSetPassword.setVisibility(View.VISIBLE);

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        parent.setSelection(0);
                    }
                });

                saveData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(layoutSetPassword.getVisibility()==View.VISIBLE){
                            Toast tost =Toast.makeText(activity.getApplicationContext(), "jestem w zmianie danych HASLO",Toast.LENGTH_SHORT);
                            tost.setGravity(Gravity.CENTER, 0,0);
                            tost.show();

                        }
                        else if (layoutOtherDate.getVisibility()==View.VISIBLE){
                            Toast tost =Toast.makeText(activity.getApplicationContext(), "jestemw zmianie danych roznych",Toast.LENGTH_SHORT);
                            tost.setGravity(Gravity.TOP, 0,0);
                            tost.show();

                        }
                    }
                });















                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

                alertDialogBuilder.setView(customizedUserView);


                alertDialog = alertDialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(false);

                alertDialog.show();

                cancelImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });






            }
        });

        myViewHolder.delateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                removeItem(position);
                //TODO usunac urzytkownika z bazy danych (implemetacja API)
                
            }
        });
    }




    public void removeItem(final int position) {
        if(!listUser.isEmpty()){
            SingelPositionCardUser card = listUser.get(position);
            ApiService api = RetroClient.getApiService();
            Call<String> delateU = (Call<String>) api.delateUsers(RetroClient.getHeadersMap(activity),card.getIdUser());
            delateU.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.code()==204){


                        listUser.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, listUser.size());
                        Toast.makeText(activity.getApplicationContext(),activity.getString(R.string.position_delate), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });



        }
        else {
            Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.donot_delete_list_empty), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }


    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended, need detect flag
                // from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    // restart app
                    Intent i = activity.getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage( activity.getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }
}
