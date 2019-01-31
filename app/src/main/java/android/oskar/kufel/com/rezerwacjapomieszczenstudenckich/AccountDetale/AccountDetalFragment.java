package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.AccountDetale;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
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
    private TextView login, typeAccont, telephoneNumber, numberRoom, textViewHintGetOldPassword, textViewHintGetNewPasswort, textViewHintGetReepenNewPassword ;
    private EditText editTextChangeNumber, editTextGetOldPassword, editTextGetNewPassword, editTextGetReepetNewPassword;
    private Switch isHide, switchSetEditPassword;
    private Button changeButton;
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
        changeButton = (Button) view.findViewById(R.id.buttonChangeDetale);


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
                }
                else if(isChecked==false) {



                    telephoneNumber.setVisibility(View.VISIBLE);
                    numberRoom.setVisibility(View.VISIBLE);

                }
            }
        });



        return view;
    }



    @Override
    public void onResume() {
        super.onResume();


        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layoutinflater = LayoutInflater.from(getActivity());
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


                            Toast toast = Toast.makeText(getActivity().getApplicationContext(),getString(R.string.Un_hide_option_edit_passwort) , Toast.LENGTH_SHORT);
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







                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

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





    }

}
