package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ViewInhabitant;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RecyclerTouchListener;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddWashhouseFragment extends Fragment {
    private Spinner spinnerChoseProgram, spinnerChoseWash;
    private Button buttonAddProgram, buttonAddRezervation, buttonClear, buttonSetTime;
    private TextView textViewChoseTime;
    private RecyclerView recyclerView;
    private List<SingelPozitionWsheProgram> singelPozitionWsheProgramList;
    private String setHourse, setProgram, setWash;
    private AddFormWashhouseRezervationAdapter mAdapter;
    private List<String> listMachine;
    private List<String> listProgram;
    private ArrayAdapter<String> dataAdapterChouseWash, dataAdapterChoiceMahine;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_washhouse, container, false);

        buttonAddProgram = view.findViewById(R.id.imageButtonAddProgramFOrm);
        buttonSetTime = view.findViewById(R.id.buttonSetTimeRezervationWashe);
        buttonAddRezervation = view.findViewById(R.id.buttonSaveRezervationWashhousePeople);
        buttonClear = view.findViewById(R.id.buttonCleenRezervation);
        recyclerView = view.findViewById(R.id.recyclerViewListDaylyRezervation);
        textViewChoseTime =view.findViewById(R.id.textViewTimeRezervationWashe);


        mAdapter = new AddFormWashhouseRezervationAdapter(singelPozitionWsheProgramList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        spinnerChoseProgram = view.findViewById(R.id.spinnerListProgram);
        spinnerChoseWash = view.findViewById(R.id.spinnerListMachine);

        listMachine = new ArrayList<String>();
        listProgram = new ArrayList<String>();


        return view;

    }

    @Override
    public void onResume() {
        super.onResume();


        dataAdapterChouseWash = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listProgram);
        dataAdapterChouseWash.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterChoiceMahine = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listMachine);
        dataAdapterChoiceMahine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        addList();


        spinnerChoseWash.setAdapter(dataAdapterChoiceMahine);
        spinnerChoseProgram.setAdapter(dataAdapterChouseWash);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                SingelPozitionWsheProgram movie = singelPozitionWsheProgramList.get(position);
                Toast.makeText(getActivity().getApplicationContext(), movie.getTimeWash() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, final int position) {

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialogBuilder.setMessage(getString(R.string.question_about_delate_position_list));
                        alertDialogBuilder.setPositiveButton(getString(R.string.yes),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        singelPozitionWsheProgramList.remove(position);
                                        Toast.makeText(getActivity(),getString(R.string.position_delate),Toast.LENGTH_LONG).show();
                                    }
                                });

                alertDialogBuilder.setNegativeButton(getString(R.string.no),new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     alertDialog.dismiss();
                    }
                });

                alertDialog.show();

            }
        }));




        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singelPozitionWsheProgramList.clear();
                textViewChoseTime.setText("");


            }
        });



        buttonAddRezervation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                                textViewChoseTime.setText(hourOfDay + ":" + minute);
                                setHourse=hourOfDay + ":" + minute;
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.setTitle("Wybierz poczotkowy czas rezerwacji");
                timePickerDialog.show();




            }
        });

        buttonAddProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(setHourse==null || setHourse.isEmpty()){
                    Toast.makeText(getActivity(),getString(R.string.dos_not_set_hour),Toast.LENGTH_LONG).show();

                }

                if(setProgram==null || setProgram.isEmpty()){
                    Toast.makeText(getActivity(),getString(R.string.dos_not_set_program),Toast.LENGTH_LONG).show();

                }

                if(setWash==null || setWash.isEmpty()){
                    Toast.makeText(getActivity(),getString(R.string.dos_not_set_wash),Toast.LENGTH_LONG).show();

                }

                else {
                    SingelPozitionWsheProgram singel = new SingelPozitionWsheProgram(setWash, setProgram, setHourse);
                    singelPozitionWsheProgramList.add(singel);
                }

                ///TODO zczytac z m spinera wybrane wartosci a nastepnie dodac do listy

            }
        });


        spinnerChoseProgram.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (item.equalsIgnoreCase("Szybkie_codzine")){
                    setHourse=setHourse+" +0,5";
                    setProgram=item;
                }
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

                setWash= item;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });



    }


    private void addList(){
        listProgram.add("Ecco");
        listProgram.add("Bawelna");
        listProgram.add("Szybkie_codzine");
        listProgram.add("Dla zwierzat");
        listProgram.add("Syntetyk");

        listMachine.add("Bosch");
        listMachine.add("Mastercook");


    }

    ///TODO Implementing Swipe to Refresh
}
