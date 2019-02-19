package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.AccountDetale;


import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.keep.KeepKey;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountDetalFragment extends Fragment {
    private TextView login, typeAccont, telephoneNumber, numberRoom, textViewEmail ;

    private Switch isHide, switchSetEditPassword;
    private ImageView cancelImageView;
    private LayoutInflater layoutinflater;
    private View customizedUserView;
    private AlertDialog alertDialog;


    public AccountDetalFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.title_activity_my_data).toUpperCase());
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_account_detal, container, false);
        login = (TextView) view.findViewById(R.id.textViewLoginAccountDetale);
        typeAccont = (TextView) view.findViewById(R.id.textViewTypeAccountDetale);
        telephoneNumber = (TextView) view.findViewById(R.id.textViewPhoneNumberDetale);
        numberRoom =  (TextView) view.findViewById(R.id.textViewRoomViewDetale);
        isHide = (Switch) view.findViewById(R.id.switchHideDateDetale);
        textViewEmail = (TextView) view.findViewById(R.id.text_view_email_fragment_detal);


        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences(KeepKey.KEY_NAME_SHARED, Context.MODE_PRIVATE); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        login.setText(pref.getString(KeepKey.KEY_USERNAME,"LOGIN"));
        typeAccont.setText(pref.getString(KeepKey.KEY_TYPE_ACCOUNT, "TYP KONTA"));
        telephoneNumber.setText(pref.getString(KeepKey.KEY_PHONE_USER, "000 000 000"));
        numberRoom.setText(pref.getString(KeepKey.KEY_ROOM_USER, "ROOM"));
        textViewEmail.setText(pref.getString(KeepKey.KEY_EMAIL_USER, "EMAIL"));




        isHide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){

                    Context context = getActivity().getApplicationContext();
                    CharSequence text = getString(R.string.hide_date);
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();



                    telephoneNumber.setVisibility(View.INVISIBLE);
                    numberRoom.setVisibility(View.INVISIBLE);
                    textViewEmail.setVisibility(View.INVISIBLE);
                }
                else if(isChecked==false) {


                    telephoneNumber.setVisibility(View.VISIBLE);
                    numberRoom.setVisibility(View.VISIBLE);
                    textViewEmail.setVisibility(View.VISIBLE);



                }
            }
        });



        return view;
    }



    @Override
    public void onResume() {
        super.onResume();

    }

}
