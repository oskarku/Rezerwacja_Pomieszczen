package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RezervationView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.AccountActivity;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
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
    private Activity activity;
    private List<SingelRezervation> listRezervation;

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


    private void showPopupMenu(View view, String startRezerwation, String endRezerwation) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_rezervation_card, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(startRezerwation, endRezerwation));
        popup.show();
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRezervationSIngelRoom.MyViewHolder myViewHolder, int position) {

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


//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setTitle(context.getString(R.string.title_info));
//                    builder.setMessage(context.getString(R.string.alert_dialog_info_detal_strat_hurse)+" "+start+"\n "+context.getString(R.string.alert_dialog_info_detal_end_hurse)+" "+ end);
//                    builder.setPositiveButton("OK", null);
//                    builder.show();


//                    final View bottomSheetLayout = activity.getLayoutInflater().inflate(R.layout.alert_dialog_detal_view, null);
//                    (bottomSheetLayout.findViewById(R.id.button_close)).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            mBottomSheetDialog.dismiss();
//                        }
//                    });
//                    (bottomSheetLayout.findViewById(R.id.button_ok)).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(getApplicationContext(), "Ok button clicked", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                    mBottomSheetDialog = new BottomSheetDialog(this);
//                    mBottomSheetDialog.setContentView(bottomSheetLayout);
//                    mBottomSheetDialog.show();



                    LayoutInflater layoutinflater = LayoutInflater.from(context);
                    View customizedUserView = layoutinflater.inflate(R.layout.alert_dialog_detal_view, null);
                    TextView startHour =(customizedUserView.findViewById(R.id.textViewStartRezerwationAlertDialog));
                    TextView endHout =(customizedUserView.findViewById(R.id.textViewEndRezerwationAlertDialog));
                    startHour.setText(start);
                    endHout.setText(end);


                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    alertDialogBuilder.setView(customizedUserView);

                    // don't do this !
                    // we have a customized header title
                    //alertDialogBuilder.setTitle("This app");

                    // all set and time to build and show up!
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();







                    return true;
                default:
            }
            return false;
        }
    }
}
