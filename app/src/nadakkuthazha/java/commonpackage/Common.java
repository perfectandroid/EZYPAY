package commonpackage;

/**
 * Created by akash on 12/24/2016.
 */

public class Common {
     public final static String MOBILE_NUM_SMS="9664172929";
     public final static String MOBILE_CODE_SMS="NSCBNK";
    public final static boolean HOSTNAMEVERFICATION_MANUAL=true;
    public final static String HOSTNAME_SUBJECT="NSCBHO17";
    public final static String CERTIFICATE_ASSET_NAME= "nadakkuthazha.crt";
    private static String BASE_URL= "https://117.218.68.114:14001";
    private static String API_NAME= "/mscore/api/MV2/";
    //===============================

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    public static String getApiName() {
        return API_NAME;
    }

    public static void setApiName(String apiName) {
        API_NAME = apiName;
    }
}
