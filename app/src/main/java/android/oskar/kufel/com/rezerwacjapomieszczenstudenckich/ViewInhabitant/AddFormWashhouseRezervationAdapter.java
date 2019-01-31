package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ViewInhabitant;

import android.content.Context;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.AccountActivity;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AddFormWashhouseRezervationAdapter extends RecyclerView.Adapter<AddFormWashhouseRezervationAdapter.MyViewHolder> {

    private List<SingelPozitionWsheProgram> singelPozitionWsheProgramList;
    private Context context;

    public Context getContext() {
        return context;
    }

    public AddFormWashhouseRezervationAdapter(Context con, List<SingelPozitionWsheProgram> list){
        this.context = con;
        this.singelPozitionWsheProgramList=list;
    }
    @NonNull
    @Override
    public AddFormWashhouseRezervationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.one_position_program_washhouse, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddFormWashhouseRezervationAdapter.MyViewHolder myViewHolder, int position) {
        SingelPozitionWsheProgram singelPozitionWsheProgram = singelPozitionWsheProgramList.get(position);
        myViewHolder.timeWash.setText(singelPozitionWsheProgram.getTimeWash());
        myViewHolder.programWash.setText(singelPozitionWsheProgram.getNameProgram());
        myViewHolder.wash.setText(singelPozitionWsheProgram.getNameWash());


    }

    public void removeItem(int position) {
        singelPozitionWsheProgramList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, singelPozitionWsheProgramList.size());

        Toast.makeText(getContext(),getContext().getString(R.string.position_delate), Toast.LENGTH_LONG).show();
    }







    @Override
    public int getItemCount() {
        return singelPozitionWsheProgramList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView wash, programWash, timeWash;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            wash = (TextView) itemView.findViewById(R.id.textViewOnePositionWashhouseWash);
            programWash = (TextView) itemView.findViewById(R.id.textViewOnePositionWashouseProgram);
            timeWash = (TextView) itemView.findViewById(R.id.textViewOnePositionWashouseTIme);



        }
    }
}
