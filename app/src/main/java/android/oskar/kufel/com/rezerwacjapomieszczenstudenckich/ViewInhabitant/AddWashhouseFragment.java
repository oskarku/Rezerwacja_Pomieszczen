package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ViewInhabitant;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.RetroClient;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.SingelMoreDetalRoomApi;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.WaschingMachine;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.WaschingMachineMode;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.MainActivity;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RecyclerTouchListener;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.SwipeToDeleteCallback;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.api.ApiService;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.keep.KeepKey;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class AddWashhouseFragment extends Fragment {
    private Spinner spinnerChoseProgram, spinnerChoseWash;
    private Button buttonAddProgram, buttonAddRezervation, buttonClear, buttonSetTime;
    private TextView textViewChoseTime;
    private RecyclerView recyclerView;
    private List<SingelPozitionWsheProgram> singelPozitionWsheProgramList;
    private int idPooblicRoom;
    private String setHourseStart,setProgramTIme , setProgram, setWash, setEndTime, dataRezervation;
    private AddFormWashhouseRezervationAdapter mAdapter;
    private List<String> listMachine;
    private List<String> listProgram;
    private ArrayAdapter<String> dataAdapterChouseWash, dataAdapterChoiceMahine;
    private  Calendar calendarSetStartTime;
    private Call<List<WaschingMachine>> getListWaschingMachineCall;
    private Call<List<WaschingMachineMode>> getListWashinMachineModeByIdCall;
    private List<WaschingMachine> waschingMachineList;
    private List<WaschingMachineMode> waschingMachineModeList;
    private List<SingelMoreDetalRoomApi> listMyRoom;
    private Call <List<SingelMoreDetalRoomApi>> getMyRoomCall;
    private SharedPreferences pref;

    private ApiService api;
    private FloatingActionButton floatingActionButtonInfo ;
    private Call<String> postRezervation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_washhouse, container, false);
        api = RetroClient.getApiService();
        floatingActionButtonInfo = (FloatingActionButton) view.findViewById(R.id.floatingActionButtonInformation);
        singelPozitionWsheProgramList = new ArrayList<SingelPozitionWsheProgram>();
        buttonAddProgram = (Button) view.findViewById(R.id.imageButtonAddProgramFOrm);
        buttonSetTime =  (Button) view.findViewById(R.id.buttonSetTimeRezervationWashe);
        buttonAddRezervation = (Button) view.findViewById(R.id.buttonSaveRezervationWashhousePeople);
        buttonClear = (Button) view.findViewById(R.id.buttonCleenRezervation);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewListProgramWash);
        textViewChoseTime = (TextView) view.findViewById(R.id.textViewTimeRezervationWashe);

        pref = getActivity().getApplicationContext().getSharedPreferences(KeepKey.KEY_NAME_SHARED, MODE_PRIVATE); // 0 - for private mode
        listMyRoom = new ArrayList<SingelMoreDetalRoomApi>();

        getMyRoomCall = (Call<List<SingelMoreDetalRoomApi>>) api.getAllListRoomMy(RetroClient.getHeadersMap(getActivity()),pref.getString(KeepKey.KEY_ROOM_USER, "0") );
        getMyRoomCall.enqueue(new Callback<List<SingelMoreDetalRoomApi>>() {
            @Override
            public void onResponse(Call<List<SingelMoreDetalRoomApi>> call, Response<List<SingelMoreDetalRoomApi>> response) {
                if(response.isSuccessful()){
                    if(!response.body().isEmpty()){
                        listMyRoom = response.body();
                        Toast.makeText(getActivity().getApplicationContext(), "Popralem Liste Pokoji dla tego pietra", Toast.LENGTH_SHORT).show();
                        for (SingelMoreDetalRoomApi position:
                                listMyRoom
                        ) {if(position.getProperty().getType().equalsIgnoreCase("LAUNDRY")){
                            idPooblicRoom=position.getId();
                            Toast.makeText(getActivity().getApplicationContext(),"Id pomieszcenia "+idPooblicRoom,Toast.LENGTH_SHORT).show();
                        }

                        }

                    }
                    else{
                        Toast.makeText(getActivity().getApplicationContext(), "Niestety twoj pokoj niema pomieszcen", Toast.LENGTH_SHORT).show();

                    }


                }


            }

            @Override
            public void onFailure(Call<List<SingelMoreDetalRoomApi>> call, Throwable t) {

            }
        });

        //Toast.makeText(getActivity().getApplicationContext(),"Id pomieszcenia "+idPooblicRoom,Toast.LENGTH_SHORT).show();




        mAdapter = new AddFormWashhouseRezervationAdapter(getContext(), singelPozitionWsheProgramList);
        setUpRecyclerView();







        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                SingelPozitionWsheProgram movie = singelPozitionWsheProgramList.get(position);
                Toast.makeText(getActivity().getApplicationContext(), movie.getTimeWash() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, final int position) {



            }
        }));

        spinnerChoseProgram = view.findViewById(R.id.spinnerListProgram);
        spinnerChoseWash = view.findViewById(R.id.spinnerListMachine);

        listMachine = new ArrayList<String>();
        listProgram = new ArrayList<String>();
        waschingMachineModeList = new ArrayList<WaschingMachineMode>();
        waschingMachineList = new ArrayList<WaschingMachine>();


        calendarSetStartTime = Calendar.getInstance();
        textViewChoseTime.setText(calendarSetStartTime.get(Calendar.HOUR_OF_DAY)+":"+calendarSetStartTime.get(Calendar.MINUTE));


        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        ;

        getListWaschingMachineCall = (Call<List<WaschingMachine>>) api.getListWaschingMachines(RetroClient.getHeadersMap(getActivity()));
        getListWaschingMachineCall.enqueue(new Callback<List<WaschingMachine>>() {
            @Override
            public void onResponse(Call<List<WaschingMachine>> call, Response<List<WaschingMachine>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity().getApplicationContext(), response.code() + "\n" + response.message(), Toast.LENGTH_SHORT).show();
                    if (waschingMachineList.isEmpty()) {
                        waschingMachineList = response.body();
                        Toast.makeText(getActivity().getApplicationContext(), "pobralem liste pralek +\n" + response.body().get(0).getName(), Toast.LENGTH_SHORT).show();
                        setUpMachineListString();
                    } else if (!waschingMachineList.isEmpty()) {
                        waschingMachineList.clear();
                        waschingMachineList = response.body();
                        listMachine.clear();
                        setUpMachineListString();

                    }
                }

                if(response.code()==401){
                    RetroClient.getNewToken(getActivity(),"pobieranie listy pralek");
                    getListWaschingMachineCall.clone();
                }



            }

            @Override
            public void onFailure(Call<List<WaschingMachine>> call, Throwable t) {


            }
        });





        dataAdapterChouseWash = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listProgram);
        dataAdapterChouseWash.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterChoiceMahine = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listMachine);
        dataAdapterChoiceMahine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerChoseWash.setAdapter(dataAdapterChoiceMahine);
        spinnerChoseProgram.setAdapter(dataAdapterChouseWash);







        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!singelPozitionWsheProgramList.isEmpty()) {
                    singelPozitionWsheProgramList.clear();
                }
                mAdapter.notifyDataSetChanged();
                textViewChoseTime.setText("");

            }
        });



        buttonAddRezervation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///TODO implementation saving using api
                JSONObject body = new JSONObject();

                try {


                    String patternData = "yyyy-MM-dd";
                    String patternHourse= "HH:mm:ss";
                    DateFormat dfHourse = new SimpleDateFormat(patternHourse);
                    DateFormat dfData = new SimpleDateFormat(patternData);
                    Date dateData = calendarSetStartTime.getTime();
                    dataRezervation = dfData.format(dateData);
                    setHourseStart = dfHourse.format(dateData);



                body.put("begin_time", dataRezervation+" "+setHourseStart);
                body.put("end_time", dataRezervation+" "+updatetime());
                body.put("public_room_id", idPooblicRoom);
                Map<String,String> headers = RetroClient.getHeadersMap(getActivity());
                RequestBody bodyJ = RequestBody.create(okhttp3.MediaType.parse("content-type:application/json"),body.toString());
                Toast.makeText(getActivity().getApplicationContext(),body.toString(),Toast.LENGTH_SHORT).show();

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

        buttonSetTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                int mHour,mMinute;

                mHour = calendarSetStartTime.get(Calendar.HOUR_OF_DAY);
                mMinute = calendarSetStartTime.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                calendarSetStartTime.set(calendarSetStartTime.get(Calendar.YEAR),calendarSetStartTime.get(Calendar.MONTH),calendarSetStartTime.get(Calendar.DATE), hourOfDay, minute);
                                textViewChoseTime.setText(hourOfDay + ":" + minute);
                                setHourseStart =hourOfDay + ":" + minute;
                            }
                        }, mHour, mMinute, true);
                Toast.makeText(getActivity().getApplicationContext(), setHourseStart, Toast.LENGTH_SHORT).show();
                timePickerDialog.setTitle("Wybierz poczotkowy czas rezerwacji");
                timePickerDialog.show();




            }
        });

        buttonAddProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(setProgramTIme ==null || setProgramTIme.isEmpty()){
                    Toast.makeText(getActivity(),getString(R.string.dos_not_set_hour),Toast.LENGTH_LONG).show();


                }

                if(setProgram==null || setProgram.isEmpty()){
                    Toast.makeText(getActivity(),getString(R.string.dos_not_set_program),Toast.LENGTH_LONG).show();

                }

                if(setWash==null || setWash.isEmpty()){
                    Toast.makeText(getActivity(),getString(R.string.dos_not_set_wash),Toast.LENGTH_LONG).show();

                }

                else if ( setProgramTIme !=null   &&  setProgram!=null   && setWash!=null ) {
                    SingelPozitionWsheProgram singel = new SingelPozitionWsheProgram(setWash, setProgram, setProgramTIme);
                    singelPozitionWsheProgramList.add(singel);
                    mAdapter.notifyItemInserted(singelPozitionWsheProgramList.size()-1);

                    recyclerView.refreshDrawableState();
                    Toast.makeText(getActivity(),getString(R.string.add_to_list_program_wash),Toast.LENGTH_LONG).show();
                }



            }
        });


        spinnerChoseProgram.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                setProgram = item;
                WaschingMachineMode mode = waschingMachineModeList.get(position);
                setProgramTIme = mode.getTime();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*
        zaimplementowac zmienianie sie adaptera wzgledem pralki
         */
        spinnerChoseWash.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                setWash = item;
                setUpModeMachineList(waschingMachineList.get(position));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        floatingActionButtonInfo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setIcon(android.R.drawable.ic_menu_info_details);
                alertDialogBuilder.setTitle("Informacja");
                alertDialogBuilder.setMessage("Czesc jestes w panelu doadawania rezerwacj pralni. \n"
                +"Wybierasz tutaj rodzaj praliki i program. \n" +
                        "Program sam oblicza koniec twojej rezerwacji za pomoca tego ile pralni wybierzesz\n" +
                        "Oto twoj czas: "+ updatetime());
                alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });



    }
    private void setUpModeMachineList(WaschingMachine washe ){

        getListWashinMachineModeByIdCall = (Call<List<WaschingMachineMode>>) api.getWaschingMode(RetroClient.getHeadersMap(getActivity()),washe.getId().toString());
        getListWashinMachineModeByIdCall.enqueue(new Callback<List<WaschingMachineMode>>() {
            @Override
            public void onResponse(Call<List<WaschingMachineMode>> call, Response<List<WaschingMachineMode>> response) {
                if(response.isSuccessful()) {
                    //Toast.makeText(getActivity().getApplicationContext(), response.message() + "\n" + "Pobieranie listy modow", Toast.LENGTH_SHORT).show();

                    waschingMachineModeList = response.body();
                    if(listProgram.isEmpty()){
                        for (WaschingMachineMode position :
                                waschingMachineModeList) {
                            listProgram.add(position.getName());
                        }
                        dataAdapterChouseWash.notifyDataSetChanged();
                    }
                    else if(!listProgram.isEmpty()){
                        listProgram.clear();
                        for (WaschingMachineMode position :
                                waschingMachineModeList) {
                            listProgram.add(position.getName());
                        }
                        dataAdapterChouseWash.notifyDataSetChanged();

                    }

                }

            }

            @Override
            public void onFailure(Call<List<WaschingMachineMode>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(),"Pobieranie listy modow \n"+t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void setUpMachineListString(){

        for (WaschingMachine position:
                waschingMachineList)
            {

                listMachine.add(position.getName());

            }
            dataAdapterChoiceMahine.notifyDataSetChanged();

    }

    private void setUpRecyclerView() {
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(mAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }



    private String updatetime(){
        Calendar localStart= new GregorianCalendar(calendarSetStartTime.get(Calendar.YEAR),calendarSetStartTime.get(Calendar.MONTH),calendarSetStartTime.get(Calendar.DATE),calendarSetStartTime.get(Calendar.HOUR_OF_DAY),calendarSetStartTime.get(Calendar.MINUTE),0);
        if(!singelPozitionWsheProgramList.isEmpty()) {


            int minute = localStart.get(Calendar.MINUTE);
            int hourse = localStart.get(Calendar.HOUR_OF_DAY);
            int secound= localStart.get(Calendar.SECOND);


            for (SingelPozitionWsheProgram singel : singelPozitionWsheProgramList
            ) {

                String myTime = singel.getTimeWash();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                Date date = null;
                try {
                    date = sdf.parse(myTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                localStart.add(Calendar.HOUR_OF_DAY,date.getHours());
                localStart.add(Calendar.MINUTE,date.getMinutes());
                localStart.add(Calendar.SECOND,date.getSeconds());

            }
            String pattern = "HH:mm:ss";
            DateFormat df = new SimpleDateFormat(pattern);
            Date sumTime = localStart.getTime();
            localStart = calendarSetStartTime;


            return df.format(sumTime);
        }
        else{
            String patternHourse= "HH:mm:ss";
            DateFormat dfHourse = new SimpleDateFormat(patternHourse);
            Date dateData = localStart.getTime();
            setEndTime = dfHourse.format(dateData);
            return dfHourse.format(dateData);
        }

    }



    ///TODO Implementing Swipe to Refresh
}
