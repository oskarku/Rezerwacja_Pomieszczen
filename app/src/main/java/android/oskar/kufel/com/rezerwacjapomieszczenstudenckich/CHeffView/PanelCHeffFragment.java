package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.CHeffView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.MainActivity;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RecyclerTouchListener;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RezervationView.AdapterRezervationSIngelRoom;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RezervationView.SingelRezervation;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class PanelCHeffFragment extends Fragment {

    private Spinner spinnerChooseDisplayAccount;
    private RecyclerView recycrelViewListAccount;
    private FloatingActionButton floatButtonAddUser;
    private ArrayAdapter<CharSequence> adapter;
    private List<SingelPositionCardUser> listAcount;
    private AdapterRecycrelViewPanelCheff mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_panel_cheff, container, false);
        getActivity().setTitle("PANEL KIEROWNIKA");




        spinnerChooseDisplayAccount = view.findViewById(R.id.spinnerTypeAccontCheff);
        recycrelViewListAccount = view.findViewById(R.id.recycrelViewAcountCheff);
        floatButtonAddUser = view.findViewById(R.id.floatingActionButtonAddAcount);

        listAcount = new ArrayList<SingelPositionCardUser>();

        mAdapter = new AdapterRecycrelViewPanelCheff(getActivity(),listAcount, getActivity());





        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recycrelViewListAccount.setLayoutManager(mLayoutManager);
        recycrelViewListAccount.setItemAnimator(new DefaultItemAnimator());
        recycrelViewListAccount.setAdapter(mAdapter);




        uploadSimleDate();



        return view;
    }







    @Override
    public void onResume() {
        super.onResume();

        //Section about float buton add user

        floatButtonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment("register",new RegisterFragment());

            }
        });
//Section about spinner
        adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.Choose_type_account, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChooseDisplayAccount.setAdapter(adapter);





    }

    public void  switchFragment(String keyTag, Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack(keyTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.frame_layout_account, fragment);
        ft.addToBackStack(keyTag);
        ft.commit();
    }

    public void uploadSimleDate() {
        SingelPositionCardUser position;
        position = new SingelPositionCardUser("student", "ola123", "215", "516020041");
        listAcount.add(position);
        position = new SingelPositionCardUser("portier", "patryko ", "218", "579519226");
        listAcount.add(position);
        position = new SingelPositionCardUser("student", "patryk123", "215", "516020041");
        listAcount.add(position);

    }
}
