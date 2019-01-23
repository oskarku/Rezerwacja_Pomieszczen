package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ViewInhabitant;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RezervationView.AdapterRezervationSIngelRoom;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RezervationView.AddRezervationFragment;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RezervationView.SingelRezervation;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.keep.KeepKey;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterRezervationSIngelRoom mAdapter;
    private List<SingelRezervation> singelRezervationList;
    private CalendarView calendarView;
    private FloatingActionButton fab, fab2;


    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);


        Long currentTime = Calendar.getInstance().getTime().getTime();

        calendarView= (CalendarView) view.findViewById(R.id.calendar_view_habbit);
        calendarView.setDate(currentTime);


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


        singelRezervationList = new ArrayList<SingelRezervation>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewListDaylyRezervation);
        mAdapter = new AdapterRezervationSIngelRoom(getContext(),singelRezervationList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        addSimpleDate();



        return view;
    }

    public void  addSimpleDate(){

        SingelRezervation singelRezervation = new SingelRezervation("Pralnia 2N", "21","Maj","215");
        singelRezervationList.add(singelRezervation);
        singelRezervation = new SingelRezervation("Pralnia 2N", "20","Czerwiec","215");
        singelRezervationList.add(singelRezervation);
        singelRezervation = new SingelRezervation("Pralnia 2N", "2","Luty","215");
        singelRezervationList.add(singelRezervation);
        singelRezervation = new SingelRezervation("Pralnia 1N", "8","Marzec","210");
        singelRezervationList.add(singelRezervation);
        singelRezervation = new SingelRezervation("Pralnia 4N", "20","Kwiecien","218");
        singelRezervationList.add(singelRezervation);
        singelRezervation = new SingelRezervation("Pralnia 3N", "20","Czerwiec","215");
        singelRezervationList.add(singelRezervation);

    }

}
