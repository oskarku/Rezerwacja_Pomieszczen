package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.CHeffView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class PanelCHeffFragment extends Fragment {

    private Spinner spinnerChooseDisplayAccount;
    private RecyclerView recycrelViewListAccount;
    private FloatingActionButton floatButtonAddUser;
    private ArrayAdapter<CharSequence> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_panel_cheff, container, false);
        spinnerChooseDisplayAccount = view.findViewById(R.id.spinnerTypeAccontCheff);
        recycrelViewListAccount = view.findViewById(R.id.recycrelViewAcountCheff);
        floatButtonAddUser = view.findViewById(R.id.floatingActionButtonAddAcount);


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        floatButtonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.Choose_type_account, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChooseDisplayAccount.setAdapter(adapter);







    }
}
