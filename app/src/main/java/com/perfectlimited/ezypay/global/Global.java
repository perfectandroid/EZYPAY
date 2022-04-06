package com.perfectlimited.ezypay.global;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.perfectlimited.ezypay.MyContentProvider;
 import com.perfectlimited.ezypay.model.ModelServerResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyManagementException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;
import javax.security.auth.x500.X500Principal;

import commonpackage.Common;

/**
 * Created by akash on 6/30/2016.
 */
public class Global extends Application {

    public static final String PROFILE_PIC_NAME="profile.jpg";

    public static boolean DEBUG=false;


    public final static String MESSAGEPASS="messagepass";
    public final static String MOBILE_NUM_SMS= Common.MOBILE_NUM_SMS;
    public final static String MOBILE_CODE_SMS= Common.MOBILE_CODE_SMS;
    public final static boolean HOSTNAMEVERFICATION_MANUAL= Common.HOSTNAMEVERFICATION_MANUAL;
    public final static String HOSTNAME_SUBJECT= Common.HOSTNAME_SUBJECT;
    public final static String CERTIFICATE_ASSET_NAME= Common.CERTIFICATE_ASSET_NAME;
    private static String BASE_URL= Common.getBaseUrl();
    private static String API_NAME= Common.getApiName();
    //===============================
    public static String getApiName() {
        return API_NAME;
    }



    public static String SHAREDPREFSTRING="dfjgkjghae";
    public static String SHAREDPREFAL_ISALIAS_LOGIN="dfjgkjghae";
    public static String SHAREDPREFAL_DBPASS="hjsdfgh";
    public static String SHAREDPREFAL_TOKEN="jdfkhjuikjnhdfy";
    public static String SHAREDPREFAL_IS_EVENT_FIRST_TIME="dfjgkjghae";

    public static String SHAREDPREFSTRING_COUNT_MESSAGE="hkjdsergafkjgh";
    public static String SHAREDPREFSTRING_COUNT_EVENT="asuikdfafhuireygxkvcna";
    public static String SHAREDPREFSTRING_COUNT_DOC="hkjfdfdsaffkjgh";
    public static String SHAREDPREFSTRING_COUNT_SHARED_DOC="gdgdfg";



    public static String EXCEPTION_DBPASSWORDKEYALTER="bad base-64";
    public static String EXCEPTION_DBPASSWORDKEYAEMPTY="No key found dbpassword";
    public static String EXCEPTION_DBINCORRECTPASSWORD="file is encrypted or is not a database";



    public static int SYNC_DURATION_HOUR =0;//minutes
    public static int SYNC_DURATION_MINUTE=15;
    public static int SYNC_INTERVAL =30;//days
    public static String LAST_SYNC="2016-01-01 00:00:00.000";

    public static String ALIASLOGINEXISTS="dfgyjgahj";
    public static String ALIASTOKEN="tofkjhdhbasdf";
    public static String ALIASDBPASSWORD="sdfkjhkjjdbpasskjdfhkj";


    public final static String DATEFORMAT_SERVER="MM/dd/yyyy hh:mm:ss a";
    public final static String DATEFORMAT_SQLITE="yyyy-MM-dd HH:mm:ss.S";
    private static Context context;


    public Global() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Global.context = getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // some of your own operations before content provider will launch

//        String dbname = "dbsec.db";
//        File dbpath =  base.getDatabasePath(dbname);
//        if(!dbpath.exists()){
//            Toast.makeText(base, "Please wait...", Toast.LENGTH_LONG).show();
//        }
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }


    public static String getFormattedString(String dateString,String oldFormat,String newFormat){
        String formattedDate="";
//        DateFormat originalFormat = new SimpleDateFormat("M/d/yyyy HH:mm:s a", Locale.ENGLISH);
//        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:s.S");


        Date date = null;

        try {
            DateFormat originalFormat = new SimpleDateFormat(oldFormat, Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat(newFormat);
            date = originalFormat.parse(dateString);
            formattedDate = targetFormat.format(date);  // 20120821
//            if(Global.DEBUG) Log.e("formateddate",formattedDate);

        } catch (Exception  e) {
            formattedDate=dateString;
            if(Global.DEBUG)  Log.e("ParseException",e.toString());
        }

        return formattedDate;
    }

    public static void cancelNotification(Context ctx, int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancel(notifyId);
    }
    public static String getBaseUrl(){
        return BASE_URL;
    }

    public boolean isUserLogin(Context context) throws Exception {
        KeyStore keyStore;
        SharedPreferences pref=context.getSharedPreferences(SHAREDPREFSTRING,MODE_PRIVATE);
        String prefAliasIsLogin= pref.getString(SHAREDPREFAL_ISALIAS_LOGIN,"");


        if (Global.DEBUG) Log.e("prefAliasIsLogin", prefAliasIsLogin+" i");


        keyStore=  KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);



        if( keyStore.containsAlias(ALIASLOGINEXISTS)){

            if (Global.DEBUG) Log.e("ALIASLOGINEXISTS", "true");


            if(prefAliasIsLogin.equals("")){
                if (Global.DEBUG) Log.e("in.equals(\"\")", "prefAliasIsLogin.equals(\"\")");

                return false;
            }else{

                if (Global.DEBUG) Log.e("else prefalis", "true)");

                String is_loggedin= doDecryption(ALIASLOGINEXISTS,prefAliasIsLogin);
                return is_loggedin.equals("true");
            }

        }else{
            if (Global.DEBUG) Log.e("ALIASLOGINEXISTS", "false");

            return false;
        }




    }
    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }
    public String getDbPassword(Context context) throws Exception{
        SharedPreferences pref=context.getSharedPreferences(Global.SHAREDPREFSTRING,MODE_PRIVATE);
        String key= pref.getString(Global.SHAREDPREFAL_DBPASS,"");
        if(key.equals(""))throw new Exception("No key found");
        return doDecryption(Global.ALIASDBPASSWORD,key);

    }

    public String doDecryption(String aliasName,String encoded) throws Exception{

        KeyStore keyStore;

        keyStore=  KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(aliasName, null);
        PrivateKey privateKey = (PrivateKey) privateKeyEntry.getPrivateKey();

        Cipher c;
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            c =  Cipher.getInstance("RSA/None/OAEPWithSHA-256AndMGF1Padding");


        }else{
            c =  Cipher.getInstance("RSA/None/PKCS1Padding");


        }
        c.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decodedUser = c.doFinal(Base64.decode(encoded, Base64.DEFAULT));

        String decryptedtext = new String(decodedUser, 0, decodedUser.length, "UTF-8");

        if(Global.DEBUG)   Log.e("decryptedtext",decryptedtext+"");
        return decryptedtext;



    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    public String doEncription(String aliasName,String text) throws Exception{



        KeyStore keyStore;

        keyStore=  KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(aliasName, null);
        PublicKey publicKey = (PublicKey) privateKeyEntry.getCertificate().getPublicKey();
        Cipher c;
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            c =  Cipher.getInstance("RSA/None/OAEPWithSHA-256AndMGF1Padding");
        }else{
            c =  Cipher.getInstance("RSA/None/PKCS1Padding");
        }

        c.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encodedUser = c.doFinal(text.getBytes());

        String enctext = Base64.encodeToString(encodedUser, Base64.DEFAULT);
        if(Global.DEBUG)    Log.e("encypted string",enctext);

        return enctext;

    }
    public boolean checkInternetConenction(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
//            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
//                // connected to wifi
//             } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
//                // connected to the mobile provider's data plan
//                Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
//            }
            return true;
        } else {
            // not connected to the internet
            return false;
        }
    }

    public static String getFromCustomer(String columnName) throws  Exception{
        String retVal="";

                        Cursor cur =   context.getContentResolver().
                        query(MyContentProvider.URI_GET_USER_DETAILS, null,
                                MyContentProvider.COLUMN_CUSTOMER_PRIMARY + " = 1", null, null);

                if (cur != null) {
                    if (cur.getCount() == 0) {

                        throw new Exception("cursor null");
                    } else {

                        if (cur.moveToFirst()) {


                            retVal=     cur.getString
                                    (cur.getColumnIndex(columnName));


                        }else {
                            throw new Exception("Cursor unable to move first exception");
                        }

                    }

                    cur.close();

                } else {
                    throw new Exception("cursor null");
                }

        return  retVal;
    }


    public static synchronized ModelServerResponse callServer(String createdUrl,Context contextPass){
        String response="";
        char[] buffer = new char[0];
        InputStream stream = null;
        ModelServerResponse modelServerResponse=new ModelServerResponse();

        StringBuilder builder = new StringBuilder();



        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                HostnameVerifier hv =
                        HttpsURLConnection.getDefaultHostnameVerifier();
                return hv.verify(Global.HOSTNAME_SUBJECT, session);
//                return  true;
            }
        };

        URL url = null;

        try {


            CertificateFactory cf = CertificateFactory.getInstance("X.509");
// From https://www.washington.edu/itconnect/security/ca/load-der.crt
            InputStream caInput =  contextPass.getAssets().open(Global.CERTIFICATE_ASSET_NAME);


            Certificate ca;

            ca = cf.generateCertificate(caInput);
//                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            if(Global.DEBUG)   Log.e("ca","ca=" + ((X509Certificate) ca).getSubjectDN()+"");

            caInput.close();

            if(Global.DEBUG)   Log.e("created url",createdUrl+"");
            url = new URL(createdUrl);

            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

// Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

// Create an SSLContext that uses our TrustManager
//                SSLContext context = SSLContext.getInstance("TLS");

            SSLContext context = SSLContext.getInstance("TLSv1.2");

            context.init(null, tmf.getTrustManagers(), null);

// Tell the URLConnection to use a SocketFactory from our SSLContext
            HttpsURLConnection urlConnection =
                    (HttpsURLConnection)url.openConnection();

           if(Global.HOSTNAMEVERFICATION_MANUAL) {
               urlConnection.setHostnameVerifier(hostnameVerifier);
             }
            urlConnection.setConnectTimeout(60000);


            urlConnection.setSSLSocketFactory(context.getSocketFactory());

            stream = urlConnection.getInputStream();

            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");

            BufferedReader readerd = new BufferedReader(reader);
            String line;
            while((line = readerd.readLine()) != null){
                builder.append(line);
            }

        } catch (CertificateException
                |NoSuchAlgorithmException|KeyStoreException|KeyManagementException
                |IOException e) {
            if(Global.DEBUG)     Log.e("Excepttion",e.toString());
            modelServerResponse.setException(e);
        }



        finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    if(Global.DEBUG)      Log.e("IOExceptionm",e.toString());
                    modelServerResponse.setException(e);

                }
            }
        }

        String res=builder.toString();
        if(Global.DEBUG)Log.e("response",res+"");

        if (modelServerResponse.getException()==null&&res.equals("")){
            modelServerResponse.setException(new Exception("Response null"));

        }
        modelServerResponse.setResponse(res);

        return modelServerResponse    ;

     }



    public void clearApplicationData(Context context) {

        File cacheDirectory = getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {

            String[] fileNames = applicationDirectory.list();

            for (String fileName : fileNames) {

//                if (!fileName.equals("lib")) {

                deleteFile(new File(applicationDirectory, fileName));

//                }

            }

        }
        Toast.makeText(context, "Logging out.Files altered", Toast.LENGTH_LONG).show();

    }
    public static void showAlert(String message,Context context,AlertDialog alertDialog){
        alertDialog=createAlert(message,context).create();
        alertDialog.show();
    }

    public static AlertDialog.Builder createAlert(String message,Context context){
        android.support.v7.app.AlertDialog.Builder alertdialog=
                new android.support.v7.app.AlertDialog.Builder(context);
        alertdialog.setMessage(message);
        alertdialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        return alertdialog;
    }
    public static boolean deleteFile(File file) {

        boolean deletedAll = true;

        if (file != null) {

            if (file.isDirectory()) {

                String[] children = file.list();

                for (int i = 0; i < children.length; i++) {

                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;

                }

            } else {

                deletedAll = file.delete();

            }

        }

        return deletedAll;

    }

    public static String getToken() {
        if (token.equals("")){

        }

        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    static  String  token="";


    public void logoutNow(Context context){


        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
            ((ActivityManager)context.getSystemService(ACTIVITY_SERVICE))
                    .clearApplicationUserData(); // note: it has a return value!
            Toast.makeText(context, "Logging out.Files altered", Toast.LENGTH_LONG).show();
        } else {
            // use old hacky way, which can be removed
            // once minSdkVersion goes above 19 in a few years.

            clearApplicationData(context);

        }


//        try {
//            String keyLoginExist=  doEncription(Global.ALIASLOGINEXISTS,"false");
//            String path = context.getDatabasePath(Dbhelper.getDatabaseName()+".db").getPath();
//            Log.e("path",path);
//
//            File file_dbpath=new File(path);
//
//            boolean is_db_delte=file_dbpath.delete();
//            Log.e("db delte",is_db_delte+"");
//            android.os.Process.killProcess(android.os.Process.myPid());
//
//
//             return  true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return  false;
//        }

    }

    public  static String getTime(){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());

    }

    public void genkey(String aliasName,Context context) throws NoSuchAlgorithmException,
            NoSuchProviderException,InvalidAlgorithmParameterException,KeyStoreException ,
            CertificateException,IOException{


        KeyStore keyStore;

        keyStore=  KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        if(!keyStore.containsAlias(aliasName)){
            KeyPairGenerator generator = null;

            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
//            end.add(Calendar.YEAR, 1);

            start.set(Calendar.YEAR,2016);
            start.set(Calendar.DATE,1);
            start.set(Calendar.MONTH,1);


            end.set(Calendar.YEAR,2041);
            end.set(Calendar.DATE,1);
            end.set(Calendar.MONTH,1);


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                generator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore");

                if(Global.DEBUG)   Log.e("keystore2", "Current version is 23(MashMello)");
                //Api level 23

                KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(
                        aliasName,
                        KeyProperties.PURPOSE_DECRYPT | KeyProperties.PURPOSE_ENCRYPT)
                        .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
                        .build();
                generator.initialize(spec);
            } else {
                generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");

                if(Global.DEBUG)      Log.e("keystore2", "Current version is < 23(MarshMello)");
                //api level 17+ 4.4.3
                KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
                        .setAlias(aliasName)
                        .setSubject(new X500Principal("CN=perfectlimited, O=Android Authority"))
                        .setSerialNumber(BigInteger.ONE)
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .build();
                generator.initialize(spec);




            }
            KeyPair keyPair = generator.generateKeyPair();
            if(Global.DEBUG)    Log.e("key generated", "yes");
        }else{
            if(Global.DEBUG)     Log.e("key exists","yes");
        }


    }

    public void clearAllAlias() {

    }
    /**
     * returns a list of all available sd cards paths, or null if not found.
     *
     * @param includePrimaryExternalStorage set to true if you wish to also include the path of the primary external storage
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static List<String> getSdCardPaths(final Context context, final boolean includePrimaryExternalStorage)
    {
        final File[] externalCacheDirs= ContextCompat.getExternalCacheDirs(context);
        if(externalCacheDirs==null||externalCacheDirs.length==0)
            return null;
        if(externalCacheDirs.length==1)
        {
            if(externalCacheDirs[0]==null)
                return null;
            final String storageState= EnvironmentCompat.getStorageState(externalCacheDirs[0]);
            if(!Environment.MEDIA_MOUNTED.equals(storageState))
                return null;
            if(!includePrimaryExternalStorage&& Build.VERSION.SDK_INT>=
                    Build.VERSION_CODES.HONEYCOMB&&Environment.isExternalStorageEmulated())
                return null;
        }
        final List<String> result=new ArrayList<>();
        if(includePrimaryExternalStorage||externalCacheDirs.length==1)
            result.add(getRootOfInnerSdCardFolder(externalCacheDirs[0]));
        for(int i=1;i<externalCacheDirs.length;++i)
        {
            final File file=externalCacheDirs[i];
            if(file==null)
                continue;
            final String storageState=EnvironmentCompat.getStorageState(file);
            if(Environment.MEDIA_MOUNTED.equals(storageState))
                result.add(getRootOfInnerSdCardFolder(externalCacheDirs[i]));
        }
        if(result.isEmpty())
            return null;
        return result;
    }

    /** Given any file/folder inside an sd card, this will return the path of the sd card */
    private static String getRootOfInnerSdCardFolder(File file)
    {
        if(file==null)
            return null;
        final long totalSpace=file.getTotalSpace();
        while(true)
        {
            final File parentFile=file.getParentFile();
            if(parentFile==null||parentFile.getTotalSpace()!=totalSpace)
                return file.getAbsolutePath();
            file=parentFile;
        }
    }

}
