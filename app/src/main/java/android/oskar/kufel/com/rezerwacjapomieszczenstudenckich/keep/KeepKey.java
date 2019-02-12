package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.keep;

public abstract class KeepKey {

    public static final String KEY_USERNAME = "username";
    public static final String KEY_NAME_SHARED = "DateUserPref";
    public static final String KEY_TYPE_ACCOUNT = "typeac";
    public static final String KEY_ID_ACCOUNT = "username_id";
    public static final String KEY_ID_TOKEN = "TOKEN";
    public static final String KEY_PHONE_USER = "PHONE";
    public static final String KEY_ROOM_USER = "ROOM";
    public static final String KEY_EMAIL_USER = "EMAIL";


    //Klucze zwiazane z stosami cofniec fragmentow
    public static final String KEY_FRAGMENT_CALENDAR = "CALENDAR_FRAGMENT";
    public static final String KEY_FRAGMENT_GENERALY_ADD_EVENT = "ADD_EVENT_GENRALY_FRAGMENT";
    public static final String KEY_FRAGMENT_WASHHOUSE_ADD_EVENT = "ADD_EVENT_WASHHOUSE_ADD_EVENT";
    public static final String KEY_FRAGMENT_OTHER_REZERVATION = "ADD_EVENT_OTHER_REZERVATION";
    public static final String KEY_FRAGMENT_MY_REZERWATION = "MOJE REZERWACJE" ;


    //Klucze zwiozane z typami konta

    public static  final String KEY_STUDENT = "STUDENT";
    public static  final String KEY_CHEFF = "ADMIN";
    public static  final String KEY_PORTIER = "PORTIER";
    public static final String KEY_FRAGMMENT_MY_DETAL_ACCOUNT = "SZCZEGOLY KONTA";
    public static final String KEY_FRAGMENT_CHEFF = "FRAGMENT_CHEFF";
    public static final String KEY_PASSWORD_USER = "PASSWORD_USER";
    public static final String KEY_DATA_CHOSE_USER = "CHOOSE_DATA_USER";
}
