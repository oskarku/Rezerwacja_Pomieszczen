package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ViewInhabitant;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RecyclerTouchListener;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.SwipeToDeleteCallback;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import java.util.zip.Inflater;


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


        singelPozitionWsheProgramList = new ArrayList<SingelPozitionWsheProgram>();

        buttonAddProgram = (Button) view.findViewById(R.id.imageButtonAddProgramFOrm);
        buttonSetTime =  (Button) view.findViewById(R.id.buttonSetTimeRezervationWashe);
        buttonAddRezervation = (Button) view.findViewById(R.id.buttonSaveRezervationWashhousePeople);
        buttonClear = (Button) view.findViewById(R.id.buttonCleenRezervation);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewListProgramWash);
        textViewChoseTime = (TextView) view.findViewById(R.id.textViewTimeRezervationWashe);

        addToRecycrelView();


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

                else if ( setHourse!=null   &&  setProgram!=null   && setWash!=null ) {
                    SingelPozitionWsheProgram singel = new SingelPozitionWsheProgram(setWash, setProgram, setHourse);
                    singelPozitionWsheProgramList.add(singel);
                    mAdapter.notifyItemInserted(singelPozitionWsheProgramList.size()-1);

                    recyclerView.refreshDrawableState();
                    Toast.makeText(getActivity(),getString(R.string.add_to_list_program_wash),Toast.LENGTH_LONG).show();
                }



            }
        });


        spinnerChoseProgram.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                /*
                stworzayc tablice z progrmami ktora bedzie aktualizowana z bazy danych
                 */
                if (item.equalsIgnoreCase("Szybkie_codzine")){
                    setHourse=setHourse+" +0,5";
                    setProgram=item;
                }
                setProgram=item;
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

                setWash=item;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });



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


    private void addList(){
        listProgram.add("Ecco");
        listProgram.add("Bawelna");
        listProgram.add("Szybkie_codzine");
        listProgram.add("Dla zwierzat");
        listProgram.add("Syntetyk");


        listMachine.add("Bosch");
        listMachine.add("Mastercook");


    }

    private void addToRecycrelView(){
        SingelPozitionWsheProgram singel = new SingelPozitionWsheProgram("wirpool","cooton","15:00");
        singelPozitionWsheProgramList.add(singel);
        singel = new SingelPozitionWsheProgram("bosh","lol","15:00");
        singelPozitionWsheProgramList.add(singel);

    }

    ///TODO Implementing Swipe to Refresh
}
