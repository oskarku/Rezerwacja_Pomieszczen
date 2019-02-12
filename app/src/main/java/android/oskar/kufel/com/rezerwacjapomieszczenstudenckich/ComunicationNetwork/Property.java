package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Property {


    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("min_room_number")
    @Expose
    private Integer minRoomNumber;
    @SerializedName("max_room_number")
    @Expose
    private Integer maxRoomNumber;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMinRoomNumber() {
        return minRoomNumber;
    }

    public void setMinRoomNumber(Integer minRoomNumber) {
        this.minRoomNumber = minRoomNumber;
    }

    public Integer getMaxRoomNumber() {
        return maxRoomNumber;
    }

    public void setMaxRoomNumber(Integer maxRoomNumber) {
        this.maxRoomNumber = maxRoomNumber;
    }
}
