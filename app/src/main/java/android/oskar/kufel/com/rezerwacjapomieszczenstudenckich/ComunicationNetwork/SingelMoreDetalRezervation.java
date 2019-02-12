package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ComunicationNetwork;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingelMoreDetalRezervation {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("begin_time")
    @Expose
    private String beginTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("public_room_id")
    @Expose
    private Integer publicRoomId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("temponary_user")
    @Expose
    private Boolean temponaryUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getPublicRoomId() {
        return publicRoomId;
    }

    public void setPublicRoomId(Integer publicRoomId) {
        this.publicRoomId = publicRoomId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getTemponaryUser() {
        return temponaryUser;
    }

    public void setTemponaryUser(Boolean temponaryUser) {
        this.temponaryUser = temponaryUser;
    }
}
