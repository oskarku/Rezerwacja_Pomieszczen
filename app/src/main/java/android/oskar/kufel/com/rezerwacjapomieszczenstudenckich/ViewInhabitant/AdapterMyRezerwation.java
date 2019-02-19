package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ViewInhabitant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork.RetroClient;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RezervationView.SingelRezervation;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.api.ApiService;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class AdapterMyrezerwation extends  RecyclerView.Adapter<AdapterMyrezerwation.MyViewHolder> {
    private Context context;
    private List<SingelRezervation> listRezervation;
    private Activity activity;
    private Call<String> delateRezervationCall;
    private ApiService api;

    public AdapterMyrezerwation(Context con, List<SingelRezervation> listRez, Activity acti){
        this.context = con;
        this.listRezervation= listRez;
        this.activity = acti;
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


    private void showPopupMenu(View view, String start, String end, int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        //TODO stworzyc menu z opcja wymiany
        inflater.inflate(R.menu.menu_my_rezervation_habitant, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(start, end, position));
        popup.show();
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {

        final SingelRezervation singelRezervation = listRezervation.get(position);
        myViewHolder.textViewRoomRezervation.setText(singelRezervation.getNumberRoomRezervation());
        myViewHolder.textViewMonthofRezerwation.setText(singelRezervation.getMonthRezervation());
        myViewHolder.textVIewTitelRezerwation.setText(singelRezervation.getTitleRezervation());
        myViewHolder.textViewDayOfRezervation.setText(singelRezervation.getDayRezervation());

        myViewHolder.iconRezerwation.setImageResource(singelRezervation.getPicture());
        myViewHolder.iconMenuDetal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopupMenu(myViewHolder.iconMenuDetal, singelRezervation.getHoursStart(), singelRezervation.getHoursEnd(),position);
            }
        });


    }

    public void removeItem(final int position) {
        if(!listRezervation.isEmpty()){
            api = RetroClient.getApiService();
            delateRezervationCall = (Call<String>) api.delateMyrezerwation(RetroClient.getHeadersMap(activity.getApplicationContext()), listRezervation.get(position).getIdRezervation());
            delateRezervationCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.code()==401){
                        RetroClient.getNewToken(activity,"usuwanie rezerwacji");
                        delateRezervationCall.clone();
                    }
                    else if(response.isSuccessful()){
                        listRezervation.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, listRezervation.size());
                        Toast.makeText(activity.getApplicationContext(),activity.getString(R.string.position_delate)+" \n" +
                                "rowniez na serwerze", Toast.LENGTH_SHORT).show();


                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
            }


        else {
            Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.donot_delete_list_empty), Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    public int getItemCount() {
        return listRezervation.size();
    }


    private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        private String start;
        private String end;
        private int positionRezerwation ;
        public MyMenuItemClickListener(String start, String end, int position){
            this.start = start;
            this.end = end ;
            this.positionRezerwation = position;

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {


            switch (menuItem.getItemId()) {
                case R.id.menu_my_rezervation_inhabit_detal:
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(context.getString(R.string.title_info));
                    builder.setMessage(context.getString(R.string.alert_dialog_info_detal_strat_hurse)+" "+start+"\n "+context.getString(R.string.alert_dialog_info_detal_end_hurse)+" "+ end+"\n" +
                            "data zakończenia"+ listRezervation.get(positionRezerwation).getEndDate() );
                    builder.setPositiveButton("OK", null);
                    builder.show();
                    return true;
                case R.id.menu_my_rezervation_delate:
                    removeItem(positionRezerwation);
                    return true;

                default:
            }
            return false;
        }
    }
}
