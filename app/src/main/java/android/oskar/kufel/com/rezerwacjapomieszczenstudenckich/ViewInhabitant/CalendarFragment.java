package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ViewInhabitant;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.AccountActivity;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.DateMe;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.Property;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.RetroClient;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.SingelMoreDetalRezervation;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.SingelMoreDetalRoomApi;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.SingelRezervationPozitionApi;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.MainActivity;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RezervationView.AdapterRezervationSIngelRoom;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RezervationView.AddRezervationFragment;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RezervationView.SingelRezervation;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.api.ApiService;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.keep.KeepKey;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.widget.CalendarView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CalendarFragment extends Fragment {


    private RecyclerView recyclerView;
    private AdapterRezervationSIngelRoom mAdapter;
    private List<SingelRezervation> singelRezervationList;
    private CalendarView calendarView;
    private FloatingActionButton fab, fab2;

    private Call<List<SingelRezervationPozitionApi>> callBackGetListRezervationonDay;
    private Call<List<SingelMoreDetalRoomApi>> callBackGetAllListRoom;
    private ApiService api;
    private ProgressDialog pDialog;
    //Lista dostepnych pokoi
    private List<SingelMoreDetalRoomApi> listDetalRoomApi;
    //Lista pobranych rezerwaji wedlug daty
    private List<SingelRezervationPozitionApi> listSingelRezervationPozitionData;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;



    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        getActivity().setTitle(getString(R.string.title_activity_calendar_rezerwation).toUpperCase());

        //Zainicjalizowanie listy pojedynczych rezerwacji

        singelRezervationList = new ArrayList<SingelRezervation>();


        api= RetroClient.getApiService();

        preferences = getActivity().getApplicationContext().getSharedPreferences(KeepKey.KEY_NAME_SHARED, Context.MODE_PRIVATE);
        editor = preferences.edit();


        /***
         *Pobieranie listy pokoi
         *
         */

        callBackGetAllListRoom =(Call<List<SingelMoreDetalRoomApi>>)  api.getAllListRoom(RetroClient.getHeadersMap(getActivity().getApplicationContext()));
        callBackGetAllListRoom.enqueue(new Callback<List<SingelMoreDetalRoomApi>>() {
            @Override
            public void onResponse(Call<List<SingelMoreDetalRoomApi>> call, Response<List<SingelMoreDetalRoomApi>> response) {
                if(response.isSuccessful() && (response.code()!=401)){
                    Log.i("pobieranie_listy_sali", "Jestem w liscie");
                    listDetalRoomApi=response.body();

                }
                else if(response.code()==401){
                    RetroClient.getNewToken(getActivity().getApplicationContext(), "lista sal");
                    callBackGetAllListRoom.clone();
                }

            }

            @Override
            public void onFailure(Call<List<SingelMoreDetalRoomApi>> call, Throwable t) {
                Log.e("bledy_pokoi", t.getMessage());


            }
        });


        //ustaw date
        Long currentTime = Calendar.getInstance().getTime().getTime();


        calendarView= (CalendarView) view.findViewById(R.id.calendar_view_habbit);
        calendarView.setShowWeekNumber(true);
        calendarView.setDate(currentTime);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, final int year, final int month, final int dayOfMonth) {

                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = formatter.parse(year+"-"+(month+1)+"-"+dayOfMonth);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String formattedDateString = formatter.format(date);
                editor.putString(KeepKey.KEY_DATA_CHOSE_USER, formattedDateString);
                editor.commit();




                //Wyswietlenie Progres Dialog
                setDialogShow();

                // przyklad pentli po liscie obiektu for (SingelRezervationPozitionApi position : response.body())

                /***
                 * Pobranie listy rezerwacji wedlug daty
                 */

                callBackGetListRezervationonDay = (Call<List<SingelRezervationPozitionApi>>) api.getListRezervationOnData(RetroClient.getHeadersMap(getActivity().getApplicationContext()),formattedDateString);
                callBackGetListRezervationonDay.enqueue(new Callback<List<SingelRezervationPozitionApi>>() {
                    @Override
                    public void onResponse(Call<List<SingelRezervationPozitionApi>> call, Response<List<SingelRezervationPozitionApi>> response) {
                        if(response.code()==401){
                            RetroClient.getNewToken(getActivity().getApplicationContext(), "Lista rezerwacji na dzien");
                            callBackGetListRezervationonDay.clone();
                        }
                        else if(response.isSuccessful() ) {
                            if (response.body().size() > 0) {

                                if (listSingelRezervationPozitionData == null) {

                                    listSingelRezervationPozitionData = response.body();
                                } else if (!listSingelRezervationPozitionData.isEmpty()) {
                                    listSingelRezervationPozitionData.clear();
                                    listSingelRezervationPozitionData = response.body();
                                }

                                setUpRoomTOListRezevation();

                                pDialog.dismiss();

                            }
                            else if (response.body().size()==0){
                                pDialog.dismiss();
                                Toast.makeText(getActivity().getApplicationContext(), "nie ma rezereacji", Toast.LENGTH_SHORT).show();
                                if(!singelRezervationList.isEmpty()){
                                    singelRezervationList.clear();
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<List<SingelRezervationPozitionApi>> call, Throwable t) {
                        pDialog.dismiss();
                        Log.e("down_list_by_day", t.getMessage());

                    }
                });


            }
        });




        ///TODO Zrobic listener ktory ustawia mi liste z danego dnia

       fab = view.findViewById(R.id.floatingActionButtonInfoCalendar);
       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Informacja");
                builder.setMessage(getString(R.string.describe_using_calendar_fragment));
                builder.setCancelable(true);
                builder.show();
            }



        });



        //float button slurzocy do przeniesienia mnie w ogolny
        fab2 = view.findViewById(R.id.floatingActionButtonAddEvent);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack(KeepKey.KEY_FRAGMENT_GENERALY_ADD_EVENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_layout_account, new AddRezervationFragment());
                ft.addToBackStack(KeepKey.KEY_FRAGMENT_GENERALY_ADD_EVENT);
                ft.commit();

            }
        });




        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewListDaylyRezervation);
        mAdapter = new AdapterRezervationSIngelRoom(getContext(),singelRezervationList, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

       // addSimpleDate();



        return view;
    }





    public void setUpRoomTOListRezevation(){
        if (listSingelRezervationPozitionData!=null){
            // przyklad pentli po liscie obiektu for (SingelRezervationPozitionApi position : response.body())
            /*
            Ponizej warunek ktory sprawdz czy lista wyswietlana jest pusta czy tez nie wiec przy kazdym klikniecu jest ona czyszczona

             */
            if(!singelRezervationList.isEmpty()){
                singelRezervationList.clear();
                mAdapter.notifyDataSetChanged();
            }

            List<SingelMoreDetalRezervation> listDetalRezervationMore = new ArrayList<SingelMoreDetalRezervation>();


            //petla po liscie rezerwacji
            for ( int i = 0; i<listSingelRezervationPozitionData.size(); i++){

                final SingelRezervationPozitionApi position = listSingelRezervationPozitionData.get(i);


                Call<SingelMoreDetalRezervation> getMoreRezervation  = api.getMoreDetalRezervation(RetroClient.getHeadersMap(getActivity()),position.getId());
                getMoreRezervation.enqueue(new Callback<SingelMoreDetalRezervation>() {
                    @Override
                    public void onResponse(Call<SingelMoreDetalRezervation> call, Response<SingelMoreDetalRezervation> response) {
                        Call<DateMe> getUser = api.getDataUser(RetroClient.getHeadersMap(getActivity()),response.body().getUserId());
                        getUser.enqueue(new Callback<DateMe>() {
                            @Override
                            public void onResponse(Call<DateMe> call, Response<DateMe> response) {

                                SingelRezervation positionI = new SingelRezervation();

                                for (SingelMoreDetalRoomApi positionRoom: listDetalRoomApi) {
                                    if(positionRoom.getId()==position.getPublicRoomId()) {
                                        positionI = new SingelRezervation(positionRoom.getProperty().getType()+"Rezervation ","","","");

                                    }
                                }
                                positionI.setNumberRoomRezervation(response.body().getRoom().toString());
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String beginTime=position.getBeginTime();
                                String endTime=position.getEndTime();
                                Toast.makeText(getActivity().getApplicationContext(), endTime+"\n"+beginTime,Toast.LENGTH_SHORT).show();

                                try {

                                    Date dateBegin = formatter.parse(beginTime);
                                    Date dateEnd = formatter.parse(endTime);
                                    positionI.setMonthRezervation(getMontchwithNumber(dateBegin.getMonth()));
                                    positionI.setDayRezervation(""+dateBegin.getDate());
                                    positionI.setHoursEnd(""+dateEnd.getHours()+":"+dateEnd.getMinutes());
                                    positionI.setHoursStart(""+dateBegin.getHours()+":"+dateBegin.getMinutes());



                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                singelRezervationList.add(positionI);
                                mAdapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onFailure(Call<DateMe> call, Throwable t) {

                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<SingelMoreDetalRezervation> call, Throwable t) {

                    }
                });

            }
        }
    }

    public String getMontchwithNumber (int number){

        switch (number) {
            case 0:
                return "Styczeń";
            case 1:
                return "Luty";
            case 2:
                return "Marzec";
            case 3:
                return "Kwiecien";
            case 4 :
                return "Maj";
            case 5:
                return "Czerwiec";
            case 6:
                return "Lipiec";
            case 7:
                return"Sierpien";
            case 8:
                return"Wrzesien";
            case 9:
                return "Pazdziernik";
            case 10:
                return "Listopad";
            case 11:
                return "Grudzien";

                default:{
                    return "miesiac";
                }
        }

    }

    public void  addSimpleDate(){

        SingelRezervation singelRezervation = new SingelRezervation("Pralnia 2N", "21","Maj","215");
        singelRezervation.setHoursStart("15:00");
        singelRezervation.setHoursEnd("23:00");
        singelRezervationList.add(singelRezervation);
        singelRezervation = new SingelRezervation("Pralnia 2N", "20","Czerwiec","215");
        singelRezervation.setHoursStart("12:00");
        singelRezervation.setHoursEnd("20:00");
        singelRezervationList.add(singelRezervation);
        singelRezervation = new SingelRezervation("Pralnia 2N", "2","Luty","215");
        singelRezervation.setHoursStart("15:00");
        singelRezervation.setHoursEnd("18:00");
        singelRezervationList.add(singelRezervation);
        singelRezervation = new SingelRezervation("Pralnia 1N", "8","Marzec","210");
        singelRezervation.setHoursStart("12:00");
        singelRezervation.setHoursEnd("22:00");
        singelRezervationList.add(singelRezervation);
        singelRezervation = new SingelRezervation("Pralnia 4N", "20","Kwiecien","218");
        singelRezervation.setHoursStart("10:00");
        singelRezervation.setHoursEnd("20:00");
        singelRezervationList.add(singelRezervation);
        singelRezervation = new SingelRezervation("Pralnia 3N", "20","Czerwiec","215");
        singelRezervation.setHoursStart("11:00");
        singelRezervation.setHoursEnd("19:00");
        singelRezervationList.add(singelRezervation);

    }

    private void setDialogShow(){
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage(getString(R.string.dialog_loading_rezerwation));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

}
