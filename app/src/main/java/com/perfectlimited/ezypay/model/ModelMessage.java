package com.perfectlimited.ezypay.model;

/**
 * Created by akash on 7/19/2016.
 */
public class ModelMessage {
   private long COLUMN_MESSAGES_ID=0;
    private String COLUMN_MESSAGES_SUBJECT="";
    private String  COLUMN_MESSAGES_MESSAGE="" ;
    private String  COLUMN_MESSAGES_FROM ="";
    private String  COLUMN_MESSAGES_TO="";
    private String COLUMN_MESSAGES_DATE="";
    private int COLUMN_MESSAGES_IS_SEEN=0;

    public int getCOLUMN_MESSAGES_IS_SEEN() {
        return COLUMN_MESSAGES_IS_SEEN;
    }

    public void setCOLUMN_MESSAGES_IS_SEEN(int COLUMN_MESSAGES_IS_SEEN) {
        this.COLUMN_MESSAGES_IS_SEEN = COLUMN_MESSAGES_IS_SEEN;
    }

    public long getCOLUMN_MESSAGES_ID() {
        return COLUMN_MESSAGES_ID;
    }

    public void setCOLUMN_MESSAGES_ID(long COLUMN_MESSAGES_ID) {
        this.COLUMN_MESSAGES_ID = COLUMN_MESSAGES_ID;
    }

    public String getCOLUMN_MESSAGES_SUBJECT() {
        return COLUMN_MESSAGES_SUBJECT;
    }

    public void setCOLUMN_MESSAGES_SUBJECT(String COLUMN_MESSAGES_SUBJECT) {
        this.COLUMN_MESSAGES_SUBJECT = COLUMN_MESSAGES_SUBJECT;
    }

    public String getCOLUMN_MESSAGES_MESSAGE() {
        return COLUMN_MESSAGES_MESSAGE;
    }

    public void setCOLUMN_MESSAGES_MESSAGE(String COLUMN_MESSAGES_MESSAGE) {
        this.COLUMN_MESSAGES_MESSAGE = COLUMN_MESSAGES_MESSAGE;
    }

    public String getCOLUMN_MESSAGES_FROM() {
        return COLUMN_MESSAGES_FROM;
    }

    public void setCOLUMN_MESSAGES_FROM(String COLUMN_MESSAGES_FROM) {
        this.COLUMN_MESSAGES_FROM = COLUMN_MESSAGES_FROM;
    }

    public String getCOLUMN_MESSAGES_TO() {
        return COLUMN_MESSAGES_TO;
    }

    public void setCOLUMN_MESSAGES_TO(String COLUMN_MESSAGES_TO) {
        this.COLUMN_MESSAGES_TO = COLUMN_MESSAGES_TO;
    }

    public String getCOLUMN_MESSAGES_DATE() {
        return COLUMN_MESSAGES_DATE;
    }

    public void setCOLUMN_MESSAGES_DATE(String COLUMN_MESSAGES_DATE) {
        this.COLUMN_MESSAGES_DATE = COLUMN_MESSAGES_DATE;
    }
}
