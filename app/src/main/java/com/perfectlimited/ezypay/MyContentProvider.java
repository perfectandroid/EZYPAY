package com.perfectlimited.ezypay;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.perfectlimited.ezypay.global.Global;

import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;
import net.sqlcipher.database.SQLiteException;
import net.sqlcipher.database.SQLiteOpenHelper;
import net.sqlcipher.database.SQLiteStatement;

import java.lang.reflect.Field;

public class MyContentProvider extends ContentProvider {
    //==========TABLES==============

    public static String TABLE_NAME_USER="TABLE_NAME_USER";
    public static String TABLE_NAME_PIN="TABLE_NAME_PIN";


    //==============TYPES FEILD==========
    private static String TYPE_INT=" integer ";
    private static String TYPE_TEXT=" text ";
    private static String TYPE_REAL=" real ";
    private static String COMMA=" , ";



    //==============COLUMN TABLE_NAME_USER =====================
    public static String COLUMN_CUSTOMER_PRIMARY ="COLUMN_CUSTOMER_PRIMARY";
    public static String COLUMN_CUSTOMER_ID ="COLUMN_CUSTOMER_ID";
    public static String COLUMN_CUSTOMER_NAME ="COLUMN_CUSTOMER_NAME";
    public static String COLUMN_CUSTOMER_DESCRIPTION ="COLUMN_CUSTOMER_DESCRIPTION";

    public static String COLUMN_CUSTOMER_MOBILE ="COLUMN_CUSTOMER_MOBILE";
    public static String COLUMN_CUSTOMER_TOKEN ="COLUMN_CUSTOMER_TOKEN";


    public static String COLUMN_CUSTOMER_FK_USERGROUP ="COLUMN_CUSTOMER_FK_USERGROUP";
    public static String COLUMN_CUSTOMER_LOGIN_TIME ="COLUMN_CUSTOMER_LOGIN_TIME";
    public static String COLUMN_CUSTOMER_LOGIN_TIME_DEVICE ="COLUMN_CUSTOMER_LOGIN_TIME_DEVICE";

    public static String COLUMN_CUSTOMER_ADMIN ="COLUMN_CUSTOMER_ADMIN";
    public static String COLUMN_CUSTOMER_PIN ="COLUMN_CUSTOMER_PIN";




    //==================================TABLE_NAME_PIN===================================================
    public static String COLUMN_PIN_PRIMARY ="COLUMN_PIN_PRIMARY";
    public static String COLUMN_PIN_PIN ="COLUMN_PIN_PIN";
    public static String COLUMN_PIN_CURRENT_PIN ="COLUMN_PIN_CURRENT_PIN";


    SQLiteDatabase db;
    SharedPreferences pref;
    private static String DBNAME="dbsec.db";
    static int DBVERSION=1;
    Global global;
    private String dbpass="";
    static Context context;

    static final String PROVIDER_NAME = BuildConfig.APPLICATION_ID+".MyContentProvider";

    //======doc=====================
    public   static final int DOC_CODE = 1;

//================insert==================================



    static final String CHANGE_DBPASS="changedbpasss";
    static final String URL_CHANGE_DBPASS = "content://" + PROVIDER_NAME + "/"+CHANGE_DBPASS;
    public static final Uri URI_CHANGE_DBPASS=Uri.parse(URL_CHANGE_DBPASS);
    public   static final int CHANGE_DBPASS_CODE = 3;

    static final String INSERT_USERDETAILS="insertuserdetails";
    static final String URL_INSERT_USERDETAILS = "content://" + PROVIDER_NAME + "/"+INSERT_USERDETAILS;
    public static final Uri URI_INSERT_USERDETAILS=Uri.parse(URL_INSERT_USERDETAILS);
    public   static final int CHANGEINSERT_USERDETAILS_CODE = 4;


    static final String INSERT_PIN="insertpin";
    static final String URL_INSERT_PIN = "content://" + PROVIDER_NAME + "/"+INSERT_PIN;
    public static final Uri URI_INSERT_PIN=Uri.parse(URL_INSERT_PIN);
    public   static final int INSERT_PIN_CODE = 5;


    //=========================end insert======================
//================query=====================
    static final String QUERY_GET_USER_DETAILS="getuserdetails";
    static final String URL_GET_USER_DETAILS = "content://" + PROVIDER_NAME + "/"+QUERY_GET_USER_DETAILS;
    public static final Uri URI_GET_USER_DETAILS=Uri.parse(URL_GET_USER_DETAILS);
    public   static final int GET_USER_DETAILS_CODE = 7;


    static final String QUERY_GET_CHECK_CORRECT_PIN="getcheckcorrectpin";
    static final String URL_GET_CHECK_CORRECT_PIN = "content://" + PROVIDER_NAME + "/"+QUERY_GET_CHECK_CORRECT_PIN;
    public static final Uri URI_GET_CHECK_CORRECT_PIN=Uri.parse(URL_GET_CHECK_CORRECT_PIN);
    public   static final int GET_CHECK_CORRECT_PIN_CODE = 20;






    static final String QUERY_GET="queryget";
    static final String URL_QUERY_GET = "content://" + PROVIDER_NAME + "/"+QUERY_GET;
    public static final Uri URI_QUERY_GET=Uri.parse(URL_QUERY_GET);
    public   static final int  QUERY_GET_CODE = 27;





    //===============end query============
//=================bulk insert=================================




    //==============end bulk insert============================
    //=====================update===================





    static final String UPDATE_USER_LOGIN_DEVICE="updateuserlogindevice";
    static final String URL_UPDATE_USER_LOGIN_DEVICE = "content://" + PROVIDER_NAME + "/"+UPDATE_USER_LOGIN_DEVICE;
    public static final Uri URI_UPDATE_USER_LOGIN_DEVICE=Uri.parse(URL_UPDATE_USER_LOGIN_DEVICE);
    public   static final int  UPDATE_USER_LOGIN_DEVICE_CODE = 34;






    static final String UPDATE_CHANGE_PIN="changepin";
    static final String URL_CHANGE_PIN = "content://" + PROVIDER_NAME + "/"+UPDATE_CHANGE_PIN;
    public static final Uri URI_CHANGE_PIN=Uri.parse(URL_CHANGE_PIN);
    public   static final int CHANGE_PIN_CODE = 25;







    //==================end update==================

    //===============delete============================





    //==================end delete======================

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, CHANGE_DBPASS, CHANGE_DBPASS_CODE);
        uriMatcher.addURI(PROVIDER_NAME, INSERT_USERDETAILS, CHANGEINSERT_USERDETAILS_CODE);
        uriMatcher.addURI(PROVIDER_NAME, INSERT_PIN, INSERT_PIN_CODE);
        uriMatcher.addURI(PROVIDER_NAME, QUERY_GET_USER_DETAILS, GET_USER_DETAILS_CODE);
        uriMatcher.addURI(PROVIDER_NAME, QUERY_GET_CHECK_CORRECT_PIN, GET_CHECK_CORRECT_PIN_CODE);
        uriMatcher.addURI(PROVIDER_NAME, UPDATE_CHANGE_PIN , CHANGE_PIN_CODE);
        uriMatcher.addURI(PROVIDER_NAME, QUERY_GET , QUERY_GET_CODE);
        uriMatcher.addURI(PROVIDER_NAME, UPDATE_USER_LOGIN_DEVICE   , UPDATE_USER_LOGIN_DEVICE_CODE);



    }



    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {




            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        switch (uriMatcher.match(uri)) {


            case CHANGEINSERT_USERDETAILS_CODE:

                try {
                    db.insertOrThrow(TABLE_NAME_USER,null,values);
                    if(Global.DEBUG)   Log.e("inserted by 2","content provider "+TABLE_NAME_USER);

                    Uri _uri = ContentUris.withAppendedId(URI_INSERT_USERDETAILS, CHANGEINSERT_USERDETAILS_CODE);
                    context.getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }catch (SQLException e){
                    if(Global.DEBUG)  Log.e("SQLException",e.toString()+"");
                    throw new SQLException(e.toString());
                }
            case INSERT_PIN_CODE:

                try {
                    db.insertOrThrow(TABLE_NAME_PIN,null,values);
                    if(Global.DEBUG)  Log.e("inserted by 2","content provider "+TABLE_NAME_PIN);

                    Uri _uri = ContentUris.withAppendedId(URI_INSERT_PIN, INSERT_PIN_CODE);
                    context.getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }catch (SQLException e){
                    if(Global.DEBUG)  Log.e("SQLException",e.toString()+"");
                    throw new SQLException(e.toString());
                }





            case CHANGE_DBPASS_CODE:
                if(Global.DEBUG)  Log.e("reach here","reach here CHANGE_DBPASS_CODE");
                try{
                    String dbpassGet = global.getDbPassword(context);
                    db.changePassword(dbpassGet);
                    if(Global.DEBUG)    Log.e("password changed",dbpassGet);
                    Uri _uri = ContentUris.withAppendedId(URI_CHANGE_DBPASS, 16);
                    context.getContentResolver().notifyChange(_uri, null);
                    return _uri;

                }catch (Exception e){
                    throw new SQLException(e.toString());

                }

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }



    }


    public MyContentProvider() {
        if(Global.DEBUG)  Log.e("reacg","MyContentProvider consturc");

    }


    @Nullable
    @Override
    public Bundle call(@NonNull String method, String arg, Bundle extras) {

        if(method.equals("callQueryMessage")) {

            long offset=  extras.getLong("offset");
            long limit=  extras.getLong("limit");





        }
        return null;

    }



    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        switch (uriMatcher.match(uri)) {


            case GET_USER_DETAILS_CODE:
                try{
                    return  db.query(TABLE_NAME_USER,projection,selection,null,null,null,null);
                }catch (Exception e){
                    throw new SQLException(e.toString());

                }







            case  QUERY_GET_CODE:
                try{

                    String tableName=projection[0];

//                    String query="select  "+COLUMN_EVENTS_NAME+COMMA+COLUMN_EVENTS_EVENTATTO+
//                            COMMA+COLUMN_EVENTS_EVENTATFROM+COMMA+
//                            COLUMN_EVENTS_ID+COMMA+COLUMN_EVENTS_DESCRIPTION+
//                            COMMA+"datetime("+COLUMN_EVENTS_EVENTON+") as "+COLUMN_EVENTS_EVENTON+" from "+
//                            TABLE_NAME_EVENTS+" where strftime('%Y-%m-%d',"+
//                            COLUMN_EVENTS_EVENTON+") ='"+formatted+"'";
//                    if(Global.DEBUG) Log.e("query",query);

                    return db.query(tableName,null,selection,selectionArgs,null,null,null,null);
//                     return  db.rawQuery(query, null);
                }catch (Exception e){
                    throw new SQLException(e.toString());

                }





            case  GET_CHECK_CORRECT_PIN_CODE:
                try{


                    String query="select * from "+ TABLE_NAME_PIN+" order by "+COLUMN_PIN_PRIMARY+
                            " desc"+" limit 1";

                    return  db.rawQuery(query, null);
                }catch (Exception e){
                    throw new SQLException(e.toString());

                }







            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }


    }



    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        switch (uriMatcher.match(uri)) {








            case UPDATE_USER_LOGIN_DEVICE_CODE:
                try{
                    db.update(TABLE_NAME_USER,values,selection,selectionArgs);
                    if(Global.DEBUG)  Log.e("update","last login device");
                    return UPDATE_USER_LOGIN_DEVICE_CODE;
                }catch (Exception e){
                    if(Global.DEBUG)  Log.e("Exception ast login device",e.toString()+"");

                    throw new SQLException(e.toString());

                }

            case CHANGE_PIN_CODE:
                try{

                    String pin=selectionArgs[0];
                    String currentPin=selectionArgs[1];


                    Log.e("checkCurrentPin",""+checkCurrentPin(currentPin));

//                    if(checkCurrentPin(currentPin)) {

                    db.update(TABLE_NAME_PIN,values,"",null);



//                    }else{
//
//                        throw new Exception(ExceptionCurrentPIN);
//                    }


                    return CHANGE_PIN_CODE;
                }catch (Exception e){
                    if(Global.DEBUG) Log.e("Exception update last sync",e.toString()+"");

                    throw new SQLException(e.toString());

                }







            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    public void changePin(String pin,String currentPin) throws Exception {



        if(checkCurrentPin(currentPin)) {

            if (canInsertNewPin(pin)) {
                insertPin(pin);
            } else {
                throw new Exception(PINEXSISTDB);
            }

        }else{
            throw new Exception(ExceptionCurrentPIN);
        }


    }

    public static String PINEXSISTDB="PINEXSISTDB";
    public static String ExceptionCurrentPIN="ExceptionCurrentPIN";


    public void insertPin(String pin) throws Exception{


        ContentValues cv=new ContentValues();
        cv.put(COLUMN_PIN_PIN,pin);
        cv.put(COLUMN_PIN_CURRENT_PIN,1);

        db.insertOrThrow(TABLE_NAME_PIN,null,cv);


    }

    public boolean canInsertNewPin(String pin) throws  Exception{



        String query="select * from "+ TABLE_NAME_PIN+" order by "+COLUMN_PIN_PRIMARY+
                " desc"+" limit 3";
        net.sqlcipher.Cursor cur = db.rawQuery(query, null);
        if(cur.getCount()==0){

            cur.close();


            return true;
        }else{


            if(cur.moveToFirst()) {
                do {


                    if(cur.getString(cur.getColumnIndex(COLUMN_PIN_PIN)).equals(pin)){
                        cur.close();

                        return false;
                    }


                } while (cur.moveToNext());
            }
            cur.close();



            return true;
        }


    }

    public boolean checkCurrentPin(String pin){



        String query="select * from "+ TABLE_NAME_PIN+" order by "+COLUMN_PIN_PRIMARY+
                " desc"+" limit 1";

        net.sqlcipher.Cursor cur = db.rawQuery(query, null);

        if(cur.getCount()==0){

            cur.close();


            return true;
        }else{


            if(cur.moveToFirst()) {



                if(cur.getString(cur.getColumnIndex(COLUMN_PIN_PIN)).equals(pin)){
                    if(Global.DEBUG)  Log.e("reach if","if");
                    cur.close();

                    return true;
                }else{
                    if(Global.DEBUG)  Log.e("reach if","else");

                    cur.close();

                    return false;
                }



            }
            cur.close();


            return true;
        }

    }


    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        switch (uriMatcher.match(uri)) {


            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }


    }

    private class DbhelperCp extends SQLiteOpenHelper{

        public DbhelperCp(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version, SQLiteDatabaseHook hook) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            sqLiteDatabase.changePassword(" ");
            //==========start========

            //=======end


            try {
                String query = "create table if not exists " + TABLE_NAME_PIN + " (" +
                        COLUMN_PIN_PRIMARY+TYPE_INT+" primary key AUTOINCREMENT "+COMMA+
                        COLUMN_PIN_PIN + TYPE_TEXT+COMMA +
                        COLUMN_PIN_CURRENT_PIN +TYPE_INT +

                        ")";
                sqLiteDatabase.execSQL(query);
                if(Global.DEBUG)  Log.e("table created "+ TABLE_NAME_PIN, "yes");
            } catch (SQLiteException e) {
                if(Global.DEBUG) Log.e("SQLiteException", e.toString() + "");
            }




            try {
                String query = "create table if not exists " + TABLE_NAME_USER + " (" +
                        COLUMN_CUSTOMER_PRIMARY+TYPE_INT+" primary key"+COMMA+
                        COLUMN_CUSTOMER_ID + TYPE_TEXT+COMMA +
                        COLUMN_CUSTOMER_NAME +TYPE_TEXT+COMMA +
                        COLUMN_CUSTOMER_DESCRIPTION+TYPE_TEXT+COMMA +
                        COLUMN_CUSTOMER_MOBILE + TYPE_TEXT +COMMA +
                        COLUMN_CUSTOMER_TOKEN + TYPE_TEXT +COMMA +
                        COLUMN_CUSTOMER_FK_USERGROUP +TYPE_TEXT+COMMA+
                        COLUMN_CUSTOMER_LOGIN_TIME +TYPE_TEXT+COMMA+
                        COLUMN_CUSTOMER_LOGIN_TIME_DEVICE+TYPE_TEXT+COMMA+
                        COLUMN_CUSTOMER_ADMIN +TYPE_TEXT+COMMA+
                        COLUMN_CUSTOMER_PIN +TYPE_TEXT+
                        ")";
                sqLiteDatabase.execSQL(query);
                if(Global.DEBUG)  Log.e("table created "+ TABLE_NAME_USER, "yes");
            } catch (SQLiteException e) {
                if(Global.DEBUG)  Log.e("SQLiteException", e.toString() + "");
            }



        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
    SQLiteDatabaseHook hook = new SQLiteDatabaseHook(){
        public void preKey(SQLiteDatabase database){}
        public void postKey(SQLiteDatabase database){
            database.rawExecSQL("PRAGMA journal_mode = WAL;");
        }
    };

    private void logoutDbKeyAlterd(Exception e, Context contexts) {
        if(Global.DEBUG)    Log.e("reached","logoutDbPasswordAlterd");
        if(e.toString().contains(Global.EXCEPTION_DBPASSWORDKEYALTER)){
            if(Global.DEBUG)      Log.e("tamperd","tamperd dbpassword");
        }else if(e.toString().contains(Global.EXCEPTION_DBPASSWORDKEYAEMPTY)){
            if(Global.DEBUG)       Log.e("tamperd","empty key dbpass");
        }else if(e.toString().contains(Global.EXCEPTION_DBINCORRECTPASSWORD)){
            if(Global.DEBUG)      Log.e("incoorect","incorrect password");
        }else{
            if(Global.DEBUG)   Log.e("exception else",e.toString()+"");
        }


        global.logoutNow(contexts);


//logout
        if(Global.DEBUG) Log.e("Logout performed","yes,altreation");


    }
    public static Global getRealApplication (Context applicationContext)
    {
        Global application = null;

        if (applicationContext instanceof Global)
        {
            application = (Global) applicationContext;
        }
        else
        {
            Application realApplication = null;
            Field magicField = null;
            try
            {
                magicField = applicationContext.getClass().getDeclaredField("realApplication");
                magicField.setAccessible(true);
                realApplication = (Application) magicField.get(applicationContext);
            }
            catch (NoSuchFieldException e)
            {
                if(Global.DEBUG)  Log.e("NoSuchFieldException", e.getMessage());
            }
            catch (IllegalAccessException e)
            {
                if(Global.DEBUG)  Log.e("IllegalAccessException", e.getMessage());
            }

            application = (Global) realApplication;
        }

        return application;
    }

    private String getDbPassword(){
        try {
            dbpass = global.getDbPassword(context);

            if (Global.DEBUG) Log.e("dbpassword ex", dbpass);
        } catch (Exception e) {
            if (Global.DEBUG) Log.e("exceptoinddd", e.toString() + "");
            logoutDbKeyAlterd(e, context);

        }

        return  dbpass;
    }

    @Override
    public boolean onCreate() {
        if (Global.DEBUG) Log.e("reached", "content provider onCreate");
        context = getContext();
        global = getRealApplication(context);



        pref=context.getSharedPreferences(Global.SHAREDPREFSTRING,Context.MODE_PRIVATE);

        SQLiteDatabase.loadLibs(context);


        pref=context.getSharedPreferences(Global.SHAREDPREFSTRING,Context.MODE_PRIVATE);
        boolean is_logged_in=false;

        try {
            is_logged_in=global.isUserLogin(context);
            if (Global.DEBUG) Log.e("is_logged_in", is_logged_in+"");

        } catch (Exception e) {

            is_logged_in=false;
            if (Global.DEBUG) Log.e("is_logged_in ex", e.toString()+"");

        }

        if (Global.DEBUG) Log.e("is_logged_in", is_logged_in+"");


        if(is_logged_in) {

            try {
                dbpass = global.getDbPassword(context);
                if (Global.DEBUG) Log.e("dbpass", dbpass + "");


            } catch (Exception e) {
                if (Global.DEBUG) Log.e("exceptoinddd", e.toString() + "");
                logoutDbKeyAlterd(e, context);
            }
            /**
             * Create a write able database which will trigger its
             * creation if it doesn't already exist.
             */


            try{
                DbhelperCp dbHelper = new DbhelperCp(context, DBNAME, null, DBVERSION, hook);
                db = dbHelper.getWritableDatabase(dbpass);
            }catch (Exception es){

            }
            return (db == null) ? false : true;
        }
        else{


            try{
                DbhelperCp dbHelper = new DbhelperCp(context, DBNAME, null, DBVERSION, hook);
                db = dbHelper.getWritableDatabase(" ");
            }catch (Exception es){

            }
            return (db == null) ? false : true;
        }

    }
}
