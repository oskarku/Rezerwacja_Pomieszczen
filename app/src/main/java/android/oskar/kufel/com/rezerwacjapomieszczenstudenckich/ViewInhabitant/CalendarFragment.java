package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ViewInhabitant;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.widget.CalendarView;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {

    private CalendarView calendarView;
    private FloatingActionButton fab;


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

       fab = view.findViewById(R.id.floatingActionButtonInfoCalendar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Nacisnij na dzien w klendarzu a przeniesie cie do listy z rezerwacjami jakie są w danym dniu :) ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });









        return view;
    }

}
