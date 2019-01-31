package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.CHeffView;

import android.content.Context;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterRecycrelViewPanelCheff extends RecyclerView.Adapter<AdapterRecycrelViewPanelCheff.MyViewHolder> {
private Context context;
private List<SingelPositionCardUser> listUser;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username, numberRoom, typAccount;
        public Button callTOUser, setingUser,delateUser;
        public ImageView avatarUser;

        public MyViewHolder(@NonNull View view) {
            super(view);
            username = view.findViewById(R.id.textViewNameUserCardCheff);
            numberRoom =


        }
    }



    @NonNull
    @Override
    public AdapterRecycrelViewPanelCheff.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecycrelViewPanelCheff.MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
