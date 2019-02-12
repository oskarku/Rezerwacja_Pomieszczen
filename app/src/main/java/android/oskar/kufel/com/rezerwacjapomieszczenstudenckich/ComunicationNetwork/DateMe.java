package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DateMe {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nick")
    @Expose
    private String nick;
    @SerializedName("phone")
    @Expose
    private Integer phone;
    @SerializedName("room")
    @Expose
    private Integer room;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("email")
    @Expose
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
