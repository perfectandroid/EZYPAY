package com.perfectlimited.ezypay.model;

/**
 * Created by akash on 7/18/2016.
 */
public class ModelShareDoc {
    private   String COLUMN_DOCUMENT_DOCNO="";
    private   String COLUMN_DOCUMENT_TITLE="";
    private   String COLUMN_DOCUMENT_DESCRIPTION="";
    private   String COLUMN_DOCUMENT_CATEGORY="";
    private   int COLUMN_DOCUMENT_LOCKED=0;
    private   String COLUMN_DOCUMENT_UPLOADEDBY="";
    private   String COLUMN_DOCUMENT_UPLOADEDON ="";
    private int COLUMN_SHARED_DOCUMENT_IS_SEEN=0;

     private long COLUMN_SHARED_DOCUMENT_SLNO=0;
    private boolean is_setSeen;

    public boolean is_setSeen() {
        return is_setSeen;
    }

    public void setIs_setSeen(boolean is_setSeen) {
        this.is_setSeen = is_setSeen;
    }

    public long getCOLUMN_SHARED_DOCUMENT_SLNO() {
        return COLUMN_SHARED_DOCUMENT_SLNO;
    }

    public void setCOLUMN_SHARED_DOCUMENT_SLNO(long COLUMN_SHARED_DOCUMENT_SLNO) {
        this.COLUMN_SHARED_DOCUMENT_SLNO = COLUMN_SHARED_DOCUMENT_SLNO;
    }

    public int getCOLUMN_SHARED_DOCUMENT_IS_SEEN() {
        return COLUMN_SHARED_DOCUMENT_IS_SEEN;
    }

    public void setCOLUMN_SHARED_DOCUMENT_IS_SEEN(int COLUMN_SHARED_DOCUMENT_IS_SEEN) {
        this.COLUMN_SHARED_DOCUMENT_IS_SEEN = COLUMN_SHARED_DOCUMENT_IS_SEEN;
    }

    public String getCOLUMN_DOCUMENT_DOCNO() {
        return COLUMN_DOCUMENT_DOCNO;
    }

    public void setCOLUMN_DOCUMENT_DOCNO(String COLUMN_DOCUMENT_DOCNO) {
        this.COLUMN_DOCUMENT_DOCNO = COLUMN_DOCUMENT_DOCNO;
    }

    public String getCOLUMN_DOCUMENT_TITLE() {
        return COLUMN_DOCUMENT_TITLE;
    }

    public void setCOLUMN_DOCUMENT_TITLE(String COLUMN_DOCUMENT_TITLE) {
        this.COLUMN_DOCUMENT_TITLE = COLUMN_DOCUMENT_TITLE;
    }

    public String getCOLUMN_DOCUMENT_DESCRIPTION() {
        return COLUMN_DOCUMENT_DESCRIPTION;
    }

    public void setCOLUMN_DOCUMENT_DESCRIPTION(String COLUMN_DOCUMENT_DESCRIPTION) {
        this.COLUMN_DOCUMENT_DESCRIPTION = COLUMN_DOCUMENT_DESCRIPTION;
    }

    public String getCOLUMN_DOCUMENT_CATEGORY() {
        return COLUMN_DOCUMENT_CATEGORY;
    }

    public void setCOLUMN_DOCUMENT_CATEGORY(String COLUMN_DOCUMENT_CATEGORY) {
        this.COLUMN_DOCUMENT_CATEGORY = COLUMN_DOCUMENT_CATEGORY;
    }

    public int getCOLUMN_DOCUMENT_LOCKED() {
        return COLUMN_DOCUMENT_LOCKED;
    }

    public void setCOLUMN_DOCUMENT_LOCKED(int COLUMN_DOCUMENT_LOCKED) {
        this.COLUMN_DOCUMENT_LOCKED = COLUMN_DOCUMENT_LOCKED;
    }

    public String getCOLUMN_DOCUMENT_UPLOADEDBY() {
        return COLUMN_DOCUMENT_UPLOADEDBY;
    }

    public void setCOLUMN_DOCUMENT_UPLOADEDBY(String COLUMN_DOCUMENT_UPLOADEDBY) {
        this.COLUMN_DOCUMENT_UPLOADEDBY = COLUMN_DOCUMENT_UPLOADEDBY;
    }

    public String getCOLUMN_DOCUMENT_UPLOADEDON() {
        return COLUMN_DOCUMENT_UPLOADEDON;
    }

    public void setCOLUMN_DOCUMENT_UPLOADEDON(String COLUMN_DOCUMENT_UPLOADEDON) {
        this.COLUMN_DOCUMENT_UPLOADEDON = COLUMN_DOCUMENT_UPLOADEDON;
    }
}
