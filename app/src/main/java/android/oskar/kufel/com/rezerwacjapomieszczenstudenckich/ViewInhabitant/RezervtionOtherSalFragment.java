package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ViewInhabitant;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.RetroClient;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.api.ApiService;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RezervtionOtherSalFragment extends Fragment {

    private Button buttonSetStartDate, buttonSetStartTime, buttonSetEndDate, buttonSetEdndTime;
    private TextView textViewStartData, textViewEndData, textViewStartTime, textViewEndTime ;
    private FloatingActionButton floatingActionButton;
    private String Title;
    private Call<String> postRezervation;
    private ApiService api;
    private ProgressDialog pDialog;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rezervtion_other_sal, container, false);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButtonSaveOtherSal);
        buttonSetStartDate = view.findViewById(R.id.buttonSetDataStart);
        buttonSetStartTime= view.findViewById(R.id.buttonSetStartHour);
        buttonSetEdndTime = view.findViewById(R.id.buttonSetHourEnd);
        buttonSetEndDate= view.findViewById(R.id.buttonSetDateEnd);


        textViewEndData= view.findViewById(R.id.textViewEndDataRezervation);
        textViewStartData= view.findViewById(R.id.textViewDataStartRezervation);
        textViewEndTime= view.findViewById(R.id.textViewEndHourRezervation);
        textViewStartTime = view.findViewById(R.id.textViewStartHourRezervation);



        Toast toast = Toast.makeText(getActivity().getApplicationContext(), Title, Toast.LENGTH_SHORT);
        toast.show();





        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        buttonSetEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mYear,mMonth,mDay;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String valideMonth = null, valideDay = null;
                                if ((monthOfYear+1)<10){
                                    valideMonth="0"+(monthOfYear+1);
                                }
                                else if((monthOfYear+1)>=10){
                                    valideMonth=""+(monthOfYear+1);
                                }
                                if(dayOfMonth<10){
                                    valideDay="0"+dayOfMonth;
                                }
                                else if(dayOfMonth>=10){
                                    valideDay=""+dayOfMonth;
                                }
                                String End = year+"-"+valideMonth+"-"+valideDay;
                                textViewEndData.setText(End);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.setTitle("Ustaw date konca rezerwacji");
                datePickerDialog.show();
            }

        });


        buttonSetEdndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                int mHour,mMinute;
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String valideHourOfDay = null, valideMinute = null;


                                if(hourOfDay<10 ){
                                    valideHourOfDay="0"+hourOfDay;
                                }
                                else if (hourOfDay>=10){
                                    valideHourOfDay = ""+hourOfDay;

                                }
                                if(minute<10){
                                    valideMinute="0"+minute;
                                }
                                else if(minute>=10){
                                    valideMinute = ""+minute;

                                }

                                textViewEndTime.setText(valideHourOfDay +":"+valideMinute+":"+"00");
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.setTitle("ustaw czas konca rezerwacji");
                timePickerDialog.show();

            }
        });


        buttonSetStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Current Time
                int mHour,mMinute;
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                String valideHourOfDay = null, valideMinute = null;


                                if(hourOfDay<10 ){
                                    valideHourOfDay="0"+hourOfDay;
                                }
                                else if (hourOfDay>=10){
                                    valideHourOfDay = ""+hourOfDay;

                                }
                                if(minute<10){
                                    valideMinute="0"+minute;
                                }
                                else if(minute>=10){
                                    valideMinute = ""+minute;

                                }


                                textViewStartTime.setText(valideHourOfDay +":"+valideMinute+":"+"01");



                            }
                        }, mHour, mMinute, true);
                timePickerDialog.setTitle("Wybierz poczotkowy czas");
                timePickerDialog.show();
            }
        });



        buttonSetStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mYear, mMonth, mDay;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String valideMonth = null, valideDay = null;
                                if ((monthOfYear+1)<10){
                                    valideMonth="0"+(monthOfYear+1);
                                }
                                else if((monthOfYear+1)>10){
                                    valideMonth=""+(monthOfYear+1);
                                }
                                if(dayOfMonth<10){
                                    valideDay="0"+dayOfMonth;
                                }
                                else if(dayOfMonth>10){
                                    valideDay=""+dayOfMonth;
                                }
                                String End = year+"-"+valideMonth+"-"+valideDay;

                                textViewStartData.setText(End);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }

        });




        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                api= RetroClient.getApiService();
                //headers.put("content-type","application/json");
                JSONObject body = new JSONObject();
                try {





                    Toast.makeText(getActivity().getApplicationContext(),textViewStartData.getText().toString()+" "+textViewStartTime.getText().toString(),Toast.LENGTH_SHORT).show();

                    body.put("begin_time", textViewStartData.getText().toString()+" "+textViewStartTime.getText().toString());
                    body.put("end_time", textViewEndData.getText().toString()+" "+textViewEndTime.getText().toString());
                    body.put("public_room_id", 1);
                    Map<String,String> headers = RetroClient.getHeadersMap(getActivity());
                    //headers.put("content-type","application/json");



                    RequestBody bodyJ = RequestBody.create(okhttp3.MediaType.parse("content-type:application/json"),body.toString());


                    postRezervation = (Call<String>) api.postRezerwation(headers,bodyJ);
                    postRezervation.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                            Toast.makeText(getActivity().getApplicationContext(), response.message()+"\n"+response.code(), Toast.LENGTH_LONG).show();

                            if(response.isSuccessful()) {

                                if (response.code() == 201) {

                                    Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.event_created), Toast.LENGTH_SHORT).show();
                                } else if (response.code() == 401) {
                                    RetroClient.getNewToken(getActivity().getApplicationContext(), "Dodawanie eventu");

                                } else if (response.code() == 409) {

                                    Toast.makeText(getActivity().getApplicationContext(), "Niestety reerwacja z tymi parametrami \n nie moze byc " +
                                            "zrealizowna", Toast.LENGTH_SHORT).show();

                                }
                            }




                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                            Toast.makeText(getActivity().getApplicationContext(),"code : "+t.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });




                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        });

    }

    private void  validateDate(){
        if(textViewStartTime.getText().length()==0){
            Toast.makeText(getActivity().getApplicationContext(),"ustaw czas strtu",Toast.LENGTH_SHORT).show();
        }
        if(textViewStartData.getText().length()==0){
            Toast.makeText(getActivity().getApplicationContext(), "",Toast.LENGTH_SHORT).show();
        }
    }


}
