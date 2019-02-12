package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WaschingMachineMode {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("time")
    @Expose
    private String time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
