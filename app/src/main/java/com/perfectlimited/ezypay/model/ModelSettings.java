package com.perfectlimited.ezypay.model;

/**
 * Created by akash on 7/27/2016.
 */
public class ModelSettings {

     public  int COLUMN_SETTINGS_SYNC_DURATION_HOUR =0;
    public  int COLUMN_SETTINGS_SYNC_DURATION_MINUTE =0;
    public  int COLUMN_SETTINGS_SYNC_INTERVAL =0;
    public  String COLUMN_SETTINGS_LAST_SYNC ="";

    public int getCOLUMN_SETTINGS_SYNC_DURATION_HOUR() {
        return COLUMN_SETTINGS_SYNC_DURATION_HOUR;
    }

    public void setCOLUMN_SETTINGS_SYNC_DURATION_HOUR(int COLUMN_SETTINGS_SYNC_DURATION_HOUR) {
        this.COLUMN_SETTINGS_SYNC_DURATION_HOUR = COLUMN_SETTINGS_SYNC_DURATION_HOUR;
    }

    public int getCOLUMN_SETTINGS_SYNC_DURATION_MINUTE() {
        return COLUMN_SETTINGS_SYNC_DURATION_MINUTE;
    }

    public void setCOLUMN_SETTINGS_SYNC_DURATION_MINUTE(int COLUMN_SETTINGS_SYNC_DURATION_MINUTE) {
        this.COLUMN_SETTINGS_SYNC_DURATION_MINUTE = COLUMN_SETTINGS_SYNC_DURATION_MINUTE;
    }

    public int getCOLUMN_SETTINGS_SYNC_INTERVAL() {
        return COLUMN_SETTINGS_SYNC_INTERVAL;
    }

    public void setCOLUMN_SETTINGS_SYNC_INTERVAL(int COLUMN_SETTINGS_SYNC_INTERVAL) {
        this.COLUMN_SETTINGS_SYNC_INTERVAL = COLUMN_SETTINGS_SYNC_INTERVAL;
    }

    public String getCOLUMN_SETTINGS_LAST_SYNC() {
        return COLUMN_SETTINGS_LAST_SYNC;
    }

    public void setCOLUMN_SETTINGS_LAST_SYNC(String COLUMN_SETTINGS_LAST_SYNC) {
        this.COLUMN_SETTINGS_LAST_SYNC = COLUMN_SETTINGS_LAST_SYNC;
    }
}
