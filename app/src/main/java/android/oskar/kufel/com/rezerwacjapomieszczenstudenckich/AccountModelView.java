package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

public class AccountModelView extends AndroidViewModel {


    private static final String KEY_INHABIT="student";
    private static final String KEY_PORTIER="portier";
    private static final String KEY_CHEF="kierownik";

    private MutableLiveData<Boolean> isInhabit = new MutableLiveData<>();
    private MutableLiveData<Boolean> isPortier = new MutableLiveData<>();
    private MutableLiveData<Boolean> isCHeif = new MutableLiveData<>();


    public AccountModelView(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<Boolean> getIsInhabit() {
        return isInhabit;
    }

    public void setIsInhabit (Boolean isInhabitB){
        isInhabit.postValue(isInhabitB);
    }


    public MutableLiveData<Boolean> getIsPortier() {
        return isPortier;
    }

    public void setIsPortier(Boolean isPortier) {
        this.isPortier.postValue(isPortier);
    }

    public MutableLiveData<Boolean> getIsCHeif() {
        return isCHeif;
    }

    public void setIsCHeif(Boolean isCHeif) {
        this.isCHeif.postValue(isCHeif);
    }

    public void checkLoginTypeAcount(String type){

        if(type.equalsIgnoreCase(KEY_INHABIT)){
            setIsPortier(false);
            setIsCHeif(false);
            setIsInhabit(true);
        }

        else if (type.equalsIgnoreCase(KEY_CHEF)){
            setIsCHeif(true);
            setIsPortier(false);
            setIsInhabit(false);
        }

        else if (type.equalsIgnoreCase(KEY_PORTIER)){
            setIsPortier(true);
            setIsInhabit(false);
            setIsCHeif(false);
        }


    }
}
