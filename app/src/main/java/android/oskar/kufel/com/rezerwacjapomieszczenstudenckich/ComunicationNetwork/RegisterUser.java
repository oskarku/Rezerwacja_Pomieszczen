package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterUser {

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("status")
    @Expose
    private String type_acoount;

    @SerializedName("status")
    @Expose
    private String email;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType_acoount() {
        return type_acoount;
    }

    public void setType_acoount(String type_acoount) {
        this.type_acoount = type_acoount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
