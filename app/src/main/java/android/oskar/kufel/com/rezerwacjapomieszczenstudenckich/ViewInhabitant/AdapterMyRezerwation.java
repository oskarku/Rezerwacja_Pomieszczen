package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ViewInhabitant;

import android.app.AlertDialog;
import android.content.Context;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RezervationView.SingelRezervation;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

class AdapterMyrezerwation extends  RecyclerView.Adapter<AdapterMyrezerwation.MyViewHolder> {
    private Context context;
    private List<SingelRezervation> listRezervation;

    public AdapterMyrezerwation(Context con, List<SingelRezervation> listRez){
        this.context = con;
        this.listRezervation= listRez;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView textVIewTitelRezerwation, textViewDayOfRezervation, textViewMonthofRezerwation, textViewRoomRezervation;
        public ImageView iconRezerwation, iconMenuDetal;




        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDayOfRezervation= (TextView) itemView.findViewById(R.id.textViewDateRezerwation);
            textVIewTitelRezerwation= (TextView) itemView.findViewById(R.id.textViewTitleRezerwation);
            textViewMonthofRezerwation = (TextView) itemView.findViewById(R.id.textViewMonthRezerwation);
            textViewRoomRezervation = (TextView) itemView.findViewById(R.id.textViewRommListRezerwation);

            iconRezerwation = (ImageView) itemView.findViewById(R.id.imageViewImageTypeRezerwationList);
            iconMenuDetal = (ImageView) itemView.findViewById(R.id.imageViewMenuRezerwation);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cards_layout_event, viewGroup, false);
        return new MyViewHolder(itemView);
    }


    private void showPopupMenu(View view, String start, String end) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        //TODO stworzyc menu z opcja wymiany
        inflater.inflate(R.menu.menu_my_rezervation_habitant, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(start, end));
        popup.show();
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position) {

        final SingelRezervation singelRezervation = listRezervation.get(position);
        myViewHolder.textViewRoomRezervation.setText(singelRezervation.getNumberRoomRezervation());
        myViewHolder.textViewMonthofRezerwation.setText(singelRezervation.getMonthRezervation());
        myViewHolder.textVIewTitelRezerwation.setText(singelRezervation.getTitleRezervation());
        myViewHolder.textViewDayOfRezervation.setText(singelRezervation.getDayRezervation());

        myViewHolder.iconRezerwation.setImageResource(singelRezervation.getPicture());
        myViewHolder.iconMenuDetal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopupMenu(myViewHolder.iconMenuDetal, singelRezervation.getHoursStart(), singelRezervation.getHoursEnd());
            }
        });


    }



    @Override
    public int getItemCount() {
        return listRezervation.size();
    }


    private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        private String start;
        private String end;
        public MyMenuItemClickListener (String start, String end){
            this.start = start;
            this.end = end ;

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {


            switch (menuItem.getItemId()) {
                case R.id.menu_my_rezervation_inhabit_detal:
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(context.getString(R.string.title_info));
                    builder.setMessage(context.getString(R.string.alert_dialog_info_detal_strat_hurse)+" "+start+"\n "+context.getString(R.string.alert_dialog_info_detal_end_hurse)+" "+ end);
                    builder.setPositiveButton("OK", null);
                    builder.show();
                    return true;
                case R.id.menu_my_rezervation_inhabit_change:
                    ///TODO : Zaimplementowac rozwizanie z wymiana pokoji dialog albo fragment
                    Toast.makeText(context, "Tu wymienisz sie rezerwacjami", Toast.LENGTH_SHORT).show();
                    return true;

                default:
            }
            return false;
        }
    }
}
