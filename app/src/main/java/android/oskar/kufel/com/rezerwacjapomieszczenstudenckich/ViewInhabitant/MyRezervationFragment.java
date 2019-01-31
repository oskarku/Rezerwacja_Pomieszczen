package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ViewInhabitant;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RezervationView.SingelRezervation;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;

import java.util.ArrayList;
import java.util.List;


public class MyRezervationFragment extends Fragment {


    private AdapterMyrezerwation mAdapter;
    private  RecyclerView recyclerView;
    private ArrayList<SingelRezervation> singelRezervationList;
    private LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View wiev = inflater.inflate(R.layout.fragment_my_rezervation, container, false);
        getActivity().setTitle(getString(R.string.title_activity_my_rezerwation).toUpperCase());

        singelRezervationList = new ArrayList<SingelRezervation>();
        recyclerView = (RecyclerView) wiev.findViewById(R.id.recycrelViewMyRezervationInhabit);
        mAdapter = new AdapterMyrezerwation(getContext(),singelRezervationList);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        return wiev;
    }

    private void uploadDateListRezervation (){
        ///TODO ogarnac api ktore zraca mi liste moich rezerwacji

    }


}
