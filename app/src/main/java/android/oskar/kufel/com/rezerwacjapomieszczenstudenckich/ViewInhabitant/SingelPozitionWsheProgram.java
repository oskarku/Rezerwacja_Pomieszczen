package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ViewInhabitant;

public class SingelPozitionWsheProgram {
    private String nameWash;
    private String nameProgram;
    private String timeWash;


    public SingelPozitionWsheProgram(String nameWash, String nameProgram, String timeWash){
        setNameProgram(nameProgram);
        setNameWash(nameWash);
        setTimeWash(timeWash);
    }


    public String getNameWash() {
        return nameWash;
    }

    public void setNameWash(String nameWash) {
        this.nameWash = nameWash;
    }

    public String getNameProgram() {
        return nameProgram;
    }

    public void setNameProgram(String nameProgram) {
        this.nameProgram = nameProgram;
    }

    public String getTimeWash() {
        return timeWash;
    }

    public void setTimeWash(String timeWash) {
        this.timeWash = timeWash;
    }
}
