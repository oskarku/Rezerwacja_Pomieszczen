package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ViewInhabitant;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.RetroClient;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.SingelMoreDetalRoomApi;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.SingelRezervationPozitionApi;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RezervationView.SingelRezervation;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.api.ApiService;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.keep.KeepKey;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class MyRezervationFragment extends Fragment {


    private AdapterMyrezerwation mAdapter;
    private  RecyclerView recyclerView;
    private ArrayList<SingelRezervation> singelRezervationList;
    private LinearLayoutManager mLayoutManager;
    private SharedPreferences pref;
    private List<SingelMoreDetalRoomApi> listMyRoom;
    private Call<List<SingelMoreDetalRoomApi>> getMyRoomCall;
    private Integer idPooblicRoom;
    private ApiService api;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View wiev = inflater.inflate(R.layout.fragment_my_rezervation, container, false);
        getActivity().setTitle(getString(R.string.title_activity_my_rezerwation).toUpperCase());

        singelRezervationList = new ArrayList<SingelRezervation>();
        recyclerView = (RecyclerView) wiev.findViewById(R.id.recycrelViewMyRezervationInhabit);
        mAdapter = new AdapterMyrezerwation(getContext(),singelRezervationList, getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());



        api = RetroClient.getApiService();




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
                        uploadDateListRezervation ();


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

        recyclerView.setAdapter(mAdapter);








        return wiev;
    }

    private void uploadDateListRezervation (){







        api = RetroClient.getApiService();
        Call<List<SingelRezervationPozitionApi>> getMyRezervationListCall;
        getMyRezervationListCall = (Call<List<SingelRezervationPozitionApi>>) api.getMyRezervationList(RetroClient.getHeadersMap(getActivity().getApplicationContext()));
        getMyRezervationListCall.enqueue(new Callback<List<SingelRezervationPozitionApi>>() {
            @Override
            public void onResponse(Call<List<SingelRezervationPozitionApi>> call, Response<List<SingelRezervationPozitionApi>> response) {
                if(response.code()==401 ){
                    RetroClient.getNewToken(getActivity(),"Lista moje rezerwacje");
                    call.clone();
                }
                else if (response.isSuccessful()){
                    if(!response.body().isEmpty()){
                        for (final SingelRezervationPozitionApi position:
                             response.body()) {
                            SingelRezervation rezervationSee = new SingelRezervation();

                            for (SingelMoreDetalRoomApi positionRoom :
                                    listMyRoom) {
                                if (positionRoom.getId() == position.getPublicRoomId()){
                                    rezervationSee.setTitleRezervation(positionRoom.getProperty().getType()+" rezervation");
                                }
                            }

                            rezervationSee.setIdRezervation(position.getId());

                            String stringBegin = position.getBeginTime();
                            String endTime = position.getEndTime();
                            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date dateBegin = null;
                            Date dateEnd = null;
                            try {
                                dateBegin = format.parse(stringBegin);
                                dateEnd = format.parse(endTime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            rezervationSee.setMonthRezervation(getMontchwithNumber(dateBegin.getMonth()));
                            rezervationSee.setDayRezervation(""+dateBegin.getDate());
                            rezervationSee.setHoursStart(""+dateBegin.getHours()+":"+dateBegin.getMinutes());
                            rezervationSee.setHoursEnd(""+dateEnd.getHours()+":"+dateEnd.getMinutes());
                            rezervationSee.setNumberRoomRezervation(pref.getString(KeepKey.KEY_ROOM_USER, "100"));

                            singelRezervationList.add(rezervationSee);
                            mAdapter.notifyDataSetChanged();





                        }
                    }


                }
            }

            @Override
            public void onFailure(Call<List<SingelRezervationPozitionApi>> call, Throwable t) {

            }
        });



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


}
