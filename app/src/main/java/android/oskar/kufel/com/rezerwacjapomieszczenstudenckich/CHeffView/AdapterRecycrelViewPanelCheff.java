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
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.MainActivity;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AdapterRecycrelViewPanelCheff extends RecyclerView.Adapter<AdapterRecycrelViewPanelCheff.MyViewHolder> {
    private Context context;
    private List<SingelPositionCardUser> listUser;
    private Activity activity;
    private LayoutInflater layoutinflater;
    private View customizedUserView;
    private EditText editTextChangeNumber, editTextGetNewPassword, editTextGetOldPassword, editTextGetReepetNewPassword;

    private TextView textViewHintGetNewPasswort, textViewHintGetOldPassword, textViewHintGetReepenNewPassword;
    private Switch switchSetEditPassword;
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
                editTextChangeNumber =(customizedUserView.findViewById(R.id.editTextNumberChcngeDetal));
                switchSetEditPassword = (customizedUserView.findViewById(R.id.switchWontChangePasswordEditDialog));
                cancelImageView = (customizedUserView.findViewById(R.id.imageViewCancelEdidAlertDialog));
                editTextGetNewPassword = (customizedUserView.findViewById(R.id.editTextGetNewPassword));
                editTextGetOldPassword = (customizedUserView.findViewById(R.id.editTextGetOldPassword));
                editTextGetReepetNewPassword = (customizedUserView.findViewById(R.id.editTextRepeatNewPassword));
                textViewHintGetNewPasswort =(customizedUserView.findViewById(R.id.textViewHintGetNewpassword));
                textViewHintGetOldPassword = (customizedUserView.findViewById(R.id.textViewHintGetOldPassword));
                textViewHintGetReepenNewPassword = (customizedUserView.findViewById(R.id.textViewReepatNewpasswordHint));


                switchSetEditPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if(isChecked==true){


                            Toast toast = Toast.makeText(activity.getApplicationContext(),activity.getString(R.string.Un_hide_option_edit_passwort) , Toast.LENGTH_SHORT);
                            toast.show();


                            editTextGetNewPassword.setVisibility(View.VISIBLE);
                            editTextGetOldPassword.setVisibility(View.VISIBLE);
                            editTextGetReepetNewPassword.setVisibility(View.VISIBLE);
                            textViewHintGetNewPasswort.setVisibility(View.VISIBLE);
                            textViewHintGetOldPassword.setVisibility(View.VISIBLE);
                            textViewHintGetReepenNewPassword.setVisibility(View.VISIBLE);

                            ///TODO zrobic opcje w api do zapisywania




                        }
                        else if(isChecked==false) {

                            editTextGetNewPassword.setVisibility(View.INVISIBLE);
                            editTextGetOldPassword.setVisibility(View.INVISIBLE);
                            editTextGetReepetNewPassword.setVisibility(View.INVISIBLE);
                            textViewHintGetNewPasswort.setVisibility(View.INVISIBLE);
                            textViewHintGetOldPassword.setVisibility(View.INVISIBLE);
                            textViewHintGetReepenNewPassword.setVisibility(View.INVISIBLE);


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
                
            }
        });
    }



    public void removeItem(int position) {
        if(!listUser.isEmpty()){
            listUser.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, listUser.size());
            Toast.makeText(activity.getApplicationContext(),activity.getString(R.string.position_delate), Toast.LENGTH_SHORT).show();}
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
