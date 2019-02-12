package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.CHeffView;

import android.content.res.Resources;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.keep.KeepKey;

public class SingelPositionCardUser {
    private String typeAccount;
    private String userName;
    private String numberRoom;
    private String phoneNumber;
    private int avatarAcoount;




    public SingelPositionCardUser(String typeAccount, String userName, String numberRoom, String phone){
        setNumberRoom(numberRoom);
        setPhoneNumber(phone);
        setTypeAccount(typeAccount);
        setUserName(userName);
        setAvatarAcoount(getTypeAccount());
    }



    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNumberRoom() {
        return numberRoom;
    }

    public void setNumberRoom(String numberRoom) {
        this.numberRoom = numberRoom;
    }

    public int getAvatarAcoount() {
        return avatarAcoount;
    }

    public void setAvatarAcoount(String typeAccount) {
        //TODO zrobic grafiki do typow studenta.


        if (typeAccount.equalsIgnoreCase(KeepKey.KEY_PORTIER)){
            this.avatarAcoount = R.drawable.baseline_power_settings_new_black_24dp;

        }
        else if(typeAccount.equalsIgnoreCase(KeepKey.KEY_STUDENT)){
            this.avatarAcoount = R.drawable.baseline_perm_identity_black_24dp;
        }

    }
}
