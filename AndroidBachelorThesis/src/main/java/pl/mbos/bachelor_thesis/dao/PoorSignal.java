package pl.mbos.bachelor_thesis.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table POOR_SIGNAL.
 */
public class PoorSignal {

    private long userId;
    private int value;
    /** Not-null value. */
    private java.util.Date collectionDate;

    public PoorSignal() {
    }

    public PoorSignal(long userId, int value, java.util.Date collectionDate) {
        this.userId = userId;
        this.value = value;
        this.collectionDate = collectionDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    /** Not-null value. */
    public java.util.Date getCollectionDate() {
        return collectionDate;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setCollectionDate(java.util.Date collectionDate) {
        this.collectionDate = collectionDate;
    }

}
