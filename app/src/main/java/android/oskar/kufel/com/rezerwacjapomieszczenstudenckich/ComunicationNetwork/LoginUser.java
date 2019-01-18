package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginUser {

    @SerializedName("status")
    @Expose
    private String username;

    @SerializedName("status")
    @Expose
    private String passwort;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
}
