package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.keep;

public abstract class KeepKey {

    public static final String KEY_USERNAME = "username";
    public static final String KEY_NAME_SHARED = "DateUserPref";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_TYPE_ACCOUNT = "typeac";
    public static final String KEY_ID_ACCOUNT = "username_id";

    //Klucze zwiazane z stosami cofniec fragmentow
    public static final String KEY_FRAGMENT_CALENDAR = "CALENDAR_FRAGMENT";
    public static final String KEY_FRAGMENT_GENERALY_ADD_EVENT = "ADD_EVENT_GENRALY_FRAGMENT";
    public static final String KEY_FRAGMENT_WASHHOUSE_ADD_EVENT = "ADD_EVENT_WASHHOUSE_ADD_EVENT";
    public static final String KEY_FRAGMENT_OTHER_REZERVATION = "ADD_EVENT_OTHER_REZERVATION";


    //Klucze zwiozane z typami konta

    public static  final String KEY_STUDENT = "student";
    public static  final String KEY_CHEFF = "kierownik";
    public static  final String KEY_PORTIER = "portier";


}
