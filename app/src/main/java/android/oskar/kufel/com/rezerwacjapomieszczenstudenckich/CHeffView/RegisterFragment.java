package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.CHeffView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.RetroClient;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RezervationView.AdapterRezervationSIngelRoom;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RezervationView.SingelRezervation;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.api.ApiService;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {
    private Spinner spinnerChoseTypeAccount;
    private EditText editTextUserNameRegister, editTextEmailUser, editTextPasswordUser, editTextReepetPaswoord,editTextPhoneNumber, editTextRoomNumber;
    private Button buttonSaveRegestrige;
    private String typeAccountString;
    private Call<String> saveUserOnServerCall;
    private ApiService api;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("PANEL REJESTRACJI");

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        spinnerChoseTypeAccount = (Spinner) view.findViewById(R.id.spinnerChoseTypeAccountRegister);
        editTextUserNameRegister = (EditText) view.findViewById(R.id.editTextUserNameRegister);
        editTextEmailUser = (EditText) view.findViewById(R.id.editTextEmailUserRegister);
        editTextPasswordUser = (EditText) view.findViewById(R.id.editTextNewPassword);
        editTextReepetPaswoord = (EditText) view.findViewById(R.id.editTextRepatePassword);
        editTextPhoneNumber = (EditText) view.findViewById(R.id.editTextPhoneNumberRegister);
        editTextRoomNumber = (EditText) view.findViewById(R.id.editTextNumberRoom);

        buttonSaveRegestrige = (Button)view.findViewById(R.id.buttonRegister);


        return view ;
    }

    @Override
    public void onResume() {
        super.onResume();


        // Spinner Drop down elements
        List<String> typeAccount = new ArrayList<String>();
        typeAccount.add("Student");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, typeAccount);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerChoseTypeAccount.setAdapter(dataAdapter);

        spinnerChoseTypeAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("student")){
                    typeAccountString = "student";
                    Toast.makeText(getActivity().getApplicationContext(), typeAccountString, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                typeAccountString = "student";
                Toast.makeText(getActivity().getApplicationContext(), "Nic nie wybralem \n"+typeAccountString, Toast.LENGTH_SHORT).show();


            }
        });


        editTextUserNameRegister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



            }

            @Override
            public void afterTextChanged(Editable s) {


                validationUsername(editTextUserNameRegister.getText().toString());


            }
        });



        editTextPasswordUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editTextUserNameRegister.getText()!=null&& editTextUserNameRegister.getText().length()>0){
                    passwordValidation(editTextUserNameRegister.getText().toString(), editTextPasswordUser.getText().toString());
                }

            }
        });

        editTextEmailUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editTextEmailUser!=null) {
                    if (!isEmailValid(editTextEmailUser.getText().toString())) {
                        editTextEmailUser.setError("To nie jest email");
                    }
                }
                else if(editTextEmailUser.getText().length()==0 || editTextEmailUser==null){
                    editTextEmailUser.setError("Te pole nie moze być puste");
                }

            }
        });


        editTextPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editTextPhoneNumber.getText()!=null){
                    if(editTextPhoneNumber.getText().toString().length()<9){
                        editTextPhoneNumber.setError("Za moło cyfr jak na numer");
                    }
                    if(!validCellPhone(editTextPhoneNumber.getText().toString())){
                        editTextPhoneNumber.setError("To nie jest numer");
                    }
                }
                else if(editTextPhoneNumber== null || editTextPhoneNumber.getText().length()==0){
                    editTextPhoneNumber.setError("Te pole nie moze byc puste !");
                }

            }
        });




        buttonSaveRegestrige.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(validationUsername(editTextUserNameRegister.getText().toString())
                        && (passwordValidation(editTextUserNameRegister.getText().toString(),editTextPasswordUser.getText().toString()))
                        && (editTextRoomNumber.getText().toString()!=null)

                ){
                    api = RetroClient.getApiService();
                    JSONObject bodyJson = new JSONObject();
                    try {
                        bodyJson.put("nick", editTextUserNameRegister.getText().toString());
                        bodyJson.put("phone", editTextPhoneNumber.getText().toString());
                        bodyJson.put("password", editTextPasswordUser.getText().toString());
                        bodyJson.put("room", Integer.valueOf(editTextRoomNumber.getText().toString()));
                        bodyJson.put("email", editTextEmailUser.getText().toString());
                        bodyJson.put("type", typeAccountString.toUpperCase());

                        RequestBody bodyJ = RequestBody.create(okhttp3.MediaType.parse("content-type:application/json"),bodyJson.toString());
                        Toast.makeText(getActivity().getApplicationContext(), bodyJ.toString(), Toast.LENGTH_SHORT).show();



                        saveUserOnServerCall= (Call<String>) api.postCreateUser(RetroClient.getHeadersMap(getActivity()), bodyJ);
                        saveUserOnServerCall.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response.isSuccessful()){
                                    if (response.code()==201){
                                        Toast.makeText(getActivity().getApplicationContext(), "Zarejestrowalem", Toast.LENGTH_SHORT).show();

                                    }
                                    else if(response.code()==409){
                                        Toast.makeText(getActivity().getApplicationContext(), "nick juz istnieje", Toast.LENGTH_SHORT).show();

                                    }
                                }
                                Toast.makeText(getActivity().getApplicationContext(), response.code()+"\n"+response.message(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();


                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }

                else if(editTextRoomNumber.getText()==null || editTextRoomNumber.getText().length()==0){
                    editTextRoomNumber.setError("Uzupelnij to pole");
                }
            }
        });


    }



    private Boolean validationUsername (String username){
        boolean valid = false;
        if (username.length()>3){
            valid = true;

        }
        else if (username.length()<3){
            editTextUserNameRegister.setError(getString(R.string.valide_username_is_small));


            valid = false;
        }
        if (username.isEmpty() || username.length()==0){
            editTextUserNameRegister.setError(getString(R.string.valide_username_is_empty));
            valid = false;
        }
        return valid;
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean validCellPhone(String number)
    {
        return android.util.Patterns.PHONE.matcher(number).matches();
    }

    private Boolean passwordValidation(String userName, String password)
    {
        boolean valid = true;
        if (password.length() > 15 || password.length() < 8)
        {
            editTextPasswordUser.setError("Przekroczyles/NIezmiesciłes sie w zakresie znaków (8-15)");
            valid = false;
        }
        if (password.indexOf(userName) > -1)
        {
            editTextPasswordUser.setError("Nazwa użytkownika nie moze byc haslem!");
            valid = false;
        }
        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars ))
        {
            editTextPasswordUser.setError("Haslo powinno miec co najmniej jedna duza litere");
            valid = false;
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars ))
        {
            editTextPasswordUser.setError("Haslo powinno zawierac co najmniej jedna mala litere");
            valid = false;
        }
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers ))
        {
            editTextPasswordUser.setError("Haslo powinno zawierac jedna cyfre");
            valid = false;
        }
        String specialChars = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
        if (!password.matches(specialChars ))
        {
            editTextPasswordUser.setError("Haslo powinno zawierac jeden znak specjalny");
            valid = false;
        }
        if(editTextReepetPaswoord.getText()==null || editTextReepetPaswoord.getText().length()==0){
            editTextReepetPaswoord.setError("Te pole nie moze byc puste");
            valid = false;
        }

        if(editTextReepetPaswoord.getText()!=null){

            if(editTextPasswordUser.getText().toString().equals(editTextReepetPaswoord.getText().toString()) && !(editTextReepetPaswoord.getText().length()>0)){
                valid = true;
            }
            else{
                editTextReepetPaswoord.setError("Hasła nie są takie same");
                valid=false;
            }
        }


        if (valid)
        {
            Toast.makeText(getActivity().getApplicationContext(),getString(R.string.valide_password_true),Toast.LENGTH_SHORT).show();

        }

        return valid;
    }
}


