

package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RezervationView;

import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.R;



public class SingelRezervation {
    private String titleRezervation;
    private String dayRezervation;
    private String monthRezervation;
    private String numberRoomRezervation;
    private String hoursStart;
    private String hoursEnd;
    private int picture;





    public SingelRezervation( String title, String day, String month, String numberRoom ){
        setDayRezervation(day);
        setMonthRezervation(month);
        setNumberRoomRezervation(numberRoom);
        setTitleRezervation(title);

        for (int i = 0; i < getTitleRezervation().length() ; i++) {
            if(getTitleRezervation().substring(0,i).equalsIgnoreCase("pralnia")){
                setPicture(R.drawable.pralnia_card);
            }
            else if(getTitleRezervation().substring(0,i).equalsIgnoreCase("sala tv")){
                setPicture(R.drawable.sala_tv_card);

            }
        }

    }


    public int getPicture() {
        return picture;
    }



    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getTitleRezervation() {
        return titleRezervation;
    }

    public void setTitleRezervation(String titleRezervation) {
        this.titleRezervation = titleRezervation;
    }

    public String getHoursStart() {
        return hoursStart;
    }

    public void setHoursStart(String hoursStart) {
        this.hoursStart = hoursStart;
    }

    public String getHoursEnd() {
        return hoursEnd;
    }

    public void setHoursEnd(String hoursEnd) {
        this.hoursEnd = hoursEnd;
    }

    public String getDayRezervation() {
        return dayRezervation;
    }

    public void setDayRezervation(String dayRezervation) {
        this.dayRezervation = dayRezervation;
    }

    public String getMonthRezervation() {
        return monthRezervation;
    }

    public void setMonthRezervation(String monthRezervation) {
        this.monthRezervation = monthRezervation;
    }

    public String getNumberRoomRezervation() {
        return numberRoomRezervation;
    }

    public void setNumberRoomRezervation(String numberRoomRezervation) {
        this.numberRoomRezervation = numberRoomRezervation;
    }
}
