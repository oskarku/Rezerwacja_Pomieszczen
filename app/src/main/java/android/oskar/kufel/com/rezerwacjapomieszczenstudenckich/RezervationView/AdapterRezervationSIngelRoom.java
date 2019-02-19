package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RezervationView;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;

public class AdapterRezervationSIngelRoom extends  RecyclerView.Adapter<AdapterRezervationSIngelRoom.MyViewHolder> {
    private Context context;
    private Activity activity;
    private List<SingelRezervation> listRezervation;
    private LayoutInflater layoutinflater;
    private View customizedUserView;
    private TextView startHourDialog, endHourDialog, titleRezerwationDialog, endDateRezervation;
    private Button cancelButtonAlertDetal;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;


    public AdapterRezervationSIngelRoom(Context con, List<SingelRezervation> listRez, Activity activity){
        this.context = con;
        this.activity = activity;
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


    private void showPopupMenu(View view, String startRezerwation, String endRezerwation, String titleRezerwation, Boolean isKey, Integer posit) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_rezervation_card, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(startRezerwation, endRezerwation, titleRezerwation, isKey, posit));
        popup.show();
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRezervationSIngelRoom.MyViewHolder myViewHolder, final int position) {

        final SingelRezervation singelRezervation = listRezervation.get(position);
        myViewHolder.textViewRoomRezervation.setText(singelRezervation.getNumberRoomRezervation());
        myViewHolder.textViewMonthofRezerwation.setText(singelRezervation.getMonthRezervation());
        myViewHolder.textVIewTitelRezerwation.setText(singelRezervation.getTitleRezervation());
        myViewHolder.textViewDayOfRezervation.setText(singelRezervation.getDayRezervation());

        myViewHolder.iconRezerwation.setImageResource(singelRezervation.getPicture());
        myViewHolder.iconMenuDetal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(myViewHolder.iconMenuDetal, singelRezervation.getHoursStart(), singelRezervation.getHoursEnd(), singelRezervation.getTitleRezervation(), singelRezervation.getKeyInPortier(), position);
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
        private String title;
        private Boolean isKey;
        private Integer position;
        public MyMenuItemClickListener (String start, String end, String title, Boolean isKey, Integer position){
            this.start = start;
            this.end = end ;
            this.title = title;
            this.isKey = isKey;
            this.position = position;


        }


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
                    menuItem.getActionProvider();

                    layoutinflater = LayoutInflater.from(context);
                    customizedUserView = layoutinflater.inflate(R.layout.alert_dialog_detal_view, null);
                    startHourDialog =(customizedUserView.findViewById(R.id.textViewStartRezerwationAlertDialog));
                    endHourDialog =(customizedUserView.findViewById(R.id.textViewEndRezerwationAlertDialog));
                    titleRezerwationDialog = (customizedUserView.findViewById(R.id.textViewTitleRezerwationAlertDialog));
                    cancelButtonAlertDetal =(customizedUserView.findViewById(R.id.buttonCancelAlertDetal));
                    endDateRezervation = (TextView)(customizedUserView.findViewById(R.id.textViewDateEndAlertDialog));

                    endDateRezervation.setText(listRezervation.get(position).getEndDate());
                    titleRezerwationDialog.setText(title);
                    startHourDialog.setText(start);
                    endHourDialog.setText(end);


                    cancelButtonAlertDetal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();

                        }
                    });


                    alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setView(customizedUserView);



                    alertDialog = alertDialogBuilder.create();
                    alertDialog.show();



                    return true;
                default:
            }
            return false;
        }
    }
}
