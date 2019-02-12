package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RezervationView;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.AccountActivity;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ViewInhabitant.AddWashhouseFragment;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ViewInhabitant.CalendarFragment;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ViewInhabitant.RezervtionOtherSalFragment;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.keep.KeepKey;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AddRezervationFragment extends Fragment {

    private Spinner spinner;
    private String[] categoriesSpinner;
    private Resources resourcesAndroidXML;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_rezervation, container, false);
        getActivity().setTitle(getString(R.string.title_activity_add_rezerwation).toUpperCase());

        resourcesAndroidXML = getResources();

        /*
        Ponizej znajduje sie przepisanuie listy z sources
        <string-array name="type_rezerwation">
            <item>Zarezerwuj Pralnie</item>
            <item>Zarezerwuj Sale TV</item>
            <item>Zarezerwuj Sale Gier i  Zabaw</item>
        </string-array>
         */
        categoriesSpinner = resourcesAndroidXML.getStringArray(R.array.type_rezerwation);

        // Spinner element
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_add_rezervation);

        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();

                if(categoriesSpinner[0].equalsIgnoreCase(item)){

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.popBackStack(KeepKey.KEY_FRAGMENT_WASHHOUSE_ADD_EVENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame_layout_add_rezerwation, new AddWashhouseFragment());
                    ft.addToBackStack(KeepKey.KEY_FRAGMENT_WASHHOUSE_ADD_EVENT);
                    ft.commit();


                }

                else {



                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.popBackStack(KeepKey.KEY_FRAGMENT_OTHER_REZERVATION, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame_layout_add_rezerwation, new RezervtionOtherSalFragment());
                    ft.addToBackStack(KeepKey.KEY_FRAGMENT_OTHER_REZERVATION);
                    ft.commit();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });








        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categoriesSpinner);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);



        return view;
    }





}


