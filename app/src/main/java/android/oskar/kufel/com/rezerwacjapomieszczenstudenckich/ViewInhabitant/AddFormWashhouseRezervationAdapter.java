package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ViewInhabitant;

import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AddFormWashhouseRezervationAdapter extends RecyclerView.Adapter<AddFormWashhouseRezervationAdapter.MyViewHolder> {

    private List<SingelPozitionWsheProgram> singelPozitionWsheProgramList;

    public AddFormWashhouseRezervationAdapter(List<SingelPozitionWsheProgram> list){
        this.singelPozitionWsheProgramList=list;
    }
    @NonNull
    @Override
    public AddFormWashhouseRezervationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.one_position_program_washhouse, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddFormWashhouseRezervationAdapter.MyViewHolder myViewHolder, int position) {
        SingelPozitionWsheProgram singelPozitionWsheProgram = singelPozitionWsheProgramList.get(position);
        myViewHolder.timeWash.setText(singelPozitionWsheProgram.getTimeWash());
        myViewHolder.programWash.setText(singelPozitionWsheProgram.getNameProgram());
        myViewHolder.wash.setText(singelPozitionWsheProgram.getNameWash());


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
