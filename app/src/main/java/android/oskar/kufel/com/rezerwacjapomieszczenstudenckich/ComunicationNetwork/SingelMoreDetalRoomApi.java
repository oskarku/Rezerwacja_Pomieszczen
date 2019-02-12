package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingelMoreDetalRoomApi {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("property")
    @Expose
    private Property property;
    @SerializedName("state")
    @Expose
    private Object state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }
}
