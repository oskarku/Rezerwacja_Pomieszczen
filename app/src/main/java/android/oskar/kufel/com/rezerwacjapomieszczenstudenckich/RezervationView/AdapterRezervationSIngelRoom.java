package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RezervationView;

import android.content.Context;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
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

public class AdapterRezervationSIngelRoom extends  RecyclerView.Adapter<AdapterRezervationSIngelRoom.MyViewHolder> {
    private Context context;
    private List<SingelRezervation> listRezervation;

    public AdapterRezervationSIngelRoom(Context con, List<SingelRezervation> listRez){
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


    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_rezervation_card, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRezervationSIngelRoom.MyViewHolder myViewHolder, int position) {

        SingelRezervation singelRezervation = listRezervation.get(position);
        myViewHolder.textViewRoomRezervation.setText(singelRezervation.getNumberRoomRezervation());
        myViewHolder.textViewMonthofRezerwation.setText(singelRezervation.getMonthRezervation());
        myViewHolder.textVIewTitelRezerwation.setText(singelRezervation.getTitleRezervation());
        myViewHolder.textViewDayOfRezervation.setText(singelRezervation.getDayRezervation());

        myViewHolder.iconRezerwation.setImageResource(singelRezervation.getPicture());
        myViewHolder.iconMenuDetal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(myViewHolder.iconMenuDetal);
            }
        });


    }



    @Override
    public int getItemCount() {
        return listRezervation.size();
    }


    private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        /**
         * This method will be invoked when a menu item is clicked if the item
         * itself did not already handle the event.
         *
         * @param menuItem the menu item that was clicked
         * @return {@code true} if the event was handled, {@code false}
         * otherwise
         */
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {


            switch (menuItem.getItemId()) {
                case R.id.menu_rezervation_detal:
                    ///TODO : Zrobic alert tialog z szczegolami wydarzenia
                    Toast.makeText(context, "Tu wyswietla sie detale wiadomosci", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
}
