package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MSG {

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("message")
    @Expose
    private String msg;

    @SerializedName("username_id")
    @Expose
    private int idUser;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("typeac")
    @Expose
    private String typeAC;

    public String getTypeAC() {
        return typeAC;
    }

    public void setTypeAC(String typeAC) {
        this.typeAC = typeAC;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
